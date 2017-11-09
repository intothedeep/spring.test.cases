package com.free.test.file.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.free.test.file.dao.CommonDao;
import com.free.test.file.dao.FileDao;
import com.free.test.file.model.FileDto;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void store(FileDto fileDto) {
		sqlSession.getMapper(FileDao.class).store(fileDto);
	}

	@Override
	public List<FileDto> list(int bcode) {
		List<FileDto> list = sqlSession.getMapper(FileDao.class).list(bcode);
		return list;
	}

	@Override
	public FileDto getFileInfo(int fileSeq) {
		return sqlSession.getMapper(FileDao.class).getFileInfo(fileSeq);
	}

	@Override
	public void deleteUpdatedToOne(int fileSeq) {
		sqlSession.getMapper(FileDao.class).deleteUpdatedToOne(fileSeq);
	}

	@Override
	public FileDto modify(int originalFileSeq, FileDto infoForFileDtoBeingModified) {
		CommonDao commonDao = sqlSession.getMapper(CommonDao.class);
		FileDao fileDao = sqlSession.getMapper(FileDao.class);
		
		//수정 전 파일 기록의 deleted항목을 1로 바꾼다. 
		fileDao.deleteUpdatedToOne(originalFileSeq);
		
		//수정 후 데이터의 deleted 항복을 2로 바꾼다 // 이 뜻은 이 파일의 원본 파일은 삭제되고 새롭게 모디 파이 된 데이터가 들어갔다는 의미
		infoForFileDtoBeingModified.setDeleted(2);
		infoForFileDtoBeingModified.setOriginalFileSeq(originalFileSeq);
		//수정 용 데이터를 새로운 항에 삽입한다.
		fileDao.store(infoForFileDtoBeingModified);
		
		//업데이트 한 파일 정보를 가져와서 리턴
		FileDto updatedFileDto = fileDao.getFileInfo(infoForFileDtoBeingModified.getFileSeq());
		return updatedFileDto;
		
	}
}

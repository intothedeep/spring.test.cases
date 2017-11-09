package com.free.test.firebase.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.free.test.firebase.dao.FileDao;
import com.free.test.firebase.model.FileDto;

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
}

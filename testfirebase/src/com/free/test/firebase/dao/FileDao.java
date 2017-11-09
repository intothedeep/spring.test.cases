package com.free.test.firebase.dao;

import java.util.List;

import com.free.test.firebase.model.FileDto;

public interface FileDao {
	void store(FileDto fileDto);
	List<FileDto> list(int bcode);
	FileDto getFileInfo(int fileSeq);
	
	void deleteUpdatedToOne(int fileSeq);

}

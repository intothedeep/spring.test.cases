package com.free.test.file.dao;

import java.util.List;

import com.free.test.file.model.FileDto;

public interface FileDao {
	void store(FileDto fileDto);
	List<FileDto> list(int bcode);
	FileDto getFileInfo(int fileSeq);
	
	void deleteUpdatedToOne(int fileSeq);
}

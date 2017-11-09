package com.free.test.file.service;

import java.util.List;

import com.free.test.file.model.FileDto;

public interface FileService {
	void store(FileDto fileDto);
	
	List<FileDto> list(int bcode);
	FileDto getFileInfo(int fileSeq);
	
	void deleteUpdatedToOne(int fileSeq);
	
	FileDto modify(int originalFileSeq, FileDto infoForFileDtoBeingModified);
}

package com.free.test.file.model;

public class FileDto extends MemberDto {
	private int fileSeq; // 파일등록 번호, 고유키
	private String originalFileName; // 원본 이름
	private String storedFileName; // 저장되는 이름
	private long fileSize; // 파일크기
	private String uploadDate; // 업로드 날짜, sysdate
	private int deleted; //삭제 여부 0 true, 1 false
	//private String email; // 파일을 업로드 한 사람 식별자 , id, 사번 등등 될 수 있음
	private int bcode; // 파일을 업로드한 게시판 종류 구분 번호
	private String storedPath; //원본 저장 된 위치
	
	private String thumbStoredFileName; // 썸네일 파일이
	private String thumbStoredPath; //썸네일 저장 위치
	private int originalFileSeq; // 리스트 불러 올 때 이것을 기준으로 불러온다. 수정 시 fileSeq가 뒤로 밀리는 것을 잡기 위해서
	
	
	public int getOriginalFileSeq() {
		return originalFileSeq;
	}
	public void setOriginalFileSeq(int originalFileSeq) {
		this.originalFileSeq = originalFileSeq;
	}
	public String getThumbStoredFileName() {
		return thumbStoredFileName;
	}
	public void setThumbStoredFileName(String thumbStoredFileName) {
		this.thumbStoredFileName = thumbStoredFileName;
	}
	public String getThumbStoredPath() {
		return thumbStoredPath;
	}
	public void setThumbStoredPath(String thumbStoredPath) {
		this.thumbStoredPath = thumbStoredPath;
	}
	public String getStoredPath() {
		return storedPath;
	}
	public void setStoredPath(String storedPath) {
		this.storedPath = storedPath;
	}
	public int getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getStoredFileName() {
		return storedFileName;
	}
	public void setStoredFileName(String storedFileName) {
		this.storedFileName = storedFileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public int getBcode() {
		return bcode;
	}
	public void setBcode(int bcode) {
		this.bcode = bcode;
	}
	
	
}

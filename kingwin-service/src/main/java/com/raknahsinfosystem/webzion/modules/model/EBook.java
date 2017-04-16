package com.raknahsinfosystem.webzion.modules.model;

public class EBook {

	private Integer Id;
	private String eBookId;
	private String eBookName;
	private String eBookType;
	private String syllabus;
	private String branch;
	private byte[] file;
	private String fileBase64;
	private String origFileName;
	private String language;
	//private String description;
	private String dateToShow;
	private Long createdDate;
	private Long updatedDate;
	
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String geteBookId() {
		return eBookId;
	}
	public void seteBookId(String eBookId) {
		this.eBookId = eBookId;
	}
	public String geteBookName() {
		return eBookName;
	}
	public void seteBookName(String eBookName) {
		this.eBookName = eBookName;
	}
	public String geteBookType() {
		return eBookType;
	}
	public void seteBookType(String eBookType) {
		this.eBookType = eBookType;
	}
	public String getSyllabus() {
		return syllabus;
	}
	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getFileBase64() {
		return fileBase64;
	}
	public void setFileBase64(String fileBase64) {
		this.fileBase64 = fileBase64;
	}
	public String getOrigFileName() {
		return origFileName;
	}
	public void setOrigFileName(String origFileName) {
		this.origFileName = origFileName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDateToShow() {
		return dateToShow;
	}
	public void setDateToShow(String dateToShow) {
		this.dateToShow = dateToShow;
	}
	public Long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}
	public Long getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Long updatedDate) {
		this.updatedDate = updatedDate;
	}
		
}

package com.raknahsinfosystem.webzion.modules.model;

public class Material {

	private Integer Id;
	private Integer materialId;
	private String materialName;
	private String syllabus;
	private String branch;
	private byte[] file;
	private String fileBase64;
	private String language;
	//private String description;
	private long dateToShow;
	private long updatedDate;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	/*public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}*/
	public long getDateToShow() {
		return dateToShow;
	}
	public void setDateToShow(long dateToShow) {
		this.dateToShow = dateToShow;
	}
	public long getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(long updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	
	
		
}

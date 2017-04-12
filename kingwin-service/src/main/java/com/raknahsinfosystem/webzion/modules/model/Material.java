package com.raknahsinfosystem.webzion.modules.model;

public class Material {

	private Integer Id;
	private Integer materialId;
	private String materialName;
	private String syllabus;
	private String branch;
	private byte[] file;
	private String fileBase64;
	private String materialType;
	private String description;
	private String createdDate;
	private String updatedDate;
	
	
	public Integer getmaterialId() {
		return materialId;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getmaterialType() {
		return materialType;
	}
	public void setmaterialType(String materialType) {
		this.materialType = materialType;
	}
	public void setmaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public String getmaterialName() {
		return materialName;
	}
	public void setmaterialName(String materialName) {
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
		
}

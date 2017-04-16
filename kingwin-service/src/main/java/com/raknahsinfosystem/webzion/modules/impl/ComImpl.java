package com.raknahsinfosystem.webzion.modules.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.raknahsinfosystem.webzion.modules.model.Branch;
import com.raknahsinfosystem.webzion.modules.model.EBook;
import com.raknahsinfosystem.webzion.util.ConnectionDAO;

public class ComImpl {
	
	public JSONArray getBranches(){
		Connection conn = null;
		PreparedStatement preparedStmt=null;
		JSONArray branchArr=new JSONArray();
		try{
			conn =ConnectionDAO.getConnection("mysql");
			String branchQry="SELECT DISTINCT BRANCH FROM BRANCH";
			preparedStmt = conn.prepareStatement(branchQry);
			ResultSet resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				branchArr.put(resultSet.getString("BRANCH"));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return branchArr;
	}
	
	@SuppressWarnings("deprecation")
	public JSONArray getEBooksImpl(String eBookType){
		Connection conn = null;
		PreparedStatement preparedStmt=null;
		JSONArray eBookList=new JSONArray();
		try{
			conn =ConnectionDAO.getConnection("mysql");
			String eBookQry="SELECT * FROM EBOOK WHERE eBookType=?";
			preparedStmt = conn.prepareStatement(eBookQry);
			preparedStmt.setString(1,eBookType);
			ResultSet resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				JSONObject tempEBook=new JSONObject();
				tempEBook.put("id",resultSet.getInt("id"));
				tempEBook.put("eBookId",resultSet.getString("eBookId"));
				tempEBook.put("eBookName",resultSet.getString("eBookName"));
				tempEBook.put("eBookType",resultSet.getString("eBookType"));
				tempEBook.put("syllabus",resultSet.getString("syllabus"));
				tempEBook.put("branch",resultSet.getString("branch"));
				InputStream fileInpStream=resultSet.getBinaryStream("file");
				tempEBook.put("fileBase64",IOUtils.toString(fileInpStream, StandardCharsets.UTF_8));
				tempEBook.put("origFileName",resultSet.getString("origFileName"));
				if(!eBookType.equals("syllabus")){
					tempEBook.put("language",resultSet.getString("language"));
					java.sql.Date dateToShow = resultSet.getDate("dateToShow");
					tempEBook.put("dateToShow",(1900+dateToShow.getYear())+"-"+dateToShow.getMonth()+"-"+dateToShow.getDate());
				}
				eBookList.put(tempEBook);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return eBookList;
	}
	
	public Integer uploadEBookImpl(EBook eBook){
		Integer genId=null;
		Connection conn = null;
		PreparedStatement preparedStmt=null;
		try{
			String insertEBookQry="INSERT INTO EBOOK";
			conn =ConnectionDAO.getConnection("mysql");
			if(eBook.geteBookType().equals("syllabus")){
				insertEBookQry+="(eBookId,eBookName,eBookType,syllabus,branch,file,origFileName) values(?,?,?,?,?,?,?)";
			}
			else{
				insertEBookQry+="(eBookId,eBookName,eBookType,syllabus,branch,file,origFileName,language,dateToShow) values(?,?,?,?,?,?,?,?,?)";
			}
				preparedStmt = conn.prepareStatement(insertEBookQry,Statement.RETURN_GENERATED_KEYS);
				preparedStmt.setString(1, eBook.geteBookId());
				preparedStmt.setString(2, eBook.geteBookName());
				preparedStmt.setString(3, eBook.geteBookType());
				preparedStmt.setString(4, eBook.getSyllabus());
				preparedStmt.setString(5, eBook.getBranch());
				preparedStmt.setBinaryStream(6, new ByteArrayInputStream(eBook.getFileBase64().getBytes(StandardCharsets.UTF_8)));
				preparedStmt.setString(7, eBook.getOrigFileName());
				if(!eBook.geteBookType().equals("syllabus")){
					preparedStmt.setString(8, eBook.getLanguage());
					String dateToShow=eBook.getDateToShow();
					Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateToShow);
					preparedStmt.setDate(9, new java.sql.Date(utilDate.getTime()));
				}
			
			int execStatus = preparedStmt.executeUpdate();
			if(execStatus==1){
				ResultSet rs = preparedStmt.getGeneratedKeys();
				if(rs.next()){
					genId=rs.getInt(1);
				}
			}
			//status=true;
			/*while(resultSet.next()){
				branchArr.put(resultSet.getString("place"));
			}*/
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return genId;
	}
	
	public boolean updateEBookImpl(EBook eBook){
		boolean status=false;
		Connection conn = null;
		PreparedStatement preparedStmt=null;
		try{
			String createEBookQry="UPDATE EBOOK SET ";
			conn =ConnectionDAO.getConnection("mysql");
			if(eBook.geteBookType().equals("syllabus")){
				createEBookQry+="eBookId=?,eBookName=?,eBookType=?,syllabus=?,branch=?,file=?,origFileName=? where id=?";
			}
			else{
				createEBookQry+="eBookId=?,eBookName=?,eBookType=?,syllabus=?,branch=?,file=?,origFileName=?,language=?,dateToShow=? where id=?";
			}
				preparedStmt = conn.prepareStatement(createEBookQry);
				preparedStmt.setString(1, eBook.geteBookId());
				preparedStmt.setString(2, eBook.geteBookName());
				preparedStmt.setString(3, eBook.geteBookType());
				preparedStmt.setString(4, eBook.getSyllabus());
				preparedStmt.setString(5, eBook.getBranch());
				preparedStmt.setBinaryStream(6, new ByteArrayInputStream(eBook.getFileBase64().getBytes(StandardCharsets.UTF_8)));
				preparedStmt.setString(7, eBook.getOrigFileName());
				int insertCount=8;
				if(!eBook.geteBookType().equals("syllabus")){
					preparedStmt.setString(insertCount, eBook.getLanguage());
					insertCount++;
					String dateToShow=eBook.getDateToShow();
					Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateToShow);
					preparedStmt.setDate(insertCount, new java.sql.Date(utilDate.getTime()));
					insertCount++;
				}
				preparedStmt.setInt(insertCount, eBook.getId());
			
			int resultSet = preparedStmt.executeUpdate();
			status=true;
			/*while(resultSet.next()){
				branchArr.put(resultSet.getString("place"));
			}*/
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return status;
	}
	
	public boolean deleteEBookImpl(int id){
		boolean status=false;
		Connection conn = null;
		PreparedStatement preparedStmt=null;
		try{
			String deleteEBookQry="DELETE FROM EBOOK WHERE ID=? ";
			conn =ConnectionDAO.getConnection("mysql");
			preparedStmt = conn.prepareStatement(deleteEBookQry);
			preparedStmt.setInt(1, id);
			int resultSet = preparedStmt.executeUpdate();
			status=true;
			/*while(resultSet.next()){
				branchArr.put(resultSet.getString("place"));
			}*/
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return status;
	}
	
	public boolean updatePasswordImpl(Branch branch){
		boolean status=false;
		Connection conn = null;
		PreparedStatement stmtToValidate=null;
		PreparedStatement stmtToUpdate=null;
		try{
			conn =ConnectionDAO.getConnection("mysql");
			String getPasswordQry="SELECT * FROM BRANCH WHERE SYLLABUS=? AND BRANCH=? AND PASSWORD=? LIMIT 1";
			stmtToValidate = conn.prepareStatement(getPasswordQry);
			stmtToValidate.setString(1, branch.getSyllabus());
			stmtToValidate.setString(2, branch.getBranch());
			stmtToValidate.setString(3, branch.getOldPassword());
			ResultSet resultSet = stmtToValidate.executeQuery();
			boolean validStatus=resultSet.next();
			if(!validStatus){
				return false;
			}
			// snippet to update branch with new password
			String updatePassQry="UPDATE BRANCH SET PASSWORD=? WHERE SYLLABUS=? AND BRANCH=?";
			stmtToUpdate=conn.prepareStatement(updatePassQry);
			stmtToUpdate.setString(1, branch.getNewPassword());
			stmtToUpdate.setString(2, branch.getSyllabus());
			stmtToUpdate.setString(3, branch.getBranch());
			int updateStatus = stmtToUpdate.executeUpdate();
			if(updateStatus==1){
				status=true;
			}
			/*while(resultSet.next()){
				branchArr.put(resultSet.getString("place"));
			}*/
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return status;
	}
}

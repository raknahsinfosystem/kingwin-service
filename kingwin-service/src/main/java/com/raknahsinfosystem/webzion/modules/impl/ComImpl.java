package com.raknahsinfosystem.webzion.modules.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.raknahsinfosystem.webzion.modules.model.EBook;
import com.raknahsinfosystem.webzion.util.ConnectionDAO;

public class ComImpl {
	
	public JSONArray getBranches(){
		Connection conn = null;
		PreparedStatement preparedStmt=null;
		JSONArray branchArr=new JSONArray();
		try{
			conn =ConnectionDAO.getConnection("mysql");
			String branchQry="SELECT DISTINCT place FROM BRANCH";
			preparedStmt = conn.prepareStatement(branchQry);
			ResultSet resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				branchArr.put(resultSet.getString("place"));
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
	
	public boolean uploadEBookImpl(EBook eBook){
		boolean status=false;
		Connection conn = null;
		PreparedStatement preparedStmt=null;
		try{
			String createEBookQry="INSERT INTO EBOOK";
			conn =ConnectionDAO.getConnection("mysql");
			if(eBook.geteBookType().equals("syllabus")){
				createEBookQry+="(eBookId,eBookName,eBookType,syllabus,branch,file,origFileName) values(?,?,?,?,?,?,?)";
				preparedStmt = conn.prepareStatement(createEBookQry);
				preparedStmt.setString(1, eBook.geteBookId());
				preparedStmt.setString(2, eBook.geteBookName());
				preparedStmt.setString(3, eBook.geteBookType());
				preparedStmt.setString(4, eBook.getSyllabus());
				preparedStmt.setString(5, eBook.getBranch());
				preparedStmt.setBinaryStream(6, new ByteArrayInputStream(eBook.getFileBase64().getBytes(StandardCharsets.UTF_8)));
				preparedStmt.setString(7, eBook.getOrigFileName());
			}
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
	
	public boolean updateEBookImpl(EBook eBook){
		boolean status=false;
		Connection conn = null;
		PreparedStatement preparedStmt=null;
		try{
			String createEBookQry="UPDATE EBOOK SET ";
			conn =ConnectionDAO.getConnection("mysql");
			if(eBook.geteBookType().equals("syllabus")){
				createEBookQry+="eBookId=?,eBookName=?,eBookType=?,syllabus=?,branch=?,file=?,origFileName=? where id=?";
				preparedStmt = conn.prepareStatement(createEBookQry);
				preparedStmt.setString(1, eBook.geteBookId());
				preparedStmt.setString(2, eBook.geteBookName());
				preparedStmt.setString(3, eBook.geteBookType());
				preparedStmt.setString(4, eBook.getSyllabus());
				preparedStmt.setString(5, eBook.getBranch());
				preparedStmt.setBinaryStream(6, new ByteArrayInputStream(eBook.getFileBase64().getBytes(StandardCharsets.UTF_8)));
				preparedStmt.setString(7, eBook.getOrigFileName());
				preparedStmt.setInt(8, eBook.getId());
			}
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
}

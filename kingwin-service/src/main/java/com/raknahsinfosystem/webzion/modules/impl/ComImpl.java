package com.raknahsinfosystem.webzion.modules.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.raknahsinfosystem.webzion.modules.model.Branch;
import com.raknahsinfosystem.webzion.modules.model.EBook;
import com.raknahsinfosystem.webzion.util.ConnectionDAO;
import com.raknahsinfosystem.webzion.util.EncryptUtils;

public class ComImpl {
	
	@Autowired
	RequestAttributes requestAttributes;
	
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
			ComImpl comImpl=new ComImpl();
			JSONObject userAuthDetails=comImpl.getUserAuthDetails();
			String usertype=userAuthDetails.getString("userType");
			conn =ConnectionDAO.getConnection("mysql");
			String eBookQry="SELECT id,eBookId,eBookName,eBookType,syllabus,branch,origFileName,language,dateToShow FROM EBOOK WHERE eBookType=?";
			if(usertype.equals("student")){
				eBookQry+=" AND dateToShow=?";
			}
			preparedStmt = conn.prepareStatement(eBookQry);
			preparedStmt.setString(1,eBookType);
			if(usertype.equals("student")){
				java.sql.Date currDate=new java.sql.Date((new Date()).getTime());
				preparedStmt.setDate(2,currDate);
			}
			ResultSet resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				JSONObject tempEBook=new JSONObject();
				tempEBook.put("id",resultSet.getInt("id"));
				tempEBook.put("eBookId",resultSet.getString("eBookId"));
				tempEBook.put("eBookName",resultSet.getString("eBookName"));
				tempEBook.put("eBookType",resultSet.getString("eBookType"));
				tempEBook.put("syllabus",resultSet.getString("syllabus"));
				tempEBook.put("branch",resultSet.getString("branch"));
				//InputStream fileInpStream=resultSet.getBinaryStream("file");
				//tempEBook.put("fileBase64",IOUtils.toString(fileInpStream, StandardCharsets.UTF_8));
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
			String fileQryToAdd="";
			boolean fileStatus=false;
			if(eBook.getFileBase64()!=null){
				fileStatus=true;
			}
			if(eBook.geteBookType().equals("syllabus")){
				createEBookQry+="eBookId=?,eBookName=?,eBookType=?,syllabus=?,branch=?,"+(fileStatus ? "file=?," : "")+"origFileName=? where id=?";
			}
			else{
				createEBookQry+="eBookId=?,eBookName=?,eBookType=?,syllabus=?,branch=?,"+(fileStatus ? "file=?," : "")+"origFileName=?,language=?,dateToShow=? where id=?";
			}
			int insertCount=0;
				preparedStmt = conn.prepareStatement(createEBookQry);
				preparedStmt.setString(1, eBook.geteBookId());
				preparedStmt.setString(2, eBook.geteBookName());
				preparedStmt.setString(3, eBook.geteBookType());
				preparedStmt.setString(4, eBook.getSyllabus());
				preparedStmt.setString(5, eBook.getBranch());
				insertCount=6;
				if(fileStatus){
					preparedStmt.setBinaryStream(insertCount, new ByteArrayInputStream(eBook.getFileBase64().getBytes(StandardCharsets.UTF_8)));
					insertCount++;
				}
				
				preparedStmt.setString(insertCount, eBook.getOrigFileName());
				insertCount++;
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
	
	public StringBuffer getEbookImpl(int id){
		StringBuffer fileStr=null;
		Connection conn = null;
		PreparedStatement preparedStmt=null;
		try{
			String getEBookQry="SELECT FILE FROM EBOOK WHERE ID=? ";
			conn =ConnectionDAO.getConnection("mysql");
			preparedStmt = conn.prepareStatement(getEBookQry);
			preparedStmt.setInt(1, id);
			ResultSet resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				InputStream fileInpStream=resultSet.getBinaryStream("file");
				fileStr=new StringBuffer(IOUtils.toString(fileInpStream, StandardCharsets.UTF_8));
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
		
		return fileStr;
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
	public JSONObject getBranchByAuth(String branchName,String password){
		Connection conn = null;
		PreparedStatement preparedStmt=null;
		JSONObject branch=null;
		try{
			conn =ConnectionDAO.getConnection("mysql");
			String branchQry="SELECT * FROM BRANCH WHERE BRANCH=? AND PASSWORD=?";
			preparedStmt = conn.prepareStatement(branchQry);
			preparedStmt.setString(1, branchName);
			preparedStmt.setString(2, password);
			ResultSet resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				branch=new JSONObject();
				branch.put("syllabus",resultSet.getString("syllabus"));
				branch.put("branch",resultSet.getString("branch"));
				branch.put("userType",resultSet.getString("userType"));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return branch;
	}
	public static Boolean getAppWorkStatus(){
		boolean status=false;
		JSONObject options=new JSONObject();
		options.put("url","http://sampleproject1.getsandbox.com/kingwin/status");
		options.put("data", "");
		options.put("methodType","GET");
		String res=doExtServieCall(options);
		String statusJSON=(new JSONObject(res)).getString("status");
		if(statusJSON.equals("1")){
			status=true;
		}
		return status;
	}
	
	@SuppressWarnings("deprecation")
	public static String doExtServieCall(JSONObject options){
		   String url=options.getString("url");
		   String data=options.getString("data");
		   String methodType=options.getString("methodType");
		   StringBuffer output=new StringBuffer();
		   BufferedReader rd=null;
		   try{
			   if(methodType.equalsIgnoreCase("POST")){
				   HttpClient client = HttpClientBuilder.create().build();
				   HttpPost post = new HttpPost(url);
					StringEntity inputData =new StringEntity(data);
					post.addHeader("content-type", "application/json");
					post.setEntity(inputData);
					HttpResponse httpResponse = client.execute(post);
					rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			   }
			   else if(methodType.equalsIgnoreCase("GET")){
				   	HttpGet getReq = new HttpGet(url);
				   	getReq.addHeader("Content-Type" , "application/json");
					HttpClient client = new DefaultHttpClient();
					HttpResponse response = client.execute(getReq);
					rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			   }
			   String inputLine = rd.readLine();
				while (inputLine != null) {
					output.append(inputLine);
					output.append(System.lineSeparator());
					inputLine = rd.readLine();
				}
		   }
		   catch(UnsupportedEncodingException ue){
			   ue.printStackTrace();
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }
		   return output.toString();
	   }
	
	public  JSONObject getUserAuthDetails() throws UnsupportedEncodingException{
		JSONObject authObj=null;
		requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			if (request != null) {
				String userToken=request.getHeader("userToken");
				userToken= java.net.URLDecoder.decode(userToken, "UTF-8");
				userToken=EncryptUtils.getDecryptedMesssage(userToken);
				if(userToken!=null){
					authObj=new JSONObject(userToken);
				}
			}
		}
		return authObj;
	}
}

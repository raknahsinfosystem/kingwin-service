package com.raknahsinfosystem.webzion.modules.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;

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
}

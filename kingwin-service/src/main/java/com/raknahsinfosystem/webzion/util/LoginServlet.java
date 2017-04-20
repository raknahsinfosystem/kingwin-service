package com.raknahsinfosystem.webzion.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.raknahsinfosystem.webzion.modules.impl.ComImpl;

/**
 * Servlet implementation class Login
 */
//@WebServlet(description = "login", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(LoginServlet.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		System.out.println(userName);
		System.out.println(password);
		System.out.println(request.getParameter("input"));
		System.out.println(request.getParameter("data"));
		JSONObject respObj=new JSONObject();
		try {
			ComImpl comImpl=new ComImpl();
			JSONObject branchObj=comImpl.getBranchByAuth(userName, password);
			//PrintWriter out=response.getWriter();
			if(branchObj==null){
				respObj.put("status", "failure");
				out.print(respObj.toString());
				//respObj.put("userType", branchObj.get("userType"));
				//respObj.put("userToken", branchObj.);
			}
			else{
				respObj.put("status", "failure");
				respObj.put("userType", branchObj.get("userType"));
				respObj.put("userToken", branchObj);
				out.print(respObj.toString());
			}
			
		}catch (Exception e) {
			logger.error("Exception occred - " + e);
			e.printStackTrace();
		}

	}

}

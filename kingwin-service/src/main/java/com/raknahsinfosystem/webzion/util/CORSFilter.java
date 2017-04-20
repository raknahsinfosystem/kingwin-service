package com.raknahsinfosystem.webzion.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.filter.GenericFilterBean;

import com.raknahsinfosystem.webzion.modules.impl.ComImpl;

import java.io.IOException;

public class CORSFilter extends GenericFilterBean {
	
	public CORSFilter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse resp = (HttpServletResponse) servletResponse;
    resp.addHeader("Access-Control-Allow-Origin","*");
    resp.addHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,HEAD");
    resp.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With,userToken, Content-Type");
    if(!ComImpl.getAppWorkStatus()){
    	resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    }
    // Just ACCEPT and REPLY OK if OPTIONS
    if ( request.getMethod().equals("OPTIONS") ) {
        resp.setStatus(HttpServletResponse.SC_OK);
        return;
    }
    else{
    	//HttpServletRequest httpReq=(HttpServletRequest)servletRequest;
    	String path=request.getServletPath();
    	if( path.contains("/rest/api/") && !path.contains("/rest/api/login")){
    		ComImpl comImpl=new ComImpl();
    		JSONObject getUserAuthDetails=comImpl.getUserAuthDetails();
    		if(getUserAuthDetails==null){
    			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    		}
    		
    		
    	}
    }
    chain.doFilter(request, servletResponse);
}

 @Override
public void destroy() {}
}
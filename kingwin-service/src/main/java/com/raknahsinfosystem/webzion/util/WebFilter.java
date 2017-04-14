/*package com.raknahsinfosystem.webzion.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import com.apptium.webconsole.admin.AuthenticationHandler;
import com.util.AppConstants;
import com.util.Utility;

*//**
 * A generic filter for security. I will check token present in the header.
 * 
 * @author Apptium
 *
 *//*
public class WebFilter extends GenericFilterBean {

	private String AUTHENTICATE_USER_KEY = System.getenv("AUTHENTICATE_USER_KEY");
	private static final Log logger = LogFactory.getLog(WebFilter.class);
	private String PLATFORM_AUTHN_USER_KEY = AppConstants.PLATFORM_AUTHN_USER_KEY;
	private String PLATFORM_AUTHZ_USER_RESOURCES = AppConstants.PLATFORM_AUTHZ_USER_RESOURCES;
	private String PLATFORM_AUTHZ_USER_ROLES = AppConstants.PLATFORM_AUTHZ_USER_ROLES;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		if(session != null){
			logger.debug("WebFilter is entered with platform session " +session.getAttribute(PLATFORM_AUTHN_USER_KEY) + " to " + request.getRequestURI());
			Object userName = session.getAttribute(PLATFORM_AUTHN_USER_KEY);
			if (userName == null) {
				userName = request.getHeader(AUTHENTICATE_USER_KEY);
				if(session.getAttribute(AUTHENTICATE_USER_KEY) != null){
					userName = session.getAttribute(AUTHENTICATE_USER_KEY);
				}else if(request.getAttribute(AUTHENTICATE_USER_KEY) != null){
					userName = request.getAttribute(AUTHENTICATE_USER_KEY);
				}else if(request.getParameter(AUTHENTICATE_USER_KEY) != null){
					userName = request.getParameter(AUTHENTICATE_USER_KEY);
				}
				if(userName != null){
					String authNUserName = userName.toString();
					external service call to get find user object starts  
					// user and roles to be get from single call (temp fix)
					JSONObject userInputOpt=new JSONObject();
					String userInputURL=System.getenv("ENDPOINT_APP_SERVICE_URL")+"rest/api/user/findUser";
					JSONObject userData=new JSONObject();
					userData.put("userName", authNUserName);
					userInputOpt.put("url", userInputURL);
					userInputOpt.put("data", userData.toString());
					userInputOpt.put("methodType", "POST");
					String userOutputData=Utility.doExtServieCall(userInputOpt);
					external service call to get find user object ends
					JSONObject userOutputObj = null;
					JSONArray userOutPutArray = new JSONArray(userOutputData);
					if(userOutPutArray !=null && userOutPutArray.length()>0){
						userOutputObj=(JSONObject) (new JSONArray(userOutputData)).get(0);
					}
					else
					{
						((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization");
						return;
					}
					session.setAttribute(PLATFORM_AUTHN_USER_KEY, authNUserName); 
					String ep_author=userOutputObj.getString("first_Name")+" "+ ((userOutputObj.get("last_Name") != null) ? userOutputObj.getString("last_Name") : "");
					session.setAttribute("ep-author", ep_author);
					JSONObject roles = authenticationHandler.getAuthorization(authNUserName,"roles");
					if(roles !=null && roles.length() > 0){
						session.setAttribute(PLATFORM_AUTHZ_USER_ROLES, roles.toString());
					}else{
						((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization");
					}
					
					JSONObject resources = authenticationHandler.getAuthorization(authNUserName,"resources");
					if(resources !=null && resources.length() > 0){
						session.setAttribute(PLATFORM_AUTHZ_USER_RESOURCES, resources.toString());
					}else{
						((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization");
					}
					try{
						//authorize page level security
						String pathInfo = request.getRequestURI();
						if(pathInfo !=null && pathInfo.contains(".jsp")){
							String moduleName = pathInfo.substring(pathInfo.lastIndexOf('/') + 1, pathInfo.indexOf(".jsp"));
							boolean isAuthorized = authenticationHandler.validatePageLeve(moduleName,session.getAttribute(PLATFORM_AUTHZ_USER_RESOURCES));
							if(!isAuthorized){
								String loginURI = request.getContextPath();
								String resource = authenticationHandler.getFirstResource(session.getAttribute(PLATFORM_AUTHZ_USER_RESOURCES)).trim();
								response.sendRedirect(loginURI+"/default/pages/"+resource+".jsp");
							}
						}
						if(resources !=null){
							//filter
							SecurityContextHolder.getContext().setAuthentication(getAuthentication(userName.toString(),resources));
							response.setHeader("Access-Control-Allow-Origin", "*");
							response.setHeader("Access-Control-Allow-Methods", "GET, POST");
							response.setHeader("Access-Control-Allow-Headers", "Content-Type");
							response.addHeader("Access-Control-Allow-Headers", "ep-user");
							response.addHeader("Access-Control-Allow-Headers", "accessedtimezone");
							response.setHeader("Access-Control-Max-Age", "86400");
						    //Tell the browser what requests we allow.
							response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
							filterChain.doFilter(req, res);
						}
					} catch (Exception e) {
						((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authentication");
					}
				}else{
					String loginURI = request.getContextPath();
					response.sendRedirect(loginURI+"/default/pages/login.jsp");
					//((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authentication");
				}
			} else {
				JSONObject resources = authenticationHandler.authorization(session.getAttribute(PLATFORM_AUTHZ_USER_RESOURCES));
				//
				
				//
				if(resources == null){
					JSONObject resourcesObj = authenticationHandler.getAuthorization(userName.toString(),"resources");
					if(resourcesObj !=null && resourcesObj.length() > 0){
						session.setAttribute(PLATFORM_AUTHZ_USER_RESOURCES, resourcesObj.toString());
						resources = resourcesObj;
					}
				}
				if(resources !=null){
					try{
						//authorize page level security
						String pathInfo = request.getRequestURI();
						if(pathInfo !=null && pathInfo.contains(".jsp")){
							String moduleName = pathInfo.substring(pathInfo.lastIndexOf('/') + 1, pathInfo.indexOf(".jsp"));
							boolean isAuthorized = authenticationHandler.validatePageLeve(moduleName,session.getAttribute(PLATFORM_AUTHZ_USER_RESOURCES));
							if(!isAuthorized){
								throw new Exception();
							}
						}
						//filter
						SecurityContextHolder.getContext().setAuthentication(getAuthentication(userName.toString(),resources));
						response.setHeader("Access-Control-Allow-Origin", "*");
						response.setHeader("Access-Control-Allow-Methods", "GET, POST");
						response.setHeader("Access-Control-Allow-Headers", "Content-Type");
						response.addHeader("Access-Control-Allow-Headers", "ep-user");
						response.addHeader("Access-Control-Allow-Headers", "accessedtimezone");
						response.setHeader("Access-Control-Max-Age", "86400");
					    //Tell the browser what requests we allow.
						response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
						filterChain.doFilter(req, res);
					} catch (Exception e) {
						((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization");
					}
				}else{
					((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization");
				}
			}
		}else{
			String loginURI = request.getContextPath();
			response.sendRedirect(loginURI+"/default/pages/login.jsp");
			//((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authentication");
		}
	}

	*//**
	 * Method for creating Authentication for Spring Security Context Holder
	 * <!--from JWT claims-->
	 * @param resources 
	 * 
	 * @param claims
	 * @return
	 *//*
	public Authentication getAuthentication(String userName, JSONObject resources) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		//List<String> roles = new ArrayList<String>();
		Iterator<?> keys = resources.keys();
		while( keys.hasNext() ) {
			String key = (String)keys.next();
			if ( resources.get(key) != null) {
				authorities.add(new SimpleGrantedAuthority(resources.get(key).toString()));
			}
		}
		User principal = new User(userName, "", authorities);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				principal, "", authorities);
		return usernamePasswordAuthenticationToken;
	}
}
*/
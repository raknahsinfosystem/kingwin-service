/*package com.raknahsinfosystem.webzion.user_mgmt.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raknahsinfosystem.webzion.user_mgmt.model.User;
import com.raknahsinfosystem.webzion.user_mgmt.service.UserService;
//import com.raknahsinfosystem.webzion.Utility;




@RestController
@RequestMapping(value="/rest/api", produces="application/json")
public class UserController  {
	final static Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	private UserService getUserService(){
		return userService;
	}
	public void setUseService(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping(value="/user", produces="application/json", method=RequestMethod.GET)
	public Object getUsers(){
		Object userList=new Object();
		try {
			logger.info("searchUser method");
			userList= getUserService().searchUser();
		} catch (Exception e) {
			logger.error("Could not get userDetails", e);
		}
		return userList;
	}
	@RequestMapping(value="/user/getUser", produces="application/json", method = RequestMethod.GET)
	public Object getUser(@RequestParam("userName") String nameValue){
		Object user=new Object();
		try {
			logger.info("searchUser method is started for userName " + nameValue);
			user= getUserService().getUsers(nameValue);			
		
		} catch (Exception e) {
			logger.error("Could not get userDetails for userid : " + nameValue, e);
		}
		return user;
	}
	@RequestMapping(value="/user/findUser", produces="application/json", method = RequestMethod.POST)
	public Object findUserByName(@RequestBody String userObj){
		Object user=new Object();
		try {
			logger.info("searchUser method is started for userName " + userObj);
			user= getUserService().findUserByNameImpl(userObj);			
		
		} catch (Exception e) {
			logger.error("Could not get userDetails for userid : " + userObj, e);
		}
		return user;
	}
	@RequestMapping(value="/user/getActive", produces="application/json", method = RequestMethod.GET)
	public Object getUserActive() {
		Object user=new Object();
		try {
			user = getUserService().getUserActiveDetails();
		
		} catch (Exception e) {
			logger.error("Could not get userDetails for userid : " , e);
		}
		return user;
	}
	@RequestMapping(value="/user/createUser", produces="application/json", method=RequestMethod.POST)
	public String createUser(@RequestBody User user) {
	String isCreated = null;
		try {
			isCreated = getUserService().createUser(user);
		} catch (Exception e) {
			logger.error("Could not create user", e);
			e.printStackTrace();
		}
		return isCreated;
	}
	@RequestMapping(value="/user", produces="application/json", method=RequestMethod.DELETE)
	public  Object deleteUser(@RequestBody User user){		
		Object result=new Object();
		try {
			result = getUserService().deleteUser(user.getUserId());
		} catch (Exception e) {
			logger.error("Could not delete user userid : " + user.getUserId(), e);
		}
		return  result;
	}

	@RequestMapping(value="/user", produces="application/json", method=RequestMethod.PUT)
	public Object updateUser(@RequestBody User user)
	{
		Object result=new Object();
		try {
			result = getUserService().updateUser(user);
			
		} catch (Exception e) {
			logger.error("Could not update user", e);
		}
		return result;
	}
}


*/
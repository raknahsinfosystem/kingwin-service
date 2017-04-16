package com.raknahsinfosystem.webzion.modules.controller;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raknahsinfosystem.webzion.modules.impl.ComImpl;
import com.raknahsinfosystem.webzion.modules.model.Branch;
import com.raknahsinfosystem.webzion.modules.model.EBook;


//import com.raknahsinfosystem.webzion.Utility;




@RestController
@RequestMapping(value="/rest/api", produces="application/json")
public class ComController  {
	/*@Autowired
	private UserService userService;

	private UserService getUserService(){
		return userService;
	}
	public void setUseService(UserService userService) {
		this.userService = userService;
	}*/
	@RequestMapping(value="/login", produces="application/json", method=RequestMethod.POST)
	public Object doLogin(){
		String userList="success";
		try {
			
			//userList= getUserService().searchUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	@RequestMapping(value="/getEbookList", produces="application/json", method=RequestMethod.GET)
	public Object getEBookList(@RequestParam("eBookType") String eBookType){
		JSONArray eBookList=null;
		
		try {
			ComImpl comImpl=new ComImpl();
			eBookList=comImpl.getEBooksImpl(eBookType);
			//userList= getUserService().searchUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eBookList.toString();
	}
	
	@RequestMapping(value="/uploadEBook", produces="application/json", method=RequestMethod.POST)
	public Object uploadEBook(@RequestBody EBook eBook){
		Integer genId=null;
		
		try {
			ComImpl comImpl=new ComImpl();
			genId=comImpl.uploadEBookImpl(eBook);
			//userList= getUserService().searchUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return genId;
	}
	@RequestMapping(value="/uploadEBook", produces="application/json", method=RequestMethod.PUT)
	public Object updateEBook(@RequestBody  EBook eBook){
		boolean uploadStatus=false;
		
		try {
			ComImpl comImpl=new ComImpl();
			uploadStatus=comImpl.updateEBookImpl(eBook);
			//userList= getUserService().searchUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadStatus;
	}
	@RequestMapping(value="/uploadEBook", produces="application/json", method=RequestMethod.DELETE)
	public Object deleteEBook(@RequestParam("id") int id){
		boolean uploadStatus=false;
		// delete ebook table with primary key not ebookId
		try {
			ComImpl comImpl=new ComImpl();
			uploadStatus=comImpl.deleteEBookImpl(id);
			//userList= getUserService().searchUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadStatus;
	}
	@RequestMapping(value="/getBranches", produces="application/json", method=RequestMethod.GET)
	public Object getBranchs(EBook ebook){
		//String userList="success";
		JSONArray branchArr=null;
		try {
			ComImpl comImpl=new ComImpl();
			branchArr=comImpl.getBranches();
			//userList= getUserService().searchUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branchArr.toString();
	}
	@RequestMapping(value="/updatePasswordSettings", produces="application/json", method=RequestMethod.POST)
	public Object updatePasswordSettings(@RequestBody Branch branch){
		boolean status=false;
		try {
			ComImpl comImpl=new ComImpl();
			status=comImpl.updatePasswordImpl(branch);
			//userList= getUserService().searchUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}


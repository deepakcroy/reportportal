package com.prp.actions;

import com.prp.models.SessionObject;

import net.aditri.web.utility.BaseWebAction;
import net.aditri.web.utility.EnumHelper.LoginAs;


public class HomeAction extends BaseWebAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String execute() throws Exception
	{
		//this.message="Hello Deepak!";
		//this.getRequest().setAttribute("sendToJSP", "WELCOME TO VALUE PASSING FROM ACTION TO JSP");
		//this.getRequest().setAttribute("sendURL",this.getRequest().getHeader("referer"));
		
		SessionObject sessionObj = (SessionObject)this.getRequest().getSession().getAttribute("LOGGEDUSER");
		if(sessionObj.getLoginAs() == LoginAs.SystemAdmin)
			return com.prp.actions.LoginAction.ADMINHOME;
		else
			return com.prp.actions.LoginAction.USERHOME;
	}
	public String index()
	{
		return SUCCESS;
	}
	
	public String sayHello()
	{
		
		message = "Hello World, Spring MVC @ Javatpoint";  
		return SUCCESS;
	}
	
}

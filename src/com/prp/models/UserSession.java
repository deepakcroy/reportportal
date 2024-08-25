package com.prp.models;

import javax.servlet.http.HttpSession;

public class UserSession {
	private String userName;
	private HttpSession session;
	public UserSession()
	{
		
	}
	public UserSession(String userName,HttpSession session)
	{
		this.userName=userName;
		this.session=session;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	
}

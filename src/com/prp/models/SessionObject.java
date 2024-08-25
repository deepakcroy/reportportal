package com.prp.models;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.app.module.Module;

import net.aditri.web.utility.EnumHelper.LoginAs;

public class SessionObject implements HttpSessionBindingListener {
	private User user;
	private LoginAs loginAs;

	private Map<String, Module> modules;
	private static final Map<String, UserSession> activeSessions = new HashMap<String, UserSession>();

	// public static int sessionCounter=0;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LoginAs getLoginAs() {
		return loginAs;
	}

	public void setLoginAs(LoginAs loginAs) {
		this.loginAs = loginAs;
	}

	public Map<String, Module> getModules() {
		return modules;
	}

	public void setModules(Map<String, Module> modules) {
		this.modules = modules;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {

		SessionObject oSO = (SessionObject) event.getValue();
		// activeSessions.put(event.getSession().getId().split("\\.")[0] ,
		// oSO.getUser().getUserName());
		activeSessions.put(event.getSession().getId(),
				new UserSession(oSO.getUser().getUserName(), event.getSession()));
		// sessionCounter++;
		// System.out.println("CURRENT ACTIVE SESSION :"+sessionCounter+"\nNew user
		// bound in session with name: " + oSO.getUser().getUserName());
		System.out.println("CURRENT ACTIVE SESSION :" + activeSessions.size()
				+ "\nNew user bound in session with name: " + oSO.getUser().getUserName());
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		SessionObject oSO = (SessionObject) event.getValue();
		// activeSessions.remove(event.getSession().getId().split("\\.")[0]);
		activeSessions.remove(event.getSession().getId());
		// sessionCounter--;
		// System.out.println("CURRENT ACTIVE SESSION :"+sessionCounter+"\nUser with
		// name: " + oSO.getUser().getUserName() + " removed from session");
		System.out.println("CURRENT ACTIVE SESSION :" + activeSessions.size() + "\nUser with name: "
				+ oSO.getUser().getUserName() + " removed from session");
	}

	public static boolean invalidate(String sessionId) {
		HttpSession session = activeSessions.get(sessionId).getSession();

		if (session != null) {
			session.invalidate();
			return true;
		} else {
			return false;
		}
	}

	public static Map<String, UserSession> getActivesessions() {
		return activeSessions;
	}
}

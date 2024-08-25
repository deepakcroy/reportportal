package net.aditri.web.utility;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class BaseWebAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	
	 private static final long serialVersionUID = 4154511508628097429L;
	 
	 private HttpServletRequest _request = null;
	 private HttpServletResponse _response = null;

	 public void setServletRequest(HttpServletRequest httpServletRequest) {
	  this._request = httpServletRequest;
	 }

	 public HttpServletRequest getRequest() {
	  return _request;
	 }

	 public HttpSession getSession() {
	  return getRequest().getSession(true);
	 }

	 public void setServletResponse(HttpServletResponse httpServletResponse) {
	  this._response = httpServletResponse;  
	 }
	 
	 public HttpServletResponse getResponse() {
	  return _response;
	 }
	 public static boolean isAjaxRequest(HttpServletRequest request) {
	   return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
	 public void raise404IFNotAjax(HttpServletRequest request)
	 {
		// IF THIS ACTION IS NOT CALLED FROM AJAX THEN DO NOT ALLOW PEOPLE TO ACCESS IT
		if(!isAjaxRequest(request))
		{
			try {
				getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	 }
	 public void print(String s)
	 {
		try{
			this._response.getWriter().write(s);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 public void printObject(Object obj)
	 {
		String jsonString;
		Gson gson = new Gson();
		
		jsonString = gson.toJson(obj); 
		this._response.setContentType("application/json");
		
		try {
			this._response.getWriter().write(jsonString );
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
}

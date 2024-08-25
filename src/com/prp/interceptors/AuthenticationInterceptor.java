package com.prp.interceptors;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.app.module.Module;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.prp.models.Menu;
import com.prp.models.SessionObject;

import net.aditri.web.utility.EnumHelper.LoginAs;

public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = -5011962009065225959L;

	@Override
	public void destroy() {
		//release resources here
	}

	@Override
	public void init() {
		// create resources here
	}
	
	@Override
	public String intercept(ActionInvocation actionInvocation)
			throws Exception {
		System.out.println("inside auth interceptor");
		Map<String, Object> sessionAttributes = actionInvocation.getInvocationContext().getSession();
		
		SessionObject sessionObj = (SessionObject) sessionAttributes.get("LOGGEDUSER");		
		if(sessionObj == null){
			return Action.LOGIN;
		}else{
			if(sessionObj.getLoginAs() == LoginAs.SystemAdmin)
				return actionInvocation.invoke();
			
			Action action = (Action) actionInvocation.getAction();
			
			List<Menu> menus = new ArrayList<Menu>();
			Map<String,Module> modules = sessionObj.getModules();
			for(Map.Entry<String, Module> oModule:modules.entrySet())
			{
				menus.addAll(oModule.getValue().getMenus());
			}
			String actionMethod = ActionContext.getContext().getName();
			
			
			// Allow null menu list. menu will be null when the application has no configuration, and will be loaded without error.
			if(menus!=null) {
				if(!action.getClass().getSimpleName().toString().equalsIgnoreCase("LoginAction")&&(!hasLinkAccess(menus,action,actionMethod)))
					return Action.LOGIN;
			}
			if(action instanceof UserAware){
				((UserAware) action).setUser(sessionObj.getUser());
			}
			
			return actionInvocation.invoke();
		}
	}
	/**
	 * 
	 * @param clazz
	 * @return
	 * Using Reflection find all the public methods of a given class
	 */
	private static Method[] getPublicMethods(Class<?> clazz) 
	{
	   List<Method> result = new ArrayList<Method>();

	   //while (clazz != null) {
	      for (Method method : clazz.getDeclaredMethods()) {
	         //int modifiers = method.getModifiers();
	         //if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
	         if (Modifier.isPublic(method.getModifiers())) {
	            result.add(method);
	         }
	      }
	      //clazz = clazz.getSuperclass();
	   //}

	   return result.toArray(new Method[result.size()]);
	}
	public boolean hasLinkAccess(List<Menu> menus,Action action,String actionName ) throws ClassNotFoundException
	{
		boolean hasControllerAccess = false;
		boolean hasActionAccess = false;
		
		String controllerName = action.getClass().getSimpleName(); 
		controllerName = controllerName.substring(0, controllerName.length()-6);// e.g.: HomeAction turns to => Home 

		//String tmpLink =(!controllerName.equalsIgnoreCase("Home"))?(controllerName+"/"+actionName):"home.action";
		if(controllerName.toUpperCase().equals("HOME"))
		{
			hasControllerAccess = true;
		}
		else
		{
			for(Menu m:menus)
			{
				if((m.getController() != null && !m.getController().isEmpty())&& m.getController().equalsIgnoreCase(controllerName))
				{
					hasControllerAccess = true;
					break;
				}
			}
		}
		
		//With Reflection Find all the public method of action class
		Class<?> tClass = Class.forName(action.getClass().getName());
		Method[] actionsInController = getPublicMethods(tClass);
		for(Method m:actionsInController)
		{
			if(m.getName().equalsIgnoreCase(actionName) || actionName.equalsIgnoreCase("Home"))
			{
				hasActionAccess = true;
				break;
			}
		}
		return ((hasControllerAccess && hasActionAccess)?true:false);
	}
	/**
	 * 
	 * @param actionInvocation
	 * This method is for getting current URL from Interceptor
	 * @return
	 */
	public String getCurrentURL(ActionInvocation actionInvocation,boolean withParam)
	{
		final ActionContext context = actionInvocation.getInvocationContext();        
		HttpServletRequest request = (HttpServletRequest)context.get(StrutsStatics.HTTP_REQUEST);
		StringBuffer url = request.getRequestURL();
		
		String fullUrl = url + (request.getQueryString()==null ? "" : ("?" + request.getQueryString()));
		
		return withParam?fullUrl:url.toString();
	}

}

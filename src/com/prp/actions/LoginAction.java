package com.prp.actions;

import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.app.module.Module;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ModelDriven;
import com.prp.models.Menu;
import com.prp.models.SessionObject;
import com.prp.models.User;

import net.aditri.dao.HibernateUtil;
import net.aditri.util.StringUtil;
import net.aditri.util.security.EncryptorMD5;
import net.aditri.web.listener.AppEnv;
import net.aditri.web.utility.BaseWebAction;
import net.aditri.web.utility.EnumHelper.LoginAs;


//import com.google.gson.Gson;

public class LoginAction extends BaseWebAction implements SessionAware, ModelDriven<User>{

	private static final long serialVersionUID = -3369875299120377549L;
	
	// Custom Actions
	public static final String ADMINHOME = "adminhome";
	public static final String USERHOME = "userhome";
	
	private User user = new User();
	private Map<String, Object> sessionAttributes = null;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setSession(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}
	@Override
	public User getModel() {
		return user;
	}
	public String logout()
	{		
		this.sessionAttributes.remove("LOGGEDUSER");
        addActionMessage("You have been Successfully Logged Out");
        return SUCCESS;

	}
	@SuppressWarnings("rawtypes")
	@Override
	public String execute(){
		if(!this.sessionAttributes.isEmpty())
			return SUCCESS;
		
		if(user.getUserName()!=null && user.getPassword()!=null)
		{
			List<Menu> menus = new ArrayList<Menu>();
			List<Menu> usermenus = new ArrayList<Menu>();
			LinkedHashMap<String,Module> modules = new LinkedHashMap<String,Module>();
			LinkedHashMap<String,Module> usermodules = new LinkedHashMap<String,Module>();
			List<String> strModules = new ArrayList<String>();
			String jsonUserModules="";
			
			String _password = "";
			boolean _hasAccess = false;
			boolean _isAdministrator = false;
			EncryptorMD5 em = new EncryptorMD5();
			if(user.getUserName().toUpperCase().equals("ADMINISTRATOR")) //pwd admin
			{
				Map sysMap = (Map) AppEnv.getObject("sys_parammap");
				String _passPhrase = ((String) sysMap.get("app.passphrase"));
				_password = ((String) sysMap.get("app.password"));
				
				
				try 
				{
					String _pwd = em.getSaltedMD5(user.getPassword(),_passPhrase.getBytes());
					if(_pwd.equals(_password.trim()))
					{
						_hasAccess =true;
						_isAdministrator =true;
						//modules=AppEnv.getModules(); //Shallow Copy 
						//usermodules.putAll(AppEnv.getModules()); //shallow Copy will not work; need to do deep copy
						Gson gson = new Gson();
						jsonUserModules = gson.toJson(AppEnv.getModules());
						Type type = new TypeToken<LinkedHashMap<String, Module>>(){}.getType();
						usermodules = gson.fromJson(jsonUserModules, type); 
					}
				} catch (NoSuchAlgorithmException e) {
					_hasAccess  = false;
					e.printStackTrace();
				} catch (NoSuchProviderException e) {
					_hasAccess  = false;
					e.printStackTrace();
				}
				catch (Exception e) {
					_hasAccess  = false;
					e.printStackTrace();
				}
			}
			else
			{
				_password = em.getMD5(user.getPassword());
				
				Session session = null;
				Transaction transaction = null;
				try {
					session = HibernateUtil.getSessionFactory().openSession();
					transaction = session.beginTransaction();
					
					String sql = " from User u where u.userName=:name and u.password=:pass";
			        Query query = session.createQuery(sql);
			        
			    
			        query.setParameter("name", user.getUserName());
			        query.setParameter("pass", _password);
			        this.user=(User)query.uniqueResult();
			        
			        if (this.user!=null) {
			        	query = null;
			        	query=session.getNamedQuery("SQL_GET_MY_MENU");
			        	query.setParameter("P_USER_ID", this.user.getId());
			        	
			        	@SuppressWarnings("unchecked")
						List<Integer>  ll= query.list();
			        	
			        	
			        	//combine all menus
			        	Gson gson = new Gson();
						jsonUserModules = gson.toJson(AppEnv.getModules());
						Type type = new TypeToken<LinkedHashMap<String, Module>>(){}.getType();
						modules = gson.fromJson(jsonUserModules, type); 
			        	//modules.putAll(AppEnv.getModules()); //shallow copy
			        	for(Map.Entry<String,Module> m:modules.entrySet())
			        	{
			        		menus.addAll(m.getValue().getMenus());
			        	}
			        	//extract user menus from all menus
			        	for(Menu m:menus)
			        	{
			        		if(ll.contains(Integer.parseInt(m.getId().toString())))
			        		{
			        			usermenus.add(m);
			        			if(!StringUtil.containsIgnoreCase(strModules,m.getModule()))
				        			strModules.add(m.getModule());
			        		}
			        	}
			        	
			        	
			        	if(usermenus.size()>0)
						{
			        		
			        		for(Map.Entry<String,Module> m:modules.entrySet())
							{
								if(StringUtil.containsIgnoreCase(strModules, m.getKey().toString()))
								{
									List<Menu> _tempMenus = new ArrayList<Menu>();
									_tempMenus = populateModuleMenu(m.getKey().toString(),usermenus,_tempMenus);
									/*for(Menu oMenu : usermenus)
									{
										if(oMenu.getModule().toLowerCase().equals(m.getKey().toString().toLowerCase()))
											_tempMenus.add(oMenu);
									}*/
									
									Module oModule = m.getValue();
									Menu oMenu = m.getValue().getMenus().get(0);
									oModule.getMenus().clear();
									//add first/default menu of each module
									oModule.getMenus().add(oMenu);
									oModule.getMenus().addAll(_tempMenus);
									
									usermodules.put(m.getKey().toString(),oModule);
								}
								
							}
						}
			        	
			        	
			        }
				} catch (HibernateException e) {
					_hasAccess  = false;
					transaction.rollback();
					e.printStackTrace();
				} 
				catch (Exception e)
				{
					_hasAccess  = false;
					transaction.rollback();
					e.printStackTrace();
				}
				finally {
					_hasAccess =true;
					_isAdministrator = false;
					if (session != null) {
						transaction.commit();
			            session.close();
					}
				}
			}
			
			if (_hasAccess && user!=null) {
				
				user.setPassword("");
				
				SessionObject oSession = new SessionObject();
				
				if(_isAdministrator)
					oSession.setLoginAs(LoginAs.SystemAdmin);
				else
					oSession.setLoginAs(LoginAs.User);
				
				oSession.setUser(user);
				oSession.setModules(usermodules);
				sessionAttributes.put("LOGGEDUSER", oSession);
				
				menus=null;
				usermenus=null;
				modules=null;
				usermodules=null;
				
				return SUCCESS;
	        }
	        else
	        {
	        	addActionError("<i class='fontello-icon-attention'></i> Please Enter Valid User or Password");;
	    		return LOGIN;
	        }
		}
		else
		{
    		return LOGIN;
		}
	}
	private List<Menu> populateModuleMenu(String module,List<Menu> menuList,List<Menu> _tempMenus)
	{
		if(menuList.size()>0)
		{
			if(menuList.get(0).getModule().toLowerCase().equals(module.toLowerCase()))
			{
				_tempMenus.add(menuList.get(0));
				menuList.remove(menuList.get(0));
				populateModuleMenu(module,menuList,_tempMenus);
			}
		}
		return _tempMenus;
	}
}

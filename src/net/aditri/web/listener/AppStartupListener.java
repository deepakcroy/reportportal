package net.aditri.web.listener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.app.module.Module;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prp.models.Menu;

import net.aditri.dao.HibernateUtil;


public class AppStartupListener implements  ServletContextListener 
{
	private static Logger logger = Logger.getLogger(AppStartupListener.class.getName());
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		AppEnv.setServletContext(event.getServletContext());
	    logger.error("Initializing the system parameters ...");
	    
	    this.initSysProperties();
	    
	    logger.error("Initialization is complete!");
	}
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("ServletContextListener destroyed");
	}
	
	@SuppressWarnings("rawtypes")
	private void initSysProperties()
	{
		Properties props = new Properties();
		Map sysMap = (Map)AppEnv.getObject("sys_parammap");
		if (sysMap == null) {
			sysMap = new HashMap();
		}
		InputStream fis = null;
		try
		{
			// init app properties
			fis = AppStartupListener.class.getResourceAsStream("/res/app/application.properties");
			props = new Properties();
			props.load(fis);
			this.addProperties(props, sysMap);
			
			// init database properties
			fis = AppStartupListener.class.getResourceAsStream("/res/app/database.properties");
			props = new Properties();
			props.load(fis);
			this.addProperties(props, sysMap);
			
			AppEnv.setObject("sys_parammap", sysMap);
			
			
			//register Modules
			this.registerModules();
			
		} catch (FileNotFoundException e) {
		      logger.error("Path /res/app.properties is invalid...", e);
	    } catch (IOException e) {
	      logger.error("/res/app.properties IO exception...", e);
	    } finally {
	      if (fis != null)
	        try {
	          fis.close();
	        } catch (IOException e) {
	          logger.error(e.getMessage());
	        }
	    }
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addProperties(Properties props, Map sysMap)
	{
		for (Iterator iter = props.keySet().iterator(); iter.hasNext(); ) {
			String key = (String)iter.next();
		    String svalue = (String)props.get(key);
		    sysMap.put(key, svalue);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void registerModules() {
		
		List<Module> oList = new ArrayList<Module>();
		LinkedHashMap<String,Module> modules = new LinkedHashMap<String,Module>();
		InputStream fis = null;
		
		String json;
		try 
		{
			fis = AppStartupListener.class.getResourceAsStream("/res/app/modules.cfg");
			json = IOUtils.toString(fis, "UTF-8");
			Type listType = new TypeToken<ArrayList<Module>>(){}.getType();
			oList = new Gson().fromJson(json, listType);
			for(Module m:oList)
			{
				modules.put(m.getName(), m);
			}
			AppEnv.setModules(modules);
			
			
			
			// register custom menus inside modules
			List<Menu> menuList = new ArrayList<Menu>();
			Session session = null;
			Transaction transaction = null;
			try {
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				
				Query query = null;
	        	query=session.getNamedQuery("SQL_GET_ALL_MENU");
	        	
	        	@SuppressWarnings("unchecked")
				List<Object>  ll= query.list();
	        	for(Object row : ll) {
	        		menuList.add((Menu)row);
	        	}
			}
			catch (HibernateException e) {
				transaction.rollback();
				e.printStackTrace();
			} 
			catch (Exception e)
			{
				transaction.rollback();
				e.printStackTrace();
			}
			finally {
				if (session != null) {
					transaction.commit();
		            session.close();
		          }
			}
			
			if(menuList.size()>0)
			{
				for(Map.Entry m:AppEnv.getModules().entrySet())
				{
					List<Menu> _tempMenus = new ArrayList<Menu>();
					/*for(Menu oMenu : menuList)
					{
						if(oMenu.getModule().toLowerCase().equals(m.getKey().toString().toLowerCase()))
						{
							_tempMenus.add(oMenu);
						}
					}*/
					_tempMenus = populateModuleMenu(m.getKey().toString(),menuList,_tempMenus);
					Module om = (Module)m.getValue();
					om.getMenus().addAll(_tempMenus);
					AppEnv.getModules().get(m.getKey()).setMenus(om.getMenus());
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private List<Menu> populateModuleMenu(String module,List<Menu> menuList,List<Menu> _tempMenus)
	{
		for(Menu m:menuList)
		{
			if(m.getModule().toLowerCase().equals(module.toLowerCase()))
			{
				_tempMenus.add(m);
			}
		}
		/*
		if(menuList.size()>0)
		{
			if(menuList.get(0).getModule().toLowerCase().equals(module.toLowerCase()))
			{
				_tempMenus.add(menuList.get(0));
				menuList.remove(menuList.get(0));
				populateModuleMenu(module,menuList,_tempMenus);
			}
			else
			{
				
				menuList.remove(menuList.get(0));
				populateModuleMenu(module,menuList,_tempMenus);
			}
		}*/
		return _tempMenus;
	}
}
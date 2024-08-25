package com.prp.actions;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import com.app.module.Module;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ModelDriven;
import com.prp.models.Group;
import com.prp.models.Menu;
import com.prp.models.SessionObject;
import com.prp.models.User;
import com.thoughtworks.xstream.XStream;
import com.prp.models.GroupMenu;

import net.aditri.dao.DAO;
import net.aditri.dao.DAOImpl;
import net.aditri.dao.DBResponse;
import net.aditri.dao.HibernateUtil;
import net.aditri.web.listener.AppEnv;
import net.aditri.web.utility.BaseWebAction;
import net.aditri.web.utility.KeyValue;
import net.aditri.web.utility.ValidationResponse;

public class GroupMenuAction extends BaseWebAction implements ModelDriven<GroupMenu>{
	private static final long serialVersionUID = 1L;
	private GroupMenu groupMenu = new GroupMenu();
	public GroupMenu getModel() {
		return this.groupMenu;
	}
	private DAO<Group> groupDAO = new DAOImpl<Group>();
	
	String menuTreeString="";
	String intendation="\t";
	public String list()
	{
		Map<String,String> trees=new HashMap<String,String>();
		List<Group> list1=groupDAO.list(new Group());
		this.getRequest().setAttribute("groups", list1);
		
		
		
		
		
		for(Map.Entry<String,Module> oModule: AppEnv.getModules().entrySet())
		{
			menuTreeString="";
			intendation="\t";
			this.createMenuItem(Long.parseLong("0"), ((Module)oModule.getValue()).getMenus());
			trees.put(oModule.getKey(),menuTreeString.trim());
		}
		
		
		//List<Menu> list2 = menuDAO.list(new Menu());
		//menuTreeString="";
		//intendation="\t";
		//this.createMenuItem(Long.parseLong("0"),list2);
		//this.getRequest().setAttribute("roleTree", menuTreeString);
		
		this.getRequest().setAttribute("menuTrees", trees);
		return SUCCESS;
	}
	public String listMyMenu()
	{
		raise404IFNotAjax(this.getRequest());
		Query query =null;
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			
			//query=session.getNamedQuery("SQL_GET_GROUPMENU_USER");
			//query.setParameter("P_UGS",getRequest().getParameter("ugs"));
			query=session.getNamedQuery("SQL_GET_GROUPMENU_MENU");
			query.setParameter("P_GROUPID",Long.parseLong(getRequest().getParameter("ugs")));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			@SuppressWarnings({"rawtypes" })
			List result = query.list();
			printObject(result);
		}
		catch(HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				transaction.commit();
	            session.close();
	          }
		}
		return null;
	}
	public String saveOrUpdate()
	{	
		raise404IFNotAjax(this.getRequest());
		ValidationResponse oValidResponse = ValidateForm(this.getRequest());
		if(oValidResponse.isSuccess())
		{
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<KeyValue>>(){}.getType();
			List<KeyValue> kvList = gson.fromJson(getRequest().getParameter("ugs"),listType);
			
			String[] strMenuList = getRequest().getParameter("ris").split(",");
			List<GroupMenu> groupMenuList = new ArrayList<GroupMenu>(); 
			User oUser = ((SessionObject)this.getSession().getAttribute("LOGGEDUSER")).getUser();
			for(String menuId:strMenuList)
			{
				for(KeyValue obj:kvList)
				{
					groupMenuList.add(new GroupMenu(Long.parseLong("0"), Long.parseLong(menuId), Long.parseLong(obj.key),  oUser.getUserName(), oUser.getUserName()));
				}
			}
			XStream xstream = new XStream();
			xstream.alias("GroupMenus", List.class);
			
			
			final String xmlGroupMenus = xstream.toXML(groupMenuList);
			System.out.println(xmlGroupMenus);
			
			
			xstream.alias("Nodes", List.class);
			final String xmlUG = xstream.toXML(kvList);
			System.out.println(xmlUG);
			
			Session session = null;
			Transaction transaction = null;
			try
			{
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				
				session.doWork(new Work(){
					@Override
					public void execute(Connection connection) throws SQLException {
						String query = "{call GROUP_MENU_SP(?,?,?,?)}";
						CallableStatement stmt = connection.prepareCall(query);
						
						
						stmt.setString(1, xmlGroupMenus); //CLOB
						stmt.setString(2, xmlUG);
						
						stmt.registerOutParameter(3, java.sql.Types.INTEGER);
						stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
						  
						stmt.execute();
						DBResponse oDBResponse =new DBResponse(stmt.getBoolean(3),stmt.getString(4));
						printObject(oDBResponse);
					}
				});
			}
			
			catch(HibernateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (session != null) {
		        	 transaction.commit();
		             session.close();
		          }
			}
		}
		else
		{
			this.printObject(oValidResponse);
		}
		return null;
	}
	private ValidationResponse ValidateForm(HttpServletRequest request)
	{
		Map<String,String> fieldMessage = new HashMap<String,String>();
		ValidationResponse oResponse = new ValidationResponse(fieldMessage);
		
		if(fieldMessage.size()==0)
			oResponse.setSuccess(true);
		
		return oResponse;
	}
	
	
	private void createMenuItem(Long parentId,List<Menu> items)
	{
		if(items.size()==0)
			return;
		 List<Menu> list = new ArrayList<Menu>();
		 list = this.getChildren(parentId,items);
		 
		 if(list.size()>0)
			 menuTreeString=menuTreeString+"\n<ul>\n";
		 
		 for(Menu obj:list)
		 {
			 Long currentMenuId=obj.getId();
			 menuTreeString=menuTreeString+"\n<li><a href=\"#\">"+obj.getTitle()+"</a>"+this.prepareMenuCtrls(currentMenuId);
			 createMenuItem(currentMenuId,items);
			 menuTreeString=menuTreeString+"</li>";
		 }
		 if(list.size()>0)
		 {
			 intendation=intendation+"\t";
			  menuTreeString=menuTreeString+"\n</ul>\n";
		 }
		  
	}
	private List<Menu> getChildren(Long parentId,List<Menu> src)
	{
		List<Menu> list = new ArrayList<Menu>();
		for(Menu obj:src)
		{
			if(obj.getParentId().equals(parentId))
			{
				list.add(new Menu(obj.getId(),obj.getParentId(),obj.getTitle(),obj.getController(),obj.getModule(),obj.getLink(),obj.getPosition(),obj.isNewTab(),obj.isLeafLevel()));
			}
		}
		return list;
	}
	private String prepareMenuCtrls(Long id)
	{
		StringBuilder sb=new StringBuilder("<div class='menu_ctrl_wrapper'>");  
		sb.append("<div class='menu_ctrl'>");
		sb.append("<span class='ctrl' data-val='edit' data-id='"+id+"'><input id='"+id+"' class='menuitem' type='checkbox' /></span>");
		sb.append("</div>");
		sb.append("</div>");
		
		return sb.toString();	
	}
}

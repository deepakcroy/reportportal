package com.prp.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.app.module.Module;
import com.opensymphony.xwork2.ModelDriven;
import com.prp.models.Menu;
import com.prp.models.SessionObject;
import com.prp.models.User;

import net.aditri.dao.DAO;
import net.aditri.dao.DAOImpl;
import net.aditri.dao.DBResponse;
import net.aditri.dao.HibernateUtil;
import net.aditri.web.listener.AppEnv;
import net.aditri.web.utility.BaseWebAction;
import net.aditri.web.utility.ValidationResponse;

public class MenuAction extends BaseWebAction implements ModelDriven<Menu> {
	private static final long serialVersionUID = 1L;
	
	private Menu menu = new Menu();
	private List<Menu> menuList = new ArrayList<Menu>();
	
	private DAO<Menu> menuDAO = new DAOImpl<Menu>();
	public Menu getModel() {
		return this.menu;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	String menuTreeString="";
	String intendation="\t";
	public String list()
	{
		Map<String,String> trees=new HashMap<String,String>();
		for(Map.Entry<String,Module> oModule: AppEnv.getModules().entrySet())
		{
			menuTreeString="";
			intendation="\t";
			this.createMenuItem(Long.parseLong("0"), ((Module)oModule.getValue()).getMenus(),(Module)oModule.getValue());
			trees.put(oModule.getKey(),menuTreeString.trim());
		}
		this.getRequest().setAttribute("menuTrees", trees);
		return SUCCESS;
	}
	
	private void createMenuItem(Long parentId,List<Menu> items,Module oModule)
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
			 menuTreeString=menuTreeString+"\n<li><a href=\"#\">"+obj.getTitle()+"</a>"+this.prepareMenuCtrls(currentMenuId,obj.getParentId(),oModule);
			 createMenuItem(currentMenuId,items,oModule);
			 menuTreeString=menuTreeString+"</li>";
		 }
		 if(list.size()>0)
		 {
			 intendation=intendation+"\t";
			  menuTreeString=menuTreeString+"\n</ul>\n";
		 }
		  
	}
	public List<Menu> getChildren(Long parentId,List<Menu> src)
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
	private String prepareMenuCtrls(Long id,Long parentId,Module oModule)
	{
		if(parentId==0L)
			return "";
		if(!oModule.isEditable())
			return "";
		
		StringBuilder sb=new StringBuilder("<div class='menu_ctrl_wrapper'>");  
		sb.append("<div class='menu_ctrl'>");
		sb.append("<span class='ctrl' data-val='edit' data-id='"+id+"' title='Edit'><i class='fontello-icon-edit'></i></span>");
		sb.append("</div>");
		sb.append("</div>");
		
		return sb.toString();
		
	}
	public String saveOrUpdateForm()
	{
		raise404IFNotAjax(this.getRequest());
		String strId = this.getRequest().getParameter("id");
		List<Menu> menus = new ArrayList<Menu>();
		List<String> controllers= new ArrayList<String>();
		String moduleName="";
		if(strId!=null) //Edit Form
		{
			menu = menuDAO.listById(menu,Long.parseLong(strId));
			menus=((SessionObject)this.getRequest().getSession().getAttribute("LOGGEDUSER")).getModules().get(menu.getController()).getMenus();
			controllers = AppEnv.getModules().get(menu.getController()).getControllers();
			moduleName = menu.getModule();
		}
		else
		{
			menus=((SessionObject)this.getRequest().getSession().getAttribute("LOGGEDUSER")).getModules().get(this.getRequest().getParameter("module")).getMenus();
			controllers = AppEnv.getModules().get(this.getRequest().getParameter("module")).getControllers();
			moduleName=this.getRequest().getParameter("module");
		}
		
		//List<Menu> list = menuDAO.list(new Menu());
		this.getRequest().setAttribute("menuList", menus);
		this.getRequest().setAttribute("controllers", controllers);
		this.getRequest().setAttribute("moduleName", moduleName);
		
		return INPUT;
	}
	public String saveOrUpdate()
	{	
		raise404IFNotAjax(this.getRequest());
		ValidationResponse oValidResponse = ValidateForm(this.getRequest());
		if(oValidResponse.isSuccess())
		{
			DBResponse oDBResponse = new DBResponse();
			final String action = (menu.getId()!=null)?"EDIT":"INSERT";
			final User oUser = ((SessionObject)this.getSession().getAttribute("LOGGEDUSER")).getUser();
			Query query =null;
			Session session = null;
			Transaction transaction = null;
			try
			{
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				if(action.equals("EDIT")) {
					query =session.getNamedQuery("SQL_EDIT_MENU");
					query.setParameter("P_MENU_ID", NumberUtils.toInt(getRequest().getParameter("id"),0));
				} else {
					query =session.getNamedQuery("SQL_INSERT_MENU");
					query.setParameter("P_CREATEDBY", oUser.getUserName());
				}
				
	        	query.setParameter("P_CONTROLLER", getRequest().getParameter("controller"));
	        	query.setParameter("P_MODULE", getRequest().getParameter("module"));
	        	query.setParameter("P_TITLE", getRequest().getParameter("title"));
	        	query.setParameter("P_LINK", getRequest().getParameter("link"));
	        	
	        	query.setParameter("P_PARENT_ID", NumberUtils.toInt(getRequest().getParameter("parentId"),0));
	        	query.setParameter("P_POSITION", NumberUtils.toInt(getRequest().getParameter("position"),0));
	        	query.setParameter("P_ISNEWTAB", NumberUtils.toInt(getRequest().getParameter("newTab"),0));
				
				if(getRequest().getParameter("leafLevel")!=null)
					query.setParameter("P_ISLEAFLEVEL", 1);
				else
					query.setParameter("P_ISLEAFLEVEL", 0);
				
				query.setParameter("P_STATUS", net.aditri.web.utility.EnumHelper.Status.valueOf(getRequest().getParameter("status")).getValue());
				query.setParameter("P_MODIFIEDBY", oUser.getUserName());
				
				int isSuccess = query.executeUpdate();
				if(isSuccess==1) {
					oDBResponse =new DBResponse(true,"Operation Successfully Executed.");
					printObject(oDBResponse);
				}
			} catch(HibernateException e) {
				transaction.rollback();
				oDBResponse =new DBResponse(false,e.getMessage());
				printObject(oDBResponse);
				e.printStackTrace();
			} catch (Exception e) {
				transaction.rollback();
				oDBResponse =new DBResponse(false,e.getMessage());
				printObject(oDBResponse);
				e.printStackTrace();
			} finally {
				if (session != null) {
					transaction.commit();
		            session.close();
		          }
			}
		} else {
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
}

package com.prp.actions;


import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.jsp.PageContext;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
//import org.hibernate.HibernateException;
//import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.hibernate.jdbc.Work;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
//import com.prp.models.Menu;
import com.prp.models.SessionObject;
import com.prp.models.User;

import net.aditri.dao.DAO;
import net.aditri.dao.DAOImpl;
import net.aditri.dao.DBResponse;
import net.aditri.dao.HibernateUtil;
import net.aditri.util.security.EncryptorMD5;
import net.aditri.web.datatable.*;
import net.aditri.web.utility.BaseWebAction;
import net.aditri.web.utility.EnumHelper;
import net.aditri.web.utility.RegExValidator;
import net.aditri.web.utility.ValidationResponse;
//import oracle.jdbc.OracleTypes;

import com.google.gson.Gson;
import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;

public class UserAction extends BaseWebAction implements ModelDriven<User> {

	private static final long serialVersionUID = -6659925652584240539L;
	
	@SessionTarget
	Session session;
	@TransactionTarget
	Transaction transaction;
	
	private User user = new User();
	private List<User> userList = new ArrayList<User>();
	private int totalRecords = 0;
	public DataTableRepresenter oDTRep = new DataTableRepresenter();
	
	
	private DAO<User> userDAO = new DAOImpl<User>();
	public User getModel() {
		return user;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	
	// Action's operations
	/*
	 * WHEN anction name="user" and method=execute or not defined then execute method will be called
	 */
	/*
	@SuppressWarnings("unchecked")
	@Override
	public String execute(){
		return SUCCESS;
	}*/
	/**
	 * To list all users.
	 * @return String
	 */
	public String list()
	{
		//userList = (List<User>)userDAO.list(user);
		return SUCCESS;
	}
	/**
	 * To open, create or edit Form
	 * @return String
	 */
	public String saveOrUpdateForm()
	{
		//HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		HttpServletRequest request = ServletActionContext.getRequest();
		raise404IFNotAjax(request);
		String strId = request.getParameter("id");
		if(strId!=null) //Edit Form
		{
			user = userDAO.listById(user,Long.parseLong(strId));
		}
		
		return INPUT;
	}
	private ValidationResponse ValidateForm(HttpServletRequest request)
	{
		Map<String,String> fieldMessage = new HashMap<String,String>();
		//User Name
		if(!RegExValidator.validate(RegExValidator.USERNAME_PATTERN,request.getParameter("userName").trim()))
		{
			fieldMessage.put("userName", RegExValidator.USERNAME_ERRMSG);
		}
		if(!RegExValidator.validate(RegExValidator.PASSWORD_PATTERN,request.getParameter("password").trim()))
		{
			fieldMessage.put("password", RegExValidator.PASSWORD_ERRMSG);
		}
		//ConPassword
		if(!request.getParameter("password").trim().equals(request.getParameter("conPassword").trim()))
		{
			fieldMessage.put("conPassword", "Password does not match with confirm password");
		}
		ValidationResponse oResponse = new ValidationResponse(fieldMessage);
		
		if(fieldMessage.size()==0)
			oResponse.setSuccess(true);
		
		return oResponse;
	}
	/**
	 * To save or update user
	 * @return String
	 */
	public String saveOrUpdate()
	{	
		raise404IFNotAjax(this.getRequest());
		ValidationResponse oValidResponse = ValidateForm(this.getRequest());
		if(oValidResponse.isSuccess())
		{
			User oUser = ((SessionObject)this.getSession().getAttribute("LOGGEDUSER")).getUser();
			if(user.getId()!=null) //Edit Form
			{
				user.setModifiedBy(oUser.getUserName());
				user.setModifiedAt(new Date());
			}
			else
			{
				user.setCreatedBy(oUser.getUserName());
				user.setModifiedBy(oUser.getUserName());
			}
			
			
			
			EncryptorMD5 em = new EncryptorMD5();
			user.setPassword(em.getMD5(user.getPassword()));
			/*try {
				user.setPassword(em.getSaltedMD5(user.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
			}*/
			
			DBResponse oDBResponse =new DBResponse();
			oDBResponse = userDAO.saveOrUpdate(user);
			
			
	        
			this.printObject(oDBResponse);
		}
		else
		{
			this.printObject(oValidResponse);
		}
		
		
		
		//return SUCCESS;
		return null;
	}
	/**
	 * To delete a user.
	 * @return String
	 */
	public String delete()
	{
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		raise404IFNotAjax(request);
		userDAO.delete(user,Long.parseLong(request.getParameter("id")));
		return SUCCESS;
	}
	public String userDataTable()
	{
		raise404IFNotAjax(this.getRequest());
		Session session = null;
		Transaction transaction = null;
		
		
    	
		try
		{
			 /*HttpServletRequest rq = getRequest();
			 HashMap<String, String> params = new HashMap<String, String>();
			 params = (HashMap<String, String>) rq.getParameterMap();*/
			 
			int pFrom =0, pTo=0, itemLength = Integer.parseInt(getRequest().getParameter("iDisplayLength"));
	    	
			//FOR ORACLE
			/*if(Integer.parseInt(getRequest().getParameter("iDisplayStart"))==0) {
	    		pTo=pFrom+itemLength;
	    	}
	    	else
	    	{
	    		pFrom = Integer.parseInt(getRequest().getParameter("iDisplayStart"))+1;
	    		pTo=(pFrom-1)+itemLength;
	    	}*/
			
			//FOR MySQL
			pTo=itemLength;
			if(Integer.parseInt(getRequest().getParameter("iDisplayStart"))==0) 
				pFrom = 0;
	    	else
	    		pFrom = Integer.parseInt(getRequest().getParameter("iDisplayStart"));//*itemLength;
			
        
        	session = HibernateUtil.getSessionFactory().openSession();
        	transaction = session.beginTransaction();
        	
        	Query query = session.getNamedQuery("SQL_GET_USER_BY_ROWNUM_RANGE");
        	query.setParameter("P_FROM", pFrom);
        	query.setParameter("P_TO", pTo);
        	
        	@SuppressWarnings("unchecked")
			List<Object>  ll= query.list();
        	for(Object row : ll) {
        		userList.add((User)row);
        	}
	        	
        	query = session.getNamedQuery("SQL_GET_TOTAL_USER");
        	totalRecords = (int) query.uniqueResult();

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
		
		String jsonString;
		Gson gson= new Gson();
		
		JQueryDataTableParamModel param = DataTablesParamUtility.getParam(getRequest());
		DataTableRepresenter o=new DataTableRepresenter();
		o.sEcho=Integer.parseInt(param.sEcho);
		o.iTotalDisplayRecords= totalRecords;
		o.iTotalRecords= totalRecords;
		o.aaData = new ArrayList<ArrayList<String>>();
		for(User c : userList)
		{
		   ArrayList<String> arrUser = new ArrayList<String>();
		   arrUser.add("<a class='label label-positive' href='javascript:void(0);' onclick='createOrEditForm(this)' data-url='"+ getRequest().getContextPath()+ "/setup/user/saveorupdateform?id="+ c.getId() +"'><i class='fontello-icon-edit'></i>Edit</a>");
		   arrUser.add(String.valueOf(c.getId()));
		   arrUser.add(c.getUserName());
		   arrUser.add(c.getPassword());
		   arrUser.add("<span class='label "+((c.getStatus()==EnumHelper.Status.Active)?"label-success":((c.getStatus()==EnumHelper.Status.Inactive)?"label-negative":((c.getStatus()==EnumHelper.Status.Pending)?"label-warning":"label-default")))+"'>"+c.getStatus().toString()+"</span>");
		   arrUser.add(c.getCreatedBy());
		   arrUser.add(c.getCreatedAt().toString());
		   arrUser.add(c.getModifiedBy());
		   arrUser.add(c.getModifiedAt().toString());
		   o.aaData.add(arrUser);
		 }
		
		jsonString = gson.toJson(o);
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json");
        try {
			response.getWriter().write(jsonString );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

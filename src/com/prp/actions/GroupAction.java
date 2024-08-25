package com.prp.actions;

import java.io.IOException;
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

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.hibernate.jdbc.Work;

import com.google.gson.Gson;
import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;
//import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.prp.models.SessionObject;
import com.prp.models.User;
import com.prp.models.Group;

import net.aditri.dao.DAO;
import net.aditri.dao.DAOImpl;
import net.aditri.dao.DBResponse;
import net.aditri.dao.HibernateUtil;
import net.aditri.web.datatable.DataTableRepresenter;
import net.aditri.web.datatable.DataTablesParamUtility;
import net.aditri.web.datatable.JQueryDataTableParamModel;
import net.aditri.web.utility.BaseWebAction;
import net.aditri.web.utility.EnumHelper;
import net.aditri.web.utility.ValidationResponse;
//import oracle.jdbc.OracleTypes;

public class GroupAction extends BaseWebAction implements ModelDriven<Group> {
	
	private static final long serialVersionUID = 1L;
	
	@SessionTarget
	Session session;
	@TransactionTarget
	Transaction transaction;
	
	private Group group = new Group();
	private List<Group> groupList = new ArrayList<Group>();
	private int totalRecords = 0;
	public DataTableRepresenter oDTRep = new DataTableRepresenter();
	
	private DAO<Group> groupDAO = new DAOImpl<Group>();
	public Group getModel() {
		return this.group;
	}
	public Group getGroup() {
		return this.group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public List<Group> getGroupList() {
		return this.groupList;
	}
	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}
	/**
	 * To list all user groups.
	 * @return String
	 */
	public String list()
	{
		return SUCCESS;
	}
	public String saveOrUpdateForm()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		// IF THIS ACTION IS NOT CALLED FROM AJAX THEN DO NOT ALLOW PEOPLE TO ACCESS IT
		raise404IFNotAjax(request);
		
		String strId = request.getParameter("id");
		if(strId!=null) //Edit Form
		{
			group = groupDAO.listById(group,Long.parseLong(strId));
		}
		return INPUT;
	}
	private ValidationResponse ValidateForm(HttpServletRequest request)
	{
		Map<String,String> fieldMessage = new HashMap<String,String>();
		//User Name
		/*if(!RegExValidator.validate(RegExValidator.USERNAME_PATTERN,request.getParameter("userName").trim()))
		{
			fieldMessage.put("userName", RegExValidator.USERNAME_ERRMSG);
		}*/
		
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
			if(group.getId()!=null) //Edit Form
			{
				group.setModifiedBy(oUser.getUserName());
				group.setModifiedAt(new Date());
			}
			else
			{
				group.setCreatedBy(oUser.getUserName());
				group.setModifiedBy(oUser.getUserName());
			}
			DBResponse oDBResponse =new DBResponse();
			oDBResponse = groupDAO.saveOrUpdate(group);
			this.printObject(oDBResponse);
		}
		else
		{
			this.printObject(oValidResponse);
		}
		return null;
	}
	/**
	 * To delete a user.
	 * @return String
	 */
	public String delete()
	{
		HttpServletRequest request = this.getRequest();
		raise404IFNotAjax(request);
		groupDAO.delete(group,Long.parseLong(request.getParameter("id")));
		return SUCCESS;
	}
	public String groupDataTable()
	{
		raise404IFNotAjax(this.getRequest());
		Session session = null;
		try
		{
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
	    		pFrom = Integer.parseInt(getRequest().getParameter("iDisplayStart"));
			
			
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Query query = session.getNamedQuery("SQL_GET_GROUP_BY_ROWNUM_RANGE");
        	query.setParameter("P_FROM", pFrom);
        	query.setParameter("P_TO", pTo);
        	
        	@SuppressWarnings("unchecked")
			List<Object>  ll= query.list();
        	for(Object row : ll) {
        		groupList.add((Group)row);
        	}
	        	
        	query = session.getNamedQuery("SQL_GET_TOTAL_GROUP");
        	totalRecords = (int) query.uniqueResult();
        	
			session.getTransaction().commit();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (session != null) {
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
		for(Group c : groupList)
		{
		   ArrayList<String> arrObj = new ArrayList<String>();
		   arrObj.add("<a class='label label-positive' href='javascript:void(0);' onclick='createOrEditForm(this)' data-url='"+ getRequest().getContextPath()+ "/setup/group/saveorupdateform?id="+ c.getId() +"'><i class='fontello-icon-edit'></i>Edit</a>");
		   arrObj.add(String.valueOf(c.getId()));
		   arrObj.add(c.getGroupName());
		   arrObj.add(c.getDescription());
		   arrObj.add("<span class='label "+((c.getStatus()==EnumHelper.Status.Active)?"label-success":((c.getStatus()==EnumHelper.Status.Inactive)?"label-negative":((c.getStatus()==EnumHelper.Status.Pending)?"label-warning":"label-default")))+"'>"+c.getStatus().toString()+"</span>");
		   arrObj.add(c.getCreatedBy());
		   arrObj.add(c.getCreatedAt().toString());
		   arrObj.add(c.getModifiedBy());
		   arrObj.add(c.getModifiedAt().toString());
		   o.aaData.add(arrObj);
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

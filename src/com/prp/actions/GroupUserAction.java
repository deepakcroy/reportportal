package com.prp.actions;


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


import com.opensymphony.xwork2.ModelDriven;
import com.prp.models.SessionObject;
import com.prp.models.User;
import com.prp.models.Group;
import com.prp.models.GroupUser;
import com.thoughtworks.xstream.XStream;

import net.aditri.dao.DAO;
import net.aditri.dao.DAOImpl;
import net.aditri.dao.DBResponse;
import net.aditri.dao.HibernateUtil;
import net.aditri.web.utility.BaseWebAction;
import net.aditri.web.utility.ValidationResponse;

public class GroupUserAction extends BaseWebAction implements ModelDriven<GroupUser> {
	private static final long serialVersionUID = 1L;
	private GroupUser groupUser = new GroupUser();
	public GroupUser getModel() {
		return this.groupUser;
	}
	private DAO<Group> groupDAO = new DAOImpl<Group>();
	private DAO<User> userDAO = new DAOImpl<User>();
	public String list()
	{
		List<Group> list1=groupDAO.list(new Group());
		List<User> list2=userDAO.list(new User());
		this.getRequest().setAttribute("userGroups", list1);
		this.getRequest().setAttribute("users", list2);
		return SUCCESS;
	}
	public String listGroupUser()
	{
		raise404IFNotAjax(this.getRequest());
		Query query =null;
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			
			query=session.getNamedQuery("SQL_GET_GROUPUSER_LIST");
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
			String[] users = getRequest().getParameter("us").split(",");
			String[] userGroups = getRequest().getParameter("ugs").split(",");
			List<GroupUser> urgList=new ArrayList<GroupUser>();
			
			User oUser = ((SessionObject)this.getSession().getAttribute("LOGGEDUSER")).getUser();
			for(String ug:userGroups)
			{
				for(String u:users)
				{
					urgList.add(new GroupUser(Long.parseLong("0"),Long.parseLong(ug),Long.parseLong(u),oUser.getUserName(),oUser.getUserName()));
				}
			}
			XStream xstream = new XStream();
			xstream.alias("GroupUsers", List.class);
			
			
			final String xmlRoles = xstream.toXML(urgList);
			System.out.println(xmlRoles);

			
			Session session = null;
			Transaction transaction = null;
			try
			{
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				
				session.doWork(new Work(){
					@Override
					public void execute(Connection connection) throws SQLException {
						String query = "{call GROUP_USER_SP(?,?,?)}";
						CallableStatement stmt = connection.prepareCall(query);

						
						stmt.setString(1, xmlRoles); //CLOB
						stmt.registerOutParameter(2, java.sql.Types.INTEGER);
						//stmt.registerOutParameter(3, java.sql.Types.NVARCHAR);
						stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
						  
						stmt.execute();
						//DBResponse oDBResponse =new DBResponse(stmt.getBoolean(2),stmt.getNString(3));
						DBResponse oDBResponse =new DBResponse(stmt.getBoolean(2),stmt.getString(3));
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
}

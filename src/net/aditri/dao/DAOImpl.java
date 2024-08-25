package net.aditri.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;

public class DAOImpl<T> implements DAO<T> {
	
	@SessionTarget
	Session session;
	
	@TransactionTarget
	Transaction transaction;

	/**
	 * Used to save or update a user.
	 */
	public DBResponse saveOrUpdate(T obj) {
		DBResponse oDBResponse = new DBResponse(true,"Successfully Saved!");
		try {
			session.saveOrUpdate(obj);
		} catch (Exception e) {
			transaction.rollback();
			oDBResponse.setSuccess(false);
			oDBResponse.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return oDBResponse;
	}

	/**
	 * Used to delete a user.
	 */
	@SuppressWarnings("unchecked")
	public void delete(T entity, Long id) {
		try {
			Object obj = session.get(entity.getClass(), id);
			session.delete((T)obj);
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} 
	}
	
	/**
	 * Used to list all the Object List.
	 */
	@SuppressWarnings("unchecked")
	public List<T> list(T entity) {
		String query = "from "+entity.getClass().getSimpleName(); 
		List<T> lst = null;
		try {
			lst = session.createQuery(query).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	/**
	 * Used to list a single Object by Id.
	 */
	@SuppressWarnings("unchecked")
	public <T2>T2 listById(T2 entity,Long id) {
		Object obj = null;
		try {
			obj = session.get(entity.getClass(), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T2)obj;
	}
	
	
}

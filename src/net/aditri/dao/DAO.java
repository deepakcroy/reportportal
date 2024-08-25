package net.aditri.dao;

import java.util.List;



public interface DAO<T> {

	public DBResponse saveOrUpdate(T entity);
	public List<T> list(T entity);
	public <T2>T2 listById(T2 entity,Long id);
	public void delete(T entity, Long id);
}

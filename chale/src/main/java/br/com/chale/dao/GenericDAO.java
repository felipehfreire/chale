package br.com.chale.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GenericDAO<T> implements Serializable {
	
	private static final long serialVersionUID = 2684717930155210023L;
	
	@Inject public EntityManager manager;
	
	/**
	 * Execute a query in database
	 * @param query - query to execute
	 * @param params - params for query(varargs)
	 * @return - A unique result
	 */
	@SuppressWarnings("unchecked")
	public T executeQuerySingleResult(String query, Object... params) {
		Query q = manager.createNamedQuery(query);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i + 1, params[i]);
		}
		List<T> result = q.getResultList();
		//Using this because q.getSingleResult throws Exception when we have no results
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}
	
	/**
	 * Execute a query in database
	 * @param query - query to execute
	 * @param params - params for query(varargs)
	 * @return - A list result
	 */
	@SuppressWarnings("unchecked")
	public List<T> executeQueryListResult(String query, Object... params) {
		Query q = manager.createNamedQuery(query);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i + 1, params[i]);
		}
		return q.getResultList();
	}
	
	/**
	 * Insert the entity in the database
	 * @param entity - entity to insert in database
	 */
	public void insert(T entity) {
		manager.getTransaction().begin();
		manager.persist(entity);
		manager.getTransaction().commit();
	}
	
	/**
	 * Insert the entity in the database
	 * @param entity - entity to insert in database
	 * @param commit - If you don't want to commit now, set false
	 */
	public void insert(T entity, boolean commit) {
		manager.getTransaction().begin();
		manager.persist(entity);
		if (commit) {
			manager.getTransaction().commit();
		}
	}
	
	/**
	 * Update the entity in the database
	 * @param entity - entity to update in database
	 * 
	 **/
	public void update(T entity) {
		manager.getTransaction().begin();
		manager.merge(entity);
		manager.getTransaction().commit();
	}
	
	/**
	 * Update the entity in the database
	 * @param entity - entity to update in database
	 * @param commit - If you don't want to commit now, set false
	 */
	public void update(T entity, boolean commit) {
		manager.getTransaction().begin();
		manager.merge(entity);
		if (commit) {
			manager.getTransaction().commit();
		}
	}
	
	/**
	 * remove the entity from  database
	 * @param entity - entity to remove 
	 */
	public void remove(T entity){
		manager.getTransaction().begin();
		manager.remove(entity);
		manager.getTransaction().commit();
	}
	
	/**
	 * remove the entity from  database
	 * @param entity - entity to remove 
	 * @param commit - If you don't want to commit now, set false
	 */
	public void remove(T entity,boolean commit){
		manager.getTransaction().begin();
		manager.remove(entity);
		if (commit) {
			manager.getTransaction().commit();
		}
	}
}

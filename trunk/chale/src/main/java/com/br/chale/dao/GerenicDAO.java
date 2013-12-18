package com.br.chale.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class GerenicDAO<T> {
	
	public EntityManager entityManager;
	
	public GerenicDAO() {
		entityManager = Persistence.createEntityManagerFactory("chale").createEntityManager();
	}
	
	public void salvar(T entity) {
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.flush();
		entityManager.getTransaction().commit();
	}
	
	public void atualizar(T entity) {
		if (!entityManager.getTransaction().isActive()) {//TODO pode ser que dê pala aqui, verificar, utilizei isso para nao dar detached quando pesquisarmos e atualizarmos a entidade
			entityManager.getTransaction().begin();
		}
		entityManager.merge(entity);
		entityManager.flush();
		entityManager.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> criarNamedQueryResultList(String query, Object... params) {
		Query q = entityManager.createNamedQuery(query);
		for (int i = 1; i <= params.length; i++) {
			q.setParameter(i, params[i-1]);
		}
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public T criarNamedQuerySingleResult(String query, Object... params) {
		Query q = entityManager.createNamedQuery(query);
		for (int i = 1; i <= params.length; i++) {
			q.setParameter(i, params[i-1]);
		}
		List<T> resultados = q.getResultList();
		if (resultados != null && resultados.size() > 0) {
			return resultados.get(0);
		}
		return null;
	}
	
	
	/**
	 *  Utilizado em funcoes no qual o GenericDAO nao abrange
	 * @return 
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}

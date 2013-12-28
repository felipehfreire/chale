package br.com.chale.factory;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Factory that creates a instance of EntityManager for CDI Injection
 * @author Jhonatan
 *
 */
public class FactoryEntityManager {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("chale");

	/**
	 * Every time that we use @Inject for EntityManager, this method will be called
	 * @RequestScoped - Create a association of EntityManager for the actual Request
	 * @see @Produces @RequestScoped
	 */
	@Produces @RequestScoped
	public EntityManager createEntityManager() {
		return factory.createEntityManager();
	}
	
	/**
	 * Execute this method for close the EntityManager
	 * @param manager
	 */
	public void finalize(@Disposes EntityManager manager) {
	      manager.close();
	   }

}

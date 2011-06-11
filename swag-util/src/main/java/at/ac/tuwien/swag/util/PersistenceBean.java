package at.ac.tuwien.swag.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Local
@Singleton
public class PersistenceBean {
	
	@PostConstruct
	public void init() {
		this.factory = Persistence.createEntityManagerFactory( "swag" );
	}
	
	@PreDestroy
	public void destroy() {
		factory.close();
	}
	
	public EntityManager makeEntityManager() {
		return factory.createEntityManager();
	}
	
	private EntityManagerFactory factory;
	
}

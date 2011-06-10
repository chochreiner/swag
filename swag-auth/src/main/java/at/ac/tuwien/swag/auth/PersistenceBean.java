package at.ac.tuwien.swag.auth;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Local
@Stateful
public class PersistenceBean {
	
	@PostConstruct
	public void init() {
		this.factory = Persistence.createEntityManagerFactory( "swag" );
	}
	
	public EntityManager makeEntityManager() {
		return factory.createEntityManager();
	}
	
	private EntityManagerFactory factory;
	
}

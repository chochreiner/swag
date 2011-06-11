package at.ac.tuwien.swag.executor;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import at.ac.tuwien.swag.model.domain.JobEntity;
import at.ac.tuwien.swag.util.PersistenceBean;

@Stateless
public class JobSchedulerBean {

	@PostConstruct
	public void initialize() throws IOException {
		em = persistence.makeEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
			em.persist( new JobEntity( new Date(), new TimerTask() {
				@Override
				public void run() {
					System.out.println();
				}
			} ) );
		tx.commit();
	}
	
	@EJB
	private PersistenceBean persistence;
	
	private EntityManager em;
	
}

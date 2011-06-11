package at.ac.tuwien.swag.executor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.persistence.EntityManager;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.util.MessageHandler;
import at.ac.tuwien.swag.util.PersistenceBean;

@MessageDriven( mappedName="swag.queue.Exec" )
public class ExecutorBean extends MessageHandler {

	@PostConstruct
	public void initialize() throws JMSException {
		EntityManager em = persistence.makeEntityManager();
		
		users  = new UserDAO( em );

		super.initialize( connectionFactory );
	}
	
	public void handle( String msg ) throws JMSException {
		reply( "Hi, Execution service speaking. It was nice to hear from you" );
	}
	
	//**** PRIVATE PARTS
	
	@EJB
	private PersistenceBean persistence;
	
	@Resource(mappedName="swag.JMS")
	private ConnectionFactory connectionFactory;
	
	private UserDAO users;
		
}

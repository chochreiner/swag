package at.ac.tuwien.swag.executor;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.jdbcjobstore.JobStoreCMT;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.JobEntity;
import at.ac.tuwien.swag.util.MessageHandler;
import at.ac.tuwien.swag.util.PersistenceBean;

@MessageDriven( mappedName="swag.queue.Exec" )
public class ExecutorBean extends MessageHandler {

	private StdSchedulerFactory factory;
	private Scheduler           scheduler;
	
	@PostConstruct
	public void initialize() throws JMSException, SchedulerException {
		EntityManager em = persistence.makeEntityManager();
		
		users  = new UserDAO( em );
//
//		Properties p = new Properties();
//		p.put( "org.quartz.jobStore.class", JobStoreCMT.class );
//		p.put( "org.quartz.scheduler.instanceName",          "Sched1" );
//		p.put( "org.quartz.threadPool.class",                "org.quartz.simpl.SimpleThreadPool" );
//		p.put( "org.quartz.threadPool.threadCount",          "10" );
//		p.put( "org.quartz.threadPool.threadPriority",       "5" );
//		p.put( "org.quartz.jobStore.misfireThreshold",       "60000" );
//		p.put( "org.quartz.jobStore.class",                  JobStoreCMT.class.getName() );
//		p.put( "org.quartz.jobStore.dataSource",             "jdbc/PostgresqlPool" );
//		p.put( "org.quartz.jobStore.nonManagedTXDataSource", "jdbc/PostgresqlPool" );
//		p.put( "org.quartz.jobStore.driverDelegateClass",    "org.quartz.impl.jdbcjobstore.StdJDBCDelegate" );
//		
//		factory   = new StdSchedulerFactory( p );
//		scheduler = factory.getScheduler();
		
		super.initialize( connectionFactory );
	}
	
	public void handle( String msg ) throws JMSException, SchedulerException, IOException {
//		reply( "Hi, Execution service speaking. It was nice to hear from you" );
		
//		em = persistence.makeEntityManager();
//		
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//			em.persist( new JobEntity( new Date(), new X() ) );
//		tx.commit();
		
		Properties p = new Properties();
		p.put( "org.quartz.jobStore.class", JobStoreCMT.class );
		p.put( "org.quartz.scheduler.instanceName",          "Sched1" );
		p.put( "org.quartz.threadPool.class",                "org.quartz.simpl.SimpleThreadPool" );
		p.put( "org.quartz.threadPool.threadCount",          "10" );
		p.put( "org.quartz.threadPool.threadPriority",       "5" );
		p.put( "org.quartz.jobStore.misfireThreshold",       "60000" );
		p.put( "org.quartz.jobStore.class",                  JobStoreCMT.class.getName() );
		p.put( "org.quartz.jobStore.driverDelegateClass",    "org.quartz.impl.jdbcjobstore.StdJDBCDelegate" );
		p.put( "org.quartz.jobStore.dataSource",             "myDS" );
		p.put( "org.quartz.jobStore.nonManagedTXDataSource", "myDS" );

		p.put( "org.quartz.dataSource.myDS.driver",         "org.postgresql.Driver" );
		p.put( "org.quartz.dataSource.myDS.URL",            "jdbc:postgresql://localhost/swa" );
		p.put( "org.quartz.dataSource.myDS.user",           "swa" );
		p.put( "org.quartz.dataSource.myDS.password",       "swa11" );
		p.put( "org.quartz.dataSource.myDS.validationQuery", "select 0 from dual" );
				
		factory   = new StdSchedulerFactory( p );
		scheduler = factory.getScheduler();
		
		Object o = null;
		try {
			Context ctx = new InitialContext();
			
			o = ctx.lookup( "jdbc/PostgresqlPool" );
		} catch ( NamingException e ) {
			e.printStackTrace();
		}
		
		System.out.println( o );
		
		JobDetail job     = JobBuilder.newJob().build();
		Trigger   trigger = TriggerBuilder.newTrigger().startNow().build();
		
//		users.beginTransaction();
			scheduler.scheduleJob( job, trigger );
//		users.commitTransaction();
	}
	
	static class X extends Task {
		@Override
		public void run() {
			System.out.println();
		}
	}
	
	//**** PRIVATE PARTS
	
	@EJB
	private PersistenceBean persistence;
	
	private EntityManager em;
	
	@Resource(mappedName="swag.JMS")
	private ConnectionFactory connectionFactory;
	
	private UserDAO users;
		
	private static abstract class Task extends TimerTask implements Serializable {
		private static final long serialVersionUID = -5744511417457052977L;	
	}
}

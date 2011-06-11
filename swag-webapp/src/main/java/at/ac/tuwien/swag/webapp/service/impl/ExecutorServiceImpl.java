package at.ac.tuwien.swag.webapp.service.impl;

import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Queue;

import com.google.inject.Inject;

import at.ac.tuwien.swag.messages.JMSHelper;
import at.ac.tuwien.swag.webapp.service.ExecutorService;

public class ExecutorServiceImpl implements ExecutorService {

	public void ping() throws JMSException {
		jms.send( exec, "PING" );
	}
	
	@Inject
	@Named( "swag.queue.Exec" )
	private Queue exec;
	@Inject
	private JMSHelper jms;
	
}

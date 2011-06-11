package at.ac.tuwien.swag.webapp.service;

import javax.jms.JMSException;

public interface ExecutorService {
	public void ping() throws JMSException;
}

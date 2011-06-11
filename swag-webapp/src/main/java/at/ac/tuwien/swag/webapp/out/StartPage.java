package at.ac.tuwien.swag.webapp.out;

import javax.jms.JMSException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.webapp.service.ExecutorService;

import com.google.inject.Inject;

public class StartPage extends OutPage {
	private static final long serialVersionUID = 1000701427791102913L;

	@Inject
	private ExecutorService exec;
	
	public StartPage(PageParameters parameters) {
        super(parameters);    
        
        try {
        	exec.ping();
        } catch ( JMSException e ) {
			e.printStackTrace();
		}
	}
}

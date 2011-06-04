package at.ac.tuwien.swag.webapp;

import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import at.ac.tuwien.swag.webapp.out.StartPage;

public class SwagApplication extends WebApplication {
		
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<StartPage> getHomePage() {
		return StartPage.class;
	}

	@Override
	public final SwagWebSession newSession( Request request, Response response ) {
		SwagWebSession session = new SwagWebSession( request );

		injector.inject( session );
		
		return session;
	}
	
    /**
     * @see org.apache.wicket.Application#init()
     */
	@Override
	public void init() {
		super.init();

		injector = new GuiceComponentInjector( this, new WebappModule() );
		
		getComponentInstantiationListeners().add( injector );
	}
	
	private GuiceComponentInjector injector;
}

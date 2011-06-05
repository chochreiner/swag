package at.ac.tuwien.swag.webapp;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import at.ac.tuwien.swag.webapp.out.Login;
import at.ac.tuwien.swag.webapp.out.StartPage;

public class SwagApplication extends AuthenticatedWebApplication {
		
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

		// enable Guice injection
		injector = new GuiceComponentInjector( this, new WebappModule() );
		getComponentInstantiationListeners().add( injector );
		
		// enable annotation based authorization
		getSecuritySettings().setAuthorizationStrategy( 
			new AnnotationsRoleAuthorizationStrategy( this )
		);
	}
	
	private GuiceComponentInjector injector;

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return SwagWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return Login.class;
	}
}

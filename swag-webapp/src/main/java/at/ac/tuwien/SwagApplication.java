package at.ac.tuwien;

import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;

import at.ac.tuwien.out.StartPage;

public class SwagApplication extends WebApplication {

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<StartPage> getHomePage() {
		return StartPage.class;
	}

    /**
     * @see org.apache.wicket.Application#init()
     */
	@Override
	public void init() {
		super.init();

		getComponentInstantiationListeners().add(
			new GuiceComponentInjector( this, new WebappModule() )
		);
	}
}

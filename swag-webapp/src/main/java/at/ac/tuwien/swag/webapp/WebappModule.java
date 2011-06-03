package at.ac.tuwien.swag.webapp;

import at.ac.tuwien.swag.webapp.service.LoginService;
import at.ac.tuwien.swag.webapp.service.LoginServiceImpl;

import com.google.inject.AbstractModule;

public class WebappModule extends AbstractModule {

	@Override
	protected void configure() {
		bind( LoginService.class ).to( LoginServiceImpl.class );
	}
}

package at.ac.tuwien;

import at.ac.tuwien.service.LoginService;
import at.ac.tuwien.service.LoginServiceImpl;

import com.google.inject.AbstractModule;

public class WebappModule extends AbstractModule {

	@Override
	protected void configure() {
		bind( LoginService.class ).to( LoginServiceImpl.class );
	}
}

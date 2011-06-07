package at.ac.tuwien.swag.webapp;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.webapp.service.LoginService;
import at.ac.tuwien.swag.webapp.service.PasswordHasher;
import at.ac.tuwien.swag.webapp.service.impl.LoginServiceImpl;
import at.ac.tuwien.swag.webapp.service.impl.PasswordHasherImpl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

public class WebappModule extends ServletModule {

	private static final String PERSISTENCE_UNIT = "swag";
	
    @Override
    protected void configureServlets() {
    	///**** JPA ******************************************//
    	// add JPA beans
        install( new JpaPersistModule( PERSISTENCE_UNIT ) );
        // filter injection requests
        filter( "/*" ).through( PersistFilter.class );
        // initialize JPA in startup
        bind(JPAInitializer.class).asEagerSingleton();

        ///**** DAOS *****************************************//
        bind( UserDAO.class );
        bind( MapDAO.class );

        ///**** WEBAPP SERVICES ******************************//
        bind( LoginService.class   ).to( LoginServiceImpl.class   );
        bind( PasswordHasher.class ).to( PasswordHasherImpl.class );
    }
	
    // dummy helper that initializes JPA on startup
	@Singleton
	public static final class JPAInitializer {
	    @Inject
	    public JPAInitializer(final PersistService service) {
	        service.start();
	    }
	}	
}

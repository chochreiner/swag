package at.ac.tuwien.swag.webapp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import at.ac.tuwien.swag.webapp.service.LoginService;
import at.ac.tuwien.swag.webapp.service.MessageService;
import at.ac.tuwien.swag.webapp.service.SetupService;
import at.ac.tuwien.swag.webapp.service.impl.LoginServiceImpl;
import at.ac.tuwien.swag.webapp.service.impl.MessageServiceImpl;
import at.ac.tuwien.swag.webapp.service.impl.SetupServiceImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class WebappModule extends AbstractModule {

    private static final String PERSISTENCE_UNIT = "swag";

    @Override
    protected void configure() {
        bind(LoginService.class).to(LoginServiceImpl.class);
        bind(MessageService.class).to(MessageServiceImpl.class);
        bind(SetupService.class).to(SetupServiceImpl.class);
    }

    @Provides
    @Singleton
    protected EntityManager provideEM() {
        EntityManagerFactory em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

        return em.createEntityManager();
    }

}

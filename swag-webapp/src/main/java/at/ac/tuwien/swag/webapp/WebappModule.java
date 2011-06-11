package at.ac.tuwien.swag.webapp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import at.ac.tuwien.swag.messages.JMSHelper;
import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.webapp.service.ExecutorService;
import at.ac.tuwien.swag.webapp.service.LogService;
import at.ac.tuwien.swag.webapp.service.LoginService;
import at.ac.tuwien.swag.webapp.service.MessageService;
import at.ac.tuwien.swag.webapp.service.SetupService;
import at.ac.tuwien.swag.webapp.service.impl.ExecutorServiceImpl;
import at.ac.tuwien.swag.webapp.service.impl.LogServiceImpl;
import at.ac.tuwien.swag.webapp.service.impl.LoginServiceImpl;
import at.ac.tuwien.swag.webapp.service.impl.MessageServiceImpl;
import at.ac.tuwien.swag.webapp.service.impl.SetupServiceImpl;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

public class WebappModule extends ServletModule {

    private static final String PERSISTENCE_UNIT = "swag";
    private static final String JMS_FACTORY = "swag.JMS";

    private final Context jndiCtx;

    public WebappModule() {
        try {
            jndiCtx = new InitialContext();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void configureServlets() {
        // /**** JPA ******************************************//
        // add JPA beans
        install(new JpaPersistModule(PERSISTENCE_UNIT));
        // filter injection requests
        filter("/*").through(PersistFilter.class);
        // initialize JPA in startup
        bind(JPAInitializer.class).asEagerSingleton();

        // /**** DAOS *****************************************//
        bind(UserDAO.class);
        bind(MapDAO.class);
        bind(MapUserDAO.class);
        bind(SquareDAO.class);

        // /**** WEBAPP SERVICES ******************************//
        bind(LoginService.class).to(LoginServiceImpl.class);
        bind(MessageService.class).to(MessageServiceImpl.class);
        bind(SetupService.class).to(SetupServiceImpl.class);
        bind(LogService.class).to(LogServiceImpl.class);
        bind( ExecutorService.class ).to( ExecutorServiceImpl.class );
        
        // /**** JMS ******************************************//
        bindJNDI( Queue.class, "swag.queue.Authentication" );
        bindJNDI( Queue.class, "swag.queue.Notification" );
        bindJNDI( Queue.class, "swag.queue.Exec" );

        // default time to wait for message replies
        bindConstant().annotatedWith( Names.named( "MESSAGE_TIMEOUT" ) ).to( 3000l );
    } 
    
    private <T> void bindJNDI(Class<T> c, String jndiName) {
        bind(c).annotatedWith(Names.named(jndiName)).toProvider(new JNDIProvider<T>(jndiName, jndiCtx));
    }

    @Provides
    public JMSHelper provideJMSHelper(Connection connection) throws JMSException {
        return new JMSHelper(connection);
    }

    @Provides
    public Context provideJNDIContext() throws NamingException {
        return jndiCtx;
    }

    @Provides
    public ConnectionFactory provideJMSConnectionFactory(Context jndiCtx) throws NamingException {
        return (ConnectionFactory) jndiCtx.lookup(JMS_FACTORY);
    }

    @Provides
    public Connection provideJMSConnection(ConnectionFactory factory) throws JMSException {
        return factory.createConnection();
    }

    @Provides
    public Session provideJMSSession(Connection connection) throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        connection.start();

        return session;
    }

    // dummy helper that initializes JPA on startup
    @Singleton
    public static final class JPAInitializer {
        @Inject
        public JPAInitializer(final PersistService service) {
            service.start();
        }
    }

    public static final class JNDIProvider<T> implements Provider<T> {
        public JNDIProvider(String jndiName, Context jndiCtx) {
            super();
            this.jndiName = jndiName;
            this.jndiCtx = jndiCtx;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T get() {
            try {
                return (T) jndiCtx.lookup(jndiName);
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }

        private final String jndiName;
        private final Context jndiCtx;
    }

}

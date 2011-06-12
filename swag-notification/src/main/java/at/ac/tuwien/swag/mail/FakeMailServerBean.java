package at.ac.tuwien.swag.mail;

//import com.dumbster.smtp.SimpleSmtpServer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

import com.dumbster.smtp.SimpleSmtpServer;

@Singleton(mappedName="mailserver")
@LocalBean
public class FakeMailServerBean {

    private SimpleSmtpServer server = SimpleSmtpServer.start(2525);

	public void ping() {/***/}
	
	@PostConstruct
	public void init() {
	}
	
    @PreDestroy
    public void stop() {
        server.stop();
    }
}

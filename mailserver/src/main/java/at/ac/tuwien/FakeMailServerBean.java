package at.ac.tuwien;

//import com.dumbster.smtp.SimpleSmtpServer;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

@Singleton(mappedName="mailserver")
@LocalBean
public class FakeMailServerBean {

//    private SimpleSmtpServer server = SimpleSmtpServer.start(2525);

	public void ping() {}
	
    @PreDestroy
    public void stop() {
//        server.stop();
    }
}

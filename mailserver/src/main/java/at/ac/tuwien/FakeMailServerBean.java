package at.ac.tuwien;

import com.dumbster.smtp.SimpleSmtpServer;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

@Singleton
@LocalBean
public class FakeMailServerBean {

    private SimpleSmtpServer server = SimpleSmtpServer.start();

    @PreDestroy
    public void start() {
        server.stop();
    }
}

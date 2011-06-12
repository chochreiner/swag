package at.ac.tuwien.swag.mail;

import at.ac.tuwien.swag.messages.email.EmailRequest;
import at.ac.tuwien.swag.util.MessageHandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;

@MessageDriven(mappedName = "swag.queue.Notification", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MailNotificationBean extends MessageHandler {
    
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @EJB
    private MailService mailService;
    
    public MailNotificationBean() {
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void handle( EmailRequest msg ) throws JMSException {
    	mailService.sendMessage( msg.from, msg.emailAddress, msg.subject, msg.text );
    	
    	reply( false );
    }
}

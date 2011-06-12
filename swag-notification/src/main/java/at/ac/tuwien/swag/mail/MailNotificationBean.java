package at.ac.tuwien.swag.mail;

import at.ac.tuwien.swag.model.dto.MessageDTO;
import at.ac.tuwien.swag.model.dto.UserDTO;
import at.ac.tuwien.swag.util.MessageHandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(mappedName = "swag.queue.Notification", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MailNotificationBean extends MessageHandler {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private MailService mailService;
    
    public MailNotificationBean() {
    }
    
    public void handle( MessageDTO msg ) throws JMSException {
        for (UserDTO toUser : msg.getTo()) {
            try {
                mailService.sendMessage( msg.getFrom().getEmail(), toUser.getEmail(), msg.getSubject(), msg.getText());
            } catch (MessagingException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

}

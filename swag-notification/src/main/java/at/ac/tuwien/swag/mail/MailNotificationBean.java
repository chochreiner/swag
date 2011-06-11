package at.ac.tuwien.swag.mail;

import at.ac.tuwien.swag.model.dto.MessageDTO;
import at.ac.tuwien.swag.model.dto.UserDTO;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(mappedName = "swag.queue.Notification", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MailNotificationBean implements MessageListener {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private MailService mailService;
    
    public MailNotificationBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                Object dto = objectMessage.getObject();
                if (dto instanceof MessageDTO) {
                    MessageDTO messageDTO = (MessageDTO) dto;
                    for (UserDTO toUser : messageDTO.getTo()) {
                        try {
                            mailService.sendMessage(messageDTO.getFrom().getEmail(), toUser.getEmail(), messageDTO.getSubject(), messageDTO.getText());
                        } catch (MessagingException ex) {
                            logger.error(ex.getMessage());
                        }
                    }
                }
            }
        } catch (JMSException ex) {
            logger.error(ex.getMessage());
        }
    }
}

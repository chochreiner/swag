package at.ac.tuwien.swag.mail;

import javax.ejb.Local;

@Local
public interface MailService {
    void sendMessage(String senderAddress, String recipientAddress, String subject, String messageText);
}

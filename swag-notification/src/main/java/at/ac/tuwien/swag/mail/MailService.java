package at.ac.tuwien.swag.mail;

import javax.mail.MessagingException;

public interface MailService {

    void sendMessage(String senderAddress, String recipientAddress, String subject, String messageText) throws MessagingException;
}

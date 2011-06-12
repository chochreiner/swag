package at.ac.tuwien.swag.webapp.service;

import java.util.List;
import java.util.Set;

import at.ac.tuwien.swag.model.dto.MessageDTO;

public interface MessageService {

    public List<MessageDTO> getInMessages(String user);

    public List<MessageDTO> getOutMessages(String user);

    public List<MessageDTO> getNotifications(String user);

    public void sendMessage(String subject, String text, Set<String> reciever, String sender);

    public MessageDTO getMessagebyId(Long id, String user);

    public void updateReadStatus(Long id);

}

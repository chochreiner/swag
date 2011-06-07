package at.ac.tuwien.swag.webapp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import at.ac.tuwien.swag.model.dao.MessageDAO;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.Message;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.model.dto.MessageDTO;
import at.ac.tuwien.swag.model.dto.UserDTO;
import at.ac.tuwien.swag.webapp.service.MessageService;

import com.google.inject.Inject;

public class MessageServiceImpl implements MessageService {

    private MessageDAO messages;
    private UserDAO users;

    @Inject
    public MessageServiceImpl(EntityManager em) {
        messages = new MessageDAO(em);
        users = new UserDAO(em);
    }

    @Override
    public List<MessageDTO> getInMessages(String user) {
        String query =
            "SELECT m FROM Message m LEFT JOIN FETCH m.from LEFT JOIN FETCH m.to AS y WHERE y.name = :username";

        Map<String, String> values = new HashMap<String, String>();
        values.put("username", user);

        List<Message> inMessages = messages.findByQuery(query, values);
        List<MessageDTO> result = new ArrayList<MessageDTO>();

        for (Message m : inMessages) {
            result.add(
            	new MessageDTO(
            		m.getTimestamp(),
            		m.getSubject(),
            		"",
            		false,
            		new UserDTO(
            			m.getFrom().getName(),
            			"",
            			"",
            			"",
            			"",
            			null,
            			null,
            			null
            		),
            		null
            	)
            );
        }

        return result;
    }

    @Override
    public List<MessageDTO> getOutMessages(String user) {
        String query =
            "SELECT m FROM Message m LEFT JOIN FETCH m.from WHERE m.from = :username";

        Map<String, String> values = new HashMap<String, String>();
        values.put("username", user);

        List<Message> inMessages = messages.findByQuery(query, values);
        List<MessageDTO> result = new ArrayList<MessageDTO>();

        for (Message m : inMessages) {
            result.add(
            	new MessageDTO(
            		m.getTimestamp(), 
            		m.getSubject(), 
            		"",
            		false,
            		new UserDTO(
            			m.getFrom().getName(),
            			"",
            			"",
            			"",
            			"",
            			null,
            			null,
            			null
            		),
            		null
            	)
            );
        }

        return result;
    }

    @Override
    public List<MessageDTO> getNotifications(String user) {
        String query =
            "SELECT m FROM Message m LEFT JOIN FETCH m.from LEFT JOIN FETCH m.to AS y WHERE y.name = :username AND m.form = :system";

        Map<String, String> values = new HashMap<String, String>();
        values.put("username", user);
        values.put("system", "system");

        List<Message> inMessages = messages.findByQuery(query, values);
        List<MessageDTO> result = new ArrayList<MessageDTO>();

        for (Message m : inMessages) {
            result.add(
            	new MessageDTO(
            		m.getTimestamp(),
            		m.getSubject(),
            		"",
            		false,
            		new UserDTO(
            			m.getFrom().getName(),
            			"",
            			"",
            			"",
            			"",
            			null,
            			null,
            			null
            		),
            		null
            	)
            );
        }

        return result;
    }

    @Override
    public MessageDTO getMessagebyId(Long id, String user) {

        String query =
            "SELECT m FROM Message m LEFT JOIN FETCH m.from LEFT JOIN FETCH m.to WHERE m.id = :number";

        Map<String, String> values = new HashMap<String, String>();
        values.put("numbver", id.toString());

        List<Message> message = messages.findByQuery(query, values);

        if (message.isEmpty()) {
            return null;
        }

        Message m = message.get(0);

        return new MessageDTO(
        		m.getTimestamp(),
        		m.getSubject(),
        		m.getText(),
        		false,
        		new UserDTO(
            		m.getFrom().getName(),
            		"",
            		"",
            		"",
            		"",
            		null,
            		null,
            		null
            	),
        		null
        );

    }

    @Override
    public void sendMessage(String subject, String text, Set<String> reciever, String sender) {

        Message message = new Message();
        message.setTimestamp(new Date());
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(users.findByName(sender).get(0));

        Set<User> recieverAsUser = new HashSet<User>();
        for (String rec : reciever) {
            recieverAsUser.add(users.findByName(rec).get(0));
        }

        message.setTo(recieverAsUser);

        messages.insert(message);

        // TODO check online status and send mails

    }

    @Override
    public void sendNotification(String subject, String text, String reciever) {

        checkPostmaster();

        Message message = new Message();
        message.setTimestamp(new Date());
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(users.findByName("postmaster").get(0));

        Set<User> recieverAsUser = new HashSet<User>();
        recieverAsUser.add(users.findByName(reciever).get(0));

        message.setTo(recieverAsUser);

        messages.insert(message);

        // TODO check online status and send mails

    }

    private void checkPostmaster() {
        // create postmaster aka root oder so

        if (users.findByName("postmaster").isEmpty()) {
            User user = new User();
            user.setName("postmaster");
            users.insert(user);
        }
    }

}

package at.ac.tuwien.swag.webapp.service.impl;

import static at.ac.tuwien.swag.util.MapMaker.map;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.persistence.EntityManager;

import at.ac.tuwien.swag.messages.JMSHelper;
import at.ac.tuwien.swag.messages.TimeoutExpiredException;
import at.ac.tuwien.swag.messages.email.EmailRequest;
import at.ac.tuwien.swag.model.dao.MessageDAO;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.Message;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.model.dto.MessageDTO;
import at.ac.tuwien.swag.model.dto.UserDTO;
import at.ac.tuwien.swag.webapp.service.LogService;
import at.ac.tuwien.swag.webapp.service.MessageService;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class MessageServiceImpl implements MessageService {

    private MessageDAO messages;
    private UserDAO users;

    @Inject
    @Named( "swag.queue.Notification" )
    private Queue notification; 

    @Inject
    private JMSHelper jms;
    
    @Inject
    @Named("MESSAGE_TIMEOUT")
    private long timeout;
    
    @Inject
    private LogService logger;

    @Inject
    public MessageServiceImpl(EntityManager em) {
        messages = new MessageDAO(em);
        users = new UserDAO(em);
    }

    @Override
    public List<MessageDTO> getInMessages(String username) {
        User user = users.findByUsername(username);

        List<Message>    inMessages = getInbox( user );
        List<MessageDTO> dtos       = new ArrayList<MessageDTO>(inMessages.size());

        for (Message m : inMessages) {
        	MessageDTO dto = new MessageDTO(
                    m.getTimestamp(),
                    m.getSubject(),
                    "",
                    m.getRead(),
                    new UserDTO(
                        m.getFrom().getUsername(),
                        "",
                        "",
                        "",
                        "",
                        false,
                        null
                    ),
                    null
        	);
        	dto.setId( m.getId() );
            dtos.add( dto );
        }

        return dtos;
    }

    @Override
    public List<MessageDTO> getOutMessages(String username) {
        User user = users.findByUsername(username);

        List<Message>    outMessages = getOutbox( user );
        List<MessageDTO> dtos        = new ArrayList<MessageDTO>(outMessages.size());

        for (Message m : outMessages) {
        	MessageDTO dto = new MessageDTO(
        		m.getTimestamp(),
        		m.getSubject(),
        		"",
        		m.getRead(),
        		new UserDTO(
        			m.getFrom().getUsername(),
        			"",
        			"",
        			"",
        			"",
        			false,
        			null
        		),
        		null
        	);
        	dto.setId( m.getId() );
        	dtos.add( dto );
        }

        return dtos;
    }

    @Override
    public List<MessageDTO> getNotifications(String user) {
        String query =
            "SELECT m FROM Message m LEFT JOIN FETCH m.from LEFT JOIN FETCH m.to AS y WHERE y.username = :username AND m.from.username = 'system'";

        Map<String, String> values = new HashMap<String, String>();
        values.put("username", user);

        List<Message> inMessages = messages.findByQuery(query, values);
        List<MessageDTO> result = new ArrayList<MessageDTO>();

        for (Message m : inMessages) {
            MessageDTO dto = new MessageDTO(
            	m.getTimestamp(),
            	m.getSubject(),
            	"",
            	m.getRead(),
            	new UserDTO(
            		m.getFrom().getUsername(),
            		"",
            		"",
            		"",
            		"",
            		false,
            		null
            	),
            	null
            );
            dto.setId( m.getId() );
            result.add( dto );
        }

        return result;
    }

    @Override
    public MessageDTO getMessagebyId(Long id, String user) {
//
//        String query =
//            "SELECT m FROM Message m LEFT JOIN FETCH m.from LEFT JOIN FETCH m.to WHERE m.id = :number";
//
//        Map<String, String> values = new HashMap<String, String>();
//        values.put("number", id.toString());
//
//        List<Message> message = messages.findByQuery(query, values);
//
//        if (message.isEmpty()) {
//            return null;
//        }
//
//        Message m = message.get(0);

    	Message m = messages.findById( id );
    	
        MessageDTO dto = new MessageDTO(
            m.getTimestamp(),
            m.getSubject(),
            m.getText(),
            m.getRead(),
            new UserDTO(
                m.getFrom().getUsername(),
                "",
                "",
                "",
                "",
                false, 
                null
            ),
            null);
        dto.setId( m.getId() );
        
        return dto;
    }

    @Override
    public void sendMessage(String subject, String text, Set<String> reciever, String sender) {
    	List<User> offline = new ArrayList<User>();
    	
        messages.beginTransaction();
        try {
        	Message message = new Message();
        	message.setTimestamp(new Date());
        	message.setSubject(subject);
        	message.setText(text);
        	message.setRead(false);

        	User senderUser = users.findByUsername( sender );  //getUserWithMessages(sender);
        	message.setFrom(senderUser);

        	Set<User> to = message.getTo();
        	for ( String rec : reciever ) {
        		User user = users.findByUsername( rec );
        		
        		if ( !user.getIsOnline() ) offline.add( user );
        		
        		to.add(  users.findByUsername( rec ) );
        	}
        	messages.update(message);
        } finally {
        	messages.commitTransaction();        	
        }

       	sendNotification( subject, text, offline );
        
        logger.logUserAction("send Message", "user [" + sender + "] sent a message.");
    }

    private void sendNotification(String subject, String text, Iterable<User> receivers ) {
    	users.beginTransaction(); 
    	try {
    		for ( User user : receivers ) {
    			try {
					jms.request( notification, Boolean.class,
						new EmailRequest(
							user.getEmail(),
							"swag@swag.com",
							subject,
							text,
							new Date()
						),	
						timeout
					);
				} catch ( JMSException e ) {
				} catch ( TimeoutExpiredException e ) {
				}
    			
    			logger.logUserAction("send Notification", "user [" + user.getUsername() + "] recieved a notification.");	
    		}
    	} finally {
    		users.commitTransaction();
    	}
    }

    @Override
    public void updateReadStatus(Long id) {
    	messages.beginTransaction();
    	try {
    		Message message = messages.findById(id);
    		message.setRead(true);
    		messages.update(message);    		
    	} finally {
    		messages.commitTransaction();
    	}
    }

    private List<Message> getOutbox( User u ) {
    	return messages.findByQuery( "SELECT m FROM Message m WHERE m.from = :user", map( "user", u ) );
    }

    private List<Message> getInbox( User u ) {
    	return messages.findByQuery(
    		"SELECT m FROM Message m JOIN m.to AS to WHERE to = :user AND m.from.username <> 'system'", 
    		map( "user", u ) 
    	);    	
    }

}

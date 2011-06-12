package at.ac.tuwien.swag.messages.email;

import java.io.Serializable;
import java.util.Date;

public class EmailRequest implements Serializable {
	private static final long serialVersionUID = -3395309722388057054L;

	public EmailRequest( String emailAddress, String from, String subject,
			String text, Date timestamp ) {
		super();
		this.emailAddress = emailAddress;
		this.from = from;
		this.subject = subject;
		this.text = text;
		this.timestamp = timestamp;
	}

	public String emailAddress;
	public String from;
	public String subject;
	public String text;
	
	public Date timestamp;
}

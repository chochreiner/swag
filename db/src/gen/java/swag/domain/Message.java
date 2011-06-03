package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "message", uniqueConstraints = {

})
public class Message extends AbstractEntity {
	//*** constructors

	public Message() {/**/
	}

	//*** getters

	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTimestamp() {
		return timestamp;
	}

	@Basic(optional = false)
	public String getSubject() {
		return subject;
	}

	@Basic(optional = false)
	public String getText() {
		return text;
	}

	@ManyToOne
	public User getFrom() {

		return from;
	}

	@ManyToMany
	public Set<User> getTo() {

		if (to == null)
			to = new HashSet<User>();

		return to;
	}

	//*** setters

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public void setTo(Set<User> to) {
		this.to = to;
	}

	//*** alternate setters which allow method chaining

	public Message timestamp(Date timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public Message subject(String subject) {
		this.subject = subject;
		return this;
	}

	public Message text(String text) {
		this.text = text;
		return this;
	}

	public Message from(User from) {
		this.from = from;
		return this;
	}

	public Message to(Set<User> to) {
		this.to = to;
		return this;
	}

	//*** misc

	public String toString() {
		return "Message[" +

		"from: " + toString(getFrom()) + ", " + "subject: "
				+ toString(getSubject()) + ", " + "text: "
				+ toString(getText()) + ", " + "timestamp: "
				+ toString(getTimestamp()) + ", " + "to: " + toString(getTo())

				+ "]";
	}

	//*** PRIVATE PARTS

	private Date timestamp;

	private String subject;

	private String text;

	private User from;

	private Set<User> to;

}

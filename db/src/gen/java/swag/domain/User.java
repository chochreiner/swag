package swag.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {

})
public class User extends AbstractEntity {
	//*** constructors

	public User() {/**/
	}

	public User(List<MapUser> maps) {

		this.maps = maps;

	}

	//*** getters

	@Basic(optional = false)
	public String getName() {
		return name;
	}

	@Basic(optional = false)
	public String getPassword() {
		return password;
	}

	@Basic(optional = false)
	public String getAddress() {
		return address;
	}

	@Basic(optional = false)
	public String getEmail() {
		return email;
	}

	@Basic(optional = false)
	public String getFullname() {
		return fullname;
	}

	@OneToMany(mappedBy = "user")
	public List<MapUser> getMaps() {

		if (maps == null)
			maps = new ArrayList<MapUser>();

		return maps;
	}

	@OneToMany
	public List<Message> getSentMessages() {

		if (sentMessages == null)
			sentMessages = new ArrayList<Message>();

		return sentMessages;
	}

	@ManyToMany
	public Set<Message> getReceivedMessages() {

		if (receivedMessages == null)
			receivedMessages = new HashSet<Message>();

		return receivedMessages;
	}

	//*** setters

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setMaps(List<MapUser> maps) {
		this.maps = maps;
	}

	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public void setReceivedMessages(Set<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	//*** alternate setters which allow method chaining

	public User name(String name) {
		this.name = name;
		return this;
	}

	public User password(String password) {
		this.password = password;
		return this;
	}

	public User address(String address) {
		this.address = address;
		return this;
	}

	public User email(String email) {
		this.email = email;
		return this;
	}

	public User fullname(String fullname) {
		this.fullname = fullname;
		return this;
	}

	public User maps(List<MapUser> maps) {
		this.maps = maps;
		return this;
	}

	public User sentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
		return this;
	}

	public User receivedMessages(Set<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
		return this;
	}

	//*** misc

	public String toString() {
		return "User[" +

		"address: " + toString(getAddress()) + ", " + "email: "
				+ toString(getEmail()) + ", " + "fullname: "
				+ toString(getFullname()) + ", " + "maps: "
				+ toString(getMaps()) + ", " + "name: " + toString(getName())
				+ ", " + "password: " + toString(getPassword()) + ", "
				+ "receivedMessages: " + toString(getReceivedMessages()) + ", "
				+ "sentMessages: " + toString(getSentMessages())

				+ "]";
	}

	//*** PRIVATE PARTS

	private String name;

	private String password;

	private String address;

	private String email;

	private String fullname;

	private List<MapUser> maps;

	private List<Message> sentMessages;

	private Set<Message> receivedMessages;

}

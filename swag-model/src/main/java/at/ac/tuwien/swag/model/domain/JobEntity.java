package at.ac.tuwien.swag.model.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.TimerTask;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
public class JobEntity extends AbstractEntity {
	private static final long serialVersionUID = -6964637365405508726L;

	public JobEntity() {/***/}
	 
	public JobEntity( Date timestamp, TimerTask task ) throws IOException {
		super();
		this.timestamp = timestamp;
		setTask( task );
	}
	
	@Transient
	public TimerTask getTask() throws IOException, ClassNotFoundException {
		return (TimerTask) new ObjectInputStream( new ByteArrayInputStream( serializedJob ) ).readObject();
	}
	
	public void setTask( TimerTask task ) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream    objs  = new ObjectOutputStream( bytes );

		objs.writeObject( task );
		objs.flush();
		
		serializedJob = bytes.toByteArray();
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp( Date timestamp ) {
		this.timestamp = timestamp;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;	
	@Lob
	private byte[] serializedJob;
}

package at.ac.tuwien.swag.model.domain.quartz;

// Generated Jun 11, 2011 5:07:29 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * QrtzCalendars generated by hbm2java
 */
@Entity
@Table(name = "qrtz_calendars", schema = "public")
public class QrtzCalendars implements java.io.Serializable {

	private QrtzCalendarsId id;
	private byte[] calendar;

	public QrtzCalendars() {
	}

	public QrtzCalendars( QrtzCalendarsId id, byte[] calendar ) {
		this.id = id;
		this.calendar = calendar;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "schedName", column = @Column(name = "sched_name", nullable = false, length = 120)),
			@AttributeOverride(name = "calendarName", column = @Column(name = "calendar_name", nullable = false, length = 200)) })
	public QrtzCalendarsId getId() {
		return this.id;
	}

	public void setId( QrtzCalendarsId id ) {
		this.id = id;
	}

	@Column(name = "calendar", nullable = false)
	public byte[] getCalendar() {
		return this.calendar;
	}

	public void setCalendar( byte[] calendar ) {
		this.calendar = calendar;
	}

}

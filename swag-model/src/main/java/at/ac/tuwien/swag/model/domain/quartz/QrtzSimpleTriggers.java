package at.ac.tuwien.swag.model.domain.quartz;

// Generated Jun 11, 2011 5:07:29 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * QrtzSimpleTriggers generated by hbm2java
 */
@Entity
@Table(name = "qrtz_simple_triggers", schema = "public")
public class QrtzSimpleTriggers implements java.io.Serializable {

	private QrtzSimpleTriggersId id;
	private QrtzTriggers qrtzTriggers;
	private long repeatCount;
	private long repeatInterval;
	private long timesTriggered;

	public QrtzSimpleTriggers() {
	}

	public QrtzSimpleTriggers( QrtzTriggers qrtzTriggers, long repeatCount,
			long repeatInterval, long timesTriggered ) {
		this.qrtzTriggers = qrtzTriggers;
		this.repeatCount = repeatCount;
		this.repeatInterval = repeatInterval;
		this.timesTriggered = timesTriggered;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "schedName", column = @Column(name = "sched_name", nullable = false, length = 120)),
			@AttributeOverride(name = "triggerName", column = @Column(name = "trigger_name", nullable = false, length = 200)),
			@AttributeOverride(name = "triggerGroup", column = @Column(name = "trigger_group", nullable = false, length = 200)) })
	public QrtzSimpleTriggersId getId() {
		return this.id;
	}

	public void setId( QrtzSimpleTriggersId id ) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public QrtzTriggers getQrtzTriggers() {
		return this.qrtzTriggers;
	}

	public void setQrtzTriggers( QrtzTriggers qrtzTriggers ) {
		this.qrtzTriggers = qrtzTriggers;
	}

	@Column(name = "repeat_count", nullable = false)
	public long getRepeatCount() {
		return this.repeatCount;
	}

	public void setRepeatCount( long repeatCount ) {
		this.repeatCount = repeatCount;
	}

	@Column(name = "repeat_interval", nullable = false)
	public long getRepeatInterval() {
		return this.repeatInterval;
	}

	public void setRepeatInterval( long repeatInterval ) {
		this.repeatInterval = repeatInterval;
	}

	@Column(name = "times_triggered", nullable = false)
	public long getTimesTriggered() {
		return this.timesTriggered;
	}

	public void setTimesTriggered( long timesTriggered ) {
		this.timesTriggered = timesTriggered;
	}

}

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
 * QrtzCronTriggers generated by hbm2java
 */
@Entity
@Table(name = "qrtz_cron_triggers", schema = "public")
public class QrtzCronTriggers implements java.io.Serializable {

	private QrtzCronTriggersId id;
	private QrtzTriggers qrtzTriggers;
	private String cronExpression;
	private String timeZoneId;

	public QrtzCronTriggers() {
	}

	public QrtzCronTriggers( QrtzTriggers qrtzTriggers, String cronExpression ) {
		this.qrtzTriggers = qrtzTriggers;
		this.cronExpression = cronExpression;
	}

	public QrtzCronTriggers( QrtzTriggers qrtzTriggers, String cronExpression,
			String timeZoneId ) {
		this.qrtzTriggers = qrtzTriggers;
		this.cronExpression = cronExpression;
		this.timeZoneId = timeZoneId;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "schedName", column = @Column(name = "sched_name", nullable = false, length = 120)),
			@AttributeOverride(name = "triggerName", column = @Column(name = "trigger_name", nullable = false, length = 200)),
			@AttributeOverride(name = "triggerGroup", column = @Column(name = "trigger_group", nullable = false, length = 200)) })
	public QrtzCronTriggersId getId() {
		return this.id;
	}

	public void setId( QrtzCronTriggersId id ) {
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

	@Column(name = "cron_expression", nullable = false, length = 120)
	public String getCronExpression() {
		return this.cronExpression;
	}

	public void setCronExpression( String cronExpression ) {
		this.cronExpression = cronExpression;
	}

	@Column(name = "time_zone_id", length = 80)
	public String getTimeZoneId() {
		return this.timeZoneId;
	}

	public void setTimeZoneId( String timeZoneId ) {
		this.timeZoneId = timeZoneId;
	}

}
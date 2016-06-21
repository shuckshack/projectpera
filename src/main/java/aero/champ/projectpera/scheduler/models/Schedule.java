package aero.champ.projectpera.scheduler.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="schedules")
public class Schedule {

	@Id
	private String id;
	
	@Field
	private String scheduleCode;
	
	@Field
	private String cronValue;
	
	@Field
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScheduleCode() {
		return scheduleCode;
	}
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	public String getCronValue() {
		return cronValue;
	}
	public void setCronValue(String cronValue) {
		this.cronValue = cronValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}

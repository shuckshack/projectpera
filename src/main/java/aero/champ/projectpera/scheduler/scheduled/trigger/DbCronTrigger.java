package aero.champ.projectpera.scheduler.scheduled.trigger;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

import aero.champ.projectpera.scheduler.service.TriggerService;

public class DbCronTrigger implements Trigger{

	private String scheduleCode;
	
	public DbCronTrigger(String scheduleCode){
		this.scheduleCode = scheduleCode;
	}
	
	@Autowired
	private TriggerService triggerService;
	
	@Override
	public Date nextExecutionTime(TriggerContext triggerContext) {
		String expression = triggerService.getCronExpression(scheduleCode);
		
		CronTrigger ct = new CronTrigger(expression);
		return ct.nextExecutionTime(triggerContext);
	}

}

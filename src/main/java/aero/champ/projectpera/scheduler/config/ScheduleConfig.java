package aero.champ.projectpera.scheduler.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import aero.champ.projectpera.scheduler.scheduled.BiMonthlyGenerator;
import aero.champ.projectpera.scheduler.scheduled.DailyTimeRetriever;
import aero.champ.projectpera.scheduler.scheduled.ReportGenerator;
import aero.champ.projectpera.scheduler.scheduled.TimeRetriever;
import aero.champ.projectpera.scheduler.scheduled.trigger.DbCronTrigger;
import aero.champ.projectpera.scheduler.service.TriggerService;
import aero.champ.projectpera.scheduler.service.TriggerServiceImpl;

@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer{
	
	@Bean 
	public DbCronTrigger dailyCronTrigger(){
		return new DbCronTrigger("daily");
	}
	
	@Bean
	public DbCronTrigger biMonthlyFirstHalf(){
		return new DbCronTrigger("bimonthFirst");
	}
	
	@Bean
	public DbCronTrigger biMonthlySecondHalf(){
		return new DbCronTrigger("bimonthSecond");
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(threadPoolTaskScheduler());
		/**
		 * 0 10 0 1/1 * ? daily
		 * 0 0 12 16 1/1 ? 16th
		 * 0 0 12 1 1/1 ? 1st
		 */
		//Daily
        taskRegistrar.addTriggerTask(
            new Runnable() { public void run() { dailyRetriever().saveTimeToday(); } },
            dailyCronTrigger()
        );
        
        //16th
        taskRegistrar.addTriggerTask(
            new Runnable() { public void run() { biMonthlyGenerator().generateCutOffReport(); } },
            biMonthlyFirstHalf()
        );
        
        //1st
        taskRegistrar.addTriggerTask(
            new Runnable() { public void run() { biMonthlyGenerator().generateCutOffReport(); } },
            biMonthlySecondHalf()
        );
	}
	
	@Bean(destroyMethod="shutdown")
	public Executor threadPoolTaskScheduler(){
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		
		scheduler.setPoolSize(5);
		return scheduler;
	}
	
	@Bean
	public TimeRetriever dailyRetriever(){
		return new DailyTimeRetriever();
	}
	
	@Bean
	@Scope("prototype")
	public ReportGenerator biMonthlyGenerator(){
		return new BiMonthlyGenerator();
	}
	
	
	
}

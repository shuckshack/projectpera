package aero.champ.projectpera.scheduler.scheduled;

import java.util.Date;

public class DailyTimeRetriever implements TimeRetriever {

	@Override
	public int saveTimeToday() {
		// TODO Auto-generated method stub
		System.out.println("Saving Daily Time For All Employees"+ new Date());
		return 0;
	}

}

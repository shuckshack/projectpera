package aero.champ.projectpera.scheduler.scheduled;

import java.util.Date;

public class BiMonthlyGenerator implements ReportGenerator{

	@Override
	public void generateCutOffReport() {
		System.out.println(System.identityHashCode(this)+": Gets date and retrieves relevant range "+new Date());
	}

}

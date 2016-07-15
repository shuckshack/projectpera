package aero.champ.projectpera.scheduler.scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import aero.champ.projectpera.BO.EmployeeDetails;

public class DailyTimeRetriever implements TimeRetriever {

	@Override
	public int saveTimeToday() {
		// TODO Auto-generated method stub
		System.out.println("Saving Daily Time For All Employees"+ new Date());
		
		List<EmployeeDetails> listEmployee = getEmpListWithInOutDetails();
		
		savelistEmployee(listEmployee);
		
			
		return 0;
	}

	private void savelistEmployee(List<EmployeeDetails> listEmployee) {
		
		//Dom's method here
		
	}

	private List<EmployeeDetails> getEmpListWithInOutDetails() {

		//Ralph's method here
		
		return null;
	}

}

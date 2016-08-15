
package aero.champ.projectpera.scheduler.scheduled;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import aero.champ.projectpera.BO.EmployeeDetails;
import aero.champ.projectpera.BO.TimeInOut;


public class BiMonthlyGeneratorTest extends TestCase {

	private List<EmployeeDetails> employeeDetails;
	private BiMonthlyGenerator biMonthlyGenerator;
	
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		biMonthlyGenerator = new BiMonthlyGenerator();
		employeeDetails = new ArrayList<EmployeeDetails>();
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.HOUR, 8);
		c.add(Calendar.MINUTE, 30);
		dt = c.getTime();
		
		EmployeeDetails empDetail = new EmployeeDetails();
		empDetail.setFirstName("JUAN");
		empDetail.setLastName("DELA CRUZ");
		empDetail.setDate(new Date());
		empDetail.setTeamLeadName("RODRIGO DUTS");
		empDetail.setTimeInOutList(getTimeInOutList(new Date(), dt));
		empDetail.setPosition("Software Engineer");
		empDetail.setDepartment("Software Engineering and Architecture");
		empDetail.setProject("project-test");
		
		EmployeeDetails empDetail2 = new EmployeeDetails();
		empDetail2.setFirstName("JOSE");
		empDetail2.setLastName("RIZAL");
		empDetail2.setDate(new Date());
		empDetail2.setTeamLeadName("ANDRES BONIFACIO");
		empDetail2.setTimeInOutList(getTimeInOutList(new Date(), dt));
		empDetail2.setPosition("Software Engineer");
		empDetail2.setDepartment("Software Engineering and Architecture");
		empDetail2.setProject("project-test2");
		
		employeeDetails.add(empDetail);
		employeeDetails.add(empDetail2);
		
	}


	@Test
	public void testGenerateCutOffReport() {
		biMonthlyGenerator.generateCutOffReport(employeeDetails);
	}
	
	private List<TimeInOut> getTimeInOutList(Date timeIn, Date timeOut){
		List<TimeInOut> timeInOutList = new ArrayList<TimeInOut>();
		for(int i = 0; i < 15; i++){
			TimeInOut timeInOut1stDay = new TimeInOut();
			timeInOut1stDay.setTimeIn(timeIn);
			timeInOut1stDay.setTimeOut(timeOut);
			timeInOutList.add(timeInOut1stDay);
			
		}
		
		return timeInOutList;
	}

}

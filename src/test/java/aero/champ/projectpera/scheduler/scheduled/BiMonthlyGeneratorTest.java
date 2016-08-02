
package aero.champ.projectpera.scheduler.scheduled;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class BiMonthlyGeneratorTest extends TestCase {

//	private List<EmployeeDetails> employeeDetails;
	private BiMonthlyGenerator biMonthlyGenerator;
	
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		biMonthlyGenerator = new BiMonthlyGenerator();
		/*employeeDetails = new ArrayList<EmployeeDetails>();
		
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
		
		List<TimeInOut> timeInOutList = new ArrayList<TimeInOut>();
		
		for(int i = 0; i < 15; i++){
			TimeInOut timeInOut1stDay = new TimeInOut();
			timeInOut1stDay.setTimeIn(new Date());
			timeInOut1stDay.setTimeOut(dt);
			timeInOutList.add(timeInOut1stDay);
			
		}
		empDetail.setTimeInOutList(timeInOutList);
		
		
		EmployeeDetails empDetail2 = new EmployeeDetails();
		empDetail2.setFirstName("JOSE");
		empDetail2.setLastName("RIZAL");
		empDetail2.setDate(new Date());
		empDetail2.setTeamLeadName("ANDRES BONIFACIO");
		
		List<TimeInOut> timeInOutList2 = new ArrayList<TimeInOut>();
		
		for(int i = 0; i < 15; i++){
			TimeInOut timeInOut1stDay = new TimeInOut();
			timeInOut1stDay.setTimeIn(new Date());
			timeInOut1stDay.setTimeOut(dt);
			timeInOutList2.add(timeInOut1stDay);
			
		}
		empDetail2.setTimeInOutList(timeInOutList2);
		
		employeeDetails.add(empDetail);
		employeeDetails.add(empDetail2);*/
		
	}

	/**
	 * Test method for {@link aero.champ.projectpera.scheduler.scheduled.BiMonthlyGenerator#generateCutOffReport()}.
	 */
	@Test
	public void testGenerateCutOffReport() {
		biMonthlyGenerator.generateCutOffReport();
	}

}

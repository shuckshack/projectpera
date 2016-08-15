package aero.champ.projectpera.scheduler.scheduled;

import java.util.List;

import aero.champ.projectpera.BO.EmployeeDetails;

/**
 * The Interface ReportGenerator.
 */
public interface ReportGenerator {


	/**
	 * Generate cut off report.
	 *
	 * @param employeeDetails the employee details
	 */
	public void generateCutOffReport(List<EmployeeDetails> employeeDetails);

}

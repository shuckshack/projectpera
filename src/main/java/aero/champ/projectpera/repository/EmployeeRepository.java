package aero.champ.projectpera.repository;

import aero.champ.projectpera.BO.EmployeeDetails;

import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public interface EmployeeRepository
{
	//~ Methods ----------------------------------

	/**
	 * DOCUMENT ME!
	 *
	 * @param  employeeDetails
	 */
	void insertEmployeeList(List<EmployeeDetails> employeeDetails);
	

	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	List<EmployeeDetails> getEmployeeList();
	

	/**
	 * DOCUMENT ME!
	 *
	 * @param  employeeDetails
	 */
	void updateEmployee(EmployeeDetails employeeDetails);
	

	/**
	 * DOCUMENT ME!
	 *
	 * @param  employeeDetails
	 */
	void insertUpdateEmployeeList(List<EmployeeDetails> employeeDetails);
}

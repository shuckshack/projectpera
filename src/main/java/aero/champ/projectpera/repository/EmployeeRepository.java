package aero.champ.projectpera.repository;

import java.util.List;

import aero.champ.projectpera.BO.EmployeeDetails;

public interface EmployeeRepository {

	void insertUpdateEmployeeList(List<EmployeeDetails> employeeDetails);
	
	List<EmployeeDetails> getEmployeeList();
	
	void updateEmployee(EmployeeDetails employeeDetails);
	
}

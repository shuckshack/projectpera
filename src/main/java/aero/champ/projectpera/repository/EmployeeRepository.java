package aero.champ.projectpera.repository;

import java.util.List;

import aero.champ.projectpera.BO.EmployeeDetails;

public interface EmployeeRepository {

	void insertEmployeeList(List<EmployeeDetails> employeeDetails);
	
	List<EmployeeDetails> getEmployeeList();
	
	void updateEmployee(EmployeeDetails employeeDetails);
	
        void updateEmployeeTimeInOut(EmployeeDetails employeeDetails);
        
        List<String> getEmployeeCardNumFrMongo();
}

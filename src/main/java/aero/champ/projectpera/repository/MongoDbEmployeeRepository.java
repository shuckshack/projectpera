package aero.champ.projectpera.repository;

import java.util.List;

import aero.champ.projectpera.BO.EmployeeDetails;

public class MongoDbEmployeeRepository extends MongoDbRepository implements EmployeeRepository {

	public MongoDbEmployeeRepository(MongoDbConnector connector, String collectionName) {
		super(connector, collectionName);
	}

	@Override
	public void insertEmployeeList(List<EmployeeDetails> employeeDetails) {
		// TODO Auto-generated method stub
		
	}
	
}

package aero.champ.projectpera.repository;

import org.bson.Document;

import java.util.List;

import aero.champ.projectpera.BO.EmployeeDetails;

public class MongoDbEmployeeRepository extends MongoDbRepository implements EmployeeRepository {

	public MongoDbEmployeeRepository(MongoDbConnector connector, String collectionName) {
		super(connector, collectionName);
	}

	@Override
	public void insertEmployeeList(List<EmployeeDetails> employeeDetails) {
		
		for (EmployeeDetails employeeDetail : employeeDetails) {
			Document employeeDocument = new Document();
			employeeDocument.put("firstName", employeeDetail.getFirstName());
			getCollection().insertOne(employeeDocument);
		}
		
	}

	@Override
	public List<EmployeeDetails> getEmployeeList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

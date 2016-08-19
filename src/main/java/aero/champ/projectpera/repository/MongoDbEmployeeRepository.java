package aero.champ.projectpera.repository;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import java.util.List;

import aero.champ.projectpera.BO.EmployeeDetails;

public class MongoDbEmployeeRepository extends MongoDbRepository implements EmployeeRepository {

	public MongoDbEmployeeRepository(MongoDbConnector connector, String collectionName) {
		super(connector, collectionName);
	}

	@Override
	public void insertEmployeeList(List<EmployeeDetails> employeeDetails) {				
		Gson gson = new Gson();
		
		for (EmployeeDetails employeeDetail : employeeDetails) {			
			String employeeJson = gson.toJson(employeeDetail);
			Document employeeDocument = Document.parse(employeeJson);
			getCollection().insertOne(employeeDocument);
		}
		
	}

	@Override
	public List<EmployeeDetails> getEmployeeList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

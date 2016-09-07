package aero.champ.projectpera.repository;

import org.bson.Document;

import com.google.gson.Gson;

import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Sorts.orderBy;
import static com.mongodb.client.model.Sorts.ascending;

import java.util.List;

import aero.champ.projectpera.BO.EmployeeDetails;
import java.util.ArrayList;
import org.bson.conversions.Bson;

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

        /**
         * Get list of all employees sort by cardNumber ascending. Exclude _id field.
         * 
         * @return List 
         */
	@Override
	public List<EmployeeDetails> getEmployeeList() {
                Bson projection = fields(excludeId());
                Bson sort = orderBy(ascending("cardNumber"));
                List<EmployeeDetails> empDetails = new ArrayList<>();
                List<Document> empList = getCollection()
                        .find()
                        .projection(projection)
                        .sort(sort)
                        .into(new ArrayList<Document>());
                Gson gson = new Gson();
                for (Document empDoc : empList) {
                    EmployeeDetails empDetail = gson.fromJson(empDoc.toJson(), EmployeeDetails.class);
                    empDetails.add(empDetail);
                }
                return empDetails;
	}

	@Override
	public void updateEmployee(EmployeeDetails employeeDetails) {
		// TODO Auto-generated method stub
		
	}
	
}

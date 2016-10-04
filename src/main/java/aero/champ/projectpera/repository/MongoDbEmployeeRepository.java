package aero.champ.projectpera.repository;

import org.bson.Document;

import com.google.gson.Gson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Sorts.orderBy;
import static com.mongodb.client.model.Sorts.ascending;

import java.util.List;

import aero.champ.projectpera.BO.EmployeeDetails;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Updates.combine;
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
     * Get list of all employees sort by cardNumber ascending. Exclude _id
     * field.
     *
     * @return List
     */
    @Override
    public List<EmployeeDetails> getEmployeeList() {
        List<EmployeeDetails> empDetails = new ArrayList<>();
        try (MongoCursor<Document> cursor = getCollection()
                .find()
                .projection(fields(excludeId()))
                .sort(orderBy(ascending("cardNumber")))
                .iterator()) {
            while (cursor.hasNext()) {
                Document empDoc = cursor.next();
                EmployeeDetails empDetail = new Gson().fromJson(empDoc.toJson(), EmployeeDetails.class);
                empDetails.add(empDetail);
            }
        }
        return empDetails;
    }

    /**
     * Update employee details document that matches employeeDetails.getCardNumber(is correct key? empNum is all 0 currently).
     * 
     * Currently sets firstName, lastName, position, project, teamLeadName, department
     * 
     * upsert = false(default)
     * 
     * @param employeeDetails EmployeeDetails object with updated fields
     */
    @Override
    public void updateEmployee(EmployeeDetails employeeDetails) {
        // is cardNumber a primary key? Y? N? Maybe?
        Bson filter = eq("cardNumber", employeeDetails.getCardNumber());
        Bson set = combine(
                set("firstName", employeeDetails.getFirstName()),
                set("lastName", employeeDetails.getLastName()),
                set("position", employeeDetails.getPosition()),
                set("project", employeeDetails.getProject()),
                set("teamLeadName", employeeDetails.getTeamLeadName()),
                set("department", employeeDetails.getDepartment())
        );
        getCollection().updateOne(filter, set);
    }
}

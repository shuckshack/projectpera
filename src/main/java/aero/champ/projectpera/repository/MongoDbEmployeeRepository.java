package aero.champ.projectpera.repository;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import aero.champ.projectpera.BO.EmployeeDetails;

import com.google.gson.Gson;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import com.mongodb.client.MongoCursor;

import org.bson.Document;

import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class MongoDbEmployeeRepository extends MongoDbRepository implements EmployeeRepository
{
	//~ Constructors -----------------------------

	/**
	 * Creates a new MongoDbEmployeeRepository object.
	 *
	 * @param  connector
	 * @param  collectionName
	 */
	public MongoDbEmployeeRepository(MongoDbConnector connector, String collectionName)
	{
		super(connector, collectionName);
	}
	//~ Methods ----------------------------------

	/**
	 * DOCUMENT ME!
	 *
	 * @param  employeeDetails
	 */
	@Override
	public void insertEmployeeList(List<EmployeeDetails> employeeDetails)
	{
		Gson gson = new Gson();

		List<String> empDetails = getEmployeeCardNumFrMongo();

		for (EmployeeDetails employeeDetail : employeeDetails)
		{
			if (empDetails.contains(employeeDetail.getCardNumber()))
			{
				updateEmployeeTimeInOut(employeeDetail);
			}
			else
			{
				String employeeJson = gson.toJson(employeeDetail);
				Document employeeDocument = Document.parse(employeeJson);
				getCollection().insertOne(employeeDocument);
			}
		}
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param  employeeDetails
	 */
	public void updateEmployeeTimeInOut(EmployeeDetails employeeDetails)
	{
		Bson filter = eq("cardNumber", employeeDetails.getCardNumber());
		Bson set = set("timeInOutList", employeeDetails.getTimeInOutList());

		getCollection().updateOne(filter, set);
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
	public List<String> getEmployeeCardNumFrMongo()
	{
		Mongo mongo = new Mongo("vl29.champ.aero", 27017);
		DB db = mongo.getDB("hrdb");

		DBCollection collection = db.getCollection("staff");

		BasicDBObject fields = new BasicDBObject();
		// fields.put("cardNumber", 34072);
		BasicDBObject filterResult = new BasicDBObject();
		filterResult.put("cardNumber", 1);
		filterResult.put("_id", 0);

		List<String> empDetails = new ArrayList<>();

		DBCursor cursor = collection.find(fields, filterResult);
		while (cursor.hasNext())
		{
			DBObject cur = cursor.next();
			String id = cur.get("cardNumber").toString();
			empDetails.add(id);
		}

		return empDetails;
	}
	
	/**
	 * Get list of all employees sort by cardNumber ascending. Exclude _id field.
	 *
	 * @return  List
	 */
	@Override
	public List<EmployeeDetails> getEmployeeList()
	{
		List<EmployeeDetails> empDetails = new ArrayList<>();
		try(MongoCursor<Document> cursor = getCollection().find().projection(fields(excludeId())).sort(
					orderBy(ascending("cardNumber"))).iterator())
		{
			while (cursor.hasNext())
			{
				Document empDoc = cursor.next();
				EmployeeDetails empDetail = new Gson().fromJson(empDoc.toJson(), EmployeeDetails.class);
				empDetails.add(empDetail);
			}
		}

		return empDetails;
	}
	
	/**
	 * Update employee details document that matches employeeDetails.getCardNumber(is correct key? empNum is all 0
	 * currently). Currently sets firstName, lastName, position, project, teamLeadName, department upsert =
	 * false(default)
	 *
	 * @param  employeeDetails  EmployeeDetails object with updated fields
	 */
	@Override
	public void updateEmployee(EmployeeDetails employeeDetails)
	{
		// is cardNumber a primary key? Y? N? Maybe?
		Bson filter = eq("cardNumber", employeeDetails.getCardNumber());
		Bson set = combine(
				set("firstName", employeeDetails.getFirstName()),
				set("lastName", employeeDetails.getLastName()),
				set("position", employeeDetails.getPosition()),
				set("project", employeeDetails.getProject()),
				set("teamLeadName", employeeDetails.getTeamLeadName()),
				set("department", employeeDetails.getDepartment()));
		getCollection().updateOne(filter, set);
	}
}

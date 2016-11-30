package aero.champ.projectpera.repository;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import aero.champ.projectpera.BO.EmployeeDetails;
import aero.champ.projectpera.BO.TimeInOut;

import com.google.gson.Gson;

import com.mongodb.client.MongoCursor;
import java.text.SimpleDateFormat;

import org.bson.Document;

import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;


/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class MongoDbEmployeeRepository extends MongoDbRepository implements EmployeeRepository
{
    private static final Logger LOG = LogManager.getLogger(MongoDbEmployeeRepository.class);
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
            LOG.debug("start insertEmployeeList");
		Gson gson = new Gson();

		List<String> empDetails = getEmployeeCardNumFrMongo();

		for (EmployeeDetails employeeDetail : employeeDetails)
		{
			if (empDetails.contains(String.valueOf(employeeDetail.getCardNumber())))
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
     * @param employeeDetails
     */
        @Override
    public void updateEmployeeTimeInOut(EmployeeDetails employeeDetails) {
        LOG.debug("start updateEmployeeTimeInOut");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        Bson filter = eq("cardNumber", employeeDetails.getCardNumber());
        BsonArray timeInOutList = new BsonArray();
        BsonDocument timeInOutDoc;
        for (TimeInOut timeInOut : employeeDetails.getTimeInOutList()) {
            timeInOutDoc = new BsonDocument("timeIn", new BsonString(sdf.format(timeInOut.getTimeIn())));
            timeInOutDoc.append("timeOut", new BsonString(sdf.format(timeInOut.getTimeOut())));
            timeInOutList.add(timeInOutDoc);
        }

        Bson set = set("timeInOutList", timeInOutList);

        getCollection().updateOne(filter, set);
    }
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return
	 */
    @Override
	public List<String> getEmployeeCardNumFrMongo()
	{
		Bson filterResult = fields(excludeId(), include("cardNumber"));

		List<String> empDetails = new ArrayList<>();
                
                
                try (MongoCursor<Document> cursor = getCollection().find().projection(filterResult).iterator()) {
                        while (cursor.hasNext())
                        {
                                Document cur = cursor.next();
                                String id = cur.getString("cardNumber");
                                empDetails.add(id);
                        }
                }
//        cursor.forEachRemaining((doc) -> {
//            empDetails.add(doc.getString("cardNumber"));
//        });
                

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

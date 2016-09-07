package aero.champ.projectpera.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import aero.champ.projectpera.BO.EmployeeDetails;

import com.mongodb.MongoClient;
import org.apache.log4j.Logger;

import static org.junit.Assert.assertTrue;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class MongoDbEmployeeRepositoryTest {

	private static EmployeeRepository employeeRepository;
        
        private static final Logger LOG = Logger.getLogger(MongoDbEmployeeRepositoryTest.class);
	
	@BeforeClass
	public static void setUp() throws Exception {
            LOG.info("=============BeforeClass============");
		MongoClient mongoClient = new MongoClient("vl29.champ.aero", 27017);
		
		MongoDbConnector connector = new MongoDbConnector(mongoClient, "hrdb");
		connector.openConnection();
		
		employeeRepository = new MongoDbEmployeeRepository(connector, "staff");
		((MongoDbEmployeeRepository) employeeRepository).initiateRepository();
		((MongoDbEmployeeRepository) employeeRepository).initializeCollection();
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
            LOG.info("=============AfterClass============");
                ((MongoDbEmployeeRepository) employeeRepository).getCollection()
                        .deleteMany(
                                and(
                                        eq("firstName", "Napoleon"),
                                        eq("lastName","Bonaparte"),
                                        eq("cardNumber",370118)
                                )
                        );
		((MongoDbEmployeeRepository) employeeRepository).closeRepository();
	}
	
	@Test
	public void testInsertEmployeeList() {
            LOG.info("=============testInsertEmployeeList============");
		EmployeeDetails napoleonBonaparte = new EmployeeDetails();
		napoleonBonaparte.setFirstName("Napoleon");
		napoleonBonaparte.setLastName("Bonaparte");
		napoleonBonaparte.setCardNumber(370118);
		
		List<EmployeeDetails> employeeDetailList = new ArrayList<EmployeeDetails>();
		employeeDetailList.add(napoleonBonaparte);
		
		employeeRepository.insertEmployeeList(employeeDetailList);
                List<Document> napoleonEmployee = ((MongoDbEmployeeRepository) employeeRepository).getCollection()
                        .find(
                                eq("cardNumber", 370118)
                        )
                        .into(new ArrayList<Document>());
                assertTrue(napoleonEmployee.size() == 1);
	}
        
    @Test
    public void testGetEmployeeList() {
        LOG.info("=============testGetEmployeeList============");
        List<EmployeeDetails> empDetailsList = employeeRepository.getEmployeeList();
        assertTrue(empDetailsList.size() > 0);
        StringBuilder logInfo;
        for (EmployeeDetails empDetails : empDetailsList) {
            logInfo = new StringBuilder();
            logInfo.append("card number: ").append(empDetails.getCardNumber());
            logInfo.append("|first name: ").append(empDetails.getFirstName());
            logInfo.append("|last name: ").append(empDetails.getLastName());
            LOG.info(logInfo.toString());
        }
    }
	
}

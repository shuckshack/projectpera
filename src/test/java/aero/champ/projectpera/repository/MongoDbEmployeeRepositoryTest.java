package aero.champ.projectpera.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import aero.champ.projectpera.BO.EmployeeDetails;
import aero.champ.projectpera.BO.TimeInOut;
import com.google.gson.Gson;

import com.mongodb.MongoClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.rits.cloning.Cloner;
import java.util.Calendar;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class MongoDbEmployeeRepositoryTest {

	private static EmployeeRepository employeeRepository;
        
        private static final EmployeeDetails NAPOLEON_BONAPARTE = new EmployeeDetails();
	
        private static Bson napoleonFilter;
        
        private static final Logger LOG = LogManager.getLogger(MongoDbEmployeeRepositoryTest.class);
	
	@BeforeClass
	public static void setUp() throws Exception {
            LOG.info("=============BeforeClass============");
            
		MongoClient mongoClient = new MongoClient("vl29.champ.aero");
		
		MongoDbConnector connector = new MongoDbConnector(mongoClient, "hrdb");
		
		employeeRepository = new MongoDbEmployeeRepository(connector, "staff");
		((MongoDbEmployeeRepository) employeeRepository).initiateRepository();
		((MongoDbEmployeeRepository) employeeRepository).initializeCollection();
                
                NAPOLEON_BONAPARTE.setFirstName("Napoleon");
		NAPOLEON_BONAPARTE.setLastName("Bonaparte");
		NAPOLEON_BONAPARTE.setCardNumber(370118);
                NAPOLEON_BONAPARTE.setEmpCode(370118);
                
                napoleonFilter = and(
                        eq("firstName", NAPOLEON_BONAPARTE.getFirstName()),
                        eq("lastName", NAPOLEON_BONAPARTE.getLastName()),
                        eq("cardNumber", NAPOLEON_BONAPARTE.getCardNumber()),
                        eq("empCode", NAPOLEON_BONAPARTE.getEmpCode())
                );
                
                List<EmployeeDetails> employeeDetailList = new ArrayList<EmployeeDetails>();
		employeeDetailList.add(NAPOLEON_BONAPARTE);
		
		employeeRepository.insertEmployeeList(employeeDetailList);
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
                LOG.info("=============AfterClass============");
                ((MongoDbEmployeeRepository) employeeRepository).getCollection()
                        .deleteMany(napoleonFilter);
                ((MongoDbEmployeeRepository) employeeRepository).closeRepository();
	}
	
	@Test
	public void testInsertEmployeeList() {
            LOG.info("=============testInsertEmployeeList============");
                List<Document> napoleonEmployee = ((MongoDbEmployeeRepository) employeeRepository).getCollection()
                        .find(napoleonFilter)
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
    
    @Test
    public void testUpdateEmployee() {
        LOG.info("=============testUpdateEmployee============");
        if (((MongoDbEmployeeRepository) employeeRepository).getCollection()
                        .find(napoleonFilter).first() == null) {
            fail("Napoleon Employee not inserted!");
        } else {
            EmployeeDetails napoleonClone = new Cloner().deepClone(NAPOLEON_BONAPARTE);
            napoleonClone.setPosition("CLONE");
            employeeRepository.updateEmployee(napoleonClone);
            Document updatedDoc = ((MongoDbEmployeeRepository) employeeRepository).getCollection()
                        .find(napoleonFilter).first();
            EmployeeDetails updatedNapoleon = new Gson().fromJson(updatedDoc.toJson(), EmployeeDetails.class);
            
            assertTrue(updatedNapoleon.getPosition().equals("CLONE"));
        }
    }
    
    @Test
    public void testUpdateEmployeeTimeInOutList() {
        LOG.info("=============testUpdateEmployeeTimeInOutList============");
        List<TimeInOut> timeInOutList = new ArrayList<>();
        TimeInOut timeInOut = new TimeInOut();
        timeInOut.setTimeIn(Calendar.getInstance().getTime());
        timeInOut.setTimeOut(Calendar.getInstance().getTime());
        timeInOutList.add(timeInOut);
        EmployeeDetails napoleonClone = new Cloner().deepClone(NAPOLEON_BONAPARTE);
        napoleonClone.setTimeInOutList(timeInOutList);
        employeeRepository.updateEmployeeTimeInOut(napoleonClone);
        Document updatedDoc = ((MongoDbEmployeeRepository) employeeRepository).getCollection()
                        .find(napoleonFilter).first();
        EmployeeDetails updatedNapoleon = new Gson().fromJson(updatedDoc.toJson(), EmployeeDetails.class);
        assertTrue(updatedNapoleon.getTimeInOutList() != null && updatedNapoleon.getTimeInOutList().size() > 0);
    }
    
    @Test
    public void testGetEmployeeCardNumFrMongo() {
        LOG.info("=============testGetEmployeeCardNumFrMongo============");
        List<String> empCardNumbers = employeeRepository.getEmployeeCardNumFrMongo();
        assertTrue(empCardNumbers.contains(String.valueOf(NAPOLEON_BONAPARTE.getCardNumber())));
    }
	
}

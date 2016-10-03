package aero.champ.projectpera.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aero.champ.projectpera.BO.EmployeeDetails;

import com.mongodb.MongoClient;

public class MongoDbEmployeeRepositoryTest {

	private EmployeeRepository employeeRepository;
	
	@Before
	public void setUp() throws Exception {
		MongoClient mongoClient = new MongoClient("vl29.champ.aero", 27017);
		
		MongoDbConnector connector = new MongoDbConnector(mongoClient, "hrdb");
		connector.openConnection();
		
		employeeRepository = new MongoDbEmployeeRepository(connector, "staff");
		((MongoDbEmployeeRepository) employeeRepository).initiateRepository();
		((MongoDbEmployeeRepository) employeeRepository).initializeCollection();
	}
	
	@After
	public void tearDown() throws Exception {
		((MongoDbEmployeeRepository) employeeRepository).closeRepository();
	}
	
	@Test
	public void testInsertEmployeeList() {
		EmployeeDetails napoleonBonaparte = new EmployeeDetails();
		napoleonBonaparte.setFirstName("Napoleon");
		napoleonBonaparte.setLastName("Bonaparte");
		napoleonBonaparte.setCardNumber(370118);
		
		List<EmployeeDetails> employeeDetailList = new ArrayList<EmployeeDetails>();
		employeeDetailList.add(napoleonBonaparte);
		
		employeeRepository.insertUpdateEmployeeList(employeeDetailList);
	}
	
}

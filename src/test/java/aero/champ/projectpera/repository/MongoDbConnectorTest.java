package aero.champ.projectpera.repository;

import org.junit.Test;

import com.mongodb.MongoClient;

import aero.champ.projectpera.util.PropertyFile;

public class MongoDbConnectorTest {

	@Test
	public void testConnection() throws Exception {
		
		MongoClient mongoClient = new MongoClient("vl29.champ.aero", 27017);
		
		MongoDbConnector connector = new MongoDbConnector(mongoClient, "hrdb");
		connector.openConnection();
		connector.closeConnection();
	}
	
}

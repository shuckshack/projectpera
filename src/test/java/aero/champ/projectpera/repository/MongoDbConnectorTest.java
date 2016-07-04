package aero.champ.projectpera.repository;

import org.junit.Test;

public class MongoDbConnectorTest {

	@Test
	public void testConnection() throws Exception {
		MongoDbConnector connector = new MongoDbConnector();
		connector.startConnection();
	}
	
}

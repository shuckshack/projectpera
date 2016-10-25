package aero.champ.projectpera.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/*import aero.champ.projectpera.util.PropertyFile;*/

public class MongoDbConnector implements DatabaseConnector {
	
	private MongoDatabase database;
	private MongoClient mongoClient;
	private String databaseName;

	public MongoDbConnector(MongoClient mongoClient, String databaseName) {
		this.mongoClient = mongoClient;
		this.databaseName = databaseName;
	}
	
	@Override
	public void openConnection() throws Exception {
		database = mongoClient.getDatabase(databaseName);
	}

	@Override
	public void closeConnection() {
		mongoClient.close();
	}
	
	protected MongoDatabase getDatabase() {
		return this.database;
	}
	
}

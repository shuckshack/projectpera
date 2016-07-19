package aero.champ.projectpera.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import aero.champ.projectpera.util.PropertyFile;

public class MongoDbConnector implements DatabaseConnector {
	
	public static final String CONFIG_FILE = "mongodb.properties";
	public static final String KEY_HOST = "db.mongo.host";
	public static final String KEY_PORT = "db.mongo.port";
	public static final String KEY_DB = "db.mongo.database";
	
	private MongoDatabase database;
	private MongoClient mongoClient;

	@Override
	public void openConnection() throws Exception {
		PropertyFile propertyFile = new PropertyFile(CONFIG_FILE);
		String host = propertyFile.getValue(KEY_HOST);
		Integer port = Integer.parseInt(propertyFile.getValue(KEY_PORT));
		String databaseName = propertyFile.getValue(KEY_DB);
		
		mongoClient = new MongoClient(host, port);
		database = mongoClient.getDatabase(databaseName);
	}

	@Override
	public void closeConnection() {
		mongoClient.close();
	}	
	
}

package aero.champ.projectpera.repository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public abstract class MongoDbRepository extends DatabaseRepository {
	
	private MongoCollection<Document> collection;
	private String collectionName;
	
	public MongoDbRepository(MongoDbConnector connector, String collectionName) {
		super(connector);
		this.collectionName = collectionName;
	}
	
	public void initializeCollection() {
		collection = ((MongoDbConnector) getConnector()).getDatabase().getCollection(collectionName);
	}
	
	public MongoCollection<Document> getCollection() {
		return this.collection;
	}
	
}

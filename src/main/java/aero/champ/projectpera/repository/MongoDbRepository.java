package aero.champ.projectpera.repository;

public abstract class MongoDbRepository extends DatabaseRepository {

	public MongoDbRepository(MongoDbConnector connector) {
		super(connector);
	}
	
}

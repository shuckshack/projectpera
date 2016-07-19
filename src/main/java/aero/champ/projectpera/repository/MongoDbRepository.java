package aero.champ.projectpera.repository;

public abstract class MongoDbRepository extends DatabaseRepository {

	public MongoDbRepository() {
		super(new MongoDbConnector());
	}
	
}

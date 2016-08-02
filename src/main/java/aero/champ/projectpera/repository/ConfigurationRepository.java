package aero.champ.projectpera.repository;

public class ConfigurationRepository extends MongoDbRepository {

	public ConfigurationRepository(MongoDbConnector connector) {
		super(connector, "");
	}
	
}

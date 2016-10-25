package aero.champ.projectpera.repository;

public abstract class DatabaseRepository {

	private DatabaseConnector connector;

	public DatabaseRepository(DatabaseConnector connector) {	
		this.connector = connector;
	}
	
	public boolean initiateRepository() {
		
		try {
			connector.openConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean closeRepository() {
		
		try {
			connector.closeConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}

	public DatabaseConnector getConnector() {
		return connector;
	}
	
}

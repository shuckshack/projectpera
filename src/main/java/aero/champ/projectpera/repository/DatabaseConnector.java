package aero.champ.projectpera.repository;

public interface DatabaseConnector {
	
	public void openConnection() throws Exception;
	
	public void closeConnection() throws Exception;

}

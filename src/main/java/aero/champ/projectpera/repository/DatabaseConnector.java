package aero.champ.projectpera.repository;

public interface DatabaseConnector {
	
	public void startConnection() throws Exception;
	
	public void endConnection() throws Exception;

}

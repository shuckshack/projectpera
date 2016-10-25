package aero.champ.projectpera.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {
	
	private Properties properties;
	
	public PropertyFile(String propertyFile) throws IOException {
		
		InputStream inputStream = null;
		
		try {
			properties = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);
			
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propertyFile + "' not found");
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
	
	public String getValue(String key) {
		return properties.getProperty(key);
	}

}

package aero.champ.projectpera.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

public class PropertyFileTest {

	@Test
	public void testConstruction() throws Exception {			
		PropertyFile property = new PropertyFile("sample-config.properties");
		
		assertNotNull(property);		
	}
	
	@Test(expected=IOException.class)
	public void testConstructionFailure() throws Exception {
		PropertyFile property = new PropertyFile("bogus-file.properties");
	}
	
	@Test
	public void testDataRetrieval() throws Exception {
		PropertyFile property = new PropertyFile("sample-config.properties");
		assertEquals(property.getValue("key1"), "value1");
		assertEquals(property.getValue("key2"), "value2");
		assertEquals(property.getValue("key3"), "value3");		
	}
	
}

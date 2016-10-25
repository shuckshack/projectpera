package aero.champ.projectpera.conf;

import org.junit.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConfigurationTest {

	@Test
	public void testConfiguration() {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
		        "classpath:aero/champ/projectpera/conf/application-context.xml");
	}
	
}

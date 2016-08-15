package aero.champ.projectpera.parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aero.champ.projectpera.BO.EmployeeDetails;
import aero.champ.projectpera.repository.EmployeeRepository;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

public class EmployeeCSVParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeCSVParser.class);
	
	private char delimeter;
	
	private EmployeeRepository employeeRepository;
	
	public EmployeeCSVParser() {
		this.delimeter = CSVParser.DEFAULT_SEPARATOR;
	}
	
	public EmployeeCSVParser(char delimeter) {
		this.delimeter = delimeter;
	}
	/**
	 * Parses the input CSV file.
	 * 
	 * @param filename
	 * @return List<String[]>
	 */
	@SuppressWarnings("resource")
	public List<String[]> parseFile(String filename) {
		
		List<String[]> employeeTimeDetailslist = new ArrayList<String[]>();
		CSVReader reader;
		try {
				reader = new CSVReader(new FileReader(filename), getDelimeter());
				employeeTimeDetailslist = reader.readAll();
	
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		//transformation logic for converting List<String[]> to List<EmployeeDetails>
     
		List<EmployeeDetails> employeeList = new ArrayList<>();
		employeeRepository.insertEmployeeList(employeeList);
		
		return employeeTimeDetailslist;
	
	}

	public char getDelimeter() {
		return delimeter;
	}

	public void setDelimeter(char delimeter) {
		this.delimeter = delimeter;
	}
	
	/**
	 * For testing purposes only.
	 */
	public static void main(String[] args) {
		
		String file = "src//main//resources//data//29APR16-DailyFILO.csv";
		
		EmployeeCSVParser parser = new EmployeeCSVParser('\n');
		List<String[]> list = parser.parseFile(file);
		
		for (String[] details: list) {
			
			for (int i = 0, j = details.length; i < j; i++) {
				System.out.println(details[i]);
			}
		}
	}

	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
}

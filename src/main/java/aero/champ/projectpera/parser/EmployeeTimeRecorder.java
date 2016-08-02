package aero.champ.projectpera.parser;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import aero.champ.projectpera.sql.bean.FalcoEmployee;
import aero.champ.projectpera.sql.dao.FalcoTransactionsDao;

public class EmployeeTimeRecorder {

	private FalcoTransactionsDao dao;
	
	public EmployeeTimeRecorder() {
		this.dao = new FalcoTransactionsDao();
	}
	
	public List<List<FalcoEmployee>> getFirstInLastOut(String dailyDate) {
		
		List<FalcoEmployee> falcoCompleteEmployeeeList = this.dao.getDetails(dailyDate);
		
		String cardNo = new String();
		
		List<FalcoEmployee> perEmployeeList = new ArrayList<FalcoEmployee>();
		
		List<List<FalcoEmployee>> segregatedEmployeeList = new ArrayList<List<FalcoEmployee>>();
		
		for (FalcoEmployee employee: falcoCompleteEmployeeeList) {
			
			if (cardNo.equals(employee.getCardNo())) {
				
				perEmployeeList.add(employee);
				
			} else {
				
				segregatedEmployeeList.add(perEmployeeList);
				perEmployeeList = new ArrayList<FalcoEmployee>();
				
				perEmployeeList.add(employee);
				cardNo = employee.getCardNo();
				
			}
			
		}
		
		return segregatedEmployeeList;
		
	}
	
	public static void main(String[] args) throws IOException {
		
		EmployeeTimeRecorder recorder = new EmployeeTimeRecorder();
		
		int startDay = 16;
		int endDay = 31;

		PrintStream writer = new PrintStream(new File("d:/testfile.txt"));
		
		// YYYY/MM/
		String dateTemplate = "2016/07/";
		
		for (int day = startDay; day <= endDay; day++) {
			
			String queryDate = dateTemplate + day;
			
			System.out.println(queryDate);
			
			writer.println("===================================================================== \n\n");
			writer.println(queryDate);
			
			List<List<FalcoEmployee>> list = recorder.getFirstInLastOut(queryDate);
			
			for (List<FalcoEmployee> oneEmployee: list) {
				
				System.out.println("Employee: ");
				
				if (oneEmployee != null && oneEmployee.size() > 0) {
					System.out.println(oneEmployee.get(0).getTrName());
					writer.println(oneEmployee.get(0).getTrName());
				}
				
				for (int i = 0; i < oneEmployee.size(); i++) {
					
					FalcoEmployee empDetails = oneEmployee.get(i);
					
					if (i == 0 || i == oneEmployee.size()-1) {
						System.out.println("CardNo: " + empDetails.getCardNo() + " | Date: " + empDetails.getTrDate() + " | Time: " + empDetails.getTrTime());
						writer.println("CardNo: " + empDetails.getCardNo() + " | Date: " + empDetails.getTrDate() + " | Time: " + empDetails.getTrTime());
					}
					
				}
				
			}
			
		}
		
		writer.flush();
		writer.close();
		writer = null;
		
	}
}

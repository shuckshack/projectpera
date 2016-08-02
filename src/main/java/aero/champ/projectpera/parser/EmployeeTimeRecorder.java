package aero.champ.projectpera.parser;

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
	
	public static void main(String[] args) {
		
		EmployeeTimeRecorder recorder = new EmployeeTimeRecorder();
		List<List<FalcoEmployee>> list = recorder.getFirstInLastOut("2016/08/01");
		
		for (List<FalcoEmployee> oneEmployee: list) {
			
			System.out.println("Employee: ");
			
			if (oneEmployee != null && oneEmployee.size() > 0) {
				System.out.println(oneEmployee.get(0).getTrName());
			}
			
			for (FalcoEmployee empDetails: oneEmployee) {
				
				System.out.println("CardNo: " + empDetails.getCardNo() + " | Time: " + empDetails.getTrTime());
				
			}
			
		}
	}
}

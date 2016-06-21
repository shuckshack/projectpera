package aero.champ.projectpera.BO;

import java.util.Date;

public class EmployeeDetails {
	
	
	
	private String firstName;
	
	private String lastName;
	
	private int empCode;
	
	private String department;
	
	private Date date;
	
	private int timeIn;
	
	private int timeOut;
	
	private boolean isLate;
	
	private boolean isNoSwipe;
	
	private String teamLeadName;
	
	private int totalRenderedHoursMins;
	
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getEmpCode() {
		return empCode;
	}

	public void setEmpCode(int empCode) {
		this.empCode = empCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(int timeIn) {
		this.timeIn = timeIn;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public boolean isLate() {
		return isLate;
	}

	public void setLate(boolean isLate) {
		this.isLate = isLate;
	}

	public boolean isNoSwipe() {
		return isNoSwipe;
	}

	public void setNoSwipe(boolean isNoSwipe) {
		this.isNoSwipe = isNoSwipe;
	}

	public String getTeamLeadName() {
		return teamLeadName;
	}

	public void setTeamLeadName(String teamLeadName) {
		this.teamLeadName = teamLeadName;
	}

	public int getTotalRenderedHoursMins() {
		return totalRenderedHoursMins;
	}

	public void setTotalRenderedHoursMins(int totalRenderedHoursMins) {
		this.totalRenderedHoursMins = totalRenderedHoursMins;
	}
	
	
	
	
	
	
	

}

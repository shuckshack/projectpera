package aero.champ.projectpera.BO;

import java.util.Date;
import java.util.List;

/**
 * The Class EmployeeDetails.
 */
public class EmployeeDetails {

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The emp code. */
	private int empCode;

	/** The department. */
	private String department;

	/** The date. */
	private Date date;

	/** The time in. */
	private List<TimeInOut> timeInOutList;

	/** The is late. */
	private boolean isLate;

	/** The is no swipe. */
	private boolean isNoSwipe;

	/** The team lead name. */
	private String teamLeadName;

	/** The total rendered hours mins. */
	private int totalRenderedHoursMins;
	
	/** The position. */
	private String position;
	
	/** The project. */
	private String project;

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the emp code.
	 *
	 * @return the emp code
	 */
	public int getEmpCode() {
		return empCode;
	}

	/**
	 * Sets the emp code.
	 *
	 * @param empCode the new emp code
	 */
	public void setEmpCode(int empCode) {
		this.empCode = empCode;
	}

	/**
	 * Gets the department.
	 *
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Sets the department.
	 *
	 * @param department the new department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}



	/**
	 * Checks if is late.
	 *
	 * @return true, if is late
	 */
	public boolean isLate() {
		return isLate;
	}

	/**
	 * Sets the late.
	 *
	 * @param isLate the new late
	 */
	public void setLate(boolean isLate) {
		this.isLate = isLate;
	}

	/**
	 * Checks if is no swipe.
	 *
	 * @return true, if is no swipe
	 */
	public boolean isNoSwipe() {
		return isNoSwipe;
	}

	/**
	 * Sets the no swipe.
	 *
	 * @param isNoSwipe the new no swipe
	 */
	public void setNoSwipe(boolean isNoSwipe) {
		this.isNoSwipe = isNoSwipe;
	}

	/**
	 * Gets the team lead name.
	 *
	 * @return the team lead name
	 */
	public String getTeamLeadName() {
		return teamLeadName;
	}

	/**
	 * Sets the team lead name.
	 *
	 * @param teamLeadName the new team lead name
	 */
	public void setTeamLeadName(String teamLeadName) {
		this.teamLeadName = teamLeadName;
	}

	/**
	 * Gets the total rendered hours mins.
	 *
	 * @return the total rendered hours mins
	 */
	public int getTotalRenderedHoursMins() {
		return totalRenderedHoursMins;
	}

	/**
	 * Sets the total rendered hours mins.
	 *
	 * @param totalRenderedHoursMins the new total rendered hours mins
	 */
	public void setTotalRenderedHoursMins(int totalRenderedHoursMins) {
		this.totalRenderedHoursMins = totalRenderedHoursMins;
	}

	/**
	 * @return the timeInOutList
	 */
	public List<TimeInOut> getTimeInOutList() {
		return timeInOutList;
	}

	/**
	 * @param timeInOutList the timeInOutList to set
	 */
	public void setTimeInOutList(List<TimeInOut> timeInOutList) {
		this.timeInOutList = timeInOutList;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

}

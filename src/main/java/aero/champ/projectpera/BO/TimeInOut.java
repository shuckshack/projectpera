package aero.champ.projectpera.BO;

import java.util.Date;

public class TimeInOut {
	
	/** The time in. */
	private Date timeIn;
	
	/** The time out. */
	private Date timeOut;
	

	/** The total time. */
	private double totalTime;
	
	/**
	 * @return the timeIn
	 */
	public Date getTimeIn() {
		return timeIn;
	}

	/**
	 * @param timeIn the timeIn to set
	 */
	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}

	/**
	 * @return the timeOut
	 */
	public Date getTimeOut() {
		return timeOut;
	}

	/**
	 * @param timeOut the timeOut to set
	 */
	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * @return the totalTime
	 */
	public double getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime the totalTime to set
	 */
	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
		
	}


	

}

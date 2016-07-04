package aero.champ.projectpera.BO;

import java.util.Date;

public class TimeInOut {
	
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

	/** The time in. */
	private Date timeIn;
	
	/** The time out. */
	private Date timeOut;
	

}

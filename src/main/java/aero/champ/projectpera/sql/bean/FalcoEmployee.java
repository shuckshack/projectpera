package aero.champ.projectpera.sql.bean;

public class FalcoEmployee {
	
	private int ID;
	
	private String trDate;
	
	private String trTime;
	
	private String cardNo;
	
	/* determines which side of which door */
	private String unitNo;
	
	/* in or out */
	private String transaction;
	
	/* in or out */
	private String trCode;
	
	/* determines which door */
	private String trController;
	
	/* employee name*/
	private String trName;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTrDate() {
		return trDate;
	}

	public void setTrDate(String trDate) {
		this.trDate = trDate;
	}

	public String getTrTime() {
		return trTime;
	}

	public void setTrTime(String trTime) {
		this.trTime = trTime;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getTrCode() {
		return trCode;
	}

	public void setTrCode(String trCode) {
		this.trCode = trCode;
	}

	public String getTrController() {
		return trController;
	}

	public void setTrController(String trController) {
		this.trController = trController;
	}

	public String getTrName() {
		return trName;
	}

	public void setTrName(String trName) {
		this.trName = trName;
	}
	
}

package aero.champ.projectpera.sql.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import aero.champ.projectpera.sql.bean.FalcoEmployee;
import aero.champ.projectpera.sql.connection.SqlServerConnectionManager;

public class FalcoTransactionsDao {
	
	private SqlServerConnectionManager sqlServer;
	
	private Connection conn;
	
	private static final int ID = 1;
	private static final int DATE = 2;
	private static final int TIME = 3;
	private static final int CARD_NO = 4;
	private static final int UNIT_NO = 5;
	private static final int TRANSACTION = 6;
	private static final int CODE = 7;
	private static final int CONTROLLER = 8;
	private static final int NAME = 9;
	
	public FalcoTransactionsDao() {
		sqlServer = new SqlServerConnectionManager();
		sqlServer.dbConnect("jdbc:sqlserver://FALCOWEB\\SQLEXPRESS;databaseName=DataDB", "sa",
	               "passw@rd1234");
		
		this.conn = sqlServer.getConn();
	}
	
	private ResultSet getDailyTransactions(String dailyDate) {
		
		Statement statement;
		
		ResultSet rs = null;
		try {
			statement = ((java.sql.Connection) conn).createStatement();
			rs = statement.executeQuery(getTransactionsSql(dailyDate));
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	private String getTransactionsSql(String dailyDate) {
		
		StringBuffer  getTransactionsSql = new StringBuffer();
		getTransactionsSql.append("SELECT [ID] ");
		getTransactionsSql.append("      ,[TrDate] ");
		getTransactionsSql.append("      ,[TrTime] ");
		getTransactionsSql.append("      ,[CardNo] ");
		getTransactionsSql.append("      ,[UnitNo] ");
		getTransactionsSql.append("      ,[Transaction] ");
		getTransactionsSql.append("      ,[TrCode] ");
		getTransactionsSql.append("      ,[TrController] ");
		getTransactionsSql.append("      ,[TrName] ");
		getTransactionsSql.append("  FROM [DataDB].[dbo].[tblTransaction] ");
		getTransactionsSql.append(" WHERE TrDate = '").append(dailyDate).append("' ");
		getTransactionsSql.append(" ORDER BY [CardNo],[ID] ASC;");	
		
		return getTransactionsSql.toString();
	}
	
	/**
	 * In this format '2016/08/01' YYYY-MM-DD
	 * 
	 * @param dailyDate
	 * @return
	 */
	public List<FalcoEmployee> getDetails(String dailyDate) {
		
		ResultSet dailyTransactions = getDailyTransactions(dailyDate);
		
		FalcoEmployee employee;
		List<FalcoEmployee> falcoEmployeeList = new ArrayList<FalcoEmployee>();
		
		final String UNKNOWN_CARD_NO = "FFFFFFFFFF";
		
		try {
			while (dailyTransactions.next()) {
				
				if (!dailyTransactions.getString(CARD_NO).equals(UNKNOWN_CARD_NO)) {
					
					employee = new FalcoEmployee();
					
					employee.setID(dailyTransactions.getInt(ID));
					employee.setTrDate(dailyTransactions.getString(DATE));
					employee.setTrTime(dailyTransactions.getString(TIME));
					employee.setCardNo(dailyTransactions.getString(CARD_NO));
					employee.setUnitNo(dailyTransactions.getString(UNIT_NO));
					employee.setTransaction(dailyTransactions.getString(TRANSACTION));
					employee.setTrCode(dailyTransactions.getString(CODE));
					employee.setTrController(dailyTransactions.getString(CONTROLLER));
					employee.setTrName(dailyTransactions.getString(NAME));
					
					falcoEmployeeList.add(employee);
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return falcoEmployeeList;
	}
	
}

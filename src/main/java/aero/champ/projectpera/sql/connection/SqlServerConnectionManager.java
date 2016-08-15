package aero.champ.projectpera.sql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlServerConnectionManager {

	private static final String SQL_SERVER_DRIVER_STRING = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	private Connection conn;
	
	private String dbConnectionString;
	
	private String dbUserName;
	
	private String dbPassword;
	
	public SqlServerConnectionManager() {
		
	}
	
	public SqlServerConnectionManager(String dbConnectionString, String dbUserName, String dbPassword) {
		this.dbConnectionString = dbConnectionString;
		this.dbUserName = dbUserName;
		this.dbPassword = dbPassword;
	}
	
	public String getDbConnectionString() {
		return dbConnectionString;
	}

	public void setDbConnectionString(String dbConnectionString) {
		this.dbConnectionString = dbConnectionString;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public void dbConnect(String db_connect_string,
	            String db_userid,
	            String db_password)
	{
         try {
			Class.forName(SQL_SERVER_DRIVER_STRING);
			this.conn = DriverManager.getConnection(db_connect_string,
	                  db_userid, db_password);
	         sampleQuery(conn);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	public Connection getConn() {
		return this.conn;
	}
	
	private void sampleQuery(Connection conn) {
		   Statement statement;
		try {
			statement = conn.createStatement();
			
			//String queryString = "select * from sysobjects where type='u'";
	         ResultSet rs = statement.executeQuery(getTestSelect());
//	         while (rs.next()) {
//	            System.out.println(rs.getString(1)+":"+rs.getString(9)+":"+rs.getString(2)+":"+rs.getString(3)+":"+rs.getString(4)+":"+rs.getString(5)+":"+rs.getString(6));
//	         }
	         rs = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	         
	   }
	   
	   public String getTestSelect() {
		   
		   StringBuffer  varname1 = new StringBuffer();
		   varname1.append("SELECT TOP 500 [ID] ");
		   varname1.append("      ,[TrDate] ");
		   varname1.append("      ,[TrTime] ");
		   varname1.append("      ,[CardNo] ");
		   varname1.append("      ,[UnitNo] ");
		   varname1.append("      ,[Transaction] ");
		   varname1.append("      ,[TrCode] ");
		   varname1.append("      ,[TrController] ");
		   varname1.append("      ,[TrName] ");
		   varname1.append("      ,[IsAlarm] ");
		   varname1.append("      ,[IsAcknowledged] ");
		   varname1.append("      ,[Type] ");
		   varname1.append("      ,[TrCamTag] ");
		   varname1.append("      ,[TransacReason] ");
		   varname1.append("      ,[TrCurrentPtr] ");
		   varname1.append("      ,[TrFilename] ");
		   varname1.append("      ,[TrPlateNo] ");
		   varname1.append("  FROM [DataDB].[dbo].[tblTransaction] ");
		   varname1.append("  ORDER BY ID desc;");
		   
		   return varname1.toString();
		   
	   }
	   
	   public static void main(String[] args)
	   {
		   SqlServerConnectionManager connServer = new SqlServerConnectionManager();
	      connServer.dbConnect("jdbc:sqlserver://FALCOWEB\\SQLEXPRESS;databaseName=DataDB", "sa",
	               "passw@rd1234");
	   }
	   

}

package genericutilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class DatabaseUtility {
	Connection con;
	public void createDatabaseConnection() {
		//	Driver driverDB = new Driver();
		//	DriverManager.registerDriver(driverDB);
		try {
			con = DriverManager.getConnection(IPathConst.DBURL,IPathConst.DBUSERNAME,IPathConst.DBPASSWORD);
		}
		catch(Exception e) {
		}
	}
	public String executeStatementAndVerifyResult(String query,String ColumnIndex,String expEntry) throws SQLException 
	{
		Statement stmt = con.createStatement();
		ResultSet rs=stmt.executeQuery(query);
		boolean flag= false;
		while(rs.next()) {
			String actualEntry=rs.getString(ColumnIndex);
			if(actualEntry.equalsIgnoreCase(expEntry)) {
				flag = true;
				break;
			}
		}
		if(flag) {
			return expEntry;
		}
		else {
			return null;
		}
	}

}

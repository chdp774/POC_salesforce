package com.salesforce.framework.PageObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ibm.as400.access.AS400JDBCDriver;

public class JDBC_Connection {
	public static void main(String[] args) throws SQLException {
		JDBC_Connection sqlConnection = new JDBC_Connection();
//		sqlConnection.updateRecords();
//		sqlConnection.getRecords();
//		sqlConnection.getValuesNull();
		sqlConnection.DBconectionChecking("85035073");
	}
	
	private final String url = "jdbc:db2://10.20.20.63:10";
	private final String user = "ITREAD";
	private final String password = "tech3qa1";
	
	public void connectionChecking() {
		try(Connection connection = DriverManager.getConnection(url, user, password)){
			if(connection != null) {
				System.out.println("Connection to postgreSQL server successfully");
			}else {
				System.out.println("Failed to connect to postgreSQL server");
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	List<String> dateList;
	public void DBconectionChecking(String str) {
		try {
			System.out.println("Trying to connect........");
			String driver = "com.ibm.as400.access.AS400JDBCDriver";
			String url = "jdbc:as400://10.20.20.63";
			
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, "ITREAD", "tech3qa1");
			
			String query = "select ABAN8, ABUPMJ, ABUPMT, ABDC from QADTA.F0101 Where ABAN8='" + str +"'";

			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while(result.next()) {
				String atriumid = result.getString("ABAN8");
				String lastUpdateDate = result.getString("ABUPMJ");
				String lastUpdateTime = result.getString("ABUPMT");
				String acctName = result.getString("ABDC");
				System.out.println(atriumid + " --- " + lastUpdateDate + " ---- " + lastUpdateTime + " --- " + acctName );
				String date = dateFormat(lastUpdateDate);
				System.out.println("Formated date: "+date);
				timeFormat(lastUpdateTime);
				dateList.add(date);
				System.out.println("-----" + dateList.size());
			}
			System.out.println("-----DONE------");
			conn.close();
		}catch(Exception e) {
//			System.out.println("DB connection failed");
		}
	}
	
	public static String dateFormat(String str) {
//		String str = "122102";
		char[] ch = str.toCharArray();
		String year = str.substring(0, 3);
		String daysCount = str.substring(3, 6);
		int daysCount_int = Integer.parseInt(daysCount);
//		System.out.println("days Count value is: "+daysCount);
//		System.out.println("year value is: "+ year);
		String Uyear = year.replaceAll(year, "2022");
//		System.out.println(Uyear + ":" + daysCount);
		
		LocalDate date = LocalDate.of(2022, Month.JANUARY, 1);
		LocalDate value = date.plusDays(daysCount_int - 1);
		String val = value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//		System.out.println("Formated date : " +val);
		return val;
	}
	
	public static void timeFormat(String str) {
//		String str = "111623";
		char[] ch = str.toCharArray();
		if(str.length() == 6) {
			System.out.println("Formated Time: " +ch[0] +""+ ch[1] + ":" + ch[2] + ch[3] + ":" + ch[4] + ch[5]);
		}else {
			System.out.println("Formated Time: " + "0" +ch[0]  + ":" + ch[1] + ch[2] + ":" + ch[3]+ ch[4]);
		}
		
	}
	
	public void getRecords() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf", "admin","perfadmin");
		System.out.println("Connection to postgreSQL server successfully");
		
		String query = "SELECT * from perftest where atrium_id =?";
		PreparedStatement stmt1 = connection.prepareStatement(query);
		stmt1.setString(1, "12345");
		ResultSet result = stmt1.executeQuery();
		
		while(result.next()) {
			String id = result.getString("atrium_id");
			System.out.println("id --- " +id);
		}
		connection.close();
	}
	
	public void getValuesNull() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf", "admin","perfadmin");
		String query ="select * from perftest where m2_createdate IS NULL";

		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(query);
		
		while(result.next()) {
			String id = result.getString("atrium_id");
			System.out.println("id --- " +id);
//			if(id.equals("000")) {
//				updateRecords();
//			}
		}
		connection.close();
		System.out.println("done");
	}
	
	public void updateRecords() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf", "admin","perfadmin");
		
		String query = "Update perftest SET m2_createdate = ? WHERE atrium_id =?";
		
		PreparedStatement stmt = connection.prepareStatement(query);
		String str = "12345";
		String str1 = "dp123";
		stmt.setString(1, str1);
		stmt.setString(2, str);
		int i = stmt.executeUpdate();
		 if(i>0){
	          System.out.println("Record updated sucessfully");
	     }else{
	          System.out.println("No Such record in the Database");
	     }
		 
		connection.close();
	}
	private void connect() {
		try(Connection connection = DriverManager.getConnection(url, user, password)){
			if(connection != null) {
				System.out.println("Connection to postgreSQL server successfully");
			}else {
				System.out.println("Failed to connect to postgreSQL server");
			}
			
//			String sql = "INSERT INTO department(id, dept, emp_id)" + "VALUES (1, 'computers', 123)";
//			String sql = "INSERT INTO perf VALUES(2,'mpc', 124)";
//			String query = "SELECT * from perftest where atrium_id=?";
			
			String getRecords = "SELECT atrium_id FROM perftest WHERE m2_createdate = ? ";
			PreparedStatement stmt1 = connection.prepareStatement(getRecords);
			String m2_create_date = "null";
			stmt1.setString(1, "null");
			ResultSet result = stmt1.executeQuery();
			
			String id = result.getString("atrium_id");
			System.out.println("id --- " +id);
			
			String query = "Update perftest SET m2_createdate = ? WHERE atrium_id =?";
			
			PreparedStatement stmt = connection.prepareStatement(query);
			String str = "12345";
			String str1 = "###";
			stmt.setString(1, str1);
			stmt.setString(2, str);
			stmt.executeUpdate();
//			Statement statement = connection.createStatement();
//			ResultSet result = statement.executeQuery(query);
			
//			while(result.next()) {
//				String id = result.getString("atrium_id");
//				System.out.println("id --- " +id);
//				if(id.contains("12345")) {
//					System.out.println("inside if case");
//					String updateQuery = "update perftest SET m2_createdate = '99999' WHERE atrium_id = '12345'";
//					
//					Statement updateStatement = connection.createStatement();
//					updateStatement.executeUpdate(updateQuery);
//				}
//			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	public void connect1() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/Salesforce", "postgres","db123");
		System.out.println("Connection to postgreSQL server successfully");
		String sql = "INSERT INTO accounts VALUES('4/10/2022 12:13 PM','prasad', 123, 'abc')";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		connection.close();
	}
}

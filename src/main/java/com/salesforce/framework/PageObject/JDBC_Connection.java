package com.salesforce.framework.PageObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_Connection {
	public static void main(String[] args) throws SQLException {
		JDBC_Connection sqlConnection = new JDBC_Connection();
//		sqlConnection.updateRecords();
//		sqlConnection.getRecords();
		sqlConnection.getValuesNull();
	}
	
	private final String url = "jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf";
	private final String user = "admin";
	private final String password = "perfadmin";
	
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
	
	public void getDB_records() {
		
		
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

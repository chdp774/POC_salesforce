package com.salesforce.framework.PageObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_Connection {
	public static void main(String[] args) throws SQLException {
		JDBC_Connection sqlConnection = new JDBC_Connection();
		sqlConnection.connect();
	}
	
	private final String url = "jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf";
	private final String user = "admin";
	private final String password = "perfadmin";
	
	private void connect() {
		try(Connection connection = DriverManager.getConnection(url, user, password)){
			if(connection != null) {
				System.out.println("Connection to postgreSQL server successfully");
			}else {
				System.out.println("Failed to connect to postgreSQL server");
			}
			
//			String sql = "INSERT INTO department(id, dept, emp_id)" + "VALUES (1, 'computers', 123)";
//			String sql = "INSERT INTO perf VALUES(2,'mpc', 124)";
			
//			Statement statement = connection.createStatement();
//			statement.executeUpdate(sql);
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

package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDemo {
	private static Connection connection;
	static {
		String drivername="oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username="hr";
		String password="password";
		
		try {
			// step 1: load the driver
			Class.forName(drivername);
			System.out.println("class loaded successfully");
			//connecting to database
			connection=DriverManager.getConnection(url,username,password);
			System.out.println("connection object created");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static Connection getConnection() {
		return connection;
	}
}

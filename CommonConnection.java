package com.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CommonConnection {

	public static Connection getConnection() throws SQLException { 
		Connection connection=null; 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "S@jid2726");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;  
		
	}	
}

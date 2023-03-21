package com.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class User {

public static void registration() {
	
	try {
		Connection userConn = CommonConnection.getConnection();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your username>> ");
		String username = sc.next();
		
		System.out.println("Enter your firstname>> ");
		String firstname = sc.next();
		
		System.out.println("Enter your lastname>> ");
		String lastname = sc.next();
		
		System.out.println("Enter your password>> ");
		String password = sc.next();
		
		System.out.println("Enter your city>> ");
		String city = sc.next();
		
		System.out.println("Enter your email>> ");
		String email = sc.next();
		
		System.out.println("Enter your mobnum>> ");
		String mobnum = sc.next();

		
		PreparedStatement ps = 
		userConn.prepareStatement("Insert into user values (?,?,?,?,?,?,?)");
		ps.setString(1, username);
		ps.setString(2, firstname);
		ps.setString(3,lastname);
		ps.setString(4,password);
		ps.setString(5,city);
		ps.setString(6,email);
		ps.setString(7,mobnum);
		
		ps.executeUpdate();
		
		System.out.println("User Created Successfully");
		userConn.close();
		ps.close();
		
	} 
	
	catch (SQLException e) {
		e.printStackTrace();
	}
	
}
	

public static String userLogin() {
	Guest guest = new Guest();
	Scanner sc = new Scanner(System.in);
	System.out.println("");
	System.out.println("Welcome to D-mart Shopping");
	System.out.println("Enter your username");
	String username = sc.next();
	System.out.println("Enter your password");
	String password = sc.next();
	String checkPassword = "";
	try {
		Connection conn = CommonConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement("select pasasword from user where username = ?");
		
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			checkPassword = rs.getString(1);
		}		
	} 
	catch (SQLException e) {

		e.printStackTrace();
	}
	
	if(checkPassword.equals(password)) {
		System.out.println("****************");
		System.out.println("Login Successful");
		System.out.println("****************");
		System.out.println("");
		guest.getProd();
		
	}
	
	else {
		try{
			throw new EcommException("Invalid username/password!");
		}
		catch(RuntimeException rex) {
			System.out.println(rex);
		}
	}

	return username;
}

public static void buyProd(String username) throws SQLException {
	String uname = username;
	int checkQuant = 0;
	LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
	Scanner sc = new Scanner(System.in);
	System.out.println("");
	System.out.println("How many products do you want?");
	int prodCount = sc.nextInt();
	
	for(int i=1; i<=prodCount;i++) {
	System.out.println("Enter the product id to buy product>>");
	int prodId = sc.nextInt();
	System.out.println("Enter the quantity of the product>>");
	int prodQuant = sc.nextInt();
	
	try {
		Connection conn = CommonConnection.getConnection();
		PreparedStatement ps =  conn.prepareStatement("select prodQuant from products where prodId = ?");
		ps.setInt(1, prodId);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			checkQuant = rs.getInt(1);
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	if(checkQuant >= prodQuant) {
		map.put(prodId, prodQuant);
	}
	
	else {
		try{
			throw new EcommException("Quanity not available!");
		}
		catch (RuntimeException rex) {
			System.out.println(rex);
		}
		
		System.out.println("Please enter the product details again");
		buyProd(uname);
	}
	
	System.out.println("Do you want to add these item/items to be added in the cart");
	System.out.println("Yes/No");
	
	String cartChoice = sc.next();
	
	if(cartChoice.equalsIgnoreCase("Yes")) {
		Connection conn = CommonConnection.getConnection();
	try {
		Set<Integer> s = map.keySet();
		Iterator<Integer> itr = s.iterator();
		while(itr.hasNext()) {
			int n=itr.next();
			PreparedStatement ps = conn.prepareStatement("insert into userhistory values (?,?,?)");
			ps.setString(1, uname);
			ps.setInt(2, n);
			ps.setInt(3, map.get(n));
			
			ps.executeUpdate();
		}
		
	}
	
	catch(RuntimeException rex) {
		System.out.println(rex);
	}
		
	}
	
		else {
		buyProd(uname);
		}
System.out.println("User history updated");
	}
sc.close();	
}





}

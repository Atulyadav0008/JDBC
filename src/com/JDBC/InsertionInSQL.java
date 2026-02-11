package com.JDBC;

import java.sql.*;

public class InsertionInSQL {
    public static void main(String[] args) throws ClassNotFoundException {
    	
    	String url = "jdbc:mysql://127.0.0.1:3306/mydatabase";
    	String username = "root";
    	String password = "15062005Ay@";
    	String query = "INSERT INTO employees(id,name,job_title,salary) values(6,'Tp','Frontend developer', 25000.0);";
    	
    	
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        
        try {
        	Connection con = DriverManager.getConnection(url,username,password);
        	System.out.println("Connection established successfully");
        	Statement stmt = con.createStatement();
        	int rowsAffected = stmt.executeUpdate(query);
        	
        	if(rowsAffected > 0) {
        		System.out.println("Data inserted succssful" + rowsAffected + " rows(s).");
        	}else {
        		System.out.println("Data insertion failed");
        	}
        
        	
        	
        	stmt.close();
        	con.close();
        	System.out.println("Connection closed successfully");
        
        	
        	
        	
        	
        }catch(SQLException e) {
        	System.out.println(e.getMessage());
        }
    }
}
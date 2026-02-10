package com.JDBC;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
    	
    	String url = "jdbc:mysql://127.0.0.1:3306/mydatabase";
    	String username = "root";
    	String password = "15062005Ay@";
    	String query = "Select * from employees;";
    	
    	
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
        	ResultSet rs =stmt.executeQuery(query);
        	
        	while(rs.next()) {
        		int id = rs.getInt("id");
        		String name = rs.getString("name");
        		String job_title = rs.getString("job_title");
        		double salary = rs.getDouble("salary");
        		
        		System.out.println("----------------");
        		System.out.println("id : " + id);
        		System.out.println("name : " + name);
        		System.out.println("job_title : " + job_title);
        		System.out.println("salary : " + salary);
        		
        		
        	}
        	rs.close();
        	stmt.close();
        	con.close();
        	System.out.println("Connection closed successfully");
        
        	
        	
        	
        	
        }catch(SQLException e) {
        	System.out.println(e.getMessage());
        }
    }
}
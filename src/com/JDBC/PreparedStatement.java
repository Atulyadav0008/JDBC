package com.JDBC;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;




public class PreparedStatement {
public static void main(String[] args) throws ClassNotFoundException {
    	
    	String url = "jdbc:mysql://127.0.0.1:3306/mydatabase";
    	String username = "root";
    	String password = "15062005Ay@";
    	String query = "Select * from employees where name = ? AND job_title = ?";
    	String query1 = "INSERT INTO employees(id,name,job_title,salary) VALUES(?,?,?,?)";
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    	
    	try {
        	Connection con = DriverManager.getConnection(url,username,password);
        	System.out.println("Connection established successfully");
        	
        	
        	Scanner sc = new Scanner(System.in);
        	int id = sc.nextInt();
        	sc.nextLine();
        	String name = sc.nextLine();
        	String job_title = sc.nextLine();
        	double salary = sc.nextDouble();
        	
        	
        	
        	
        	
            java.sql.PreparedStatement preparedStatement = con.prepareStatement(query1);
            
            //this code is for putting data during code
//        	preparedStatement.setString(1, "Atul yadav");
//        	preparedStatement.setString(2,"JavaDeveloper");
        	
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,job_title);
            preparedStatement.setDouble(4, salary);
        	
//        	ResultSet rs = preparedStatement.executeQuery();
//        	
//        	
//        	
//        	while(rs.next()) {
//        		int id = rs.getInt("id");
//        		String name = rs.getString("name");
//        		String job_title = rs.getString("job_title");
//        		double salary = rs.getDouble("salary");
//        		
//        		System.out.println("ID : " + id);
//        		System.out.println("Name : " + name);
//        		System.out.println("Job_title : " + job_title);
//        		System.out.println("Salary : " + salary);
//        	} 
            
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0) {
            	System.out.println("Data inserted successfully");
            }else {
            	System.out.println("Data insertion failed");
            }
            
        	con.close();
        	
        	preparedStatement.close();
        	System.out.println("Connection closed successfully");
        
        	
        	

        	
        }catch(SQLException e) {
        	System.out.println(e.getMessage());
        }
    }
}

package com.JDBC;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Scanner;

public class HotelreservationSystem {
	 public static void main(String[] args) throws ClassNotFoundException , SQLException {
	    	
	        private static final String url = "jdbc:mysql://127.0.0.1:3306/mydatabase";
	        private static final String username = "root";
	        private static final String password = "15062005Ay@";
	    
	    	
	    	
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	          
	        } catch (ClassNotFoundException e) {
	            System.out.println(e.getMessage());
	        }
	        
	        
	        try {
	        	Connection con = DriverManager.getConnection(url,username,password);
               while(true) {
            	   System.out.println();
            	   System.out.println("Hotel Reservation System : ");
            	   Scanner sc = new Scanner(System.in);
            	   System.out.println("1. Reserve a room. ");
            	   System.out.println("2. Check Reservation. ");
            	   System.out.println("3. Get Room Number. ");
            	   System.out.println("4. Update reservation. ");
            	   System.out.println("5. Delete Reservation. ");
            	   System.out.println("6. Exit. ");
            	   System.out.println("Choose an option--->>>.");
            	   	int choice = sc.nextInt();
            	   	
            	   	switch(choice) {
            	   	case 1:
            	   		reserveRoom(con,sc);
            	   		break;
            	   	case 2:
            	   		checkreservation(con,sc);
            	   		break;
            	   	case 3:
            	   	    getRoomNumber(con,sc);
            	   	    break;
            	   	case 4:
            	   		updateReservation(con,sc);
            	   		break;
            	   	case 5:
            	   		delatereservation(con,sc);
            	   		break;
            	   	case 6:
            	   		exit();
            	   		sc.close();
            	   		return;
            	   	default:
            	   		
            	   		
            	   	}
            	   	}
            	   }
	        	
	        		
	        	
	        }catch(SQLException e) {
	        	System.out.println(e.getMessage());
	        }
	    }

	 private static void Switch() {
		// TODO Auto-generated method stub
		
	 }
}

package com.JDBC;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Scanner;

public class HotelreservationSystem {
	 
	    	
	        private static final String url = "jdbc:mysql://127.0.0.1:3306/mydatabase";
	        private static final String username = "root";
	        private static final String password = "15062005Ay@";
	    
	        public static void main(String[] args) throws ClassNotFoundException , SQLException {
	    	
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
            	   System.out.println("0. Exit. ");
            	   System.out.println("Choose an option--->>>.");
            	   	int choice = sc.nextInt();
            	   	
            	   	switch(choice) {
            	   	case 1:
            	   		reserveRoom(con,sc);
            	   		break;
            	   	case 2:
            	   		checkReservation(con);
            	   		break;
            	   	case 3:
            	   	    getRoomNumber(con,sc);
            	   	    break;
            	   	case 4:
            	   		updateReservation(con,sc);
            	   		break;
            	   	case 5:
            	   		deleteReservation(con,sc);
            	   		break;
            	   	case 0:
            	   		exit();
            	   		sc.close();
            	   		return;
            	   	default:
            	 	   System.out.println("Invalid choice , Enter Valid one.");
            	   	}
            		}
               	
	        }catch(SQLException e) {
	        	System.out.println(e.getMessage());
	        }catch(InterruptedException e) {
	        	throw new RuntimeException(e);
	        }
	    }

	private static void reserveRoom(Connection con, Scanner sc) {
		try {
			System.out.print("Enter guest name : ");
			String guest_name = sc.next();
			sc.nextLine();
			System.out.print("Enter room number : ");
			int room_no = sc.nextInt();
			System.out.print("Enter contact number please: ");
			String contact_no = sc.next();
			
			String query = "INSERT INTO reservations (guest_name, room_no, contact_no) " +
                    "VALUES ('" + guest_name + "', " + room_no + ", '" + contact_no + "')";
			
			try(Statement stmt = con.createStatement()){
				int affected_rows = stmt.executeUpdate(query);
				
				if(affected_rows > 0) {
					System.out.println("Data inserted.");
				}else {
					System.out.println("Data insertion failed.");
				}
			}
			}catch(SQLException e){
			      e.printStackTrace();
			}
		}
	
		
		
		
	private static void checkReservation(Connection con) throws SQLException{

	    String query = "SELECT reservation_id, guest_name, room_no, contact_no, reservation_date FROM reservations";

	    try(Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query)) {

	        System.out.println("Current Reservations.");
	        System.out.println("+--------------------------+--------------------------+------------------------------+---------------------------+-------------------------------------+");
	        System.out.println("|   Res_ID                 | Guest                    | Room_no                      | Cont_no                   |  Res_date                           |");
	        System.out.println("+--------------------------+--------------------------+------------------------------+---------------------------+-------------------------------------+");

	        while(rs.next()) {
	            int res_ID = rs.getInt("reservation_id");
	            String guest = rs.getString("guest_name");
	            int room_no = rs.getInt("room_no");
	            String cont_no = rs.getString("contact_no");
	            String res_date = rs.getTimestamp("reservation_date").toString();

	            System.out.printf("| %-19d | %-19s | %-19d | %-19s | %-19s |\n",
	                    res_ID,guest,room_no,cont_no,res_date);
	        }

	        System.out.println("+---------------------------+---------------------------+----------------------------+----------------------------+------------------------------------+");
	    }
	}
		
	private static void getRoomNumber(Connection con, Scanner sc) {
	    try {
	        System.out.print("Enter reservation ID : ");
	        int reservation_id = sc.nextInt();
	        System.out.print("Enter guest name: ");
	        String guest_name = sc.next();

	        String query = "SELECT room_no FROM reservations " +
	                "WHERE reservation_id = " + reservation_id +
	                " AND guest_name = '" + guest_name + "'";

	        try(Statement stmt = con.createStatement();
	            ResultSet rs = stmt.executeQuery(query)) {

	            if(rs.next()) {
	                int room_no = rs.getInt("room_no");
	                System.out.println("Room_no for reservation_ID " + reservation_id +
	                        " and Guest " + guest_name + " is " + room_no );
	            } else {
	                System.out.println("Reservation not found for the given ID and guest_name");
	            }
	        }
	    } catch(SQLException e) {
	        e.printStackTrace();
	    }
	}
		
		
	private static void updateReservation(Connection con , Scanner sc) {
	    try {
	        System.out.print("Enter reservaion ID to Update: ");
	        int reservation_id = sc.nextInt();
	        sc.nextLine();

	        if(!reservationExists(con,reservation_id)) {
	            System.out.println("Reservation not found for the given ID.");
	            return;
	        }

	        System.out.print("Enter the guest_name: ");
	        String new_GuestName = sc.nextLine();
	        System.out.print("Enter new room number: ");
	        int new_room_no = sc.nextInt();
	        System.out.print("Enter new contact number : ");
	        String new_contact_no = sc.next();

	        String query = "UPDATE reservations SET guest_name = '" + new_GuestName + "', " +
	                "room_no = " + new_room_no + ", " +
	                "contact_no = '" + new_contact_no + "' " +
	                "WHERE reservation_id = " + reservation_id;

	        try(Statement stmt = con.createStatement()){
	            int affected_rows = stmt.executeUpdate(query);

	            if(affected_rows > 0) {
	                System.out.println("Reservation updated successfully.");
	            } else {
	                System.out.println("Reservation updation failed.");
	            }
	        }

	    } catch(SQLException e){
	        e.printStackTrace();
	    }
	}
					
		
		private static void deleteReservation(Connection con , Scanner sc) {
			try {
				System.out.println("Enter reservation ID to delete.");
				int reservation_id = sc.nextInt();
				
				if(!reservationExists(con,reservation_id)) {
					System.out.println("Reservation not found for the given ID.");
					return;
				}
				
				String query = "DELETE FROM reservations WHERE reservation_id = " + reservation_id;
				
				try(Statement stmt = con.createStatement()){
					int affected_rows = stmt.executeUpdate(query);
					
					if(affected_rows > 0) {
						System.out.println("Reservation updated successfully.");
					}else {
						System.out.println("Reservation updation failed.");
					}
				}
					
				}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		private static boolean reservationExists(Connection con, int reservation_id) {
			
			try {
				String query = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservation_id;
				
				try(Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query)){
					
				return rs.next();
				}
				}catch(SQLException e){
				e.printStackTrace();
				return false;
			}
		}
		
		
	
			public static void exit() throws InterruptedException{
				System.out.print("Exiting System");
				int i = 5;
				while(i!=0) {
					System.out.print(".");
					Thread.sleep(1000);
					i--;
				}
				System.out.println();
				System.out.println("Thankyou for using Hotel Reservation System !!!");
			}
			}


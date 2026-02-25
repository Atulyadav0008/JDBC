package com.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;   // ✅ IMPORTANT

public class Transaction_Handling {

    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/mydatabase";
        String username = "root";
        String password = "15062005Ay@";
        String withdraw_query = "UPDATE ACCOUNTS SET ACC_BALANCE = ACC_BALANCE - ? WHERE ACC_NO= ?";
        String deposit_query = "UPDATE ACCOUNTS SET ACC_BALANCE = ACC_BALANCE + ? WHERE ACC_NO = ?";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded Successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established Successfully");
            con.setAutoCommit(false);

            try {
                PreparedStatement withdrawStatement = con.prepareStatement(withdraw_query);
                PreparedStatement depositStatement = con.prepareStatement(deposit_query);

                withdrawStatement.setDouble(1, 500.00);
                withdrawStatement.setString(2, "ACCOUNT123");

                depositStatement.setDouble(1, 500.00);
                depositStatement.setString(2, "ACCOUNT124");

                withdrawStatement.executeUpdate();
                depositStatement.executeUpdate();   // ✅ Correct one

                con.commit();
                System.out.println("Transaction Successful");
                con.close();

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Transaction failed");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();
        }
    }
}
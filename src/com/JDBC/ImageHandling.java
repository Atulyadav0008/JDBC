package com.JDBC;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageHandling {

    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/mydatabase";
        String username = "root";
        String password = "15062005Ay@";
        String path = "C:\\Users\\Lenovo\\OneDrive\\Desktop\\Image\\at.jpg";
        String query = "INSERT INTO image_table(image_data) VALUES(?)";

        File file = new File(path);

        // ✅ Check if file exists first
        if (!file.exists()) {
            System.out.println("File not found at: " + path);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(url, username, password);
                 FileInputStream fis = new FileInputStream(file);
                 PreparedStatement ps = con.prepareStatement(query)) {

                byte[] imageData = fis.readAllBytes();
                ps.setBytes(1, imageData);

                int affectedRows = ps.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Image inserted successfully.");
                } else {
                    System.out.println("Image insertion failed.");
                }

            }

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("Error while reading the file.");
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");
            e.printStackTrace();
        }
    }
}
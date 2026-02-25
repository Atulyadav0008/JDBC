package com.JDBC;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageHandling {

    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/mydatabase";
        String username = "root";
        String password = "15062005Ay@";
        String folder_path = "C:\\Users\\Lenovo\\OneDrive\\Pictures\\JDBC_images\\";
        String query = "SELECT image_data from image_table where image_id = (?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try {
                Connection con = DriverManager.getConnection(url, username, password);
                System.out.println("Driver loaded Successfully");

                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, 1);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    System.out.println("Image found in database.");

                    byte[] image_data = resultSet.getBytes("image_data");

                    // Ensure folder exists
                    File folder = new File(folder_path);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }

                    String image_path = folder_path + "Extracted_Image.jpg";
                    FileOutputStream outputStream = new FileOutputStream(image_path);
                    outputStream.write(image_data);
                    outputStream.close();

                    System.out.println("Image saved successfully.");
                } else {
                    System.out.println("Image not found!!!");
                }

                resultSet.close();
                preparedStatement.close();
                con.close();

            } catch (SQLException e) {
                System.out.println("Database error occurred.");
                e.printStackTrace();

            } catch (IOException e) {
                System.out.println("Error while writing the file.");
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");
            e.printStackTrace();
        }
    }
}
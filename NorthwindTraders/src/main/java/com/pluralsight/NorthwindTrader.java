package com.pluralsight;
import com.mysql.cj.jdbc.exceptions.SQLError;

import java.sql.*;

public class NorthwindTrader {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (
                    Connection connection = DriverManager.getConnection(url, username, password);
                    PreparedStatement statement = connection.prepareStatement("SELECT ProductName FROM Products");
                    ResultSet resultSet = statement.executeQuery();
            ) {
                System.out.println("Products in the Northwind DB:");
                System.out.println("--------------------------------");

                while (resultSet.next()) {
                    String name = resultSet.getString("ProductName");
                    System.out.println("- " + name);
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("There was an error!");
            e.printStackTrace();
        }
    }
}
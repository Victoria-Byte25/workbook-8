package com.pluralsight;

import java.sql.*;

public class NorthwindTrader {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, username, password);

            statement = connection.prepareStatement(
                    "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products"
            );
            resultSet = statement.executeQuery();

            System.out.println("Products in the Northwind DB:");
            System.out.println("--------------------------------");

            while (resultSet.next()) {
                int id = resultSet.getInt("ProductID");
                String name = resultSet.getString("ProductName");
                double price = resultSet.getDouble("UnitPrice");
                int stock = resultSet.getInt("UnitsInStock");

                System.out.println("Product Id: " + id);
                System.out.println("Name      : " + name);
                System.out.println("Price     : " + price);
                System.out.println("Stock     : " + stock);
                System.out.println("----------------------");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(" There was an error:");
            e.printStackTrace();

        } finally {

            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(" Error closing resources:");
                ex.printStackTrace();
            }
        }
    }
}

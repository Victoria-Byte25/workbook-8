package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class NorthwindTrader {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);

            int choice;

            do {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    statement = connection.prepareStatement(
                            "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products"
                    );
                    resultSet = statement.executeQuery();

                    System.out.println("\n Product List:\n");
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

                } else if (choice == 2) {
                    statement = connection.prepareStatement(
                            "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country"
                    );
                    resultSet = statement.executeQuery();

                    System.out.println("\n Customer List:\n");
                    while (resultSet.next()) {
                        String contactName = resultSet.getString("ContactName");
                        String company = resultSet.getString("CompanyName");
                        String city = resultSet.getString("City");
                        String country = resultSet.getString("Country");
                        String phone = resultSet.getString("Phone");

                        System.out.println("Customer : " + contactName);
                        System.out.println("Company  : " + company);
                        System.out.println("City     : " + city);
                        System.out.println("Country  : " + country);
                        System.out.println("Phone    : " + phone);
                        System.out.println("-------------------------------");
                    }
                }

            } while (choice != 0);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(" There was an error:");
            e.printStackTrace();
        } finally {
            // Clean up!
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(" Error closing resources");
                e.printStackTrace();
            }
        }

        scanner.close();
        System.out.println("Goodbye! ");
    }
}

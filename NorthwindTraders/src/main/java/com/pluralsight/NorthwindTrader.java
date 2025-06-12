package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class NorthwindTrader {

    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(System.in);
                Connection connection = Database.getDataSource().getConnection()
        ) {
            int choice;

            do {
                choice = displayMenu(scanner);

                if (choice == 1) {
                    displayProducts(connection);
                } else if (choice == 2) {
                    displayCustomers(connection);
                } else if (choice == 3) {
                    displayCategoriesAndProducts(connection, scanner);
                }

            } while (choice != 0);

            System.out.println("\nGoodbye! ");

        } catch (SQLException e) {
            System.out.println(" There was an error:");
            e.printStackTrace();
        }
    }

    public static int displayMenu(Scanner scanner) {
        System.out.println("\nWhat do you want to do?");
        System.out.println("1) Display all products");
        System.out.println("2) Display all customers");
        System.out.println("3) Display all categories");
        System.out.println("0) Exit");
        System.out.print("Select an option: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public static void displayProducts(Connection conn) throws SQLException {
        try (
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products"
                );
                ResultSet rs = stmt.executeQuery()
        ) {
            System.out.println("\n Product List:\n");
            while (rs.next()) {
                System.out.println("Product Id: " + rs.getInt("ProductID"));
                System.out.println("Name      : " + rs.getString("ProductName"));
                System.out.println("Price     : " + rs.getDouble("UnitPrice"));
                System.out.println("Stock     : " + rs.getInt("UnitsInStock"));
                System.out.println("----------------------");
            }
        }
    }

    public static void displayCustomers(Connection conn) throws SQLException {
        try (
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country"
                );
                ResultSet rs = stmt.executeQuery()
        ) {
            System.out.println("\n Customer List:\n");
            while (rs.next()) {
                System.out.println("Customer : " + rs.getString("ContactName"));
                System.out.println("Company  : " + rs.getString("CompanyName"));
                System.out.println("City     : " + rs.getString("City"));
                System.out.println("Country  : " + rs.getString("Country"));
                System.out.println("Phone    : " + rs.getString("Phone"));
                System.out.println("-------------------------------");
            }
        }
    }

    public static void displayCategoriesAndProducts(Connection conn, Scanner scanner) throws SQLException {

        try (
                PreparedStatement catStmt = conn.prepareStatement(
                        "SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID"
                );
                ResultSet catResults = catStmt.executeQuery()
        ) {
            System.out.println("\n Categories:");
            while (catResults.next()) {
                System.out.println(catResults.getInt("CategoryID") + ") " + catResults.getString("CategoryName"));
            }
        }

        System.out.print("\nEnter a category ID to view its products: ");
        int categoryId = Integer.parseInt(scanner.nextLine());

        try (
                PreparedStatement productStmt = conn.prepareStatement(
                        "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products WHERE CategoryID = ?"
                )
        ) {
            productStmt.setInt(1, categoryId);
            try (ResultSet rs = productStmt.executeQuery()) {
                System.out.println("\n Products in Category " + categoryId + ":\n");
                while (rs.next()) {
                    System.out.println("Product Id: " + rs.getInt("ProductID"));
                    System.out.println("Name      : " + rs.getString("ProductName"));
                    System.out.println("Price     : " + rs.getDouble("UnitPrice"));
                    System.out.println("Stock     : " + rs.getInt("UnitsInStock"));
                    System.out.println("------------------------");
                }
            }
        }
    }
}

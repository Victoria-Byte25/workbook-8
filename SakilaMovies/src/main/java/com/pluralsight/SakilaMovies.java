package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class SakilaMovies {
    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(System.in);
                Connection conn = Database.getDataSource().getConnection()
        ) {
            System.out.print("Enter an actor's last name: ");
            String lastName = scanner.nextLine();
            boolean found = findActorsByLastName(conn, lastName);

            if (!found) {
                System.out.println("No matches found.");
                return;
            }

            System.out.print("\nEnter the actor's first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter the actor's last name: ");
            String fullLastName = scanner.nextLine();

            findFilmsByActor(conn, firstName, fullLastName);

        } catch (SQLException e) {
            System.out.println(" Database error:");
            e.printStackTrace();
        }
    }

    public static boolean findActorsByLastName(Connection conn, String lastName) throws SQLException {
        String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, lastName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\nYour matches:");
                    do {
                        System.out.printf("ID: %d - %s %s\n",
                                rs.getInt("actor_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"));
                    } while (rs.next());
                    return true;
                }
            }
        }
        return false;
    }

    public static void findFilmsByActor(Connection conn, String firstName, String lastName) throws SQLException {
        String sql = """
                SELECT film.title
                FROM film
                JOIN film_actor ON film.film_id = film_actor.film_id
                JOIN actor ON actor.actor_id = film_actor.actor_id
                WHERE actor.first_name = ? AND actor.last_name = ?
            """;

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\n🎬 Films:");
                    do {
                        System.out.println("- " + rs.getString("title"));
                    } while (rs.next());
                } else {
                    System.out.println("No films found for that actor.");
                }
            }
        }
    }
}

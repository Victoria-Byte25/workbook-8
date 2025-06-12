package com.pluralsight;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SakilaMovies {
    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(System.in);
                Connection conn = Database.getDataSource().getConnection()
        ) {
            DataManager data = new DataManager(conn);

            System.out.print("Enter an actor's last name (or part of it): ");
            String namePart = scanner.nextLine();
            List<Actor> actors = data.findActorsByName(namePart);

            if (actors.isEmpty()) {
                System.out.println("No matching actors found.");
                return;
            }

            System.out.println("\n Matching Actors:");
            for (Actor actor : actors) {
                System.out.println(actor);
            }

            System.out.print("\nEnter the ID of an actor to see their movies: ");
            int actorId = Integer.parseInt(scanner.nextLine());
            List<Film> films = data.findFilmsByActorId(actorId);

            if (films.isEmpty()) {
                System.out.println("No films found for this actor.");
            } else {
                System.out.println("\n Films:");
                for (Film film : films) {
                    System.out.println(film);
                    System.out.println("------------------------------");
                }
            }

        } catch (SQLException e) {
            System.out.println(" Database error:");
            e.printStackTrace();
        }
    }
}

package com.pluralsight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private Connection conn;

    public DataManager(Connection conn) {
        this.conn = conn;
    }

    public List<Actor> findActorsByName(String lastNamePart) throws SQLException {
        String sql = "select actor_id, first_name, last_name from actor where last_name like ?";
        List<Actor> actors = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + lastNamePart + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Actor actor = new Actor(
                            rs.getInt("actor_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name")
                    );
                    actors.add(actor);
                }
            }
        }

        return actors;
    }

    public List<Film> findFilmsByActorId(int actorId) throws SQLException {
        String sql = """
                SELECT f.film_id, f.title, f.description, f.release_year, f.length
                FROM film f
                JOIN film_actor fa ON f.film_id = fa.film_id
                WHERE fa.actor_id = ?
            """;
        List<Film> films = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, actorId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Film film = new Film(
                            rs.getInt("film_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("release_year"),
                            rs.getInt("length")
                    );
                    films.add(film);
                }
            }
        }

        return films;
    }
}


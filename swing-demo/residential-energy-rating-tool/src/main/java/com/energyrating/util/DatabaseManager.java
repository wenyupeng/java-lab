package com.energyrating.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:energy-rating.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialiseDatabase() {
        String sql = """
                CREATE TABLE IF NOT EXISTS projects (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    address TEXT NOT NULL,
                    climate_zone TEXT NOT NULL,
                    floor_area REAL NOT NULL,
                    wall_type TEXT NOT NULL,
                    window_type TEXT NOT NULL,
                    roof_insulation TEXT NOT NULL,
                    orientation TEXT NOT NULL,
                    shading INTEGER NOT NULL,
                    cross_ventilation INTEGER NOT NULL
                );
                """;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialise database", e);
        }
    }
}

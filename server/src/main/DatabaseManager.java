package src.main;

import java.nio.file.*;
import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:data/assistant.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = connect()) {
            String schemaSQL = Files.readString(Paths.get("sqlite_schema.sql"));
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(schemaSQL);
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}

public static void testDB() {
    try (Connection conn = connect()) {
        ResultSet rs = conn.createStatement().executeQuery("SELECT name FROM sqlite_master WHERE type='table'");
        while (rs.next()) {
            System.out.println("Table: " + rs.getString("name"));
        }
    } catch (Exception e) {
        System.err.println("DB Test failed: " + e.getMessage());
    }
}
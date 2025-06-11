package main;

import java.nio.file.*;
import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:data/assistant.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try{
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e){
            System.err.println("Error loading SQLite JDBC driver: " + e.getMessage());
        }
        try(Connection conn = connect()) {
            String schemaFilePath = "../database/sqlite_schema.sql";
            String schemaSQL = Files.readString(Paths.get(schemaFilePath));
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(schemaSQL);
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
}


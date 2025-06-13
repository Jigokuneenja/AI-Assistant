package test;

import java.sql.Connection;
import java.sql.ResultSet;

import main.AssistantCore;
import main.DatabaseManager;

public class Tests {
    public static void Tester(){
        //AssistantCoreTest();
        //testDB();
    }
    public static void AssistantCoreTest() {
        
    }
    public static void testDB() {
        try (Connection conn = DatabaseManager.connect()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT name FROM sqlite_master WHERE type='table'");
            while (rs.next()) {
                System.out.println("Table: " + rs.getString("name"));
            }
        } catch (Exception e) {
            System.err.println("DB Test failed: " + e.getMessage());
        }
    }
}

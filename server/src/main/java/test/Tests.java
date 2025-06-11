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
        AssistantCore.addTask("Test Task", "For testing purpouses only", "2024-06-12");
        AssistantCore.addTask("Second Task", "The second testing task", "2025-06-12");
        AssistantCore.addTask("Third Task", "The third testing task", "2026-06-12");
        AssistantCore.listTasks();
        AssistantCore.removeTask(1);
        AssistantCore.listTasks();
        AssistantCore.removeTask(2);
        AssistantCore.removeTask(3);
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

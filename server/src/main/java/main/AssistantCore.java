package main;

import java.sql.*;

public class AssistantCore {
    public static void addTask(String title, String description, String dueDate) {
        String sql = "INSERT INTO tasks (title, description, due_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, dueDate);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Task added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add task: " + e.getMessage());
        }
    }
    public static void listTasks() {
        String sql = "SELECT id, title, description, due_date, status FROM tasks";
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d | %s;%s (Due: %s) status:%s%n",
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("due_date"),
                    rs.getString("status"));
            }
        } catch (SQLException e) {
            System.err.println("List tasks failed: " + e.getMessage());
        }
    }
    public static void removeTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Task removed.");
        } catch (SQLException e) {
            System.err.println("Remove task failed: " + e.getMessage());
        }
    }
}
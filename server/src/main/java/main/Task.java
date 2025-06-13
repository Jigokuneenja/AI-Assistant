package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Task {
    public int id = 0;
    public String title;
    public String description;
    public String dueDate;
    public String status = "pending";

    public Task() {
    }

    public Task(String title, String description, String dueDate, String status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getDueDate() {
        return dueDate;
    }
    public String getStatus() {
        return status;
    }
    public List<Reminder> getReminders() {
        return Reminder.findByTaskId(this.id);
    }

    public void setTitle(String title) {
        this.title = title;
        updateTask();
    }
    public void setDescription(String description) {
        this.description = description;
        updateTask();
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
        updateTask();
    }
    public void setStatus(String status) {
        this.status = status;
        updateTask();
    }

    public void save(){
        if(id == 0){
            insertTask();
        } else {
            updateTask();
        }
    }

    public void insertTask() {
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
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Failed to add task: " + e.getMessage());
        }
    }

    public void updateTask() {
        String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, this.title);
            pstmt.setString(2, this.description);
            pstmt.setString(3, this.dueDate);
            pstmt.setString(4, this.status);
            pstmt.setInt(5, this.id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task ID " + this.id + " updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update task: " + e.getMessage());
        }
    }

    public void delete() {
        if (this.id == 0) {
            System.err.println("Cannot delete a task that has not been saved.");
        }
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task ID " + this.id + " removed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Remove task failed: " + e.getMessage());
        }
    }

    public static List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT id, title, description, due_date, status FROM tasks";
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = new Task();
                task.id = rs.getInt("id");
                task.title = rs.getString("title");
                task.description = rs.getString("description");
                task.dueDate = rs.getString("due_date");
                task.status = rs.getString("status");
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.err.println("List tasks failed: " + e.getMessage());
        }
        return tasks;
    }

    public static Task find(int id) {
        String sql = "SELECT id, title, description, due_date, status FROM tasks WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Task task = new Task();
                    task.id = rs.getInt("id");
                    task.title = rs.getString("title");
                    task.description = rs.getString("description");
                    task.dueDate = rs.getString("due_date");
                    task.status = rs.getString("status");
                    return task;
                }
            }
        } catch (SQLException e) {
            System.err.println("Find task failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Task{id=%d, title='%s', description='%s', dueDate='%s', status='%s'}",
                id, title, description ,dueDate, status);
    }
}

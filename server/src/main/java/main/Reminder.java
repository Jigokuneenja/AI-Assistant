package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reminder {
    public int id = 0;
    public int taskId;
    public String remindAt;
    
    private Task task;

    public Reminder() {
    }

    public Reminder(int taskId, String remindAt) {
        this.taskId = taskId;
        this.remindAt = remindAt;
    }

    public void save() {
        if (this.id == 0) {
            insertReminder();
        } else {
            updateReminder();
        }
    }

    private void insertReminder() {
        String sql = "INSERT INTO reminders (task_id, remind_at) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, this.taskId);
            pstmt.setString(2, this.remindAt);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                        System.out.println("Reminder created with ID: " + this.id);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to insert reminder: " + e.getMessage());
        }
    }

    private void updateReminder() {
        String sql = "UPDATE reminders SET task_id = ?, remind_at = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.taskId);
            pstmt.setString(2, this.remindAt);
            pstmt.setInt(3, this.id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Reminder ID " + this.id + " updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update reminder: " + e.getMessage());
        }
    }

    public boolean delete() {
        if (this.id == 0) {
            System.err.println("Cannot delete a reminder that has not been saved.");
            return false;
        }
        String sql = "DELETE FROM reminders WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Reminder ID " + this.id + " removed successfully.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Remove reminder failed: " + e.getMessage());
        }
        return false;
    }

    public static List<Reminder> findAll() {
        List<Reminder> reminders = new ArrayList<>();
        String sql = "SELECT id, task_id, remind_at FROM reminders";
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reminder reminder = new Reminder();
                reminder.id = rs.getInt("id");
                reminder.taskId = rs.getInt("task_id");
                reminder.remindAt = rs.getString("remind_at");
                reminders.add(reminder);
            }
        } catch (SQLException e) {
            System.err.println("List reminders failed: " + e.getMessage());
        }
        return reminders;
    }

    public static Reminder find(int id) {
        String sql = "SELECT id, task_id, remind_at FROM reminders WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Reminder reminder = new Reminder();
                    reminder.id = rs.getInt("id");
                    reminder.taskId = rs.getInt("task_id");
                    reminder.remindAt = rs.getString("remind_at");
                    return reminder;
                }
            }
        } catch (SQLException e) {
            System.err.println("Find reminder failed: " + e.getMessage());
        }
        return null;
    }

    public static List<Reminder> findByTaskId(int taskId) {
        List<Reminder> reminders = new ArrayList<>();
        String sql = "SELECT id, task_id, remind_at FROM reminders WHERE task_id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reminder reminder = new Reminder();
                    reminder.id = rs.getInt("id");
                    reminder.taskId = rs.getInt("task_id");
                    reminder.remindAt = rs.getString("remind_at");
                    reminders.add(reminder);
                }
            }
        } catch (SQLException e) {
            System.err.println("Find reminders by task ID failed: " + e.getMessage());
        }
        return reminders;
    }

    public Task getTask() {
        if (task == null && taskId > 0) {
            task = Task.find(taskId);
            return task;
        }
        return null;
    }

    public int getId() { return id; }
    public int getTaskId() { return taskId; }
    public String getRemindAt() { return remindAt; }

    public void setTaskId(int taskId) { 
        this.taskId = taskId;
        this.task = getTask();
    }
    public void setRemindAt(String remindAt) { this.remindAt = remindAt; updateReminder();}

    @Override
    public String toString() {
        return String.format("Reminder{id=%d, taskId=%d, remindAt='%s'}", 
                id, taskId, remindAt);
    }
}
package main;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();

        // Proceed to load CLI or core logic
        System.out.println("Assistant starting...");
        // AssistantCore.launch(); or similar
    }
}
package main;

import test.*;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        System.out.println("Assistant starting...");
        Tests.Tester();
    }
    
}
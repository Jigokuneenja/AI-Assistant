package main;

import java.util.Map;

import org.sqlite.core.DB;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        // Create the Javalin app
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        });

        // CORS setup for all incoming requests
        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        // Handle CORS preflight (OPTIONS) requests
        app.options("/*", ctx -> {
            ctx.status(204);
        });

        // Sample GET route to test if backend is running
        app.get("/", ctx -> {
            ctx.result("AI Assistant Backend is Running");
        });

        app.get("/api/tasks", ctx -> {
            ctx.json(Task.findAll());
        });

        app.post("/api/tasks", ctx -> {
            Task newTask = ctx.bodyAsClass(Task.class);
            newTask.save();
            ctx.json(newTask);
        });

        // Start server
        app.start(8080);
    }
}

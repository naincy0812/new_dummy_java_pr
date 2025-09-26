package com.example.forum;

import java.sql.*;
import java.util.*;

/**
 * A simple Discussion Forum simulation with MySQL integration.
 * Intentionally written with some issues and bugs.
 */
public class DiscussionService {

    private Connection connection;

    public DiscussionService(String url, String user, String password) {
        try {
            // ❌ Bug: Driver not loaded explicitly, may cause ClassNotFoundException in some environments
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Store threads in memory as a fallback (not thread-safe!)
    private static List<String> cachedThreads = new ArrayList<>();

    /**
     * Create a new discussion thread
     */
    public void createThread(String title, String content, String author) {
        try {
            String sql = "INSERT INTO threads(title, content, author) VALUES ('" 
                          + title + "', '" + content + "', '" + author + "')"; 
            // ❌ Issue: SQL Injection possible here, PreparedStatement should be used
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);

            // Add to cache
            cachedThreads.add(title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetch all discussion threads
     */
    public List<String> getAllThreads() {
        List<String> threads = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT title FROM threads");

            while (rs.next()) {
                threads.add(rs.getString("title"));
            }

            // ❌ Bug: Cached list and DB list can go out of sync
            threads.addAll(cachedThreads);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return threads;
    }

    /**
     * Delete a thread by title
     */
    public void deleteThread(String title) {
        try {
            String sql = "DELETE FROM threads WHERE title='" + title + "'";
            // ❌ Issue: Again, SQL Injection vulnerability
            Statement stmt = connection.createStatement();
            int rows = stmt.executeUpdate(sql);

            if (rows == 0) {
                System.out.println("⚠️ Warning: Thread not found in DB, but may still exist in cache.");
            }

            // ❌ Bug: Cache not properly updated
            if (cachedThreads.contains(title)) {
                cachedThreads.remove(title); // removes only first occurrence
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close DB connection
     */
    public void close() {
        try {
            if (connection != null) {
                connection.close();  // ❌ Bug: Cached threads are lost, no persistence handling
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main for testing
    public static void main(String[] args) {
        // ❌ Issue: Hardcoded credentials
        DiscussionService service = new DiscussionService(
            "jdbc:mysql://localhost:3306/forumdb", "root", "password"
        );

        // Create threads
        service.createThread("First Post", "Hello world!", "Alice");
        service.createThread("Second Post", "Discussion about bugs", "Bob");

        // Fetch threads
        System.out.println("Threads: " + service.getAllThreads());

        // Delete one
        service.deleteThread("First Post");

        // Fetch again
        System.out.println("Threads after delete: " + service.getAllThreads());

        service.close();
    }
}

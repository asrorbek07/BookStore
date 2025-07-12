package uz.kruz;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.db.DatabaseInitializer;
import uz.kruz.db.DataPopulator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * BookStoreAdmin class for managing bookstore operations
 */
public class BookStoreAdmin {

    /**
     * Run all bookstore operations
     */
    public void run() {
        System.out.println("Bookstore Application Started!");

        // Initialize database
        initializeDatabase();

        // Populate database with sample data
        populateDatabase();

        // Display database connection info
        displayDatabaseInfo();

        // Test database queries
        testDatabaseQueries();
    }

    /**
     * Initialize the database by creating tables
     */
    private void initializeDatabase() {
        DatabaseInitializer initializer = new DatabaseInitializer();
        initializer.initializeDatabase();
    }

    /**
     * Populate the database with sample data
     */
    private void populateDatabase() {
        DataPopulator populator = new DataPopulator();
        populator.populateDatabase();
    }

    /**
     * Display database connection information
     */
    private void displayDatabaseInfo() {
        Connection connection = null;

        try {
            connection = DatabaseConnection.getInstance().getConnection();
            DatabaseMetaData metaData = connection.getMetaData();

            System.out.println("\nDatabase Connection Information:");
            System.out.println("-------------------------------");
            System.out.println("Connected to: " + metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion());
            System.out.println("JDBC Driver: " + metaData.getDriverName() + " " + metaData.getDriverVersion());
            System.out.println("URL: " + metaData.getURL());
            System.out.println("Username: " + metaData.getUserName());

        } catch (SQLException e) {
            System.err.println("Error getting database metadata: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    /**
     * Test database queries using plain JDBC
     */
    private void testDatabaseQueries() {
        Connection connection = null;

        try {
            connection = DatabaseConnection.getInstance().getConnection();
            System.out.println("\nTesting database queries...");

            // Count books
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM books")) {
                if (rs.next()) {
                    int bookCount = rs.getInt(1);
                    System.out.println("Total books in database: " + bookCount);
                }
            }

            // List categories
            System.out.println("\nAvailable Book Categories:");
            System.out.println("------------------------");
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id, name FROM categories")) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    System.out.println(id + ": " + name);
                }
            }

            // List books with authors
            System.out.println("\nBooks and Authors:");
            System.out.println("----------------");
            String booksQuery = "SELECT b.title, a.full_name AS author " +
                               "FROM books b " +
                               "JOIN book_authors ba ON b.id = ba.book_id " +
                               "JOIN authors a ON ba.author_id = a.id " +
                               "ORDER BY b.title";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(booksQuery)) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    System.out.println("\"" + title + "\" by " + author);
                }
            }

            // List users
            System.out.println("\nRegistered Users:");
            System.out.println("----------------");
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id, full_name, email, role FROM users")) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String fullName = rs.getString("full_name");
                    String email = rs.getString("email");
                    String role = rs.getString("role");
                    System.out.println(id + ": " + fullName + " (" + email + ") - " + role);
                }
            }

            System.out.println("\nDatabase queries completed successfully.");

        } catch (SQLException e) {
            System.err.println("Error executing database queries: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }
}
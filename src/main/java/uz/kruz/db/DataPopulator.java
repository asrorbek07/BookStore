package uz.kruz.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for populating the database with sample data
 */
public class DataPopulator {

    /**
     * Populate the database with sample data
     */
    public void populateDatabase() {
        Connection connection = null;
        Statement statement = null;

        try {
            // Get database connection
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.createStatement();

            System.out.println("Populating database with sample data...");

            // Read and execute SQL statements from data.sql
            List<String> sqlStatements = readSqlStatementsFromFile("/data.sql");
            for (String sql : sqlStatements) {
                if (!sql.trim().isEmpty()) {
                    // Modify INSERT statements to use INSERT IGNORE to handle duplicates
                    if (sql.trim().toUpperCase().startsWith("INSERT INTO")) {
                        sql = sql.trim().replaceFirst("(?i)INSERT INTO", "INSERT IGNORE INTO");
                    }
                    statement.execute(sql);
                }
            }

            System.out.println("Database populated successfully.");

        } catch (SQLException e) {
            System.err.println("Error populating database: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading data file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing statement: " + e.getMessage());
                }
            }
            DatabaseConnection.closeConnection(connection);
        }
    }

    /**
     * Read SQL statements from a file in the resources directory
     * 
     * @param fileName Name of the file in the resources directory
     * @return List of SQL statements
     * @throws IOException If an I/O error occurs
     */
    private List<String> readSqlStatementsFromFile(String fileName) throws IOException {
        List<String> sqlStatements = new ArrayList<>();
        StringBuilder statement = new StringBuilder();

        try (InputStream is = getClass().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comments and empty lines
                if (line.trim().isEmpty() || line.trim().startsWith("--")) {
                    continue;
                }

                statement.append(line).append(" ");

                // If the line ends with a semicolon, it's the end of a statement
                if (line.trim().endsWith(";")) {
                    String sql = statement.toString().trim();
                    if (!sql.isEmpty()) {
                        sqlStatements.add(sql);
                    }
                    statement = new StringBuilder();
                }
            }

            // Add any remaining SQL that might not end with a semicolon
            String remainingSql = statement.toString().trim();
            if (!remainingSql.isEmpty()) {
                sqlStatements.add(remainingSql);
            }
        }

        return sqlStatements;
    }
}

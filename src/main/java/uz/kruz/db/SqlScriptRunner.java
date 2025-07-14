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

public class SqlScriptRunner {

    public void runScript(String resourcePath, boolean insertIgnore) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            System.out.println("Executing SQL script: " + resourcePath);

            for (String sql : readSqlStatements(resourcePath)) {
                if (!sql.isBlank()) {
                    if (insertIgnore && sql.toUpperCase().startsWith("INSERT INTO")) {
                        sql = sql.replaceFirst("(?i)INSERT INTO", "INSERT IGNORE INTO");
                    }
                    statement.execute(sql);
                }
            }

            System.out.println("Script executed successfully.");

        } catch (SQLException | IOException e) {
            System.err.println("Script execution error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<String> readSqlStatements(String fileName) throws IOException {
        List<String> statements = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();

        try (InputStream is = getClass().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.trim().startsWith("--")) continue;
                buffer.append(line).append(" ");
                if (line.trim().endsWith(";")) {
                    statements.add(buffer.toString().trim());
                    buffer.setLength(0);
                }
            }

            String remaining = buffer.toString().trim();
            if (!remaining.isEmpty()) {
                statements.add(remaining);
            }
        }

        return statements;
    }
}

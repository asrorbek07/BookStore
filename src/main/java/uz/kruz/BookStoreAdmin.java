package uz.kruz;

import uz.kruz.db.SqlScriptRunner;
import uz.kruz.menu.MainMenu;

/**
 * BookStoreAdmin class for managing bookstore operations
 */
public class BookStoreAdmin {
    private final MainMenu mainMenu;

    BookStoreAdmin() {
        mainMenu = new MainMenu();
    }

    public void run() {
        System.out.println("Bookstore Application Started!");
//        initializeDatabase();
//        populateDatabase();
        mainMenu.show();

    }

    private void initializeDatabase() {
        SqlScriptRunner initializer = new SqlScriptRunner();
        initializer.runScript("/schema.sql", false);
    }

    private void populateDatabase() {
        SqlScriptRunner populator = new SqlScriptRunner();
        populator.runScript("/data.sql", true);
    }

}
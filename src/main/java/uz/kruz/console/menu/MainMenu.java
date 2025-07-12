package uz.kruz.console.menu;

import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class MainMenu {
    private BookMenu bookMenu;
    private AuthorMenu authorMenu;
    private CategoryMenu categoryMenu;
    private UserMenu userMenu;
    private OrderMenu orderMenu;
    
    private Scanner scanner;
    private Narrator narrator; 

    public MainMenu() {
        this.bookMenu = new BookMenu();
        this.authorMenu = new AuthorMenu();
        this.categoryMenu = new CategoryMenu();
        this.userMenu = new UserMenu();
        this.orderMenu = new OrderMenu();
        
        this.scanner = new Scanner(System.in);
        this.narrator = new Narrator(this, TalkingAt.Left); 
    }

    public void show() {
        int inputNumber = 0;

        while (true) {
            displayMenu();
            inputNumber = selectMenu();

            switch (inputNumber) {
            case 1:
                bookMenu.show();
                break;
            case 2:
                authorMenu.show(); 
                break;
            case 3:
                categoryMenu.show(); 
                break;
            case 4:
                userMenu.show();
                break;
            case 5:
                orderMenu.show();
                break;
            case 0:
                this.exitProgram();

            default:
                narrator.sayln("Choose again!");
            }
        }
    }

    private void displayMenu() {
        narrator.sayln("");
        narrator.sayln("..............................");
        narrator.sayln(" Bookstore Main Menu ");
        narrator.sayln("..............................");
        narrator.sayln(" 1. Book menu");
        narrator.sayln(" 2. Author menu");
        narrator.sayln(" 3. Category menu");
        narrator.sayln(" 4. User menu");
        narrator.sayln(" 5. Order menu");
        narrator.sayln("..............................");
        narrator.sayln(" 0. Exit program");
        narrator.sayln("..............................");
    }

    private int selectMenu() {
        narrator.say("Select: ");
        int menuNumber = scanner.nextInt();

        if (menuNumber >= 0 && menuNumber <= 5) {
            scanner.nextLine();
            return menuNumber;
        } else {
            narrator.sayln("It's an invalid number --> " + menuNumber);
            return -1;
        }
    }
    
    private void exitProgram() {
        narrator.sayln("Program exit. Bye...");
        scanner.close();
        System.exit(0);
    }    
}
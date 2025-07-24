package uz.kruz.menu;


import uz.kruz.console.BookConsole;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class BookMenu {
    //

    private Scanner scanner;
    private Narrator narrator;
    private BookConsole bookConsole;

    public BookMenu() {
        //
        this.scanner = new Scanner(System.in);
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.bookConsole = new BookConsole();
    }

    public void show() {
        //
        int inputNumber = 0;

        while (true) {
            displayMenu();
            inputNumber = selectMenu();

            switch (inputNumber) {
                case 1:
                    bookConsole.showAll();
                    break;
                case 2:
                    bookConsole.register();
                    break;
                case 3:
                    narrator.sayln("New Book deleted");
                    break;
                case 4:
                    narrator.sayln("Show all books");
                    break;
                case 5:
                    narrator.sayln("Search book by author id");
                    break;
                case 0:
                    this.exitProgram();
                default:
                    narrator.sayln("Choose again!");
            }
        }
    }

    private void displayMenu() {
        //
        narrator.sayln("");
        narrator.sayln("..............................");
        narrator.sayln(" Book menu ");
        narrator.sayln("..............................");
        narrator.sayln(" 1. View all books");
        narrator.sayln(" 2. Create book");
        narrator.sayln(" 3. Edit book");
        narrator.sayln(" 4. Delete book");
        narrator.sayln(" 5. Find books by author id");
        narrator.sayln("..............................");
        narrator.sayln(" 0. Exit program");
        narrator.sayln("..............................");
    }

    private int selectMenu() {
        //
        narrator.say("Select: ");
        int menuNumber = scanner.nextInt();

        if (menuNumber >= 0 && menuNumber <= 5) {
            scanner.nextLine();
            return menuNumber;
        } else {
            narrator.sayln("It's a invalid number --> " + menuNumber);
            return -1;
        }
    }

    private void exitProgram() {
        //
        narrator.sayln("Program exit. Bye...");
        scanner.close();
        System.exit(0);
    }
}
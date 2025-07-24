package uz.kruz.menu;


import uz.kruz.console.AuthorConsole;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;


import java.util.Scanner;

public class AuthorMenu {
    //
    private AuthorConsole authorConsole;
    private Scanner scanner;
    private Narrator narrator;

    public AuthorMenu() {
        //
        this.authorConsole = new AuthorConsole();
        this.scanner = new Scanner(System.in);
        this.narrator = new Narrator(this, TalkingAt.Left);
    }

    public void show() {
        //
        int inputNumber = 0;

        while (true) {
            displayMenu();
            inputNumber = selectMenu();

            switch (inputNumber) {
                //
                case 1:
                    authorConsole.register();
                    break;
                case 2:
                    authorConsole.modify();
                    break;
                case 3:
                    authorConsole.remove();
                    break;
                case 4:
                    authorConsole.showAll();
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
        narrator.sayln(" Author menu ");
        narrator.sayln("..............................");
        narrator.sayln("1. Add new author");
        narrator.sayln("2. Edit author name");
        narrator.sayln("3. Delete author");
        narrator.sayln("4. Show all authors");
        narrator.sayln("5. Delete all authors");
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
package uz.kruz.menu;


import uz.kruz.console.PublisherConsole;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class PublisherMenu {
    //
    PublisherConsole publisherConsole;
    private Scanner scanner;
    private Narrator narrator;

    public PublisherMenu() {
        //
        this.publisherConsole = new PublisherConsole();
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
                case 0:
                    this.exitProgram();
                case 1:
                    publisherConsole.showAll();
                    break;
                case 2:
                    publisherConsole.register();
                    break;
                case 3:
                    publisherConsole.modify();
                    break;
                case 4:
                    publisherConsole.remove();
                    break;
                default:
                    narrator.sayln("Choose again!");
            }
        }
    }

    private void displayMenu() {
        //
        narrator.sayln("");
        narrator.sayln("..............................");
        narrator.sayln(" Publisher menu ");
        narrator.sayln("..............................");
        narrator.sayln(" 1. View all publisher");
        narrator.sayln(" 2. Create publisher");
        narrator.sayln(" 3. Edit publisher");
        narrator.sayln(" 4. Delete publisher");
        narrator.sayln("..............................");
        narrator.sayln(" 0. Exit program");
        narrator.sayln("..............................");
    }

    private int selectMenu() {
        //
        narrator.say("Select: ");
        int menuNumber = scanner.nextInt();

        if (menuNumber >= 0 && menuNumber <= 4) {
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
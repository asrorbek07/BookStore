package uz.kruz.menu;


import uz.kruz.console.CategoryConsole;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class CategoryMenu {
    //
    private CategoryConsole categoryConsole;
    private Scanner scanner;
    private Narrator narrator;

    public CategoryMenu() {
        //
        this.categoryConsole = new CategoryConsole();
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
                    categoryConsole.showAll();
                    break;
                case 2:
                    categoryConsole.register();
                    break;
                case 3:
                    categoryConsole.modify();
                    break;
                case 4:
                    categoryConsole.remove();
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
        narrator.sayln(" Category menu ");
        narrator.sayln("..............................");
        narrator.sayln(" 1. View all categories");
        narrator.sayln(" 2. Create category");
        narrator.sayln(" 3. Edit category");
        narrator.sayln(" 4. Delete category");
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
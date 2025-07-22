package uz.kruz.menu;


import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class ReviewMenu {
    //

    private Scanner scanner;
    private Narrator narrator;

    public ReviewMenu() {
        //
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
                    narrator.sayln("Added review");
                    break;
                case 2:
                    narrator.sayln("Deleted review");
                    break;
                case 3:
                    narrator.sayln("Edited review");
                    break;
                case 4:
                    narrator.sayln("Showing reviews");
                    break;
                case 5:
                    narrator.sayln("Searching for reviews");
                    break;
                case 6:
                    narrator.sayln("Filtered by ratings");
                    break;
                case 7:
                    narrator.sayln("Showing user's reviews");
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
        narrator.sayln(" Review menu ");
        narrator.sayln("..............................");
        narrator.sayln("1. Add review");
        narrator.sayln("2. Delete review");
        narrator.sayln("3. Update review");
        narrator.sayln("4. View reviews");
        narrator.sayln("5. Search reviews");
        narrator.sayln("6. Filter reviews by rating");
        narrator.sayln("7. View reviews by User");
        narrator.sayln("..............................");
        narrator.sayln(" 0. Exit program");
        narrator.sayln("..............................");
    }

    private int selectMenu() {
        //
        narrator.say("Select: ");
        int menuNumber = scanner.nextInt();

        if (menuNumber >= 0 && menuNumber <= 7) {
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
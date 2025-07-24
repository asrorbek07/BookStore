package uz.kruz.menu;


import uz.kruz.console.OrderConsole;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class OrderMenu {
    //

    private Scanner scanner;
    private Narrator narrator;
    private OrderConsole orderConsole;

    public OrderMenu() {
        //
        this.scanner = new Scanner(System.in);
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.orderConsole = new OrderConsole();
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
                    orderConsole.showAll();
                    break;
                case 2:
                    orderConsole.register();
                    break;
                case 3:
                    orderConsole.delete();
                    break;
                case 4:
                    orderConsole.modify();
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
        narrator.sayln(" Order menu ");
        narrator.sayln("..............................");
        narrator.sayln("1. Show all orders");
        narrator.sayln("2. Add Order");
        narrator.sayln("3. Delete Order");
        narrator.sayln("4. Update Order");
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
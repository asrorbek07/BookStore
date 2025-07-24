package uz.kruz.menu;


import uz.kruz.console.ShipmentConsole;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class ShipmentMenu {
    //
    private ShipmentConsole shipmentConsole;
    private Scanner scanner;
    private Narrator narrator;

    public ShipmentMenu() {
        //
        this.scanner = new Scanner(System.in);
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.shipmentConsole = new ShipmentConsole();
    }

    public void show() {
        //
        int inputNumber = 0;

        while (true) {
            displayMenu();
            inputNumber = selectMenu();

            switch (inputNumber) {
                case 1:
                    shipmentConsole.showAll();
                    break;
                case 2:
                    shipmentConsole.register();
                    break;
                case 3:
                    narrator.sayln("Updated shipment");
                    break;
                case 4:
                    narrator.sayln("View shipments");
                    break;
                case 5:
                    narrator.sayln("Searched for shipment");
                    break;
                case 6:
                    narrator.sayln("Filtered by status");
                    break;
                //
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
        narrator.sayln(" Shipment menu ");
        narrator.sayln("..............................");
        narrator.sayln("1. View All shipments");
        narrator.sayln("2. Create shipment");
        narrator.sayln("3. Update shipment");
        narrator.sayln("4. View shipments");
        narrator.sayln("5. Search shipment");
        narrator.sayln("6. Filter shipment by status");
        narrator.sayln("..............................");
        narrator.sayln(" 0. Exit program");
        narrator.sayln("..............................");
    }

    private int selectMenu() {
        //
        narrator.say("Select: ");
        int menuNumber = scanner.nextInt();

        if (menuNumber >= 0 && menuNumber <= 6) {
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
package uz.kruz.menu;


import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class MainMenu {
    //
    private UserMenu userMenu;
    private AuthorMenu authorMenu;
    private PublisherMenu publisherMenu;
    private BookMenu bookMenu;
    private OrderMenu orderMenu;
    private ShipmentMenu shipmentMenu;
    private ReviewMenu reviewMenu;
    private CategoryMenu categoryMenu;
    private Scanner scanner;
    private Narrator narrator;

    public MainMenu() {
        //
        this.userMenu = new UserMenu();
        this.authorMenu = new AuthorMenu();
        this.publisherMenu = new PublisherMenu();
        this.bookMenu = new BookMenu();
        this.orderMenu = new OrderMenu();
        this.shipmentMenu = new ShipmentMenu();
        this.reviewMenu = new ReviewMenu();
        this.categoryMenu = new CategoryMenu();
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
                    userMenu.show();
                    break;
                case 2:
                    authorMenu.show();
                    break;
                case 3:
                    publisherMenu.show();
                    break;
                case 4:
                    bookMenu.show();
                    break;
                case 5:
                    orderMenu.show();
                    break;
                case 6:
                    shipmentMenu.show();
                    break;
                case 7:
                    reviewMenu.show();
                    break;
                case 8:
                    categoryMenu.show();
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
        narrator.sayln(" Main menu ");
        narrator.sayln("..............................");
        narrator.sayln(" 1. User Menu");
        narrator.sayln(" 2. Author menu");
        narrator.sayln(" 3. Publisher menu");
        narrator.sayln(" 4. Book menu");
        narrator.sayln(" 5. Order menu");
        narrator.sayln(" 6. Shipment menu");
        narrator.sayln(" 7. Review menu");
        narrator.sayln(" 8. Category menu");
        narrator.sayln("..............................");
        narrator.sayln(" 0. Exit program");
        narrator.sayln("..............................");
    }

    private int selectMenu() {
        //
        narrator.say("Select: ");
        int menuNumber = scanner.nextInt();

        if (menuNumber >= 0 && menuNumber <= 8) {
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
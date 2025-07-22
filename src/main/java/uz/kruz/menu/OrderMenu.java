package uz.kruz.menu;


import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class OrderMenu {
	//

	private Scanner scanner;
	private Narrator narrator;

	public OrderMenu() {
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
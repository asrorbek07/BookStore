package uz.kruz.menu;


import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class UserMenu {
	//

	private Scanner scanner;
	private Narrator narrator;

	public UserMenu() {
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
				case 1:
					narrator.sayln("Added User");
					break;
				case 2:
					narrator.sayln("Deleted user");
					break;
					case 3:
						narrator.sayln("View user");
						break;
						case 4:
							narrator.sayln("Updated user");
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
		narrator.sayln(" User menu ");
		narrator.sayln("...............................");
		narrator.sayln("1. Add User ");
		narrator.sayln("2. Delete User");
		narrator.sayln("3. View User");
		narrator.sayln("4. Update User");
		narrator.sayln("0. Exit program");
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
package uz.kruz.menu;


import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;

import java.util.Scanner;

public class PublisherMenu {
	//

	private Scanner scanner;
	private Narrator narrator;

	public PublisherMenu() {
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
					narrator.sayln("Added publisher");
					break;
					case 2:
						narrator.sayln("deleted publisher");
						break;
						case 3:
							narrator.sayln("updated publisher");
							break;
							case 4:
								narrator.sayln("Updated publisher");
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
		narrator.sayln(" Main menu ");
		narrator.sayln("1. Add publisher");
		narrator.sayln("2. Delete publisher");
		narrator.sayln("3. Edit publisher");
		narrator.sayln("4. Update publisher");
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
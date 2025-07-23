package uz.kruz.util;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleUtil {
	//
	private final Scanner scanner;
	private final Narrator narrator;

	public ConsoleUtil(Object target) {
		// 
		this.scanner = new Scanner(System.in); 
		this.narrator = new Narrator(target.getClass().getSimpleName(), TalkingAt.Left); 
	}
	
	public ConsoleUtil(Narrator narrator) {
		// 
		this.scanner = new Scanner(System.in); 
		this.narrator = narrator; 
	}
	
	public String getValueOf(String label){
		//
		narrator.say(label + ": ");
		String inputStr = scanner.nextLine();
		inputStr = inputStr.trim();
		return inputStr;
	}

	public BigDecimal getValueOfBigDecimal(String label){
		while (true){
			narrator.say(label + ": ");
			String inputStr = scanner.nextLine();
			inputStr = inputStr.trim();
			try {
				return new BigDecimal(inputStr);
			} catch (NumberFormatException e) {
				narrator.say("Invalid input. Please enter a valid decimal number.");
			}
		}
	}
	public Integer getValueOfInteger(String label){
		while (true){
			narrator.say(label + ": ");
			String inputStr = scanner.nextLine();
			inputStr = inputStr.trim();
			try {
				return Integer.parseInt(inputStr);
			} catch (NumberFormatException e) {
				narrator.say("Invalid input. Please enter a valid integer.");
			}
		}
	}
}
package uz.kruz.util;

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
}
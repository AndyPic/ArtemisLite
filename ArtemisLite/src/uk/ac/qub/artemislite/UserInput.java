/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.Scanner;

/**
 * @author Jordan Davis
 *
 */
public class UserInput {

	private static Scanner scanner = new Scanner(System.in);

	/**
	 * Return int value for user input
	 * 
	 * @return int
	 */
	public static int getUserInputInt() {

		int input;
		String userInput;

		while (!scanner.hasNextInt()) {
			 userInput = scanner.nextLine();
			if(UserInterface.commands(userInput)) {
				continue;
			} else {
				System.out.println("That is not a valid number! please try again");
			}
			
		}

		input = scanner.nextInt();
		//clear for next input
		scanner.nextLine();

		return input;

	}

	/**
	 * Return String value for user input
	 * 
	 * @return String
	 */
	public static String getUserInputString() {
		String userInput = scanner.nextLine();
		
		if(UserInterface.commands(userInput)) {
			//recursive call to allow another entry after rules.
			getUserInputString();
		}
			return userInput;
	}

	/**
	 * Method to close the scanner
	 */
	public static void closeScanner() {
		scanner.close();
	}


}

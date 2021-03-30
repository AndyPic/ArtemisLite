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

		while (!scanner.hasNextInt()) {
			System.out.println("That is not a valid number! please try again");
			scanner.nextLine();
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

		return scanner.nextLine();

	}

	/**
	 * Method to close the scanner
	 */
	public static void closeScanner() {
		scanner.close();
	}


}

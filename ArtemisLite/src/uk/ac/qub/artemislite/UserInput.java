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

	static Scanner scanner = new Scanner(System.in);

	/**
	 * Return int value for user input
	 * 
	 * @return int
	 */
	public static int getUserInputInt() {

		int input;

		while (!scanner.hasNextInt()) {
			System.out.println("That is not a valid number!");
			scanner.nextLine();
		}

		input = scanner.nextInt();

		// Do i need to clear scanner here? or can input var just be removed and
		// replaced with "return scanner.nextInt();"
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

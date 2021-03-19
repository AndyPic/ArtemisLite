/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class GUI implements Runnable {

	/**
	 * Loops through the start-up message with a delay, print the rest of the
	 * message if it's interrupted before finishing
	 */
	@Override
	public void run() {

		// Keep track of what messages have been printed
		int progress = 0;

		// Array of intro message sequence
		String[] startingMessage = new String[7];
		startingMessage[0] = ArtemisCalendar.getMonthName(ArtemisCalendar.getCalendar().get(2)) + ", "
				+ ArtemisCalendar.getCalendar().get(1) + ".";
		startingMessage[1] = "You and your colleagues are tasked with delivering The Artemis Project to success.";
		startingMessage[2] = "The Artemis Project aims to launch the first woman, and next man to the moon by "
				+ ArtemisCalendar.getMonthName(ArtemisCalendar.getEndDate().get(2)) + ", "
				+ ArtemisCalendar.getEndDate().get(1) + ".";
		startingMessage[3] = "In order to accomplish this lofty goal, you must work with your colleagues to ensure 'All Systems are Go!' by launch-day.";
		startingMessage[4] = "Can your team acquire and fully develop all of the systems needed for a successful Lift-off?";
		startingMessage[5] = "...or will you just be in it for personal gain?";
		startingMessage[6] = "You decide!";

		// Quick sleep so cancel message prints first
		try {
			Thread.sleep(10);
		} catch (InterruptedException e1) {
			// Print error, shouldn't get interrupted, unless you're really quick!
			e1.printStackTrace();
		}

		// Loop through the intro message with a delay
		for (int loop = 0; loop < startingMessage.length; loop++) {

			try {
				System.out.println(startingMessage[loop]);
				progress++;
				Thread.sleep(1337);
			} catch (InterruptedException e) {
				// Break out of for loop on interrupt
				break;
			}

		}

		// Loop through the rest of intro if interrupted
		if (progress < startingMessage.length) {
			for (; progress < startingMessage.length; progress++) {
				System.out.println(startingMessage[progress]);
			}
		}

	}// END

	/**
	 * 'Clears the console' by printing 25 blank lines in console. 
	 * Overloaded version of this method accepts specific number of lines to clear
	 */
	public static void clearConsole() {

		for (int loop = 0; loop <= 25; loop++) {
			System.out.println();
		}

	}
	
	/**
	 * 'Clears the console' by printing {@link numberOfLines} blank lines in
	 * console.
	 * 
	 * @param numberOfLines
	 */
	public static void clearConsole(int numberOfLines) {

		for (int loop = 0; loop < numberOfLines; loop++) {
			System.out.println();
		}

	}

	/**
	 * displays yes/no menu and asks for user input. Accepts "1" or "yes" from user
	 * 
	 * @return returns 1 for yes, 2 nore no.
	 */
	public static int yesNoMenu() {
		String userInput;
		int userInt;
		boolean valid;

		valid = false;
		userInt = 0;

		System.out.println("1. Yes\n2. No");

		do {
			userInput = UserInput.getUserInputString().trim().toLowerCase();

			if (userInput.length() > 0) {
				switch (userInput.charAt(0)) {
				case '1':
				case 'y':
					userInt = 1;
					valid = true;
					break;
				case '2':
				case 'n':
					userInt = 2;
					valid = true;
					break;
				default:
					System.out.println("Invalid input - please try again");
				}
			}

		} while (!valid);

		return userInt;

	}

	/**
	 * Method to capitalise the first letter of a String.
	 * 
	 * @param input
	 * @return
	 */
	public static String capitaliseFirstLetter(String input) {

		// Check if 1st char is already Uppercase
		if (!Character.isUpperCase(input.charAt(0))) {
			input = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
		}
		return input;
	}

}

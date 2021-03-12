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

		// Array of intro message sequence (can be as long as needed)
		String[] startingMessage = new String[6];
		startingMessage[0] = "A long time ago...";
		startingMessage[1] = "In a galaxy far, far away...";
		startingMessage[2] = "Bla";
		startingMessage[3] = "Blaa";
		startingMessage[4] = "Blaaa";
		startingMessage[5] = "END";

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
				// TODO Change sleep to whatever feels right
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
	 * Displays the intro sequence for the game
	 */
	public static void displayIntroMessage() {

		System.out.println("\nIntro message......");
		System.out.println("Press Enter to begin the game");
		UserInput.getUserInputString();

	}

	/**
	 * Displays the game loss message
	 */
	public static void displayGameLossMessage(int turnNumber) {
		// TODO: add actual ending message
		System.out.println("\nProject Artemis has failed, the year is " + turnNumber + ".................");

	}

	/**
	 * Displayes the game won message
	 */
	public static void displayGameWonMessage(int turnNumber) {
		// TODO: add actual ending message
		System.out.println("\nProject Artemis is a success, the year is " + turnNumber + ".................");

	}

}

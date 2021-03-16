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
		// TODO fix input fall-through

		// Keep track of what messages have been printed
		int progress = 0;

		// Array of intro message sequence (can be as long as needed)
		String[] startingMessage = new String[7];
		startingMessage[0] = ArtemisCalendar.getMonthName(ArtemisCalendar.getCalendar().get(2)) + ", "
				+ ArtemisCalendar.getCalendar().get(1) + ".";
		startingMessage[1] = "You and your colleagues are tasked with delivering The Artemis Project to success.";
		// TODO: Change date expected to launch to that in calendar
		startingMessage[2] = "The Artemis Project aims to launch the first woman, and next man to the moon by 2024.";
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
	 * Displays the game loss message
	 */
	public static void displayGameLossMessage(Board board) {
		// TODO: add actual ending message
		// TODO: show mission progress
		System.out.printf("On %s The Artemis Project has failed at %.1f%s completion.\n",
				ArtemisCalendar.getCalendar().getTime(), GUI.missionProgress(board), "%");

	}

	/**
	 * Displays the game won message
	 */
	public static void displayGameWonMessage() {
		// TODO: add actual ending message
		// TODO: show time under / over estimated completion date
		System.out.printf("On %s The Artemis Project has succesfully launched!\n",
				ArtemisCalendar.getCalendar().getTime());

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

			// Inelegant fix to trying to perform charAt on an empty String
			userInput += "x";

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

		} while (!valid);

		return userInt;

	}
	
	/**
	 * Method to calculate the progress towards completing the mission.
	 * 
	 * @return
	 */
	public static double missionProgress(Board board) {

		// Get resource amount for completion
		double totalCost = 0;

		for (SquareDetails square : SquareDetails.values()) {
			totalCost += square.getCost();
			totalCost += square.getMajorCost();
			totalCost += (square.getMinorCost() * 3);
		}

		// Get resource amount currently invested
		double currentProgress = 0;

		for (Square b : board.getSquares()) {

			if (b instanceof StandardSquare) {
				StandardSquare stdSq = (StandardSquare) b;

				if (stdSq.getOwnedBy() != null) {

					currentProgress += stdSq.getRentCost();

					if (stdSq.getCurrentMajorDevLevel() > 0) {
						currentProgress += (stdSq.getCurrentMajorDevLevel() * stdSq.getMajorDevCost());
					}

					if (stdSq.getCurrentMinorDevLevel() > 0) {
						currentProgress += (stdSq.getCurrentMinorDevLevel() * stdSq.getMinorDevCost());
					}
				}
			}
		}

		return (currentProgress / totalCost) * 100;
	} // END

}

/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class UserInterface implements Runnable {

	private Queue<String> messageToDisplay = new LinkedList<String>();

	/**
	 * Loops through the start-up message with a delay, print the rest of the
	 * message if it's interrupted before finishing
	 */
	@Override
	public void run() {

		// Loop through the intro message with a delay
		while (!messageToDisplay.isEmpty()) {

			try {
				System.out.println(messageToDisplay.poll());
				//only pauses betweeen lines when it has not been interrupted
				if (!Thread.interrupted()) {
					Thread.sleep(1337);
				}

			} catch (InterruptedException e) {
				//on interrupt continue to print message
			}

		}

	}// END

	/**
	 * Que the into message for display on screen
	 */
	public void loadIntroMessage() {

		messageToDisplay.add(ArtemisCalendar.getMonthName(ArtemisCalendar.getCalendar().get(2)) + ", "
				+ ArtemisCalendar.getCalendar().get(1) + ".");

		messageToDisplay.add("NASA have chosen you to help them deliver The Artemis Project successfully.");
		messageToDisplay.add("The Project aims to launch the first woman, and next man to the moon by "
				+ ArtemisCalendar.getMonthName(ArtemisCalendar.getEndDate().get(2)) + ", "
				+ ArtemisCalendar.getEndDate().get(1) + ".");
		messageToDisplay.add(
				"In order to accomplish this lofty goal, you must work alongside other chosen companies to ensure 'All Systems are Go!' by launch-day.");
		messageToDisplay.add(
				"Can you work together to research and fully develop all of the systems needed for a successful Lift-off?");
		messageToDisplay.add("...or will your team just be in it for personal gain?");
		messageToDisplay.add("You decide!");
		GameLauncher.displayMessage();
	}
	
	/**
	 * Que the game win message for display on screen
	 */
	public void loadWinMessage() {

		messageToDisplay.add(String.format("On %s The Artemis Project has succesfully launched!\n",
				ArtemisCalendar.getCalendar().getTime()));
		if (GameStatistics.timeToLaunch() > 0) {
			messageToDisplay.add(String.format("Congratulations! The mission launched %d months ahead of schedule.\n" , GameStatistics.timeToLaunch()));
		} else if (GameStatistics.timeToLaunch() < 0) {
			messageToDisplay.add(String.format("Unfortunately, the mission launched %d months behind schedule.\n" , GameStatistics.timeToLaunch()));
		} else {
			messageToDisplay.add("The mission launched on schedule.\n");
		}
		messageToDisplay.add("\nMission Debrief:\n");
		messageToDisplay.add(systemCompletion(SystemType.ORION));
		messageToDisplay.add(systemCompletion(SystemType.SLS));
		messageToDisplay.add(systemCompletion(SystemType.EGS));
		messageToDisplay.add(systemCompletion(SystemType.GATEWAY));
		GameLauncher.displayMessage();
	}
	
	/**
	 * Que the game loss message for display on screen
	 */
	public void loadLossMessage() {

		messageToDisplay.add(ArtemisCalendar.getMonthName(ArtemisCalendar.getCalendar().get(2)) + ", "
				+ ArtemisCalendar.getCalendar().get(1) + ".");
		messageToDisplay.add(String.format("On %s The Artemis Project has failed at %.1f%s completion.\n\nMission Debrief:\n",
				ArtemisCalendar.getCalendar().getTime(), GameStatistics.missionProgress(GameLauncher.board), "%"));
		messageToDisplay.add(systemCompletion(SystemType.ORION));
		messageToDisplay.add(systemCompletion(SystemType.SLS));
		messageToDisplay.add(systemCompletion(SystemType.EGS));
		messageToDisplay.add(systemCompletion(SystemType.GATEWAY));
		GameLauncher.displayMessage();
	}

	/**
	 * finds if a system has started research, completed construction or never
	 * started and returns string
	 * 
	 * @param systemType to be checked
	 * @return returns string with approperate message
	 */
	public String systemCompletion(SystemType system) {
		boolean isComplete = true;
		boolean isStarted = true;
		Player player = null;
		String result;

		for (StandardElement stdElement : GameLauncher.board.getStdElements()) {

			if (stdElement.getElementSystem().equals(system)) {

				if (!stdElement.isMaxDevelopment()) {
					isComplete = false;
				}
				if (stdElement.getOwnedBy() == null) {
					isComplete = false;
					isStarted = false;
					break;
				} else {
					player = stdElement.getOwnedBy();
				}

			}
		}

		if (isComplete) {
			result = String.format("\nAll elements of %s were successfully researched and constructed by %s!\n",
					system.getName(), player.getName());
		} else if (isStarted) {
			result = String.format(
					"\n%s started research & development on the %s, but unfortunately the Artemis project failed before construction could be completed.\n",
					player.getName(), system.getName());
		} else {
			result = String.format(
					"\nDespite the efforts invested by the teams, %s never managed to get past the initial research stages.\n",
					system.getName());
		}

		return result;
	}

	/**
	 * 'Clears the console' by printing 25 blank lines in console. Overloaded
	 * version of this method accepts specific number of lines to clear
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
	
	/**
	 * Method to hold various chat commands
	 * 
	 * @return
	 */
	public static boolean commands(String userInput) {
		boolean isCmdValid = false;
		List<String> validCommands = new ArrayList<>();
		validCommands.add("rules");
		validCommands.add("giveall");
		
		// Find if command is valid
		for (String cmd: validCommands) {
			if (userInput.equalsIgnoreCase(cmd.trim())) {
				isCmdValid = true;
				// If cmd valid, execute the command
				switch (validCommands.indexOf(cmd)) {
				
				case 0:
					GameLauncher.showGameRules();
					break;
				case 1:
					GameLauncher.giveAll();
					break;
				
				}
			}
		}
		
		return isCmdValid;
	}

}

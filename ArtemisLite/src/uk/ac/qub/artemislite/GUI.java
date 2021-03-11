/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * @author Jordan Davis
 *
 */
public class GUI {

	
	
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
		
		System.out.println("\nIntro message.............................");
		System.out.println("Press Enter to begin the game");
		UserInput.getUserInputString();
		
	}
	
	/**
	 * Displays the game loss message
	 */
	public static void displayGameLossMessage() {
		//TODO: add actual ending message
		System.out.println("\nProject Artemis has failed.................");
		
	}
	
	/**
	 * Displayes the game won message
	 */
	public static void displayGameWonMessage() {
		//TODO: add actual ending message
		System.out.println("\nProject Artemis is a success.................");
		
	}
	
}

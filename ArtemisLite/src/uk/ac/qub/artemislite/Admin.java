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
public class Admin {
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		beginGame();

	}// END MAIN
	
	public static void beginGame() {
		
		//displays game intro
		GameLauncher.introMessage();
		
		// Runs game start Menu
		GameLauncher.startMenu();
		
		//end game
		GameLauncher.gameOverSequence();

		// Close scanner
		UserInput.closeScanner();
		
		
	}
	
}

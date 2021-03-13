/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;

/**
 * 
 * @author Jordan Davis
 *
 */
public class GameLauncher {

	private final static int MIN_PLAYERS = 2;
	private final static int MAX_PLAYERS = 4;

	/**
	 * Game starting Menu
	 */
	public static void startMenu() {
		boolean validOption = false;

		System.out.println("Welcome to Artemis Lite");

		do {

			System.out.println("\nPlease select a menu option by entering a number: (e.g. 1)" + "\n1.Start Game"
					+ "\n2.Show Game Rules" + "\n3.Quit Game");

			// TODO: Should we change this so all menus accept a String as valid also? JD
			// (e.g.'Start game')
			switch (UserInput.getUserInputInt()) {
			case 1:
				validOption = true;
				break;
			case 2:
				// TODO: Game rules method needed JD
				System.out.println("game rules shown");
				break;
			case 3:
				//TODO: Not working, game does not end until after players have been entered? needs fixed JD
				Admin.GAME_OVER = true;
				validOption = true;
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
			}

		} while (!validOption);
		GUI.clearConsole(4);
	}

	/**
	 * Menu for game settings (players & turn order)
	 * 
	 * @param turnLauncher
	 */
	public static void startGame(TurnLauncher turnLauncher) {

		boolean start = false;

		System.out.println("Now its time to add players. This game supports between " + MIN_PLAYERS + " and "
				+ MAX_PLAYERS + " players.");

		do {
			ArrayList<Player> players = turnLauncher.getPlayers();

			if (turnLauncher.players.size() > 0) {
				System.out.println("\nCurrent Registered Players:");
				turnLauncher.displayPlayers();
			}

			// TODO: this list need to be made dynamic so that you cant add more players
			// when at max etc. JD
			System.out.println(
					"\nSelect an option:\n1. Add New Player" + "\n2. Modify Existing Player" + "\n3. Begin Game");

			switch (UserInput.getUserInputInt()) {
			case 1:
				if (players.size() < MAX_PLAYERS) {
					turnLauncher.addPlayer();
				} else {
					System.out.println("Sorry you are already at the max number of players");
				}
				break;
			case 2:

				if (players.size() >= 1) {
					turnLauncher.modifyPlayer();
				} else {
					System.out.println("There is no players to modify!");
				}

				break;
			case 3:
				if (players.size() >= MIN_PLAYERS) {
					start = true;
				} else {
					System.out.println("You need to register more players");
				}
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
			}

		} while (!start);

		
		// Allow option to play a long game with greater initial resources
		// or a short game with default resources
		GUI.clearConsole(4);
		System.out.println("\nSelect an option:\n1. Short Game" + "\n2. Long Game");
		switch (UserInput.getUserInputInt()) {
		case 1:
			break;
		case 2:
			turnLauncher.setupLongGame();
			break;
		default:
			System.out.println("Invalid Menu Option, please try again");
		}

		// finds the order that players will take their turn
		turnLauncher.findPlayerOrder();

		GUI.displayIntroMessage();

	}

}

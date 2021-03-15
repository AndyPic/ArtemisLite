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
				System.out.println("Are you sure you want to quit the game?");
				if (GUI.yesNoMenu() == 1) {
					Admin.GAME_OVER = true;
					validOption = true;
				}
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

			System.out.printf("\nSelect an option:");

			if (players.size() < MAX_PLAYERS) {
				System.out.printf("\n1. Add New Player");
			}
			if (players.size() >= 1 && players.size() < MAX_PLAYERS) {
				System.out.printf("\n2. Modify Existing Player");
			}
			if (players.size() >= MIN_PLAYERS && players.size() < MAX_PLAYERS) {
				System.out.printf("\n3. Begin Game");
			}
			if (players.size() == MAX_PLAYERS) {
				System.out.printf("\n1. Begin Game\n2. Modify Existing Player");
			}

			switch (UserInput.getUserInputInt()) {
			case 1:
				if (players.size() < MAX_PLAYERS) {
					turnLauncher.addPlayer();
				} else {
					start = true;
				}
				break;
			case 2:
				if (players.size() >= 1) {
					turnLauncher.modifyPlayer();
					// break inside if, so fall through to default !if
					break;
				}
			case 3:
				if (players.size() >= MIN_PLAYERS && players.size() < MAX_PLAYERS) {
					start = true;
					break;
				}
			default:
				System.out.println("Invalid Menu Option, please try again");
			}

		} while (!start);

		// Allow option to play a long game with greater initial resources
		// or a short game with default resources
		GUI.clearConsole(4);

		int gameLengthInput;
		do {
			System.out.println("\nSelect an option:\n1. Short Game" + "\n2. Long Game" + "\n3. Game length details");
			gameLengthInput = UserInput.getUserInputInt();
			switch (gameLengthInput) {
			case 1:
				break;
			case 2:
				turnLauncher.setupLongGame();
				break;
			case 3:
				// TODO update info with new balance changes
				System.out.println("Some details about the different modes...");

				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
			}
		} while (gameLengthInput != 1 && gameLengthInput != 2);

		// finds the order that players will take their turn
		turnLauncher.findPlayerOrder();

		// GUI.displayIntroMessage();

	}

}

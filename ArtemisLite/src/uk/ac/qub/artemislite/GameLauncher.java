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
	
	// Sets game-over, main game loop
	private static boolean gameOver = false;

	/**
	 * Game starting Menu
	 */
	public static void startMenu() {
		boolean validOption = false;

		System.out.println("Welcome to Artemis Lite");

		do {

			System.out.println("\n=====| MENU |=====\nHint: you can select a menu option by entering a number: (e.g. 1)" + "\n1.Start Game"
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
					gameOver = true;
					validOption = true;
				}
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
			}

		} while (!validOption);
		GUI.clearConsole(8);
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
				System.out.println("\n=====| PLAYERS |=====");
				turnLauncher.displayPlayers();
			}

			System.out.printf("\n=====| MENU |=====\n");

			if (players.size() < MAX_PLAYERS) {
				System.out.printf("1. Add New Player\n");
			}
			if (players.size() >= 1 && players.size() < MAX_PLAYERS) {
				System.out.printf("2. Modify Existing Player\n");
			}
			if (players.size() >= MIN_PLAYERS && players.size() < MAX_PLAYERS) {
				System.out.printf("3. Begin Game\n");
			}
			if (players.size() == MAX_PLAYERS) {
				System.out.printf("(Max number of players reached)\n1. Begin Game\n2. Modify Existing Player\n");
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
		GUI.clearConsole(8);

		int gameLengthInput;
		do {
			System.out.println("\n=====| MENU |=====\n1. Short Game" + "\n2. Long Game" + "\n3. Game length details");
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
				GUI.clearConsole(8);
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
			}
		} while (gameLengthInput != 1 && gameLengthInput != 2);
		
		GUI.clearConsole(8);
		
		// finds the order that players will take their turn
		turnLauncher.findPlayerOrder();

		// GUI.displayIntroMessage();

	}

	/**
	 * @return the gameOver
	 */
	public static boolean isGameOver() {
		return gameOver;
	}
	
	/**
	 * allow gameOver to be declared externally from 
	 */
	public static void declareGameOver() {
		gameOver = true;
	}
	
	/**
	 * 
	 * @param turnLauncher
	 * @param board
	 */
	public static void endGame() {
		
		System.out.println("Are you sure you want to declare bankruptcy and end the game?");
		
		if (GUI.yesNoMenu() == 1) {
			gameOver = true;
		}
		
	}

}

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

	private static TurnLauncher turnLauncher = new TurnLauncher();
	private static Board board = new Board();

	private final static int MIN_PLAYERS = 2;
	private final static int MAX_PLAYERS = 4;
	private final static String MENU_HEADER = "\n=====| MENU |=====\n";

	// Sets game-over, main game loop
	private static boolean gameOver = false;

	/**
	 * displays game intro message to screen
	 */
	public static void introMessage() {
		// Intro message
		// TODO: can this be made static?
		GUI introMessage = new GUI();
		BufferedInterrupter buffInter = new BufferedInterrupter();
		Thread introThread = new Thread(introMessage);
		Thread inputThread = new Thread(buffInter);

		System.out.println("== Hit enter to skip intro ==\n");

		introThread.start();

		inputThread.start();

		// interrupt introThread if still running on input
		while (introThread.isAlive()) {
			if (!inputThread.isAlive()) {
				introThread.interrupt();
			}
		}
		// Stops the input thread after intro message finished
		inputThread.interrupt();

		GUI.clearConsole(1);

	}

	/**
	 * Game starting Menu
	 */
	public static void startMenu() {

		boolean gameBegin = false;

		do {

			System.out.println(MENU_HEADER + "Hint: you can select a menu option by entering a number: (e.g. 1)"
					+ "\n1.Start Game" + "\n2.Show Game Rules" + "\n3.Quit Game");

			// TODO: Should we change this so all menus accept a String as valid also? JD
			// (e.g.'Start game')
			switch (UserInput.getUserInputInt()) {
			case 1:
				gameBegin = true;
				break;
			case 2:
				// TODO: Game rules method needed JD
				System.out.println("game rules shown");
				break;
			case 3:
				System.out.println("Are you sure you want to quit the game?");
				if (GUI.yesNoMenu() == 1) {
					declareGameOver();
					gameBegin = true;
				}
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
			}

		} while (!gameBegin);

		GUI.clearConsole(20);

		if (!gameOver) {
			GameLauncher.startGame();
		}

	}

	/**
	 * Menu for game settings (players & turn order)
	 * 
	 * @param turnLauncher
	 */
	public static void startGame() {

		System.out.println("Now its time to add players. This game supports between " + MIN_PLAYERS + " and "
				+ MAX_PLAYERS + " players.");

		createPlayers();

		int gameLengthInput;
		do {
			System.out.println(MENU_HEADER + "1. Short Game" + "\n2. Long Game" + "\n3. Game length details");
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
				GUI.clearConsole(20);
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
			}
		} while (gameLengthInput != 1 && gameLengthInput != 2);

		GUI.clearConsole(20);

		// finds the order that players will take their turn
		turnLauncher.findPlayerOrder();

		// Game Loop
		GameLauncher.gameLoop();

	}

	/**
	 * runs menu to setup new players / modify existing
	 */
	public static void createPlayers() {
		ArrayList<Player> players;
		boolean start = false;
		int numOfPlayers;

		while (!start) {

			players = turnLauncher.getPlayers();
			numOfPlayers = players.size();

			if (numOfPlayers > 0) {
				System.out.print(MENU_HEADER);
				turnLauncher.displayPlayers();
			}

			System.out.print(MENU_HEADER);

			if (numOfPlayers < MAX_PLAYERS) {
				System.out.printf("1. Add New Player\n");
			}
			if (numOfPlayers >= 1 && players.size() < MAX_PLAYERS) {
				System.out.printf("2. Modify Existing Player\n");
			}
			if (numOfPlayers >= MIN_PLAYERS && players.size() < MAX_PLAYERS) {
				System.out.printf("3. Begin Game\n");
			}
			if (numOfPlayers == MAX_PLAYERS) {
				System.out.printf("(Max number of players reached)\n1. Begin Game\n2. Modify Existing Player\n");
			}

			switch (UserInput.getUserInputInt()) {
			case 1:
				if (numOfPlayers < MAX_PLAYERS) {
					turnLauncher.addPlayer();
				} else {
					start = true;
				}
				break;
			case 2:
				if (numOfPlayers >= 1) {
					turnLauncher.modifyPlayer();
					// break inside if, so fall through to default !if
					break;
				}
			case 3:
				if (numOfPlayers >= MIN_PLAYERS && numOfPlayers < MAX_PLAYERS) {
					start = true;
					break;
				}
			default:
				System.out.println("Invalid Menu Option, please try again");
			}

		}

		GUI.clearConsole(20);
	}

	/**
	 * main game loop
	 */
	public static void gameLoop() {

		while (!gameOver) {

			mainHeadder();

			ArtemisCalendar.displayDate();

			System.out.printf("\nIt's " + turnLauncher.getActivePlayer().getName() + "'s turn.\n");

			turnLauncher.moveMethod(board);
			turnLauncher.checkElement(board);
			turnLauncher.playerTurnMenu(board);
			
		}

	}

	/**
	 * Runs correct game-over sequence depending on win or loss
	 * 
	 * @param board
	 */
	public static void gameOverSequence() {

		if (board.allSystemComplete()) {
			GUI.displayGameWonMessage();
		} else {
			GUI.displayGameLossMessage(board);
		}

		// TODO:bug, message is being displayed even when there is no game history to
		// show
		// on completion, show a history of game moves
		turnLauncher.gameHistoryStorage.displayMoveHistory();

		if (turnLauncher.players.size() > 0) {
			turnLauncher.endingPlayerScore(board);
		}

	}

	/**
	 * sets the game over state
	 */
	public static void declareGameOver() {
		gameOver = true;
	}

	/**
	 * Displays the game loss message
	 */
	public void displayGameLossMessage(Board board) {
		// TODO: add actual ending message
		// TODO: show mission progress
		System.out.printf("On %s The Artemis Project has failed at %.1f%s completion.\n",
				ArtemisCalendar.getCalendar().getTime(), GameStatistics.missionProgress(board), "%");

	}

	/**
	 * Displays the game won message
	 */
	public void displayGameWonMessage() {
		// TODO: add actual ending message
		// TODO: show time under / over estimated completion date
		System.out.printf("On %s The Artemis Project has succesfully launched!\n",
				ArtemisCalendar.getCalendar().getTime());

	}

	/**
	 * Runs correct game-over sequence depending on win or loss
	 * 
	 * @param board
	 */
	public void gameOverSequence(Board board) {

		if (board.allSystemComplete()) {
			displayGameWonMessage();
		} else {
			displayGameLossMessage(board);
		}

		postGameMenu(board);

	}

	public void postGameMenu(Board board) {

		int userInput;
		do {
			GUI.clearConsole(1);
			System.out.printf("=====| MENU |===== \n1. View score board\n2. View full move history\n3. Exit game");
			GUI.clearConsole(1);
			userInput = UserInput.getUserInputInt();
			switch (userInput) {
			case 1:
				if (TurnLauncher.getPlayers().size() > 0) {
					GameStatistics.endingPlayerScore(board);
				}
				break;
			case 2:
				TurnLauncher.getGameHistoryStorage().displayMoveHistory();
				break;
			case 3:
				GUI.clearConsole(2);
				System.out.println("====| Thank you for playing Artemis Lite |====");
				break;
			default:
				System.out.println("Invalid input, try again.");
			}
		} while (userInput != 3);

	}

	/**
	 * main UI display headder
	 */
	public static void mainHeadder() {

		Player activePlayer = turnLauncher.getActivePlayer();

		System.out.printf("=====| PLAYER: %s |=====| RESOURCES: %d |=====| LOCATION: %s |=====\n",
				activePlayer.getName(), activePlayer.getBalanceOfResources(),
				board.getSquares().get(activePlayer.getCurrentPosition()).getSquareName());

	}

	/**
	 * @return the menuHeader
	 */
	public static String getMenuHeader() {
		return MENU_HEADER;
	}

}

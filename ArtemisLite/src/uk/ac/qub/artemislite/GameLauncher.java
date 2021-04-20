/**
 * 
 */
package uk.ac.qub.artemislite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author Jordan Davis
 *
 */
public class GameLauncher {

	protected static TurnLauncher turnLauncher = new TurnLauncher();
	protected static Board board = new Board();
	private static UserInterface msgPrinter = new UserInterface();
	private static BufferedInterrupter buffInter = new BufferedInterrupter();

	private final static int MIN_PLAYERS = 2;
	private final static int MAX_PLAYERS = 4;
	private final static String MENU_HEADER = "\n=====| MENU |=====\n";
	private final static int RESOURCE_VALUE_SHORT_GAME = 300;
	private final static int RESOURCE_VALUE_LONG_GAME = 200;

	// Sets game-over, main game loop
	private static boolean gameOver = false;

	/**
	 * displays game messages to screen with a delay between each line
	 */
	public static void displayMessage() {

		Thread msgThread = new Thread(msgPrinter);
		Thread inputThread = new Thread(buffInter);

		System.out.println("== Hit enter to skip ==\n");

		msgThread.start();
		inputThread.start();
		// interrupt introThread if still running on input
		while (msgThread.isAlive()) {
			if (!inputThread.isAlive()) {
				msgThread.interrupt();
			}

		}
		// Stops the input thread after intro message finished
		if (inputThread.isAlive()) {
			inputThread.interrupt();
		}

	}

	/**
	 * Game starting Menu
	 */
	public static void startMenu() {

		boolean gameBegin = false;

		msgPrinter.loadIntroMessage();

		do {

			System.out.println(MENU_HEADER + "Hint: you can select a menu option by entering a number: (e.g. 1)"
					+ "\n1.Start Game" + "\n2.Show Game Rules" + "\n3.Quit Game");

			switch (UserInput.getUserInputInt()) {
			case 1:
				gameBegin = true;
				break;
			case 2:
				showGameRules();
				break;
			case 3:
				System.out.println("Are you sure you want to quit the game?");
				if (UserInterface.yesNoMenu() == 1) {
					gameOver = true;
					gameBegin = true;
				}
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
			}

		} while (!gameBegin);

		UserInterface.clearConsole();

		if (!gameOver) {
			startGame();
		}

	}

	/**
	 * displays the game rules to screen
	 */
	public static void showGameRules() {
		File file = new File("GameRules.txt");
		String line;

		UserInterface.clearConsole();

		try (FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);) {

			line = bufferedReader.readLine();

			while (line != null) {
				System.out.println(line);
				line = bufferedReader.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("Game rules have not been found");
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("There was a problem opening game rules");
			//e.printStackTrace();
		} catch (Exception e) {
			System.out.println("An error has occured, please restart the app");
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
			System.out.printf(
					"Hint: you can view the game rules at any point by entering the word 'rules'\n%s1. Short Game (%d staff-hours per lap of board)\n2. Long Game (%d staff-hours per lap of board)\n",
					MENU_HEADER, RESOURCE_VALUE_SHORT_GAME, RESOURCE_VALUE_LONG_GAME);
			gameLengthInput = UserInput.getUserInputInt();

			switch (gameLengthInput) {
			case 1:
				setGameResources(RESOURCE_VALUE_SHORT_GAME);
				break;
			case 2:
				setGameResources(RESOURCE_VALUE_LONG_GAME);
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
			}
		} while (gameLengthInput != 1 && gameLengthInput != 2);

		UserInterface.clearConsole();

		// finds the order that players will take their turn
		turnLauncher.findPlayerOrder();

		gameLoop();

	}

	/**
	 * sets the starting resources & resources per lap of board & player
	 * 
	 * @param resourceValue to set
	 */
	public static void setGameResources(int resourceValue) {
		// updates all resourceElements
		board.getResourceElement().setResourceToAllocate(resourceValue);

		// updates all player starting resource
		for (Player player : turnLauncher.getPlayers()) {
			player.setBalanceOfResources(resourceValue*8);
		}
	}

	/**
	 * runs menu to setup new players / modify existing
	 */
	public static void createPlayers() {
		List<Player> players;
		boolean start = false;
		int numOfPlayers;

		// Force the first two players to be added
		System.out.println("Lets add the first player");
		turnLauncher.addPlayer();
		System.out.println("Now its time for player two");
		turnLauncher.addPlayer();

		while (!start) {

			players = turnLauncher.getPlayers();
			numOfPlayers = players.size();

			if (numOfPlayers > 0) {
				System.out.print("\n=====| REGISTERED COMPANIES |=====\n");
				turnLauncher.displayPlayers();
			}

			System.out.print(MENU_HEADER);

			if (numOfPlayers < MAX_PLAYERS) {
				System.out.printf("1. Add a New Company\n");
			}
			if (numOfPlayers >= 1 && players.size() < MAX_PLAYERS) {
				System.out.printf("2. Modify an Existing Company\n");
			}
			if (numOfPlayers >= MIN_PLAYERS && players.size() < MAX_PLAYERS) {
				System.out.printf("3. Begin the Game\n");
			}
			if (numOfPlayers == MAX_PLAYERS) {
				System.out.printf("(Max number of players reached)\n1. Begin Game\n2. Modify Existing Company\n");
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

		UserInterface.clearConsole();
	}

	/**
	 * main game loop
	 */
	public static void gameLoop() {

		while (!gameOver) {

			mainHeadder();

			System.out.println(ArtemisCalendar.getDate());

			System.out.printf("\nIt's time for " + turnLauncher.getActivePlayer().getName() + " to take a turn.\n");

			turnLauncher.moveMethod(board);
			turnLauncher.checkElement(board);
			if(!gameOver) {
				turnLauncher.playerTurnMenu(board);
			}

		}

	}

	/**
	 * sets the game over state
	 */
	public static void declareGameOver() {
		gameOver = true;
		turnLauncher.endTurn();
	}

	/**
	 * returns state of the gamge
	 * 
	 * @return
	 */
	public static boolean getGameOver() {
		return gameOver;
	}

	/**
	 * Runs correct game-over sequence depending on win or loss
	 * 
	 * @param board
	 */
	public static void gameOverSequence() {

		if (board.allSystemComplete()) {
			msgPrinter.loadWinMessage();

		} else {
			msgPrinter.loadLossMessage();
		}

		if (turnLauncher.getTurnNumber() != 0) {
			postGameMenu();
		}

	}
	
	/**
	 * Method to display and carry out options after a game has ended.
	 */
	public static void postGameMenu() {

		int userInput;
		do {
			System.out.printf("%s \n1. View score board\n2. View full move history\n3. Exit game", MENU_HEADER);
			UserInterface.clearConsole(1);
			userInput = UserInput.getUserInputInt();
			switch (userInput) {
			case 1:
				UserInterface.clearConsole();
				GameStatistics.endingPlayerScore(board);
				break;
			case 2:
				UserInterface.clearConsole();
				GameHistoryStorage.displayMoveHistory();
				break;
			case 3:
				UserInterface.clearConsole(2);
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

		System.out.printf("=====| PLAYER: %s |=====| STAFF-HOURS: %d |=====| LOCATION: %s |=====\n\n",
				activePlayer.getName(), activePlayer.getBalanceOfResources(),
				board.getElements().get(activePlayer.getCurrentPosition()).getElementName());

	}

	/**
	 * @return the menuHeader
	 */
	public static String getMenuHeader() {
		return MENU_HEADER;
	}
	
	/**
	 * Sets the active player to own all elements
	 * 
	 * @param board
	 */
	public static void giveAll() {
		
		for (StandardElement stdElement : board.getStdElements()) {
			stdElement.setOwnedBy(turnLauncher.getActivePlayer());
		}
		
	}

}

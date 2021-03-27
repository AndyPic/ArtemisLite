/**
 * 
 */
package uk.ac.qub.artemislite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		UserInterface introMessage = new UserInterface();
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
		if (inputThread.isAlive()) {
			inputThread.interrupt();
		}

		UserInterface.clearConsole(1);

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
				showGameRules();
				break;
			case 3:
				System.out.println("Are you sure you want to quit the game?");
				if (UserInterface.yesNoMenu() == 1) {
					declareGameOver();
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

	public static void showGameRules() {
		File file = new File("GameRules.txt");
		String line;

		UserInterface.clearConsole();

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			line = bufferedReader.readLine();

			while (line != null) {
				System.out.println(line);
				line = bufferedReader.readLine();
			}

			bufferedReader.close();
			fileReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("Game rules have not been found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("There was a problem opening game rules");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("A error has occured, please restart the app");
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
				// TODO: thoughts on updating this to remove 'game length details'. Instead
				// rename the options to be something like 'Short game (start with 200
				// resources)' & 'Long game (start with 100 resources)'. I think the games feels
				// like it has too many menu options
				System.out.println("Some details about the different modes...");
				UserInterface.clearConsole();
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
	 * runs menu to setup new players / modify existing
	 */
	public static void createPlayers() {
		ArrayList<Player> players;
		boolean start = false;
		int numOfPlayers;

		// Force the first two players to be added
		System.out.println("Lets add the first player");
		turnLauncher.addPlayer();
		System.out.println("Now its time for player two");
		turnLauncher.addPlayer();

		while (!start) {

			players = TurnLauncher.getPlayers();
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

			ArtemisCalendar.displayDate();

			System.out.printf("\nIt's time for " + turnLauncher.getActivePlayer().getName() + " to take a turn.\n");

			turnLauncher.moveMethod(board);
			turnLauncher.checkElement(board);
			turnLauncher.playerTurnMenu(board);

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
	public static void displayGameLossMessage(Board board) {
		// TODO: add actual ending message
		// TODO: show mission progress
		System.out.printf("On %s The Artemis Project has failed at %.1f%s completion.\n",
				ArtemisCalendar.getCalendar().getTime(), GameStatistics.missionProgress(board), "%");

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
	 * Runs correct game-over sequence depending on win or loss
	 * 
	 * @param board
	 */
	public static void gameOverSequence() {

		if (board.allSystemComplete()) {
			displayGameWonMessage();
		} else {
			displayGameLossMessage(board);
		}

		postGameMenu();

	}

	public static void postGameMenu() {

		int userInput;
		do {
			System.out.printf("%s \n1. View score board\n2. View full move history\n3. Exit game", MENU_HEADER);
			UserInterface.clearConsole(1);
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

		System.out.printf("=====| PLAYER: %s |=====| STAFF-HOURS: %d |=====| LOCATION: %s |=====\n",
				activePlayer.getName(), activePlayer.getBalanceOfResources(),
				board.getElements().get(activePlayer.getCurrentPosition()).getElementName());

	}

	/**
	 * @return the menuHeader
	 */
	public static String getMenuHeader() {
		return MENU_HEADER;
	}

}

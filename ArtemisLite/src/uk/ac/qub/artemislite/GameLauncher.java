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

	// Sets game-over, main game loop
	private static boolean gameOver = false;

	/**
	 * displays game intro message to screen
	 */
	public static void introMessage() {
		// Intro message
		//TODO: can this be made static? 
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
		boolean validOption = false;

		System.out.println("Welcome to Artemis Lite");

		do {

			System.out.println("\n=====| MENU |=====\nHint: you can select a menu option by entering a number: (e.g. 1)"
					+ "\n1.Start Game" + "\n2.Show Game Rules" + "\n3.Quit Game");

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
					declareGameOver();
					validOption = true;
				}
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
			}

		} while (!validOption);
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

		boolean start = false;

		if (!gameOver) {

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
			GUI.clearConsole(20);

			int gameLengthInput;
			do {
				System.out
						.println("\n=====| MENU |=====\n1. Short Game" + "\n2. Long Game" + "\n3. Game length details");
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

		}
		
		//Game Loop
		GameLauncher.gameLoop();
		
	}

	/**
	 * main game loop
	 */
	public static void gameLoop() {

		while (!gameOver) {

			// TODO: method calls need cleaned up + this is duplicated code JD
			System.out.printf("=====| PLAYER: %s |=====| RESOURCES: %d |=====| LOCATION: %s |=====\n",
					turnLauncher.getActivePlayer().getName(), turnLauncher.getActivePlayer().getBalanceOfResources(),
					board.getSquares().get(turnLauncher.getActivePlayer().getCurrentPosition()).getSquareName());

			System.out.printf("\nDate: %s, %s.\n", ArtemisCalendar.getMonthName(ArtemisCalendar.getCalendar().get(2)),
					ArtemisCalendar.getCalendar().get(1));

			System.out.printf("\nIt's " + turnLauncher.getActivePlayer().getName() + "'s turn.\n");

			turnLauncher.moveMethod(board);
			turnLauncher.checkElement(board);

			TurnLauncher.setTurnOver(false);
			while (!TurnLauncher.isEndTurn()) {

				// Check if player owns any squares
				boolean owner = Player.isOwner(board, turnLauncher.getActivePlayer());

				// TODO: method calls need cleaned up + this is duplicated code JD
				System.out.printf("=====| PLAYER: %s |=====| RESOURCES: %d |=====| LOCATION: %s |=====\n",
						turnLauncher.getActivePlayer().getName(),
						turnLauncher.getActivePlayer().getBalanceOfResources(),
						board.getSquares().get(turnLauncher.getActivePlayer().getCurrentPosition()).getSquareName());

				System.out.println("\nPlease select one of the below options");

				// TODO also check if they have enough money to develop
				if (owner) {
					System.out.printf(
							"\n=====| MENU |===== \n1. View all element ownership \n2. View my elements \n3. Get current square details \n4. Increase Development level \n5. End turn \n6. End game\n");
				} else {
					System.out.printf(
							"\n=====| MENU |===== \n1. View all element ownership \n2. Get current square details \n3. End turn \n4. End game\n");

				}

				// surround with try / catch to catch BankruptcyException when modifying player
				// resources would result in a negative balance
				try {

					// TODO clean up a bit, code duplication, own method?
					switch (UserInput.getUserInputInt()) {

					case 1:
						board.viewElementOwnership();
						break;
					case 2:

						if (owner) {
							board.viewMyElements(turnLauncher.getActivePlayer());
						} else {
							turnLauncher.getActivePlayer().getCurrentPositionDetails(board);
						}

						break;
					case 3:
						if (owner) {
							turnLauncher.getActivePlayer().getCurrentPositionDetails(board);
						} else {
							turnLauncher.endTurn(board);
						}
						break;
					case 4:
						if (owner) {
							// Increase development level
							System.out.println("Increase development level - Not yet implemented");
						} else {
							turnLauncher.endGame();
							turnLauncher.endTurn(board);
						}
						break;
					case 5:
						if (owner) {
							turnLauncher.endTurn(board);
							GUI.clearConsole(20);
							break;
						}

					case 6:
						if (owner) {
							turnLauncher.endGame();
							turnLauncher.endTurn(board);
							break;
						}

					default:
						System.out.println("Invalid option - try again");

					}

				} catch (BankruptException bankruptExc) {
					// declare the game over at a BankruptException
					bankruptExc.getLocalizedMessage();
					declareGameOver();
				}
			}

			GUI.clearConsole(2);

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

		// on completion, show a history of game moves
		turnLauncher.gameHistoryStorage.displayMoveHistory();

		if (turnLauncher.players.size() > 0) {
			turnLauncher.endingPlayerScore(board);
		}

	}
	
	public static void declareGameOver() {
		gameOver = true;
	}

}

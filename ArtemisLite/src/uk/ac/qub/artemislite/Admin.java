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
	 * Sets game over (main game loop)
	 */
	public static boolean GAME_OVER = false;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		TurnLauncher turnLauncher = new TurnLauncher();

		// create Board
		Board board = new Board();

		// Intro message
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

		// Runs game start Menu
		GameLauncher.startMenu();

		if (!GAME_OVER) {
			GameLauncher.startGame(turnLauncher);
		}

		while (!GAME_OVER) {

			System.out.println("It is " + turnLauncher.getActivePlayer().getName() + "'s turn.");

			int activePlayerIndex = turnLauncher.players.indexOf(turnLauncher.getActivePlayer());
			Square currentPosition = board.getSquares().get(turnLauncher.getActivePlayer().getCurrentPosition());
			Boolean endTurn = false;

			turnLauncher.moveMethod(board);
			turnLauncher.checkElement(board);

			currentPosition = board.getSquares().get(turnLauncher.getActivePlayer().getCurrentPosition());

			while (!endTurn) {

				boolean owner = Player.isOwner(board, turnLauncher.getActivePlayer());

				// Check if player owns any squares to develop
				// TODO also check if they have enough money to develop
				if (owner) {
					System.out.printf(
							"\n[%s]\nEnter: \n1. blank \n2. Get square details \n3. Increase Development level \n4. End turn \n5. End game\n",
							turnLauncher.getActivePlayer().getName());
				} else {
					System.out.printf("\n[%s]\nEnter: \n1. blank \n2. Get square details \n3. End turn \n4. End game\n",
							turnLauncher.getActivePlayer().getName());

				}

				// TODO: update blank
				// TODO clean up a bit, code duplication, own method?
				switch (UserInput.getUserInputInt()) {

				case 1:

					break;
				case 2:

					if (currentPosition instanceof StandardSquare) {
						StandardSquare ssq = (StandardSquare) currentPosition;
						System.out.println(ssq.toString());
					} else if (currentPosition instanceof ResourceSquare) {
						ResourceSquare rsq = (ResourceSquare) currentPosition;
						System.out.println(rsq.toString());
					} else {
						System.out.println(currentPosition.toString());
					}

					break;
				case 3:
					if (owner) {
						// Increase development level
						System.out.println("Not yet implemented");
					} else {
						if (activePlayerIndex != turnLauncher.players.size() - 1) {
							turnLauncher.setActivePlayer(turnLauncher.players.get(activePlayerIndex + 1));
						} else {
							turnLauncher.setActivePlayer(turnLauncher.players.get(0));
							turnLauncher.roundEnd();
						}
						endTurn = true;
					}
					break;
				case 4:
					if (owner) {

						if (activePlayerIndex != turnLauncher.players.size() - 1) {
							turnLauncher.setActivePlayer(turnLauncher.players.get(activePlayerIndex + 1));
						} else {
							turnLauncher.setActivePlayer(turnLauncher.players.get(0));
							turnLauncher.roundEnd();
						}
						endTurn = true;
					} else {
						System.out.println("Are you sure you want to declare bankruptcy and end the game?");
						if (GUI.yesNoMenu() == 1) {
							GAME_OVER = true;
							endTurn = true;
							turnLauncher.getActivePlayer().setBalanceOfResources(-1);
						}
					}
					break;
				case 5:
					if (owner) {
						System.out.println("Are you sure you want to declare bankruptcy and end the game?");
						if (GUI.yesNoMenu() == 1) {
							GAME_OVER = true;
							endTurn = true;
							turnLauncher.getActivePlayer().setBalanceOfResources(-1);
						}
						break;
					}

				default:
					System.out.println("Invalid option - try again");

				}
			}

			GUI.clearConsole(2);

		}

		turnLauncher.gameOverSequence(board);

		// Close scanner
		UserInput.closeScanner();

	}// END MAIN

}

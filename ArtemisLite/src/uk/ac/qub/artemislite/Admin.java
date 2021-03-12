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

		// Runs game start Menu
		GameLauncher.startMenu();
		GameLauncher.startGame(turnLauncher);

		// sets first player
		Player activePlayer = turnLauncher.players.get(0);

		while (!GAME_OVER) {

			// Clear console
			GUI.clearConsole(10);

			System.out.println("It is " + activePlayer.getName() + "'s turn.");
			System.out.println("Enter: \n1. End turn\n2. Start turn\n3. End game");

			int activePlayerIndex = turnLauncher.players.indexOf(activePlayer);

			// check user input
			switch (UserInput.getUserInputInt()) {

			// to next player
			case 1:

				if (activePlayerIndex != turnLauncher.players.size() - 1) {
					activePlayer = turnLauncher.players.get(activePlayerIndex + 1);
				} else {
					activePlayer = turnLauncher.players.get(0);
					turnLauncher.roundEnd();
				}
				break;

			case 2:

				Square currentPosition = board.getSquares().get(activePlayer.getCurrentPosition());
				Boolean endTurn = false;
				Boolean hasMoved = false;

				while (!endTurn) {
					
					/*
					 * AP - Changed this a little as buy / auction are in the
					 * "turnLauncher.moveMethod" together. Also threw it in a while loop so it'll
					 * keep going until they end their turn / game
					 */
					System.out.println(
							"Enter: \n1. Move \n2. Get square details \n3. Increase Development level \n4. End turn \n5. End game");
					switch (UserInput.getUserInputInt()) {

					case 1:
						// AP - Temporary hasMoved solution
						if (!hasMoved) {
							turnLauncher.moveMethod(activePlayer, board, turnLauncher);

							currentPosition = board.getSquares().get(activePlayer.getCurrentPosition());

							hasMoved = true;
						} else {
							System.out.println("You've already moved this turn.");
						}
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
						// Increase development level
						System.out.println("Not yet implemented");
						break;
					case 4:

						// AP - just c+p code from above, probably needs a method made
						if (activePlayerIndex != turnLauncher.players.size() - 1) {
							activePlayer = turnLauncher.players.get(activePlayerIndex + 1);
						} else {
							activePlayer = turnLauncher.players.get(0);
						}

						endTurn = true;
						break;
					case 5:
						GAME_OVER = true;
						endTurn = true;
						break;
					default:
						System.out.println("Invalid option - try again");

					}
				}
				break;

			// End the game
			case 3:
				GAME_OVER = true;
				break;
			default:
				System.out.println("Invalid option - try again");
			}

			// Clear console
			GUI.clearConsole(2);

		}

		turnLauncher.gameOverSequence(board);

		// Close scanner
		UserInput.closeScanner();

	}// END MAIN

}

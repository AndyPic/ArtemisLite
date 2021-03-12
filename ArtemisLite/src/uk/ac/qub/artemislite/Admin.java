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
		BufferedInterrupter buffInter = new BufferedInterrupter(); // League pun intended
		Thread introThread = new Thread(introMessage);
		Thread inputThread = new Thread(buffInter);

		System.out.println("== Hit enter to skip intro ==");

		introThread.start();

		inputThread.start();

		// interrupt newThread if still running on input
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
		GameLauncher.startGame(turnLauncher);


		while (!GAME_OVER) {
			
			// Clear console
			GUI.clearConsole(10);

			System.out.println("It is " + turnLauncher.getActivePlayer().getName() + "'s turn.");

			
			int activePlayerIndex = turnLauncher.players.indexOf(turnLauncher.getActivePlayer());
			Square currentPosition = board.getSquares().get(turnLauncher.getActivePlayer().getCurrentPosition());
			Boolean endTurn = false;
	
			
			turnLauncher.moveMethod(board);

			currentPosition = board.getSquares().get(turnLauncher.getActivePlayer().getCurrentPosition());
			

			while (!endTurn) {

				/*
				 * AP - Changed this a little as buy / auction are in the
				 * "turnLauncher.moveMethod" together. Also threw it in a while loop so it'll
				 * keep going until they end their turn / game
				 * TODO: update blank
				 */
				System.out.println(
						"Enter: \n1. blank \n2. Get square details \n3. Increase Development level \n4. End turn \n5. End game");
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
					// Increase development level
					System.out.println("Not yet implemented");
					break;
				case 4:

					// AP - just c+p code from above, probably needs a method made
					if (activePlayerIndex != turnLauncher.players.size() - 1) {
						turnLauncher.setActivePlayer(turnLauncher.players.get(activePlayerIndex + 1));
					} else {
						turnLauncher.setActivePlayer(turnLauncher.players.get(0));
						turnLauncher.roundEnd();
					}

					endTurn = true;
					break;
				case 5:
					GAME_OVER = true;
					endTurn = true;
					turnLauncher.getActivePlayer().setBalanceOfResources(-1);
					break;
				default:
					System.out.println("Invalid option - try again");

				}
			}

			// Clear console
			GUI.clearConsole(2);

		}

		turnLauncher.gameOverSequence(board);

		// Close scanner
		UserInput.closeScanner();

	}// END MAIN

}

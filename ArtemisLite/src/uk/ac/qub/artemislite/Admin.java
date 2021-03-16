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

		if (!GameLauncher.isGameOver()) {
			GameLauncher.startGame(turnLauncher);
		}

		while (!GameLauncher.isGameOver()) {
			
			System.out.printf("It's "+turnLauncher.getActivePlayer().getName()+"'s turn.\n");
			
			System.out.printf("Date: April 2020\n");
			
			//TODO: method calls need cleaned up + this is duplicated code JD
			System.out.printf("=====| PLAYER: %s |=====| RESOURCES: £%d |=====| LOCATION: %s |=====\n\n",turnLauncher.getActivePlayer().getName(),turnLauncher.getActivePlayer().getBalanceOfResources(), board.getSquares().get(turnLauncher.getActivePlayer().getCurrentPosition()).getSquareName());

			turnLauncher.moveMethod(board);
			turnLauncher.checkElement(board);

			TurnLauncher.setTurnOver(false);
			while (!TurnLauncher.isEndTurn()) {

				// Check if player owns any squares
				boolean owner = Player.isOwner(board, turnLauncher.getActivePlayer());
				
				//TODO: need to add dynamic date from ArtemisCalendar here.

				// TODO also check if they have enough money to develop
				if (owner) {
					System.out.printf(
							"\n[%s]\n=====| MENU |===== \n1. View all element ownership \n2. View my elements \n3. Get current square details \n4. Increase Development level \n5. End turn \n6. End game\n",
							turnLauncher.getActivePlayer().getName());
				} else {
					System.out.printf(
							"\n[%s]\n=====| MENU |===== \n1. View all element ownership \n2. Get current square details \n3. End turn \n4. End game\n",
							turnLauncher.getActivePlayer().getName());

				}

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
						GameLauncher.endGame();
						turnLauncher.endTurn(board);
					}
					break;
				case 5:
					if (owner) {
						turnLauncher.endTurn(board);
						break;
					}

				case 6:
					if (owner) {
						GameLauncher.endGame();
						turnLauncher.endTurn(board);
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

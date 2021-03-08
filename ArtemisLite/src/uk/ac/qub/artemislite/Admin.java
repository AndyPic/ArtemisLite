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
		
		//Runs game start Menu
		GameLauncher.startMenu();
		GameLauncher.startGame(turnLauncher);

		// sets first player
		Player activePlayer = turnLauncher.players.get(0);

		// create Board
		Board board = new Board();

		while (!GAME_OVER) {
			
			System.out.println("It is " + activePlayer.getName() + "'s turn.");
			System.out.println("Enter: \n1. End turn\n2. Roll Dice\n3. End game");

			/**
			 * Reads user selection, if user input != int, then catches exception sets input
			 * to default and continues to switch
			 */
//			try {
//				userInput = scanner.nextInt();
//				scanner.nextLine();
//			} catch (InputMismatchException e) {
//				userInput = 0;
//				scanner.nextLine();
//			}

				
			// Clear console
			clearConsole(10);

			// check user input
			switch (UserInput.getUserInputInt()) {
			case 1:
				int activePlayerIndex = turnLauncher.players.indexOf(activePlayer);
				if (activePlayerIndex!=turnLauncher.players.size()-1) {
					activePlayer = turnLauncher.players.get(activePlayerIndex+1);
				}  else {
					activePlayer = turnLauncher.players.get(0);
				}
				break;

			case 2:
				// added only to check function of Board. needs implemented properly
				int currentPos = activePlayer.getCurrentPosition();
				Square currentSquare = board.getSquares().get(currentPos);

				System.out.println("You are currently on " + currentSquare.getSquareName());

				int roll = turnLauncher.rollDice();

				System.out.println("You have rolled " + roll);
				int newPos = currentPos + roll;
				if (newPos > 11) {
					newPos -= 12;
				}
				activePlayer.setCurrentPosition(newPos);

				Square newSquare = board.getSquares().get(newPos);

				System.out.println("You are now on " + newSquare.getSquareName());
				if (newSquare instanceof StandardSquare) {
					StandardSquare standardSquare = (StandardSquare) newSquare;
					System.out.println("The cost of rent is " + standardSquare.getRentCost());
					System.out.println("Increasing development level....");
					standardSquare.increaseDev();
					System.out.println("The cost of rent after increase is " + standardSquare.getRentCost());
				}
				break;

			case 3:
				GAME_OVER = true;
				break;
			default:
				System.out.println("Invalid option - try again");
			}

			// Clear console
			clearConsole(2);

		}

		System.out.println("Game Over");

	}// END MAIN

	/**
	 * 'Clears the console' by printing {@link numberOfLines} blank lines in
	 * console.
	 * 
	 * @param numberOfLines
	 */
	public static void clearConsole(int numberOfLines) {

		for (int loop = 0; loop < numberOfLines; loop++) {
			System.out.println();
		}

	}

}

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
			
			// Clear console
			GUI.clearConsole(10);
			
			System.out.println("It is " + activePlayer.getName() + "'s turn.");
			System.out.println("Enter: \n1. End turn\n2. Start turn\n3. End game");

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
				
				System.out.println("Press enter to roll the dice");
				
				UserInput.getUserInputString();

				String roll = turnLauncher.rollDice();
				
				System.out.println("You "+roll);
				
				int newPos = currentPos + turnLauncher.getRollValue(roll);
				if (newPos > 11) {
					newPos -= 12;
				}
				activePlayer.setCurrentPosition(newPos);

				Square newSquare = board.getSquares().get(newPos);

				System.out.println("You are now on " + newSquare.getSquareName());
				/*
				if (newSquare instanceof StandardSquare) {
					StandardSquare standardSquare = (StandardSquare) newSquare;
					System.out.println("The cost of rent is " + standardSquare.getRentCost());
					System.out.println("Increasing development level....");
					standardSquare.increaseDev();
					System.out.println("The cost of rent after increase is " + standardSquare.getRentCost());
				}*/
				
				// not sure if this is the best way of doing this 
				System.out.println("Enter: \n1. Get square details \n2. Buy square \n3. Auction square \n4. Increase Development level \n5. End turn \n6. End game");
				switch(UserInput.getUserInputInt()) {
				case 1:
						if (newSquare instanceof StandardSquare) {
							StandardSquare ssq = (StandardSquare) newSquare;
							ssq.displayDetails();
						} else if (newSquare instanceof ResourceSquare) {
							ResourceSquare rsq = (ResourceSquare) newSquare; 	
							rsq.toString();
						} else {
							newSquare.toString();
						}
						
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					GAME_OVER = true;
					break;
				default:
					System.out.println("Invalid option - try again");
				
				}
				break;

			case 3:
				GAME_OVER = true;
				break;
			default:
				System.out.println("Invalid option - try again");
			}

			// Clear console
			GUI.clearConsole(2);

		}

		System.out.println("Game Over");

	}// END MAIN

}

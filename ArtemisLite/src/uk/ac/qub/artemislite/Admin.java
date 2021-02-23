/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.InputMismatchException;
import java.util.Scanner;

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

		Scanner scanner = new Scanner(System.in);

		// create players
		Player p1 = new Player("Jordan");
		Player p2 = new Player("David");
		Player p3 = new Player("Andrew");
		Player p4 = new Player("Joseph");

		// sets first player
		Player activePlayer = p1;

		// stores user input
		int userInput;

		// create Board
		Board board = new Board();

		// create dice
		Dice dice = new Dice();

		do {

			System.out.println("It is " + activePlayer.getName() + "'s turn.");
			System.out.println("Enter: \n1. End turn\n2. Roll Dice\n3. End game");

			/**
			 * Reads user selection, if user input != int, then catches exception sets input
			 * to default and continues to switch
			 */
			try {
				userInput = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				userInput = 0;
				scanner.nextLine();
			}

			// Clear console
			clearConsole(10);

			// check user input
			switch (userInput) {
			case 1:
				if (activePlayer.equals(p1)) {
					activePlayer = p2;
				} else if (activePlayer.equals(p2)) {
					activePlayer = p3;
				} else if (activePlayer.equals(p3)) {
					activePlayer = p4;
				} else {
					activePlayer = p1;
				}
				break;

			case 2:
				// added only to check function of Board. needs implemented properly
				int currentPos = activePlayer.getCurrentPosition();
				Square currentSquare = board.getSquares().get(currentPos);

				System.out.println("You are currently on " + currentSquare.getSquareName());

				int roll1 = dice.rollDice();
				int roll2 = dice.rollDice();

				System.out.println("You have rolled " + roll1 + " and " + roll2);
				int newPos = currentPos + roll1 + roll2;
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

		} while (!GAME_OVER);

		System.out.println("Game Over");
		scanner.close();

	}// END MAIN
	
	/**
	 * 'Clears the console' by printing {@link numberOfLines} blank lines in console.
	 * @param numberOfLines
	 */
	public static void clearConsole(int numberOfLines) {

		for (int loop = 0; loop < numberOfLines; loop++) {
			System.out.println(); 
		}

	}

}

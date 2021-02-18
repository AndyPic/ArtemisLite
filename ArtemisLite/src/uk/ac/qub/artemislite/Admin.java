/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.Scanner;

/**
 * @author Jordan Davis
 *
 */
public class Admin {

	/**
	 * Sets game over (main game loop)
	 */
	public static boolean GAME_OVER = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

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

		do {

			System.out.println();
			System.out.println("It is " + activePlayer.getName() + " turn.");
			System.out.println("Enter: \n1. End turn\n2. End game");

			// reads user selection
			userInput = scanner.nextInt();

			// clears scanner
			scanner.nextLine();

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
				GAME_OVER = true;
				break;
			default:
				System.out.println("Invalid option - try again");
			}
			
		} while (!GAME_OVER);

		System.out.println("Game Over");
		scanner.close();
	}

}

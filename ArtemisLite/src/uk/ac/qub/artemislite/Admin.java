/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;
import java.util.HashMap;

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
		int activePlayerIndex = 0;
		String activePlayerName = turnLauncher.players.get(activePlayerIndex).getName();

		while (!GAME_OVER) {
			
			// Clear console
			GUI.clearConsole(10);
			
			System.out.println("It is " + activePlayerName + "'s turn.");
			System.out.println("Enter: \n1. End turn\n2. Start turn\n3. End game");

			// check user input
			switch (UserInput.getUserInputInt()) {

			// to next player
			case 1:
				if (activePlayerIndex != turnLauncher.players.size() - 1) {
					activePlayerIndex += 1;
					activePlayerName = turnLauncher.players.get(activePlayerIndex).getName();
				} else {
					activePlayerIndex = 0;
					activePlayerName = turnLauncher.players.get(activePlayerIndex).getName();
				}
				break;

			// Roll and move the player
			case 2:
				System.out.println("You are currently on "
						+ board.getSquares().get(turnLauncher.players.get(activePlayerIndex).getCurrentPosition()));

				String roll = turnLauncher.rollDice();
				System.out.println("You " + roll);

				int newPos = turnLauncher.players.get(activePlayerIndex).getCurrentPosition()
						+ turnLauncher.getRollValue(roll);

				if (newPos > 11) {
					newPos -= 12;
				}

				// Update player position
				turnLauncher.players.get(activePlayerIndex).setCurrentPosition(newPos);

				System.out.println("You are now on: " + board.getSquares()
						.get(turnLauncher.players.get(activePlayerIndex).getCurrentPosition()).toString());

				// Check if square is standard
				if (board.getSquares().get(
						turnLauncher.players.get(activePlayerIndex).getCurrentPosition()) instanceof StandardSquare) {

					// standardSquare MUST override the corresponding square in board when any
					// changes are made, using standardSquareIndex
					StandardSquare standardSquare = (StandardSquare) board.getSquares()
							.get(turnLauncher.players.get(activePlayerIndex).getCurrentPosition());

					// Stores index of current square
					int standardSquareIndex = board.getSquares().indexOf(standardSquare);

					int userInt = 0;
					int rentCost = standardSquare.getRentCost();

					// Default to active player
					Player auctionWinner = turnLauncher.players.get(activePlayerIndex);

					// Check if the square is owned by someone already, but not active player
					if (standardSquare.getOwnedBy() != null) {

						// If the current player owns this square
						if (standardSquare.getOwnedBy() != turnLauncher.players.get(activePlayerIndex)) {

							System.out.printf("%s, you already own this square.\n", activePlayerName);

							// If someone else owns the square
						} else {

							// Player squareOwner = standardSquare.getOwnedBy();
							int squareOwnerIndex = turnLauncher.players.indexOf(standardSquare.getOwnedBy());
							String squareOwnerName = turnLauncher.players.get(squareOwnerIndex).getName();

							System.out.printf(
									"This square is currently owned by %s.\n\n %s what would you like to do?\n1. Charge rent of %d.\n2. Let %s stay for free.\n",
									squareOwnerName, squareOwnerName.toUpperCase(), rentCost, activePlayerName);
							// Get user response
							userInt = UserInput.getUserInputInt();
							// Check it's a 1 or a 2 TODO maybe have a method for yes/no response?
							while (userInt != 1 && userInt != 2) {
								System.out.println("Invalid input - please try again");
								userInt = UserInput.getUserInputInt();
							}
							switch (userInt) {

							case 1:
								// Take rent off active player
								turnLauncher.players.get(activePlayerIndex).setBalanceOfResources(
										turnLauncher.players.get(activePlayerIndex).getBalanceOfResources() - rentCost);

								// Give rent to square owner
								turnLauncher.players.get(squareOwnerIndex).setBalanceOfResources(
										turnLauncher.players.get(squareOwnerIndex).getBalanceOfResources() + rentCost);

								// Tell players what happened
								System.out.printf("%s charged %s rent of [%d].\n%s now has [%d].\n%s now has [%d].\n",
										squareOwnerName, activePlayerName, rentCost, squareOwnerName,
										turnLauncher.players.get(squareOwnerIndex).getBalanceOfResources(),
										activePlayerName,
										turnLauncher.players.get(activePlayerIndex).getBalanceOfResources());
								break;
							case 2:
								// Tell players what happened
								System.out.printf("%s chose to not charge %s rent.\n", squareOwnerName,
										activePlayerName);
								break;
							default:
								System.out.println("Welp... that's not supposed to happen");
							}
							// TODO better message here?
							System.out.println(activePlayerName + " has control again.");
						}

						// Check if player can afford to buy the square
					} else if (turnLauncher.players.get(activePlayerIndex).getBalanceOfResources() >= standardSquare
							.getPurchaseCost()) {
						// Offer player buy square
						System.out.printf(
								// TODO rename resources to whatever we decide to call it
								"You currently have %d RESOURCES, would you like to buy it?\n1. Yes\n2. No\n",
								turnLauncher.players.get(activePlayerIndex).getBalanceOfResources());
						// Get user response
						userInt = UserInput.getUserInputInt();
						// Check it's a 1 or a 2 TODO maybe have a method for yes/no response?
						while (userInt != 1 && userInt != 2) {
							// TODO could probably standardise the invalid response messages
							System.out.println("Invalid input - please try again");
							userInt = UserInput.getUserInputInt();
						}

						if (userInt == 1) {
							// Charge player for square
							turnLauncher.players.get(activePlayerIndex).setBalanceOfResources(
									turnLauncher.players.get(activePlayerIndex).getBalanceOfResources()
											- standardSquare.getPurchaseCost());
							// Update active player balance in array
							turnLauncher.players.set(
									turnLauncher.players.indexOf(turnLauncher.players.get(activePlayerIndex)),
									turnLauncher.players.get(activePlayerIndex));
							// Update square owner
							standardSquare.setOwned(true);
							standardSquare.setOwnedBy(turnLauncher.players.get(activePlayerIndex));
							// Update board with new values
							board.getSquares().set(standardSquareIndex, standardSquare);
							// TODO resources name
							System.out.printf("%s now owns %s, and has %d RESOURCES.\n", activePlayerName,
									standardSquare.getSquareName(),
									turnLauncher.players.get(activePlayerIndex).getBalanceOfResources());
						} else if (userInt == 2) {
							// Auction the square, doesn't want to buy
							auctionWinner = auctionSquare("doesn't want to buy it.",
									turnLauncher.players.get(activePlayerIndex), standardSquare, turnLauncher.players);
						}
					} else {
						// Auction the square, not enough resources to buy
						auctionWinner = auctionSquare("doesn't have enough RESOURCES to buy it.",
								turnLauncher.players.get(activePlayerIndex), standardSquare, turnLauncher.players);
					}
					// Make changes to player balance and square ownership, based on winner of
					// auction & square price
					if (auctionWinner != turnLauncher.players.get(activePlayerIndex)) {
						// Loop through all players
						for (int loop = 0; loop < turnLauncher.players.size(); loop++) {
							// Check auction winner against players
							if (auctionWinner == turnLauncher.players.get(loop)) {
								turnLauncher.players.get(loop)
										.setBalanceOfResources(turnLauncher.players.get(loop).getBalanceOfResources()
												- standardSquare.getPurchaseCost());

								// Update square owner
								standardSquare.setOwned(true);
								standardSquare.setOwnedBy(turnLauncher.players.get(loop));
								// Update board with new values
								board.getSquares().set(standardSquareIndex, standardSquare);

								System.out.printf("%s now owns %s, and has %d RESOURCES.\n",
										turnLauncher.players.get(loop).getName(), board.getSquares().get(standardSquareIndex).getSquareName(),
										turnLauncher.players.get(loop).getBalanceOfResources());

							}
						}
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
			
			

			board.getSquares().toString();

			// Clear console
			GUI.clearConsole(2);

		}
		
		System.out.println("Game Over");

		turnLauncher.endingPlayerScore(board);
		
		// Close scanner
		UserInput.closeScanner();

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

	}// END

	/**
	 * 
	 * Method that returns the player who won the auction. <br>
	 * <b>NOTE:</b> this method does not update the player balance, or square
	 * ownership.
	 * 
	 * @param reasonToAuction
	 * @param activePlayer
	 * @param standardSquare
	 * @param players
	 * @return
	 */
	public static Player auctionSquare(String reasonToAuction, Player activePlayer, StandardSquare standardSquare,
			ArrayList<Player> players) {

		int userInt = 0;
		int count = 0;

		int highRoll = -1;
		int newRoll = 0;
		// Default to the active player
		int highRollPlayerIndex = players.indexOf(activePlayer);
		boolean matching = true;

		// TODO Players arraylist may need updated to .get, if set to private in future
		System.out.printf("%s is being auctioned because %s %s\n", standardSquare.getSquareName(),
				activePlayer.getName(), reasonToAuction);

		// Hashmap of player indexes who want to buy the property & their index
		HashMap<Integer, Integer> playerResponses = new HashMap<Integer, Integer>();

		// Arraylist of players indexes who rolled to low, and will be removed from
		// contention
		ArrayList<Integer> removePlayers = new ArrayList<Integer>();

		for (int loop = 0; loop < players.size(); loop++) {

			if (players.get(loop) == activePlayer) {
				// Do nothing if player is active player

				// Check if player has enough resources to buy property
			} else if (players.get(loop).getBalanceOfResources() >= standardSquare.getPurchaseCost()) {
				// Ask player what they want to do
				System.out.printf(
						"%s: you currently have %d RESOURCES, would you like to buy %s for %d\n1. Yes\n2. No\n",
						players.get(loop).getName().toUpperCase(), players.get(loop).getBalanceOfResources(),
						standardSquare.getSquareName(), standardSquare.getPurchaseCost());
				// Get user response
				userInt = UserInput.getUserInputInt();
				// Check it's a 1 or a 2 TODO maybe have a method for yes/no response?
				while (userInt != 1 && userInt != 2) {
					// TODO could probably standardise the invalid response messages
					System.out.println("Invalid input - please try again");
					userInt = UserInput.getUserInputInt();
				}
				// Add player responses to arraylist
				if (userInt == 1) {

					// Count = key of player, loop = index of player in players araylist
					playerResponses.put(count, loop);
					System.out.printf("%s DOES want to buy %s\n", players.get(loop).getName(),
							standardSquare.getSquareName());

					// Increase count everytime someone wants to buy
					count++;
				} else {
					System.out.printf("%s DOES NOT want to buy %s\n", players.get(loop).getName(),
							standardSquare.getSquareName());
				}
			} else {
				System.out.printf("%s doesn't have enough RECOURCES to buy %s\n", players.get(loop).getName(),
						standardSquare.getSquareName());
			}
		}

		// Check that at least 1 player wanted to buy the property
		if (!playerResponses.isEmpty()) {
			// Check if more than 1 player wanted the property
			if (playerResponses.size() > 1) {
				System.out.printf("%d players want to buy %s\n\n", playerResponses.size(),
						standardSquare.getSquareName());

				System.out.printf("The players who want to buy will now roll to see who wins %s.\n",
						standardSquare.getSquareName());
				// Loop through all players who want to buy
				do {

					// Reset highest roll value
					highRoll = -1;

					// Check if there are any players to be removed
					if (!removePlayers.isEmpty()) {
						// Loop through players to be removed
						for (int loop = 0; loop < removePlayers.size(); loop++) {

							// Remove from HashMap of players
							playerResponses.remove(removePlayers.get(loop));
						}

					}

					for (int loop = 0; loop < playerResponses.size(); loop++) {

						// TODO probably implement 'findPlayerOrder' here somehow?

						System.out.printf("%s press enter to roll the dice",
								players.get(playerResponses.get(loop)).getName());

						UserInput.getUserInputString();

						// TEMPORARY CODE FOR ROLL
						Die di = new Die();

						newRoll = (di.rollDie() + di.rollDie());

						System.out.printf("%s rolled a total of %d", players.get(playerResponses.get(loop)).getName(),
								newRoll);

						if (newRoll > highRoll) {
							highRoll = newRoll;
							highRollPlayerIndex = playerResponses.get(loop);
							matching = false;
							System.out.printf(" which is now the top roll!\n");
						} else if (newRoll == highRoll) {
							matching = true;
							System.out.printf(" which is a draw with %s.\n",
									players.get(highRollPlayerIndex).getName());
						} else {
							System.out.printf(" which was not quite high enough.\n");

							// Add players who roll low to remove list
							removePlayers.add(loop);

						}

					}
				} while (matching);
			} else {
				// If only 1 players wants it, then they are index 0 in hashmap.
				highRollPlayerIndex = playerResponses.get(0);
			}

		} else {
			// No one wanted it
			System.out.printf("Nobody wanted to buy %s.\n", standardSquare.getSquareName());
		}
		// Announce winner of auction

		System.out.printf("The winner of the auction is: %s\n", players.get(highRollPlayerIndex).getName());

		// Return the player who rolled highest for the square, OR the person who's turn
		// it is, if no one wanted it.
		return players.get(highRollPlayerIndex);

	}// END

}

/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Jordan Davis
 *
 */
public class TurnLauncher {

	// TODO: This should prob be private to decrease coupling JD
	protected ArrayList<Player> players;

	private Die die;

	private final int NUM_OF_DICE = 2;

	private int roundNumber;

	/**
	 * default constructor
	 */
	public TurnLauncher() {
		this.players = new ArrayList<Player>();
		this.die = new Die();
		this.roundNumber = 2000;
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Adds a new player
	 */
	public void addPlayer() {

		Player player = new Player();

		System.out.println("Please enter your name");
		player.setName(UserInput.getUserInputString());

		players.add(player);

		GUI.clearConsole(4);

	}

	/**
	 * Prints all players to screen
	 */
	public void displayPlayers() {

		for (int loop = 1; loop <= this.players.size(); loop++) {
			System.out.println(loop + ". " + players.get(loop - 1).getName());
		}

	}

	/**
	 * Finds first player based on highest roll.
	 */
	public void findPlayerOrder() {

		int highestRoll, playerRoll;
		Player firstToPlay;
		String roll;
		boolean matchingRoll;

		highestRoll = 0;
		playerRoll = 0;
		firstToPlay = this.players.get(0);
		matchingRoll = false;

		GUI.clearConsole(4);
		System.out.println("Its time to find out who goes first...\nPress enter to roll the dice!");
		UserInput.getUserInputString();
		GUI.clearConsole(4);

		do {

			for (Player player : this.players) {
				roll = rollDice();
				playerRoll = getRollValue(roll);

				System.out.println(player.getName() + " " + roll);

				if (playerRoll > highestRoll) {
					highestRoll = playerRoll;
					firstToPlay = player;
					matchingRoll = false;
				} else if (playerRoll == highestRoll) {
					matchingRoll = true;

				}

			}

			if (matchingRoll == true) {
				System.out.println("\nThe roll was a draw, lets try again. Please press enter to roll");
				UserInput.getUserInputString();
			}

		} while (matchingRoll);

		System.out.println("\n" + firstToPlay.getName() + " is first to play with the highest roll of " + highestRoll
				+ "\nPress enter to continue");
		UserInput.getUserInputString();

		// rearanges the player array so that the correct player is first
		while (firstToPlay != this.players.get(0)) {

			this.players.add(this.players.get(0));
			this.players.remove(0);

		}

	}

	/**
	 * Method rolls the game dice and returns the result
	 * 
	 * @return int array containing dice rolls, last index is roll total
	 */
	public String rollDice() {

		String result;
		int totalRoll, currentRoll;

		result = " rolled a ";
		totalRoll = 0;

		for (int loop = 1; loop <= NUM_OF_DICE; loop++) {
			currentRoll = this.die.rollDie();
			totalRoll += currentRoll;

			if (loop == NUM_OF_DICE) {
				result += currentRoll + " " + "for a total of : " + totalRoll;
			} else {
				result += currentRoll + " and ";
			}

		}

		return result;
	}

	/**
	 * Converts string from dice roll into an int value
	 * 
	 * @param previous dice roll string
	 * @return int value of total roll
	 */
	public int getRollValue(String roll) {

		// removes all non-digit chars and whitespace
		roll = roll.replaceAll("[^\\d]+", " ").trim();

		String[] rollArr = roll.split(" ");

		return Integer.parseInt(rollArr[rollArr.length - 1]);

	}

	/**
	 * Modify or delete a player
	 */
	public void modifyPlayer() {

		int userInput;
		boolean valid = false;

		do {
			System.out.println("Select a player to modify:");
			displayPlayers();
			userInput = UserInput.getUserInputInt() - 1;

			if (userInput >= 0 && userInput < this.players.size()) {
				valid = true;
			}

			if (!valid) {
				System.out.println("Your selection was invalid, please try again");
			}

		} while (!valid);

		Player player = this.players.get(userInput);

		System.out.println("What would you like to do with " + player.getName()
				+ "?\n1. Modify Name\n2. Delete Player\n3. Go back");

		do {

			switch (UserInput.getUserInputInt()) {
			case 1:
				System.out.println("Please enter the new name");
				player.setName(UserInput.getUserInputString());
				break;
			case 2:
				this.players.remove(userInput);
				System.out.println("Player has been deleted");
				break;
			case 3:
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
				valid = false;
			}
			System.out.println("loop end");
		} while (!valid);
		System.out.println("end");
	}

	/**
	 * 
	 * Method to auction a square to all players, except the player auctioning it
	 * 
	 * @param reasonToAuction - eg. "not enough resources"
	 * @param activePlayer    - The player who's turn it is
	 * @param standardSquare  - The square being auctioned
	 * @param players         - ArrayList of all players
	 */
	public void auctionSquare(String reasonToAuction, Player activePlayer, StandardSquare standardSquare,
			ArrayList<Player> players) {

		int userInt = 0;
		int highRoll = -1;
		int newRoll = 0;
		// Default to the active player
		Player highRollPlayer = null;
		boolean matching = true;
		String squareName = standardSquare.getSquareName();
		String activePlayerName = activePlayer.getName();

		System.out.printf("%s is being auctioned because %s %s\n", squareName, activePlayerName, reasonToAuction);

		// Arraylist of players that want the square
		ArrayList<Player> playersWant = new ArrayList<Player>();

		// Arraylist of players that want the square
		ArrayList<Player> playersToRemove = new ArrayList<Player>();

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

					playersWant.add(players.get(loop));

					System.out.printf("%s DOES want to buy %s\n", players.get(loop).getName(),
							standardSquare.getSquareName());
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
		if (!playersWant.isEmpty()) {
			// Check if more than 1 player wanted the property
			if (playersWant.size() > 1) {
				System.out.printf("%d players want to buy %s\n\n", playersWant.size(), standardSquare.getSquareName());

				System.out.printf("The players who want to buy will now roll to see who wins %s.\n",
						standardSquare.getSquareName());
				// Loop through all players who want to buy
				do {

					// Reset highest roll value
					highRoll = -1;

					// Remove all players who didn't roll high enough
					playersWant.removeAll(playersToRemove);
					// Clear the list
					playersToRemove.clear();

					for (int loop = 0; loop < playersWant.size(); loop++) {

						// TODO probably implement 'findPlayerOrder' here somehow?

						System.out.printf("%s press enter to roll the dice", playersWant.get(loop).getName());

						UserInput.getUserInputString();

						// TEMPORARY CODE FOR ROLL
						Die di = new Die();

						newRoll = (di.rollDie() + di.rollDie());

						System.out.printf("%s rolled a total of %d", playersWant.get(loop).getName(), newRoll);

						if (newRoll > highRoll) {

							// Different message for 1st roller
							if (highRollPlayer == null) {
								System.out.printf(".\n");
							} else {
								System.out.printf(" which is now the top roll!\n");
								// remove the previous high roll player
								playersToRemove.add(highRollPlayer);
							}

							highRoll = newRoll;
							highRollPlayer = playersWant.get(loop);
							matching = false;

						} else if (newRoll == highRoll) {
							matching = true;
							// TODO No message for a 3-way draw
							System.out.printf(" which is a draw with %s.\n", highRollPlayer.getName());

						} else {
							System.out.printf(" which was not quite high enough.\n");

							// Add players who roll low to remove list
							playersToRemove.add(playersWant.get(loop));

						}

					}
				} while (matching);
			} else {
				// If only 1 players wants it, then they are index 0
				highRollPlayer = playersWant.get(0);
			}

		} else {
			// No one wanted it
			System.out.printf("Nobody wanted to buy %s.\n", standardSquare.getSquareName());
		}

		// If someone wanted the square, do some maths
		if (highRollPlayer != activePlayer) {
			// Announce winner of auction
			System.out.printf("The winner of the auction is: %s\n", highRollPlayer.getName());

			// Update player currency
			players.get(players.indexOf(highRollPlayer))
					.setBalanceOfResources(players.get(players.indexOf(highRollPlayer)).getBalanceOfResources()
							- standardSquare.getPurchaseCost());

			// Update square ownership
			standardSquare.setOwned(true);
			standardSquare.setOwnedBy(players.get(players.indexOf(highRollPlayer)));

			// Tell players what happened
			System.out.printf("%s now owns %s, and has %d RESOURCES.\n",
					players.get(players.indexOf(highRollPlayer)).getName(), standardSquare.getSquareName(),
					players.get(players.indexOf(highRollPlayer)).getBalanceOfResources());
		}

	}// END

	/**
	 * TODO needs refactoring, probably quite a bit, but should work as-is!
	 * 
	 * @param activePlayer
	 * @param board
	 * @param turnLauncher
	 */
	public void moveMethod(Player activePlayer, Board board, TurnLauncher turnLauncher) {

		String activePlayerName = activePlayer.getName();
		int currentPos = activePlayer.getCurrentPosition();

		System.out.println("You are currently on\n" + board.getSquares().get(currentPos).toString());

		String roll = turnLauncher.rollDice();
		System.out.println("You" + roll);

		int newPos = activePlayer.getCurrentPosition() + turnLauncher.getRollValue(roll);

		if (newPos > 11) {
			newPos -= 12;
			// TODO Resources for passing GO - needs doing properly!
			System.out.println("You passed GO +200 resources woooop!");
			activePlayer.setBalanceOfResources(activePlayer.getBalanceOfResources() + 200);
		}

		// Update player position
		activePlayer.setCurrentPosition(newPos);

		Square newSquare = board.getSquares().get(newPos);

		System.out.println("You are now on\n" + newSquare.toString());

		// Check if square is standard
		if (newSquare instanceof StandardSquare) {

			StandardSquare standardSquare = (StandardSquare) newSquare;

			int userInt = 0;
			int rentCost = standardSquare.getRentCost();

			// Check if the square is owned by someone already
			if (standardSquare.getOwnedBy() != null) {

				// If the current player owns this square
				if (standardSquare.getOwnedBy() == activePlayer) {

					System.out.printf("%s, you already own this square.\n", activePlayerName);

					// If someone else owns the square
				} else {

					Player squareOwner = standardSquare.getOwnedBy();
					String squareOwnerName = squareOwner.getName();

					if (activePlayer.getBalanceOfResources() <= rentCost) {
						// TODO Better message here, implement game end?
						System.out.printf(
								"%s does not have enough RESOURCES to pay. %s What would you like to do?\n1. Charge %d RESOURCES anyway.\n2. Let %s stay for free.\n",
								activePlayerName, squareOwnerName.toUpperCase(), rentCost, activePlayerName);

					} else {
						System.out.printf(
								"\nThis square is currently owned by %s.\n%s what would you like to do?\nYou currently have %d resources, and %s has %d.\n1. Charge %d RESOURCES.\n2. Let %s stay for free.\n",
								squareOwnerName, squareOwnerName.toUpperCase(), squareOwner.getBalanceOfResources(),
								activePlayerName, activePlayer.getBalanceOfResources(), rentCost, activePlayerName);
					}
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
						activePlayer.setBalanceOfResources(activePlayer.getBalanceOfResources() - rentCost);

						// Give rent to square owner
						squareOwner.setBalanceOfResources(squareOwner.getBalanceOfResources() + rentCost);

						// Tell players what happened
						System.out.printf("%s charged %s rent of [%d].\n%s now has [%d].\n%s now has [%d].\n",
								squareOwnerName, activePlayerName, rentCost, squareOwnerName,
								squareOwner.getBalanceOfResources(), activePlayerName,
								activePlayer.getBalanceOfResources());
						break;
					case 2:
						// Tell players what happened
						System.out.printf("%s chose to not charge %s rent.\n", squareOwnerName, activePlayerName);
						break;
					default:
						System.out.println("Welp... that's not supposed to happen");
					}
					// TODO better message here?
					System.out.println(activePlayerName + " has control again.");
				}

				// Check if player can afford to buy the square
			} else if (activePlayer.getBalanceOfResources() >= standardSquare.getPurchaseCost()) {
				// Offer player the square
				System.out.printf(
						// TODO rename resources to whatever we decide to call it
						"You currently have %d RESOURCES, would you like to buy it?\n1. Yes\n2. No\n",
						activePlayer.getBalanceOfResources());

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
					activePlayer.setBalanceOfResources(
							activePlayer.getBalanceOfResources() - standardSquare.getPurchaseCost());

					// Update square owner
					standardSquare.setOwned(true);
					standardSquare.setOwnedBy(activePlayer);

					// TODO resources name
					System.out.printf("%s now owns %s, and has %d RESOURCES.\n", activePlayerName,
							standardSquare.getSquareName(), activePlayer.getBalanceOfResources());
				} else if (userInt == 2) {
					// Auction the square, doesn't want to buy
					turnLauncher.auctionSquare("doesn't want to buy it.", activePlayer, standardSquare,
							turnLauncher.players);
				}
			} else {
				// Auction the square, not enough resources to buy
				turnLauncher.auctionSquare("doesn't have enough RESOURCES to buy it.", activePlayer, standardSquare,
						turnLauncher.players);
			}

		}
	} // END

	/**
	 * Calculates the total worth of a Player
	 * 
	 * @param player
	 */
	public int calculatePlayerWorth(Player player, Board board) {

		int playerValue;
		StandardSquare stdSquare;

		playerValue = player.getBalanceOfResources();

		for (Square square : board.getSquares()) {

			if (square instanceof StandardSquare) {

				stdSquare = (StandardSquare) square;

				if (stdSquare.getOwnedBy() != null && stdSquare.getOwnedBy().equals(player)) {

					playerValue += stdSquare.getPurchaseCost();
					playerValue += (stdSquare.getCurrentMinorDevLevel() * stdSquare.getMinorDevCost());
					playerValue += (stdSquare.getCurrentMajorDevLevel() * stdSquare.getMajorDevCost());

				}

			}

		}

		return playerValue;

	}

	/**
	 * Finds the ending scores of all players
	 */
	public void endingPlayerScore(Board board) {

		System.out.println("The scores are as follows:");
		
		// checks if player is not bankrupt then calculates score
		for (Player player : this.players) {
			if (player.getBalanceOfResources() > 0) {
				player.setBalanceOfResources(calculatePlayerWorth(player, board));
			}
		}
		
		//Orders the players in decending order
		Collections.sort(this.players, Collections.reverseOrder(new ResourcesComparator()));

		//displays all player scores
		for (Player player : this.players) {
			if (player.getBalanceOfResources() > 0) {
				System.out.println(player.getName() + " : " + player.getBalanceOfResources());
			} else
				System.out.println(player.getName() + " : Bankrupt");
		}
	}

	/**
	 * increases the year(roundNumber) by 1
	 */
	public void roundEnd() {
		this.roundNumber +=1;
	}

	/**
	 * Runs correct gameover sequence depending on win or loss 
	 * @param board
	 */
	public void gameOverSequence(Board board) {

		if (board.allSystemComplete()) {
			GUI.displayGameWonMessage(this.roundNumber);
		} else {
			GUI.displayGameLossMessage(this.roundNumber);
		}
		
		

		endingPlayerScore(board);

	}

}

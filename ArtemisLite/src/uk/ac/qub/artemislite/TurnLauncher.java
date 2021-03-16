/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class TurnLauncher {

	// Variables

	// TODO: This should prob be private to decrease coupling JD
	protected ArrayList<Player> players;

	private Player activePlayer;

	private Die die;

	private final int NUM_OF_DICE = 2;

	// halve the default resources from 200 to 100 for a longer game
	private final int RESOURCE_VALUE_LONG_GAME = -100;

	private int turnNumber = 0;

	private static boolean turnOver = false;

	// Constructors

	/**
	 * default constructor
	 */
	public TurnLauncher() {
		this.players = new ArrayList<Player>();
		this.die = new Die();
	}

	// Methods

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the activePlayer
	 */
	public Player getActivePlayer() {
		return activePlayer;
	}

	/**
	 * @param activePlayer the activePlayer to set
	 */
	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}

	/**
	 * Adds a new player
	 */
	public void addPlayer() {

		Player player = new Player();

		promptName(player);

		players.add(player);

		GUI.clearConsole(8);

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

		Player firstToPlay;
		System.out.println("Lets roll the dice to find out who goes first...\n");
		GUI.clearConsole(8);
		
		firstToPlay = allPlayersRoll(players);

		GUI.clearConsole(8);

		// Rearranges the player array so that the correct player is first
		while (firstToPlay != this.players.get(0)) {

			this.players.add(this.players.get(0));
			this.players.remove(0);

		}

		activePlayer = firstToPlay;

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

		/*
		 * removes all non-digit chars and whitespace Regex explanation: [^ ] = match
		 * any character not in the brackets \d = any digit + = match adjacent
		 * characters
		 */
		roll = roll.replaceAll("[^\\d]+", " ").trim();

		String[] rollArr = roll.split(" ");

		return Integer.parseInt(rollArr[rollArr.length - 1]);

	}

	/**
	 * Prompts user for name and validates against existing players
	 * 
	 * @param player name to add
	 */
	public void promptName(Player player) {
		String name;
		boolean valid;

		System.out.println("Please enter your name");

		do {
			valid = true;
			name = UserInput.getUserInputString();
			// TODO: is there any other name validation needed? JD
			if (players != null) {

				if (name.equals("")) {
					valid = false;
					System.out.println("That name is invalid, please enter a different name");
				}

				for (Player user : players) {
					if (!user.equals(player)) {
						if (user.getName().equalsIgnoreCase(name.trim())) {
							valid = false;
							System.out.println(
									"That name has already been used by a player, please enter something different");
						}
					}
				}

			}

		} while (!valid);

		player.setName(name);

	}

	/**
	 * Modify or delete a player
	 */
	public void modifyPlayer() {

		int userInput = 0;
		boolean valid = false;
		Player player = null;

		if (this.players.size() > 1) {
			do {
				System.out.println("=====| MODIFY PLAYER |=====");
				displayPlayers();
				userInput = UserInput.getUserInputInt() - 1;

				if (userInput >= 0 && userInput < this.players.size()) {
					valid = true;
				}

				if (!valid) {
					System.out.println("Your selection was invalid, please try again");
				}

			} while (!valid);
		}

		player = this.players.get(userInput);
		valid = true;

		GUI.clearConsole(8);
		
		do {
			System.out.println("=====| PLAYER OPTIONS |=====" + player.getName()
					+ "?\n1. Modify Name\n2. Delete Player\n3. Go back");

			switch (UserInput.getUserInputInt()) {
			case 1:
				promptName(player);
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
		} while (!valid);
		
		GUI.clearConsole(8);
	}

	/**
	 * Increases the initial value of resources for all players for a longer game
	 */
	public void setupLongGame() {
		ModifyPlayerResources.modifyResourcesAllPlayers(players, this.RESOURCE_VALUE_LONG_GAME);
	}

	/**
	 * 
	 * Method to auction a square to all players, except the player auctioning it
	 * 
	 * @param reasonToAuction - eg. "not enough resources"
	 * @param standardSquare  - The square being auctioned
	 */
	public void auctionSquare(String reasonToAuction, StandardSquare standardSquare) {

		int purchaseCost;
		String squareName, activePlayerName;
		// Default to the active player
		Player highRollPlayer;
		// Arraylist of players that want the square
		ArrayList<Player> playersWant;

		highRollPlayer = null;
		purchaseCost = standardSquare.getPurchaseCost();
		squareName = standardSquare.getSquareName();
		activePlayerName = activePlayer.getName();
		playersWant = new ArrayList<Player>();

		System.out.printf("%s is being auctioned because %s %s\n", squareName, activePlayerName, reasonToAuction);

		for (int loop = 0; loop < players.size(); loop++) {
			// Do nothing if player is active player
			if (players.get(loop) != activePlayer) {
				// Check if player has enough resources to buy property
				if (players.get(loop).getBalanceOfResources() >= purchaseCost) {
					// Ask player what they want to do
					System.out.printf("%s: you currently have %d RESOURCES, would you like to buy %s for %d\n",
							players.get(loop).getName().toUpperCase(), players.get(loop).getBalanceOfResources(),
							squareName, purchaseCost);

					// Add player responses to arraylist
					switch (GUI.yesNoMenu()) {
					case 1:
						playersWant.add(players.get(loop));
						System.out.printf("%s DOES want to buy %s\n", players.get(loop).getName(), squareName);
						break;
					case 2:
						System.out.printf("%s DOES NOT want to buy %s\n", players.get(loop).getName(), squareName);
						break;
					}

				} else {
					System.out.printf("%s doesn't have enough RECOURCES to buy %s\n", players.get(loop).getName(),
							squareName);
				}
			}
		}

		// Check that at least 1 player wanted to buy the property
		if (playersWant.isEmpty()) {
			// No one wanted it
			System.out.printf("Nobody purchased %s.\n", squareName);

		} else if (playersWant.size() == 1) {
			// If only 1 players wants it, then they are index 0
			highRollPlayer = playersWant.get(0);

		} else {
			// else roll dice to see who wins the property
			System.out.printf("%d players want to buy %s\n\n", playersWant.size(), squareName);

			System.out.printf("The players who want to buy will now roll to see who wins %s.\n", squareName);

			highRollPlayer = allPlayersRoll(playersWant);

		}

		// If someone wanted the square, do some maths
		if (highRollPlayer != null) {
			// Announce winner of auction
			System.out.printf("The winner of the auction is: %s\n", highRollPlayer.getName());

			// Update player currency
			highRollPlayer.setBalanceOfResources(highRollPlayer.getBalanceOfResources() - purchaseCost);

			// Update square ownership
			standardSquare.setOwnedBy(highRollPlayer);

			// Tell players what happened
			System.out.printf("%s now owns %s, and has %d RESOURCES.\n", highRollPlayer.getName(), squareName,
					highRollPlayer.getBalanceOfResources());
		}

	}// END

	/**
	 * Moves players on the game board based on dice roll
	 * 
	 * @param board
	 */
	public void moveMethod(Board board) {

		String roll;
		int currentPos, newPos;
		Square newSquare;

		currentPos = activePlayer.getCurrentPosition();

		System.out.println("You are currently on\n" + board.getSquares().get(currentPos).toString());
		System.out.println("Ready to roll the dice?\n-----> PRESS ENTER <-----");
		UserInput.getUserInputString();
		GUI.clearConsole(8);

		roll = rollDice();
		System.out.println("You" + roll);

		newPos = activePlayer.getCurrentPosition() + getRollValue(roll);

		if (newPos > 11) {
			newPos -= 12;
			// TODO Resources for passing GO - needs doing properly!
			System.out.println("You passed GO +200 resources woooop!");
			activePlayer.setBalanceOfResources(activePlayer.getBalanceOfResources() + 200);
		}

		// Update player position
		activePlayer.setCurrentPosition(newPos);

		newSquare = board.getSquares().get(newPos);

		System.out.println("You are now on\n" + newSquare.toString());

	} // END

	/**
	 * checks the element for any responsabilities when landed on
	 */
	public void checkElement(Board board) {
		int pos;
		Square newSquare;

		pos = activePlayer.getCurrentPosition();
		newSquare = board.getSquares().get(pos);

		// Check if square is standard
		if (newSquare instanceof StandardSquare) {

			StandardSquare standardSquare = (StandardSquare) newSquare;

			// Check if the square is owned by someone already
			if (standardSquare.getOwnedBy() != null) {
				chargeRent(standardSquare);
			} else if (activePlayer.getBalanceOfResources() >= standardSquare.getPurchaseCost()) {
				offerElement(standardSquare);
			} else {
				// Only start auction if there is a player that can afford it
				for (int loop = 0; loop < players.size(); loop++) {
					if (players.get(loop).getBalanceOfResources() >= standardSquare.getPurchaseCost()) {
						auctionSquare("doesn't have enough RESOURCES to buy it.", standardSquare);
						break;
					} else if (loop == (players.size() - 1)) {
						System.out.printf("%s and no other player have enough RESOURCES to buy %s.\n",
								activePlayer.getName(), standardSquare.getSquareName());
					}
				}

			}

		}
	}

	/**
	 * Give player the option to charge rent from the active player
	 * 
	 * @param standardSquare
	 */
	public void chargeRent(StandardSquare standardSquare) {
		String activePlayerName, squareOwnerName;
		int rentCost;
		Player squareOwner;

		activePlayerName = activePlayer.getName();
		rentCost = standardSquare.getRentCost();
		squareOwner = standardSquare.getOwnedBy();
		squareOwnerName = squareOwner.getName();

		if (squareOwner == activePlayer) {
			System.out.printf("%s, you already own this square.\n", activePlayerName);
			return;
		}

		if (activePlayer.getBalanceOfResources() <= rentCost) {
			// TODO Better message here, implement game end?
			System.out.printf(
					"%s does not have enough RESOURCES to pay, they will go bankrupt and the game will end. %s Would you like to charge rent anyway?\n",
					activePlayerName, squareOwnerName.toUpperCase());

		} else {
			System.out.printf(
					"\nThis square is currently owned by %s.\nYou currently have %d resources, and the rent is %d.\n%s would you like to charge them rent of %d?\n",
					squareOwnerName, activePlayer.getBalanceOfResources(), rentCost, squareOwnerName.toUpperCase(),
					rentCost);
		}

		switch (GUI.yesNoMenu()) {
		case 1:
			// Take rent off active player
			activePlayer.setBalanceOfResources(activePlayer.getBalanceOfResources() - rentCost);
			// Give rent to square owner
			squareOwner.setBalanceOfResources(squareOwner.getBalanceOfResources() + rentCost);
			System.out.printf("%s charged %s rent of [%d].\n%s now has [%d].\n%s now has [%d].\n", squareOwnerName,
					activePlayerName, rentCost, squareOwnerName, squareOwner.getBalanceOfResources(), activePlayerName,
					activePlayer.getBalanceOfResources());
			break;
		case 2:
			System.out.printf("%s chose to not charge %s rent.\n", squareOwnerName, activePlayerName);
			break;
		default:
			System.out.println("Welp... that's not supposed to happen");
		}
		// TODO better message here?
		System.out.println(activePlayerName + " has control again.");

	}

	/**
	 * Offers element to active player to purchase
	 * 
	 * @param StandardSquare to be offered
	 */
	public void offerElement(StandardSquare standardSquare) {
		// Offer player the square
		System.out.printf(
				// TODO rename resources to whatever we decide to call it
				"You currently have %d RESOURCES, would you like to buy it?\n", activePlayer.getBalanceOfResources());

		switch (GUI.yesNoMenu()) {
		case 1:
			// Charge player for square
			activePlayer.setBalanceOfResources(activePlayer.getBalanceOfResources() - standardSquare.getPurchaseCost());
			// Update square owner
			standardSquare.setOwnedBy(activePlayer);
			// TODO resources name
			System.out.printf("%s now owns %s, and has %d RESOURCES.\n", activePlayer.getName(),
					standardSquare.getSquareName(), activePlayer.getBalanceOfResources());
			break;
		case 2:
			// Auction the square, doesn't want to buy
			auctionSquare("doesn't want to buy it.", standardSquare);
			break;
		}

	}

	/**
	 * Rolls dice for each player in an array and returns the highest scoring player
	 * 
	 * @param arrayList of players to roll
	 * @return Player with highest dice roll
	 */
	public Player allPlayersRoll(ArrayList<Player> playersToRoll) {
		String roll;
		int highestRoll, playerRoll;
		Player highestRollPlayer;
		boolean matchingRoll;

		playerRoll = 0;
		highestRollPlayer = playersToRoll.get(0);
		matchingRoll = false;
		// TODO: do you want this so each player has to press to roll dice? or just 1
		// press rolls for all players JD
		System.out.println("-----> PRESS ENTER <-----");

		do {
			// Moved this inside the do-while, so it gets reset to 0 AP
			highestRoll = 0;

			UserInput.getUserInputString();
			for (Player player : playersToRoll) {
				roll = rollDice();
				playerRoll = getRollValue(roll);

				System.out.println(player.getName() + " " + roll);

				if (playerRoll > highestRoll) {
					highestRoll = playerRoll;
					highestRollPlayer = player;
					matchingRoll = false;
				} else if (playerRoll == highestRoll) {
					matchingRoll = true;
				}

			}

			if (matchingRoll == true) {
				System.out.println("\nThe roll was a draw, lets try again. \n-----> PRESS ENTER <-----");
			}

		} while (matchingRoll);

		System.out.println("\n" + highestRollPlayer.getName() + " got the highest roll of " + highestRoll
				+ "\n-----> PRESS ENTER <-----");
		UserInput.getUserInputString();

		GUI.clearConsole(8);
		return highestRollPlayer;

	}

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

		// Orders the players in decending order
		Collections.sort(this.players, Collections.reverseOrder(new ResourcesComparator()));

		// displays all player scores
		for (Player player : this.players) {
			if (player.getBalanceOfResources() > 0) {
				System.out.println(player.getName() + " : " + player.getBalanceOfResources());
			} else
				System.out.println(player.getName() + " : Bankrupt");
		}
	}

	/**
	 * Method that increments the calendar date and turnNumber by 1 and displays an
	 * end of round message to players.
	 */
	public void roundEnd(Board board) {

		double progress = GUI.missionProgress(board);

		ArtemisCalendar.getCalendar().incrementDate();

		turnNumber += 1;

		GUI.clearConsole(2);

		System.out.printf("Round %d has ended. The date is now %s, %d.\n", turnNumber,
				ArtemisCalendar.getMonthName(ArtemisCalendar.getCalendar().get(2)),
				ArtemisCalendar.getCalendar().get(1));
		if (progress > 0) {
			System.out.printf("The Artemis Project is %.1f%s complete.\n", progress, "%");
		}

		GUI.clearConsole(1);

	}

	/**
	 * @return the turnNumber
	 */
	public int getTurnNumber() {
		return turnNumber;
	}

	/**
	 * Runs correct game-over sequence depending on win or loss
	 * 
	 * @param board
	 */
	public void gameOverSequence(Board board) {

		if (board.allSystemComplete()) {
			GUI.displayGameWonMessage();
		} else {
			GUI.displayGameLossMessage(board);
		}

		if(this.players.size() > 0) {
			endingPlayerScore(board);
		}

	}

	/**
	 * Method to end the current players turn.
	 * 
	 * @param board
	 */
	public void endTurn(Board board) {

		int activePlayerIndex = players.indexOf(getActivePlayer());

		if (activePlayerIndex != players.size() - 1) {
			setActivePlayer(players.get(activePlayerIndex + 1));
		} else {
			setActivePlayer(players.get(0));
			roundEnd(board);
		}
		turnOver = true;

	}

	/**
	 * @return the endTurn
	 */
	public static boolean isEndTurn() {
		return turnOver;
	}

	/**
	 * @param turnOver the turnOver to set
	 */
	public static void setTurnOver(boolean turnOver) {
		TurnLauncher.turnOver = turnOver;
	}

}

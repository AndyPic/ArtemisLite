/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;

/**
 * @author Jordan Davis
 *
 */
public class TurnLauncher {

	// TODO: This should prob be private to decrease coupling JD
	protected ArrayList<Player> players;

	private Die die;

	private final int NUM_OF_DICE = 2;

	/**
	 * default constructor
	 */
	public TurnLauncher() {
		this.players = new ArrayList<Player>();
		this.die = new Die();
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

	}

	/**
	 * Prints all players to screen
	 */
	public void displayPlayers() {

		for (Player player : players) {
			System.out.println(player.getName());
		}
		System.out.println();

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

		System.out.println("Its time to find out who goes first...\nPress enter to roll the dice!");
		UserInput.getUserInputString();

		// TODO: needs updated so that it will roll again if 2 players end up with the
		// same max number. Currently just gives it to the first player that rolls max
		// JD

		do {
			
			for (Player player : this.players) {
				roll = rollDice();
				playerRoll = getRollValue(roll);

				System.out.println(player.getName() + roll);

				if (playerRoll > highestRoll) {
					highestRoll = playerRoll;
					firstToPlay = player;
					matchingRoll = false;
				} else if(playerRoll == highestRoll) {
					matchingRoll = true;
					
				}

			}
			
			if(matchingRoll = true) {
				System.out.println("The roll was a draw, lets try again. Please press enter to roll");
				UserInput.getUserInputString();
			}

		} while (matchingRoll);

		System.out.println(firstToPlay.getName() + " rolled the highest and is 1st to play");

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

		result = "rolled a ";
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
		System.out.println(roll);
		String[] rollArr = roll.split(" ");

		return Integer.parseInt(rollArr[rollArr.length - 1]);

	}

}

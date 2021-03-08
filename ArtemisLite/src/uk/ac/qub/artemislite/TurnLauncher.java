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

	private Die[] dice;

	private final int NUM_OF_DICE = 2;

	/**
	 * default constructor
	 */
	public TurnLauncher() {
		this.players = new ArrayList<Player>();
		setDice(NUM_OF_DICE);
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the dice
	 */
	public Die[] getDice() {
		return dice;
	}

	/**
	 * Creates the dice array
	 */
	public void setDice(int num) {

		this.dice = new Die[num];

		for (int loop = 0; loop < this.dice.length; loop++) {
			this.dice[loop] = new Die();
		}
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

		int roll, highestRoll;
		Player firstToPlay;

		highestRoll = 0;
		firstToPlay = this.players.get(0);

		System.out.println("Its time to find out who goes first...\nPress enter to roll the dice!");
		UserInput.getUserInputString();

		// TODO: needs updated so that it will roll again if 2 players end up with the
		// same max number. Currently just gives it to the first player that rolls max
		// JD
		for (Player player : this.players) {
			roll = rollDice();
			System.out.println(player.getName() + " rolled a " + roll);

			if (roll > highestRoll) {
				highestRoll = roll;
				firstToPlay = player;
			}

		}

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
	 * @return int, total roll value
	 */
	public int rollDice() {
		// TODO: method made folling class diagram but we should review. Why are we
		// creating a dice array? the only place that die.rollDie() method is used is in
		// this method. Could just create the 1 die and call it multiple times based on
		// NUM_OF_DICE. Maybe this method should be in the Die class to increase
		// cohesion? JD
		// TODO: need to split this so that each roll + the total are returned. Need to
		// review best method to do this that would allow the number of dice to change
		// and not break the method that calls it JD
		int result = 0;

		for (Die die : this.dice) {
			result += die.rollDie();
		}

		return result;
	}

}

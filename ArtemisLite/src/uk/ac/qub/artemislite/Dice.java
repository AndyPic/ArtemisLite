/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.Random;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class Dice {

	// Instance Vars

	private final int MIN_NUMBER = 1;
	private final int MAX_NUMBER = 6;
	private Random diceRoll = new Random();

	// Constructors

	/**
	 * default dice constructor
	 */
	public Dice() {

	}

	// Methods

	@Override
	public String toString() {
		return "Dice [MIN_NUMBER=" + MIN_NUMBER + ", MAX_NUMBER=" + MAX_NUMBER + "]";
	}

	/**
	 * Rolls dice and returns the result
	 * 
	 * @return int between MIN_NUMBER & MAX_NUMBER
	 */
	public int rollDice() {

		return MIN_NUMBER + diceRoll.nextInt(MAX_NUMBER);

	}

}

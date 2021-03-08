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
public class Die {

	// Instance Vars

	private final int MIN_NUMBER = 1;
	private final int MAX_NUMBER = 6;
	private Random dieRoll = new Random();

	// Constructors

	/**
	 * default die constructor
	 */
	public Die() {

	}

	// Methods

	@Override
	public String toString() {
		return "Die [MIN_NUMBER=" + MIN_NUMBER + ", MAX_NUMBER=" + MAX_NUMBER + "]";
	}

	/**
	 * Rolls die and returns the result
	 * 
	 * @return int between MIN_NUMBER & MAX_NUMBER
	 */
	public int rollDie() {

		return MIN_NUMBER + dieRoll.nextInt(MAX_NUMBER);

	}

}

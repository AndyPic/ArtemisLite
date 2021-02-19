/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.Random;

/**
 * @author Jordan Davis
 *
 */
public class Dice {

	/**
	 * Min number on dice roll
	 */
	private final int MIN_NUMBER = 1;
	
	/**
	 * Max number of dice roll
	 */
	private final int MAX_NUMBER = 6;
	
	/**
	 * Create random generator
	 */
	private Random diceRoll = new Random();
	
	/**
	 * default dice constructor
	 */
	public Dice() {
		
	}
	
	/**
	 * Rolls dice and returns the result
	 * @return int between MIN_NUMBER & MAX_NUMBER
	 */
	public int rollDice() {
		
		return MIN_NUMBER + diceRoll.nextInt(MAX_NUMBER);
		
	}

	@Override
	public String toString() {
		return "Dice [MIN_NUMBER=" + MIN_NUMBER + ", MAX_NUMBER=" + MAX_NUMBER + "]";
	}

}

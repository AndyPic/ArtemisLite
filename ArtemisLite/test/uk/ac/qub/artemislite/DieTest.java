/**
 * 
 */
package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jordan Davis
 *
 */
class DieTest {

	// Test Data
	int numOfTests;
	Die die;
	int[] rolledNums;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		numOfTests = 100;
		die = new Die();
		rolledNums = new int[6];
	}

	/**
	 * Test method for dice roll
	 */
	@Test
	void testValidDieRoll() {

		for (int loop = 0; loop < numOfTests; loop++) {

			int roll = die.rollDie();
			
			if (roll > 6 || roll < 0) {
				System.out.println(roll);
				assertFalse(true);
			}
			
			rolledNums[roll - 1] += 1;

		}

		// displays all rolls
		System.out.println("Total rolls:");
		System.out.println("1:" + rolledNums[0] + " 2:" + rolledNums[1] + " 3:" + rolledNums[2] + " 4:" + rolledNums[3]
				+ " 5:" + rolledNums[4] + " 6:" + rolledNums[5]);
	}

}

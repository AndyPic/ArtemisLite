package uk.ac.qub.artemislite;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnLauncherTest {

	// Test Data
	TurnLauncher turnLauncher;

	@BeforeEach
	void setUp() throws Exception {
		turnLauncher = new TurnLauncher();

	}

	@Test
	void testFindPlayerOrder() {

		turnLauncher.addPlayer();
		turnLauncher.addPlayer();
		turnLauncher.addPlayer();

		System.out.println("Original player order:");
		turnLauncher.displayPlayers();
		turnLauncher.findPlayerOrder();

		System.out.println("Rearanged player order:");
		turnLauncher.displayPlayers();

	}

	@Test
	void testRollDice() {

		int total = 0;
		String roll;

		for (int outer = 0; outer <= 1; outer++) {
			roll = turnLauncher.rollDice();

			roll = roll.replaceAll("[^\\d]+", " ").trim();

			String[] rollArr = roll.split(" ");

			for (int inner = 0; outer < rollArr.length; outer++) {

				if (inner == roll.length() - 1) {

					if (total != Integer.parseInt(rollArr[inner])) {
						assertTrue(false);
					}

				} else {
					total = Integer.parseInt(rollArr[inner]);
				}
			}

			System.out.println(roll);
		}
		
	}
	
	
	@Test 
	void testGetRollValue() {
		
		int expected = 7;
		
		String testRoll = "You have rolled a 1 and 6 for a total of : 7";
		
		int result = turnLauncher.getRollValue(testRoll);
		
		assertEquals(expected, result);
		
	}

}

package uk.ac.qub.artemislite;


import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnLauncherTest {

	// Test Data
	TurnLauncher turnLauncher;
	HashMap<Integer, Integer> diceMap;

	@BeforeEach
	void setUp() throws Exception {
		turnLauncher = new TurnLauncher();

		diceMap = new HashMap<Integer, Integer>();

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

		int count, roll;

		for (int loop = 0; loop <= 1000; loop++) {
			roll = turnLauncher.rollDice();
	        count = diceMap.getOrDefault(roll, 0);
	        diceMap.put(roll, count + 1);
		}
		
		System.out.println("Rolls:");
		for(int key: diceMap.keySet()) {
			System.out.println(key+" = "+diceMap.get(key));
		}

	}

}

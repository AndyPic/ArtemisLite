/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jordan Davis
 *
 */
class ResourcesComparatorTest {

	//Test Data
	Player p1,p2,p3;
	TurnLauncher turnLauncher;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		turnLauncher = new TurnLauncher();
		
		p1 = new Player("Jordan");
		p2 = new Player("Andrew");
		p3 = new Player("David");
		
		p1.setBalanceOfResources(199);
		p2.setBalanceOfResources(201);

		
		turnLauncher.players.add(p1);
		turnLauncher.players.add(p2);
		turnLauncher.players.add(p3);
		
	}

	@Test
	void test() {
		
		turnLauncher.displayPlayers();
		
		Collections.sort(turnLauncher.players, Collections.reverseOrder(new ResourcesComparator()));
		System.out.println();
		
		turnLauncher.displayPlayers();
		
		Collections.sort(turnLauncher.players, new ResourcesComparator());
		System.out.println();
		
		turnLauncher.displayPlayers();
		
	}

}

















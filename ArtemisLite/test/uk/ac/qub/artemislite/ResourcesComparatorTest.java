/**
 * 
 */
package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		p3.setBalanceOfResources(200);

		TurnLauncher.getPlayers().add(p1);
		TurnLauncher.getPlayers().add(p2);
		TurnLauncher.getPlayers().add(p3);
		
	}

	@Test
	void test() {
		
		turnLauncher.displayPlayers();
		
		Collections.sort(TurnLauncher.getPlayers(), Collections.reverseOrder(new ResourcesComparator()));
		
		System.out.println();
		turnLauncher.displayPlayers();
		
		assertEquals(p1, TurnLauncher.getPlayers().get(2));
		
	}

}

















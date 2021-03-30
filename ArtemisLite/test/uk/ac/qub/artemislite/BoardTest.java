package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

	// Test Data
	Board board;
	int expectedCostSquarei;
	Element temp;
	StandardElement sq1, sq2;
	Player p1, p2;

	@BeforeEach
	void setUp() throws Exception {

		board = new Board();
		expectedCostSquarei = ElementDetails.PP.getCost();
		
		p1 = new Player("Jordan");
		p2 = new Player("David");

		sq1 = (StandardElement)board.getElements().get(1);
		sq2 = (StandardElement)board.getElements().get(2);
		
		sq1.setOwnedBy(p2);
		sq2.setOwnedBy(p2);
		
	}

//	@Test
//	void testConstructor() {
//
//		board = new Board();
//
//		ArrayList<Element> elements = board.getElements();
//
//		for (Element element : elements) {
//			System.out.println(element);
//			System.out.println();
//		}
//
//		StandardElement s = (StandardElement) board.getElements().get(10);
//
//		assertEquals(expectedCostSquarei, s.getPurchaseCost());
//
//	}
	
	@Test
	void testSystemFullyOwned() {
		
		assertTrue(board.systemFullyOwned(sq1, p2));
		
	}

}

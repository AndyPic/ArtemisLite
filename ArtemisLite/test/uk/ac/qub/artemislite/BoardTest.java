package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

	// Test Data
	Board board;
	int expectedCostSquarei;

	@BeforeEach
	void setUp() throws Exception {

		expectedCostSquarei = ElementDetails.SQUAREi.getCost();

	}

	@Test
	void testConstructor() {

		board = new Board();

		ArrayList<Element> elements = board.getElements();

		for (Element element : elements) {
			System.out.println(element);
			System.out.println();
		}

		StandardElement s = (StandardElement) board.getElements().get(10);

		assertEquals(expectedCostSquarei, s.getPurchaseCost());

	}

}

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

		expectedCostSquarei = SquareDetails.SQUAREi.getCost();

	}

	@Test
	void testConstructor() {

		board = new Board();

		ArrayList<Square> squares = board.getSquares();

		for (Square square : squares) {
			System.out.println(square);
			System.out.println();
		}

		StandardSquare s = (StandardSquare) board.getSquares().get(10);

		assertEquals(expectedCostSquarei, s.getPurchaseCost());

	}

}

package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
	
	//Test Data
	Board board;

	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
	}
	
	@Test
	void testBuildGameBoard() {
		
		board.buildGameBoard();
		
		ArrayList<Square> squares = board.getSquares();

		for(Square square: squares) {
			System.out.println(square.toString());
			System.out.println();
		}
		
	}

}

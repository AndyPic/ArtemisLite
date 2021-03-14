package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StandardSquareTest {
	
	// test data 
	int purchaseCostValid, purchaseCostInvalid;
	int boardPositionValid, boardPositionInvalid;
	int minorDevCostValid, minorDevCostInvalid, majorDevCostValid, majorDevCostInvalid;
	int rentCostValid, rentCostInvalid;
	int minorDevLevelValid, minorDevLevelInvalid, majorDevLevelValid, majorDevLevelInvalid;
	String nameValid, nameInvalid;
	SystemType squareSystemValid, squareSystemInvalid;
	boolean isOwned;
	StandardSquare ssq1;
	
	
	
	@BeforeEach
	void setUp() throws Exception {
		purchaseCostValid = 100;
		purchaseCostInvalid = -1;
		
		boardPositionValid = 2;
		boardPositionInvalid = 13;
		
		minorDevCostValid = 200;
		minorDevCostInvalid = -1;
		majorDevCostValid = 400;
		majorDevCostInvalid = -1;
		
		rentCostValid = 10;
		rentCostInvalid = -1;
		
		minorDevLevelValid = 1;
		minorDevLevelInvalid = 4;
		majorDevLevelValid = 1;
		majorDevLevelInvalid = 2;
		
		nameValid = "SquareX";
		nameInvalid = "";
		squareSystemValid = SystemType.SYSTEM1;
		squareSystemInvalid = SystemType.SYSTEM3;
		
		ssq1 = new StandardSquare();
		
	}
	
	@Test
	void testConstructorValid() {
		StandardSquare ssq2 = new StandardSquare(boardPositionValid, nameValid, squareSystemValid, purchaseCostValid, minorDevCostValid, majorDevCostValid, rentCostValid);
		
		assertEquals(boardPositionValid, ssq2.getBoardPosition());
		assertEquals(nameValid, ssq2.getSquareName());
		assertEquals(squareSystemValid, ssq2.getSquareSystem());
		assertEquals(purchaseCostValid, ssq2.getPurchaseCost());
		assertEquals(minorDevCostValid, ssq2.getMinorDevCost());
		assertEquals(majorDevCostValid, ssq2.getMajorDevCost());
		assertEquals(rentCostValid, ssq2.getRentCost());
	}

	@Test
	void testStandardSquareIntStringElementTypeIntIntIntIntBoolean() {
		fail("Not yet implemented");
	}

	@Test
	void testIncreaseDev() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPurchaseCost() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMinorDevCost() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMajorDevCost() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentMinorDevLevel() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentMajorDevLevel() {
		fail("Not yet implemented");
	}

	@Test
	void testGetRentCost() {
		fail("Not yet implemented");
	}

	@Test
	void testIsOwned() {
		fail("Not yet implemented");
	}

	@Test
	void testGetOwnedBy() {
		fail("Not yet implemented");
	}

	@Test
	void testGetSystemID() {
		fail("Not yet implemented");
	}

}

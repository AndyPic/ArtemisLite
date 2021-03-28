package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StandardElementTest {

	// test data 
	int purchaseCostValid;
	int boardPositionValid, boardPositionInvalid;
	int minorDevCostValid, majorDevCostValid;
	int rentCostValid;
	int minorDevLevelValid, minorDevLevelInvalid, majorDevLevelValid, majorDevLevelInvalid;
	String nameValid, nameInvalid;
	boolean isOwned;
	SystemType squareSystemValid;
	Player p1, p2;
	
	StandardElement stdElement, stdElementSet;
	
	
	
	@BeforeEach
	void setUp() throws Exception {
		purchaseCostValid = 100;
		
		boardPositionValid = 2;
		boardPositionInvalid = 13;
		
		minorDevCostValid = 200;
		majorDevCostValid = 400;
		
		rentCostValid = 10;
		
		minorDevLevelValid = 1;
		minorDevLevelInvalid = 4;
		majorDevLevelValid = 1;
		majorDevLevelInvalid = 2;
		
		nameValid = "SquareX";
		nameInvalid = "";
		
		squareSystemValid = SystemType.ORION;
		
		p1 = new Player("Jordan");
		p2 = new Player("David");
		
		stdElement = new StandardElement();
		stdElementSet = new StandardElement(boardPositionValid, nameValid, squareSystemValid, purchaseCostValid, minorDevCostValid, majorDevCostValid, rentCostValid);
		
	}

	@Test
	void testConstructorValid() {
		StandardElement newElement = new StandardElement(boardPositionValid, nameValid, squareSystemValid, purchaseCostValid, minorDevCostValid, majorDevCostValid, rentCostValid);
		
		assertNotNull(newElement);
	
	}


	@Test
	void testGetPurchaseCost() {
		assertEquals(purchaseCostValid, stdElementSet.getPurchaseCost());
	}

	@Test
	void testGetMinorDevCost() {
		assertEquals(minorDevCostValid, stdElementSet.getMinorDevCost());
	}

	@Test
	void testGetMajorDevCost() {
		assertEquals(majorDevCostValid, stdElementSet.getMajorDevCost());
	}

	@Test
	void testGetSetCurrentMinorDevLevelValid() {
		stdElement.incrementCurrentMinorDevLevel();
		assertEquals(1, stdElement.getCurrentMinorDevLevel());
	}
	
	@Test
	void testGetSetCurrentMinorDevLevelException() {
		stdElement.incrementCurrentMinorDevLevel();
		stdElement.incrementCurrentMinorDevLevel();
		stdElement.incrementCurrentMinorDevLevel();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				stdElement.incrementCurrentMinorDevLevel();
		});
		
		assertEquals("Already max minor dev level", exception.getMessage());
	}

	@Test
	void testGetSetCurrentMajorDevLevelValid() {
		stdElement.incrementCurrentMajorDevLevel();
		assertEquals(1, stdElement.getCurrentMajorDevLevel());
	}
	
	@Test
	void testGetSetCurrentMajorDevLevelException() {
		stdElement.incrementCurrentMajorDevLevel();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			stdElement.incrementCurrentMajorDevLevel();
		});
		
		assertEquals("Already max major dev level", exception.getMessage());
	}

	@Test
	void testSetGetRentCost() {
		stdElement.setRentCost(rentCostValid);
		assertEquals(rentCostValid, stdElement.getRentCost());
	}

	@Test
	void testGetSetOwnedBy() {
		stdElement.setOwnedBy(p1);
		assertEquals(p1, stdElement.getOwnedBy());
	}

	@Test
	void testGetMIN_DEV_LEVEL() {
		assertEquals(0, stdElement.getMIN_DEV_LEVEL());
	}

	@Test
	void testGetMAX_MINOR_DEV() {
		assertEquals(3, stdElement.getMAX_MINOR_DEV());
	}

	@Test
	void testGetMAX_MAJOR_DEV() {
		assertEquals(1, stdElement.getMAX_MAJOR_DEV());
	}
	
	@Test
	void testIncreaseDevMinorlevel1() {
		stdElement.increaseDev();
		assertEquals(1, stdElement.getCurrentMinorDevLevel());
	}
	
	@Test
	void testIncreaseDevMinorlevel3() {
		stdElement.increaseDev();
		stdElement.increaseDev();
		stdElement.increaseDev();
		assertEquals(3, stdElement.getCurrentMinorDevLevel());
	}
	
	@Test
	void testIncreaseDevMajorlevel1() {
		stdElement.increaseDev();
		stdElement.increaseDev();
		stdElement.increaseDev();
		stdElement.increaseDev();
		assertEquals(1, stdElement.getCurrentMajorDevLevel());
		assertEquals(3, stdElement.getCurrentMinorDevLevel());
	}
	
	@Test
	void testIncreaseDevError() {
		stdElement.increaseDev();
		stdElement.increaseDev();
		stdElement.increaseDev();
		stdElement.increaseDev();
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			stdElement.increaseDev();
		});
		
		assertEquals("Invalid dev increase", exception.getMessage());
	}

	@Test
	void testIncreaseRent() {
		stdElementSet.incrementCurrentMinorDevLevel();
		stdElementSet.increaseRent();
		assertEquals(200, stdElementSet.getRentCost());
		
	}

	@Test
	void testIsMaxDevelopmentTrue() {
		stdElement.increaseDev();
		stdElement.increaseDev();
		stdElement.increaseDev();
		stdElement.increaseDev();
		assertTrue(stdElement.isMaxDevelopment());
	}
	
	@Test
	void testIsMaxDevelopmentFlase() {
		stdElement.increaseDev();
		stdElement.increaseDev();
		stdElement.increaseDev();
		assertFalse(stdElement.isMaxDevelopment());
	}
	
	@Test
	void testIsOwnedBy() {
		stdElement.setOwnedBy(p1);
		assertTrue(stdElement.isOwnedBy(p1));
	}

}

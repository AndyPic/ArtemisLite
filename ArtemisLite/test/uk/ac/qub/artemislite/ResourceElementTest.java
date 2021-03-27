package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResourceElementTest {
	
	//test data
	ResourceElement resourceElement;
	int shortGameValue, longGameValue;
	Player p1, p2;

	@BeforeEach
	void setUp() throws Exception {
		resourceElement = new ResourceElement();
		shortGameValue = 200;
		longGameValue = 100;
		p1 = new Player("Jordan");
		p2 = new Player("Andrew");
		
	}

	@Test
	void testResourceElementConstructor() {
		ResourceElement resourceElementConstructor = new ResourceElement(0, "Resource", SystemType.RESOURCE);
		assertNotNull(resourceElementConstructor);		
	}

	@Test
	void testGiveInvestment() {
		resourceElement.setResourceToAllocate(longGameValue);
		resourceElement.giveInvestment(p1);
		assertEquals(longGameValue, p1.getBalanceOfResources());
		
	}

	@Test
	void testGetSetResourceToAllocate() {
		resourceElement.setResourceToAllocate(shortGameValue);
		assertEquals(shortGameValue, resourceElement.getResourceToAllocate());
	}

}

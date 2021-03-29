package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
	
	//Test data
	Player p1;
	String name;
	int balance, pos;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new Player();
		name = "Jordan";
		balance = 150;
		pos = 2;
	}

	@Test
	void testPlayerConstructor() {
		Player playerTest = new Player("David");
		assertNotNull(playerTest);
	}

	@Test
	void testGetName() {
		p1.setName(name);
		assertEquals(name, p1.getName());
	}

	@Test
	void testGetBalanceOfResources() {
		p1.setBalanceOfResources(balance);
		assertEquals(balance, p1.getBalanceOfResources());
	}

	@Test
	void testGetCurrentPosition() {
		p1.setCurrentPosition(pos);
		assertEquals(pos, p1.getCurrentPosition());
	}

}

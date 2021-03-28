package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserInputTest {
	
	//Test data

	@BeforeEach
	void setUp() throws Exception {
		
	}


	@Test
	void testGetUserInputInt() {
		System.out.println("GetUserInputInt test - pass value = '5'");
		int testInt = UserInput.getUserInputInt();
		assertEquals(5, testInt);
	}

	@Test
	void testGetUserInputString() {
		System.out.println("GetUserInputInt test - pass value = 'test'");
		String testString = UserInput.getUserInputString();
		assertEquals("test", testString);
	}

}

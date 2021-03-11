package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author David Finlay Student Number: 40312100
 *
 */

class ModifyPlayerResourcesTest {

	// setup test variables
	Player player1, player2, player3, player4;
	int initialBalance, resourceValue1, resourceValue2, resourceValue3;
	ArrayList<Player> players = new ArrayList<Player>();
	String bankruptMessage;
	
	@BeforeEach
	void setUp() throws Exception {
		initialBalance = 100;
		resourceValue1 = 150;
		resourceValue2 = -70;
		resourceValue3 = -500;
		
		player1 = new Player();
		player2 = new Player();
		player3 = new Player();
		player4 = new Player();
		
		player1.setBalanceOfResources(initialBalance);
		player2.setBalanceOfResources(initialBalance);
		player3.setBalanceOfResources(initialBalance);
		player4.setBalanceOfResources(initialBalance);
		
		player1.setName("Player1Name");
		player3.setName("Player3Name");
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		
			
	}

	@Test
	void testModifyResourcesSinglePlayer() {
		
		// test with positive value ie adding resources
		ModifyPlayerResources.modifyResourcesSinglePlayer(player1, resourceValue1);
		assertEquals(initialBalance + resourceValue1, player1.getBalanceOfResources());
		
		// testing with negative value ie. subtracting resources
		ModifyPlayerResources.modifyResourcesSinglePlayer(player2, resourceValue2);
		assertEquals(initialBalance + resourceValue2, player2.getBalanceOfResources());
		
		// testing with resource value that will bankrupt the player
		BankruptException bankruptExc = assertThrows(BankruptException.class, ()->{
			ModifyPlayerResources.modifyResourcesSinglePlayer(player3, resourceValue3);
		});
		bankruptMessage = "Player: " + player3.getName() + " is bankrupt!";
		assertEquals(bankruptExc.getMessage(), bankruptMessage);
		
	}

	@Test
	void testModifyResourcesAllPlayers() {
		
		// testing with positive resource value
		ModifyPlayerResources.modifyResourcesAllPlayers(players, resourceValue1);
		assertEquals(initialBalance + resourceValue1, player1.getBalanceOfResources());
		assertEquals(initialBalance + resourceValue1, player2.getBalanceOfResources());
		assertEquals(initialBalance + resourceValue1, player3.getBalanceOfResources());
		assertEquals(initialBalance + resourceValue1, player4.getBalanceOfResources());
		
		// testing with negative resource value
		ModifyPlayerResources.modifyResourcesAllPlayers(players, resourceValue2);
		assertEquals(initialBalance + resourceValue1 + resourceValue2, player1.getBalanceOfResources());
		assertEquals(initialBalance + resourceValue1 + resourceValue2, player2.getBalanceOfResources());
		assertEquals(initialBalance + resourceValue1 + resourceValue2, player3.getBalanceOfResources());
		assertEquals(initialBalance + resourceValue1 + resourceValue2, player4.getBalanceOfResources());
		
		// testing where one players resources would be negative, ie. they are bankrupt - game over state.
		BankruptException bankruptExc = assertThrows(BankruptException.class, ()->{
			ModifyPlayerResources.modifyResourcesAllPlayers(players, resourceValue3);
		});
		bankruptMessage = "Player: " + player1.getName() + " is bankrupt!";
		assertEquals(bankruptExc.getMessage(), bankruptMessage);
		
	}

}

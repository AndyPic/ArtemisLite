package uk.ac.qub.artemislite;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author David Finlay Student Number: 40312100
 *
 */
class GameHistoryStorageTest {

	// setup test variables

	String playerNameValid1, playerNameValid2, playerNameValid3, playerNameValid4;
	int boardLandingPosValid1, boardLandingPosValid2, boardLandingPosValid3, boardLandingPosValid4;
	GameHistoryAction gameHistoryActionValid1, gameHistoryActionValid2, gameHistoryActionValid3,
			gameHistoryActionValid4;

	// console output variable setup
	private PrintStream sysOut;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@BeforeEach
	void setUp() throws Exception {

		playerNameValid1 = "player1";
		boardLandingPosValid1 = 3;
		gameHistoryActionValid1 = GameHistoryAction.DEVELOP_PORTFOLIO;

		playerNameValid2 = "player2";
		boardLandingPosValid2 = 8;
		gameHistoryActionValid2 = GameHistoryAction.DEVELOP_THIS_ELEMENT;

		playerNameValid3 = "player3";
		boardLandingPosValid3 = 4;
		gameHistoryActionValid3 = GameHistoryAction.FORFEIT_RESOURCES;

		playerNameValid4 = "player4";
		boardLandingPosValid4 = 7;
		gameHistoryActionValid4 = GameHistoryAction.PURCHASE_THIS_ELEMENT;

		sysOut = System.out;
		System.setOut(new PrintStream(outContent));
	}

	@Test
	void testAddAndDisplayMoveHistory() {
		GameHistoryStorage.addMoveToHistory(playerNameValid1, boardLandingPosValid1, gameHistoryActionValid1);
		GameHistoryStorage.addMoveToHistory(playerNameValid2, boardLandingPosValid2, gameHistoryActionValid2);
		GameHistoryStorage.addMoveToHistory(playerNameValid3, boardLandingPosValid3, gameHistoryActionValid3);
		GameHistoryStorage.addMoveToHistory(playerNameValid4, boardLandingPosValid4, gameHistoryActionValid4);
		
		 GameHistoryStorage.displayMoveHistory();
		 String expectedResult = "Game Move History..."
				 +"\n1.  Location: Solid Rocket Boosters            player1 advanced research on this project     Date: January, 2020.\n\t"
				 +"\n2.  Location: Mobile Launchers                 player2 advanced research on this project     Date: January, 2020.\n\t"
				 +"\n3.  Location: RS-25 Engines                    player3 forfeit staff-hours                   Date: January, 2020.\n" 
				 +"\n4.  Location: Crawlers                         player4 took on this project                  Date: January, 2020.";
		 System.out.println("Expected :\n"+expectedResult);
		 System.out.println("\n\n Actual :\n"+outContent.toString());

	}

	@After
	public void revertStreams() {
		System.setOut(sysOut);
	}

}

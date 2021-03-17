package uk.ac.qub.artemislite;

import static org.junit.jupiter.api.Assertions.*;

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
		GameHistoryStorage gameHistoryStorage = new GameHistoryStorage();
		gameHistoryStorage.addMoveToHistory(playerNameValid1, boardLandingPosValid1, gameHistoryActionValid1);
		gameHistoryStorage.addMoveToHistory(playerNameValid2, boardLandingPosValid2, gameHistoryActionValid2);
		gameHistoryStorage.addMoveToHistory(playerNameValid3, boardLandingPosValid3, gameHistoryActionValid3);
		gameHistoryStorage.addMoveToHistory(playerNameValid4, boardLandingPosValid4, gameHistoryActionValid4);
		
		 gameHistoryStorage.displayMoveHistory();
		 String expectedResult = "Game Move History...\n"
		 + "Action number   1. Location: element  3. Player player1 developed portfolio\n"
		 + "Action number   2. Location: element  8. Player player2 developed this element\n"
		 + "Action number   3. Location: element  4. Player player3 forfeit resources\n"
		 + "Action number   4. Location: element  7. Player player4 purchased this element\n";
		 assertEquals(expectedResult, outContent.toString());
	}

	@After
	public void revertStreams() {
		System.setOut(sysOut);
	}

}

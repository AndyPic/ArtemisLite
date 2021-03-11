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
	GameHistoryAction gameHistoryActionValid1, gameHistoryActionValid2, gameHistoryActionValid3, gameHistoryActionValid4;
	int resourceValueValid1, resourceValueValid2, resourceValueValid3, resourceValueValid4;

	// console output variable setup
    private PrintStream sysOut;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	
	@BeforeEach
	void setUp() throws Exception {
			
		playerNameValid1 = "player1";
		boardLandingPosValid1 = 3;
		gameHistoryActionValid1 = GameHistoryAction.DEVELOP_PORTFOLIO;
		resourceValueValid1 = 120;
		
		playerNameValid2 = "player2";
		boardLandingPosValid2 = 8;
		gameHistoryActionValid2 = GameHistoryAction.DEVELOP_THIS_ELEMENT;
		resourceValueValid2 = 110;
		
		playerNameValid3 = "player3";
		boardLandingPosValid3 = 4;
		gameHistoryActionValid3 = GameHistoryAction.FORFEIT_RESOURCES;
		resourceValueValid3 = 50;
		
		playerNameValid4 = "player4";
		boardLandingPosValid4 = 7;
		gameHistoryActionValid4 = GameHistoryAction.PURCHASE_THIS_ELEMENT;
		resourceValueValid4 = 150;
		
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
	}


	@Test
	void testAddAndDisplayMoveHistory() {
		GameHistoryStorage.addMoveToHistory(playerNameValid1, boardLandingPosValid1, gameHistoryActionValid1, resourceValueValid1);
		GameHistoryStorage.addMoveToHistory(playerNameValid2, boardLandingPosValid2, gameHistoryActionValid2, resourceValueValid2);
		GameHistoryStorage.addMoveToHistory(playerNameValid3, boardLandingPosValid3, gameHistoryActionValid3, resourceValueValid3);
		GameHistoryStorage.addMoveToHistory(playerNameValid4, boardLandingPosValid4, gameHistoryActionValid4, resourceValueValid4);
		GameHistoryStorage.displayMoveHistory();
		String expectedResult = "On move: 1 Player: player1 landed on element: 3, choosing to: develop portfolio costing: 120 resources\n"
				+ "On move: 2 Player: player2 landed on element: 8, choosing to: develop this element costing: 110 resources\n"
				+ "On move: 3 Player: player3 landed on element: 4, choosing to: forfeit resources costing: 50 resources\n"
				+ "On move: 4 Player: player4 landed on element: 7, choosing to: purchase this element costing: 150 resources\n";
		assertEquals(expectedResult, outContent.toString());
	}
	
	
    @After
    public void revertStreams() {
        System.setOut(sysOut);
    }

}

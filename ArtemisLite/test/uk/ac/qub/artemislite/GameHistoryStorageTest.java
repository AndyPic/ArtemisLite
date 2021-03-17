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
	int diceRollValid1, diceRollValid2, diceRollValid3, diceRollValid4;

	// console output variable setup
    private PrintStream sysOut;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	
	@BeforeEach
	void setUp() throws Exception {
			
		playerNameValid1 = "player1";
		boardLandingPosValid1 = 3;
		gameHistoryActionValid1 = GameHistoryAction.DEVELOP_PORTFOLIO;
		diceRollValid1=5;
		
		playerNameValid2 = "player2";
		boardLandingPosValid2 = 8;
		gameHistoryActionValid2 = GameHistoryAction.DEVELOP_THIS_ELEMENT;
		diceRollValid2=7;
		
		playerNameValid3 = "player3";
		boardLandingPosValid3 = 4;
		gameHistoryActionValid3 = GameHistoryAction.FORFEIT_RESOURCES;
		diceRollValid3=1;
		
		playerNameValid4 = "player4";
		boardLandingPosValid4 = 7;
		gameHistoryActionValid4 = GameHistoryAction.PURCHASE_THIS_ELEMENT;
		diceRollValid4=12;
		
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
	}


	@Test
	void testAddAndDisplayMoveHistory() {
		GameHistoryStorage.addMoveToHistory(playerNameValid1, boardLandingPosValid1, gameHistoryActionValid1, diceRollValid1);
		GameHistoryStorage.addMoveToHistory(playerNameValid2, boardLandingPosValid2, gameHistoryActionValid2, diceRollValid2);
		GameHistoryStorage.addMoveToHistory(playerNameValid3, boardLandingPosValid3, gameHistoryActionValid3, diceRollValid3);
		GameHistoryStorage.addMoveToHistory(playerNameValid4, boardLandingPosValid4, gameHistoryActionValid4, diceRollValid4);
		GameHistoryStorage.displayMoveHistory();
		String expectedResult = "On move 1, player1 rolled 5, landing on element 3 and developed portfolio\n"
				+ "On move 2, player2 rolled 7, landing on element 8 and developed this element\n"
				+ "On move 3, player3 rolled 1, landing on element 4 and forfeit resources\n"
				+ "On move 4, player4 rolled 12, landing on element 7 and purchased this element\n";
		assertEquals(expectedResult, outContent.toString());
	}
	
	
    @After
    public void revertStreams() {
        System.setOut(sysOut);
    }

}

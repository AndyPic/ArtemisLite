package uk.ac.qub.artemislite;

/**
 * Holds a history of one individual move showing what happened in the game /**
 * 
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class GameHistoryItem {

	// keeps a global record of the move number as a counter
	public static int GAME_COUNTER = 0; // Not sure it's customary to use this naming convention for non-constants?

	// non-static instance counter for the move
	int gameCounter;

	private String playerName;

	/**
	 * the new post-dice roll position
	 */
	private int boardLandingPosition;

	/**
	 * the action taken on this square
	 */
	private GameHistoryAction gameHistoryAction;

	/**
	 * total dice roll
	 */
	private int diceRollTotal;

	/**
	 * default constructor
	 */
	public GameHistoryItem() {
		// increment the game counter on each instantiation of a GameHistoryItem
		++GAME_COUNTER;

		// create the non-static counter reference for this history item object
		gameCounter = GameHistoryItem.GAME_COUNTER;

	}

	/**
	 * constructor with fields
	 * 
	 * @param gameCounter          is the counter for the game move
	 * @param playerName
	 * @param boardLandingPosition
	 * @param gameHistoryAction
	 * 
	 */
	public GameHistoryItem(String playerName, int boardLandingPosition, GameHistoryAction gameHistoryAction, int diceRollTotal) {
		this();
		this.playerName = playerName;
		this.boardLandingPosition = boardLandingPosition;
		this.gameHistoryAction = gameHistoryAction;
		this.diceRollTotal = diceRollTotal;
	}

	public void displayAll() {
		String historyOutput;

		historyOutput = "On move " + this.gameCounter + ", " + this.playerName + " rolled " + this.diceRollTotal
				+ ", landing on element " + this.boardLandingPosition + " and "
				+ this.gameHistoryAction.label + "\n";

		System.out.print(historyOutput);
	}

	public int getDiceRollTotal() {
		return diceRollTotal;
	}

	public void setDiceRollTotal(int diceRollTotal) {
		this.diceRollTotal = diceRollTotal;
	}

}

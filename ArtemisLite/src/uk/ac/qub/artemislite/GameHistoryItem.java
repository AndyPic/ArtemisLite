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
	public static int GAME_COUNTER = 0;

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
	 * the value of the above gameHistoryAction transaction
	 */
	private int resourceValue;
	
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
	 * @param gameCounter is the counter for the game move
	 * @param playerName
	 * @param boardLandingPosition
	 * @param gameHistoryAction
	 * @param resourceValue
	 */
	public GameHistoryItem(String playerName, int boardLandingPosition, GameHistoryAction gameHistoryAction,
			int resourceValue, int diceRollTotal ) {
		this();
		this.playerName = playerName;
		this.boardLandingPosition = boardLandingPosition;
		this.gameHistoryAction = gameHistoryAction;
		this.resourceValue = resourceValue;
		this.diceRollTotal = diceRollTotal;
	}

	public void displayAll() {
		String historyOutput;
		
		historyOutput = "On move: " + this.gameCounter + " Player: " + this.playerName + " rolled " + this.diceRollTotal 
				+ " and landed on element: " + this.boardLandingPosition + ", choosing to: " + this.gameHistoryAction.label + " costing: "
				+ this.resourceValue + " resources\n";
		
		System.out.print(historyOutput);
	}

	public int getDiceRollTotal() {
		return diceRollTotal;
	}

	public void setDiceRollTotal(int diceRollTotal) {
		this.diceRollTotal = diceRollTotal;
	}

}

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
	 * the action taken on this element
	 */
	private GameHistoryAction gameHistoryAction;

	/**
	 * date action occured;
	 */
	private String actionDate;

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
	public GameHistoryItem(String playerName, int boardLandingPosition, GameHistoryAction gameHistoryAction) {
		// explicitly call default constructor to ensure game counter is incremented
		this();
		this.playerName = playerName;
		this.boardLandingPosition = boardLandingPosition;
		this.gameHistoryAction = gameHistoryAction;
		this.actionDate = ArtemisCalendar.getDate();
	}

	/**
	 * Converts a board landing position (as an <code>ing</code>) into the
	 * equivalent name
	 * 
	 * @param boardLandingPosition is the position on the board
	 * @return the name from the ElementDetails class
	 */
	public String getElementNameFromPosition(int boardLandingPosition) {
		String elementName = "";
		// finds the details from the ENUM
		for (ElementDetails elementDetails : ElementDetails.values()) {
			if (boardLandingPosition == elementDetails.getElementPos()) {
				elementName = elementDetails.getName();
			}
		}
		return elementName;
	}

	/**
	 * Displays a standardised output for a game history item
	 */
	public void displayAll() {
		System.out.printf("%-3s Location: %-32s %s %-37s %-25s\n", this.gameCounter + ".",
				this.getElementNameFromPosition(this.boardLandingPosition), this.playerName,
				this.gameHistoryAction.label, this.actionDate);
	}

}

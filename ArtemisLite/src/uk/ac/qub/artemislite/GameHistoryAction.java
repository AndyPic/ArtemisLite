package uk.ac.qub.artemislite;

/**
 * Denotes the actions that can be performed when landing on a square and that appear in the game history
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public enum GameHistoryAction {

	GAIN_RESOURCES("gained 200 resources"),				// landing on the resources square
	PURCHASE_THIS_ELEMENT("purchased this element"), 	// when landing on an unowned or auctioned square
	DEVELOP_THIS_ELEMENT("developed this element"),		// develop this element 
	DEVELOP_PORTFOLIO("developed portfolio"), 			// develop any owned element other than the current position
	FORFEIT_RESOURCES("forfeit resources"), 			// when landing on a square owned by another player
	NO_ACTION("did nothing"),							// when unable to purchase a square
	QUIT("quit");									
	
	// note, claim resources is not an value in this enum because
	// as current player, you will not claim resources on this square
	// instead it will be another player that will FORFEIT resources TO you
	
	/**
	 * declaring the String label for console display
	 */
	public final String label;
	
	
	/**
	 * constructor with the String label parameter
	 * @param label - the String name of the action
	 */
	private GameHistoryAction(String label) {
		this.label = label;
	}
}

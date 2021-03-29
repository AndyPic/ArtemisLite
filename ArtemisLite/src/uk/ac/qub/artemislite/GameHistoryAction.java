package uk.ac.qub.artemislite;

/**
 * Denotes the actions that can be performed when landing on a element and that appear in the game history
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public enum GameHistoryAction {

	DID_NOT_INVEST("decided not to invest"),								// when refusing the opportunity to take on an element
	PASSED_RESOURCES_ELEMENT("acquired resources"),							// when landing on the resources element
	PURCHASE_THIS_ELEMENT("purchased this element"), 						// when landing on an unowned element
	PURCHASE_THIS_ELEMENT_AT_AUCTION("purchased this element at auction"), 	// when purchasing element at auction
	DEVELOP_THIS_ELEMENT("developed this element"),							// develop this element 
	DEVELOP_PORTFOLIO("developed portfolio"), 								// develop any owned element other than the current position
	FORFEIT_RESOURCES("forfeit resources"), 								// when landing on a element owned by another player
	NO_ACTION("took no action"),											// when unable to purchase a element
	QUIT("quit, causing the game to end");									
	
	// note, claim resources is not an value in this enum because
	// as current player, you will not claim resources on this element
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

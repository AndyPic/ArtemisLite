package uk.ac.qub.artemislite;

/**
 * Denotes the actions that can be performed when landing on a element and that appear in the game history
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public enum GameHistoryAction {

	DID_NOT_INVEST("decided not to take this project on"),							// when refusing the opportunity to take on an element
	PASSED_RESOURCES_ELEMENT("recruited new engineers"),							// when landing on the resources element
	PURCHASE_THIS_ELEMENT("took on this project"), 									// when landing on an unowned element
	PURCHASE_THIS_ELEMENT_AT_AUCTION("took on this project from auction"), 			// when purchasing element at auction
	DEVELOP_THIS_ELEMENT("advanced research on this project"),						// develop this element 
	DEVELOP_PORTFOLIO("advanced research on this project"), 						// develop any owned element other than the current position
	FORFEIT_RESOURCES("forfeit staff-hours"), 										// when landing on a element owned by another player
	NO_ACTION("took no action"),													// when unable to purchase an element
	QUIT("quit, causing the game to end"),
	STARTED_RESEARCH_ON_SYSTEM("has taken on all projects in a system"),			// when acquire every element in a system
	COMPLETED_A_SYSTEM("compelted constuction on a system!");									
	
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

package uk.ac.qub.artemislite;

/**
 * Possible Menu Options
 * @author Jordan Davis
 *
 */
public enum MenuOption {

	VIEW_ALL_ELEMENTS ("View all elements"),
	VIEW_PLAYER_ELEMENTS ("View elements you own"),
	GET_ELEMENT_DETAILS ("Get current element details"),
	INCREASE_DEVELOPMENT ("Increase element development"),
	AUCTION_ELEMENT ("Auction one of your elements"),
	END_TURN ("End your turn"),
	END_GAME ("End the game");
	
	private final String menuOption;

	/**
	 * 
	 * @param menuOption
	 */
	private MenuOption(String menuOption) {
		this.menuOption = menuOption;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMenuOption() {
		return this.menuOption;
	}
	
}

package uk.ac.qub.artemislite;

/**
 * Possible Menu Options
 * @author Jordan Davis
 *
 */
public enum MenuOption {

	VIEW_ALL_ELEMENTS ("View all elements", true),
	VIEW_PLAYER_ELEMENTS ("View elements you own", true),
	GET_SQUARE_DETAILS ("Get current element details", true),
	INCREASE_DEVELOPMENT ("Increase element development", false),
	END_TURN ("End your turn", true),
	END_GAME ("End the game", true);
	
	private final String menuOption;
	private final boolean defaultState;
	
	/**
	 * 
	 * @param menuOption
	 */
	private MenuOption(String menuOption, boolean defaultState) {
		this.menuOption = menuOption;
		this.defaultState = defaultState;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMenuOption() {
		return this.menuOption;
	}
	
	public boolean getDefaultState() {
		return this.defaultState;
	}
	
}

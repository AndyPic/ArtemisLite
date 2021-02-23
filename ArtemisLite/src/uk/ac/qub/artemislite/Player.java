/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * @author Jordan Davis
 *
 */
public class Player {

	/**
	 * Player Name 
	 */
	private String name;
	
	/**
	 * 
	 */
	private int balanceOfResources;
	
	/**
	 * player position on board, defaults to 0
	 */
	private int currentPosition = 0;
	
	/**
	 * Default Player constructor
	 */
	public Player() {
		
	}
	
	/**
	 * Player constructor with args
	 * @param name
	 * @param isCurrentPlayer
	 */
	public Player(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name, the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return the balance of resources
	 */
	public int getBalanceOfResources() {
		return balanceOfResources;
	}

	/**
	 * 
	 * @param balanceOfResources, the balance of resources to set
	 */
	public void setBalanceOfResources(int balanceOfResources) {
		this.balanceOfResources = balanceOfResources;
	}

	/**
	 * @return the currentPosition
	 */
	public int getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @param currentPosition the currentPosition to set
	 */
	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	
}

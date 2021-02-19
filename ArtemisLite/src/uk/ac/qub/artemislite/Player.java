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
	private int balanceOfResources;
	
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
	
	
}

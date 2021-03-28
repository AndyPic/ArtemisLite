/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class Player {

	// Instance Vars

	private String name;
	private int balanceOfResources; // Set starting resources
	private int currentPosition = 0; // Set starting position

	// Constructors

	/**
	 * Default Player constructor
	 */
	public Player() {

	}

	/**
	 * Player constructor with args
	 * 
	 * @param name
	 * @param isCurrentPlayer
	 */
	public Player(String name) {
		this.name = name;
	}

	// Methods

	// TODO: what is this metod used for? can it be removed?
//	/**
//	 * Method to check if the player owns any elements
//	 * 
//	 * @param board
//	 * @param player
//	 * @return
//	 */
//	public boolean isOwner(Board board) {
//
//		for (int loop = 0; loop < board.getElements().size(); loop++) {
//			if (board.getElements().get(loop) instanceof StandardElement) {
//				StandardElement stSq = (StandardElement) board.getElements().get(loop);
//				if (stSq.getOwnedBy() == this) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}

	/**
	 * Method that returns details about the players currently occupied element
	 */
	public void getCurrentPositionDetails(Board board) {
		Element element = board.getElements().get(currentPosition);
		
		if (element instanceof StandardElement) {
			StandardElement ssq = (StandardElement) element;
			System.out.println(ssq.toString());
		} else if (element instanceof ResourceElement) {
			ResourceElement rsq = (ResourceElement) element;
			System.out.println(rsq.toString());
		} else {
			System.out.println(element.toString());
		}
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
		this.name = name.trim();
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

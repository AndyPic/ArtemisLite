/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * @author Jordan Davis
 *
 */
public class ResourceSquare extends Square {

	/**
	 * 
	 */
	private final int RESOURCES_TO_ALLOCATE = 200;

	/**
	 * default constructor
	 */
	public ResourceSquare() {

	}

	/**
	 * Constructor with args
	 * @param boardPosition
	 * @param squareName
	 * @param resourcesToAllocate
	 */
	public ResourceSquare(int boardPosition, String squareName, ElementType squareElement) {
		super(boardPosition, squareName, squareElement);
	}

	/**
	 * Increase player resources
	 * @param player
	 */
	public void giveInvestment(Player player) {
		
		int currentResource;
		currentResource = player.getBalanceOfResources();

		player.setBalanceOfResources(currentResource + this.RESOURCES_TO_ALLOCATE);

	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "ResourceSquare [resourcesToAllocate=" + this.RESOURCES_TO_ALLOCATE + "]";
	}

}

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
public class ResourceSquare extends Square {

	// Constants

	private final int RESOURCES_TO_ALLOCATE = 200;

	// Constructors

	public ResourceSquare() {

	}

	/**
	 * Constructor with args
	 * 
	 * @param boardPosition
	 * @param squareName
	 * @param resourcesToAllocate
	 */
	public ResourceSquare(int boardPosition, String squareName, SystemType squareSystem) {
		super(boardPosition, squareName, squareSystem);
	}

	// Methods

	/**
	 * Increase player resources
	 * 
	 * @param player
	 */
	public void giveInvestment(Player player) {

		player.setBalanceOfResources(player.getBalanceOfResources() + this.RESOURCES_TO_ALLOCATE);

	}

	/**
	 * Formatted toString
	 */
	@Override
	public String toString() {
		return super.toString() + String.format("%15s\t+%d", "Resources:", RESOURCES_TO_ALLOCATE);
	}

}

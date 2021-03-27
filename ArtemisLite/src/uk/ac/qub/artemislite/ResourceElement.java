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
public class ResourceElement extends Element {

	// Constants

	private final int RESOURCES_TO_ALLOCATE = 200;

	// Constructors

	public ResourceElement() {

	}

	/**
	 * Constructor with args
	 * 
	 * @param boardPosition
	 * @param elementName
	 * @param resourcesToAllocate
	 */
	public ResourceElement(int boardPosition, String elementName, SystemType elementSystem) {
		super(boardPosition, elementName, elementSystem);
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
		return super.toString() + String.format("%15s\t+%d", "Hours:", RESOURCES_TO_ALLOCATE);
	}

}

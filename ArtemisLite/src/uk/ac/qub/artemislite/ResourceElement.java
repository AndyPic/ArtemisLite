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

	private int resourceToAllocate;

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

		player.setBalanceOfResources(player.getBalanceOfResources() + this.resourceToAllocate);
		GameHistoryStorage.addMoveToHistory(player.getName(), getBoardPosition(), GameHistoryAction.PASSED_RESOURCES_ELEMENT);
		System.out.println(
				"\nAfter stopping by the recruitment office you are able to hire more talented engineers (+"
						+ getElementName() + " staff-hours)\n");
	}

	/**
	 * Formatted toString
	 */
	@Override
	public String toString() {
		return super.toString() + String.format("%15s\t+%d", "Hours:", resourceToAllocate);
	}

	/**
	 * @return the resourceToAllocate
	 */
	public int getResourceToAllocate() {
		return resourceToAllocate;
	}

	/**
	 * @param resourceToAllocate the resourceToAllocate to set
	 */
	public void setResourceToAllocate(int resourceToAllocate) {
		this.resourceToAllocate = resourceToAllocate;
	}

	

}

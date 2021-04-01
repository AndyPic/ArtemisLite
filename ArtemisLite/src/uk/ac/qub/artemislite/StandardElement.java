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
public class StandardElement extends Element {

	// Constants

	private final int MIN_DEV_LEVEL = 0;
	private final int MAX_MINOR_DEV = 3;
	private final int MAX_MAJOR_DEV = 1;

	// Instance Vars

	private int purchaseCost, minorDevCost, majorDevCost, currentMinorDevLevel, currentMajorDevLevel, rentCost;
	private Player ownedBy;

	// Constructors

	/**
	 * Default constructor
	 */
	public StandardElement() {

	}

	/**
	 * Constructor with args
	 * 
	 * @param boardPosition
	 * @param elementName
	 * @param purchaseCost
	 * @param minorDevCost
	 * @param majorDevCost
	 * @param maxMajorDev
	 * @param maxMinorDev
	 * @param rentCost
	 */
	public StandardElement(int boardPosition, String elementName, SystemType elementSystem, int purchaseCost,
			int minorDevCost, int majorDevCost, int rentCost) {
		super(boardPosition, elementName, elementSystem);
		this.purchaseCost = purchaseCost;
		this.minorDevCost = minorDevCost;
		this.majorDevCost = majorDevCost;
		this.currentMinorDevLevel = MIN_DEV_LEVEL;
		this.currentMajorDevLevel = MIN_DEV_LEVEL;
		this.rentCost = rentCost;
	}

	// Methods

	/**
	 * Formatted toString to return dynamic info based on current game state
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		String strFormat = "%-30s: %s\n";
		String intFormat = "%-30s: %d\n";

		string.append(super.toString());

		if (ownedBy != null) {
			string.append(String.format(strFormat, "Element Owner", ownedBy.getName()));
			string.append(String.format(intFormat, "Current Research Stage", currentMinorDevLevel));
		} else {
			string.append(String.format(intFormat, "Initial research hours", purchaseCost));
		}

		string.append(String.format(intFormat, "Hours per reseach stage", minorDevCost));

		if (ownedBy != null) {
			string.append(String.format(intFormat, "Current construction stage", currentMajorDevLevel));
		}
		string.append(String.format(intFormat, "Hours per construction stage", majorDevCost));
		string.append(String.format(intFormat, "Hours per help request", majorDevCost));

		return string.toString();
	}

	/**
	 * Increases dev level of element
	 * 
	 * @throws Exception
	 */
	public void increaseDev() {

		if (currentMinorDevLevel == MAX_MINOR_DEV-1) {
			incrementCurrentMinorDevLevel();
			System.out.println("You have completed the last research stage (" + (currentMinorDevLevel)
					+ "). It's now time to start the construction!");
		} else if (currentMinorDevLevel < MAX_MINOR_DEV) {
			incrementCurrentMinorDevLevel();
			System.out.println("You have complete research stage " + (currentMinorDevLevel - 1)
					+ ". It's now time to begin stage " + currentMinorDevLevel);
		} else if (currentMajorDevLevel < MAX_MAJOR_DEV) {
			incrementCurrentMajorDevLevel();
			System.out
					.println("This element has just completed construction, we are 1 step closer to the project Artemis Launch");
		} else if (currentMajorDevLevel == MAX_MAJOR_DEV) {
			System.out.println("\nThis element is already has already completed research and development!\n");
		}

	}

	/**
	 * increases rent based on current dev level
	 * 
	 * @throws NullPointerException
	 */
	public void increaseRent() throws NullPointerException {

		int totalDevLevel = currentMinorDevLevel + currentMajorDevLevel;

		// finds the details from the ENUM
		ElementDetails currentElementDetails = null;
		for (ElementDetails elementDetails : ElementDetails.values()) {
			if (getElementName().equals(elementDetails.getName())) {
				currentElementDetails = elementDetails;
				break;
			}
		}

		// Confirms that the ENUM was found
		if (currentElementDetails == null) {
			throw new NullPointerException("ENUM details not found");
		}

		// increases rent cost
		switch (totalDevLevel) {
		case 1:
			this.setRentCost(currentElementDetails.getRentMinor1());
			break;
		case 2:
			this.setRentCost(currentElementDetails.getRentMinor2());
			break;
		case 3:
			this.setRentCost(currentElementDetails.getRentMinor3());
			break;
		case 4:
			this.setRentCost(currentElementDetails.getRentMajor1());
			break;
		}

	}

	/**
	 * checks if element has reached max development
	 * 
	 * @return true if development is maxed
	 */
	public boolean isMaxDevelopment() {
		boolean isMax = false;

		if (MAX_MAJOR_DEV == currentMajorDevLevel) {
			isMax = true;
		}

		return isMax;

	}

	/**
	 * checks if element is owned by specified player
	 * 
	 * @param player to check
	 * @return true if element is owned by player
	 */
	public boolean isOwnedBy(Player player) {
		boolean isOwnedByPlayer = false;

		if (ownedBy != null && ownedBy.equals(player)) {
			isOwnedByPlayer = true;
		}

		return isOwnedByPlayer;

	}

	/**
	 * @return the purchaseCost
	 */
	public int getPurchaseCost() {
		return purchaseCost;
	}

	/**
	 * @return the minorDevCost
	 */
	public int getMinorDevCost() {
		return minorDevCost;
	}

	/**
	 * @return the majorDevCost
	 */
	public int getMajorDevCost() {
		return majorDevCost;
	}

	/**
	 * @return the currentMinorDevLevel
	 */
	public int getCurrentMinorDevLevel() {
		return currentMinorDevLevel;
	}

	/**
	 * increase minor dev level by 1
	 * 
	 * @param currentMinorDevLevel the currentMinorDevLevel to set
	 */
	public void incrementCurrentMinorDevLevel() throws IllegalArgumentException {
		if (currentMinorDevLevel < MAX_MINOR_DEV) {
			currentMinorDevLevel++;
		} else {
			throw new IllegalArgumentException("Already max minor dev level");
		}
	}

	/**
	 * @return the currentMajorDevLevel
	 */
	public int getCurrentMajorDevLevel() {
		return currentMajorDevLevel;
	}

	/**
	 * increase minor dev level by 1
	 * 
	 * @param currentMajorDevLevel the currentMajorDevLevel to set
	 */
	public void incrementCurrentMajorDevLevel() throws IllegalArgumentException {
		if (currentMajorDevLevel < MAX_MAJOR_DEV) {
			currentMajorDevLevel++;
		} else {
			throw new IllegalArgumentException("Already max major dev level");
		}
	}

	/**
	 * @return the rentCost
	 */
	public int getRentCost() {
		return rentCost;
	}

	/**
	 * @param rentCost the rentCost to set
	 */
	public void setRentCost(int rentCost) {
		this.rentCost = rentCost;
	}

	/**
	 * @return the ownedBy
	 */
	public Player getOwnedBy() {
		return ownedBy;
	}

	/**
	 * @param ownedBy the ownedBy to set
	 */
	public void setOwnedBy(Player ownedBy) {
		this.ownedBy = ownedBy;
	}

	/**
	 * @return the mIN_DEV_LEVEL
	 */
	public int getMIN_DEV_LEVEL() {
		return MIN_DEV_LEVEL;
	}

	/**
	 * @return the mAX_MINOR_DEV
	 */
	public int getMAX_MINOR_DEV() {
		return MAX_MINOR_DEV;
	}

	/**
	 * @return the mAX_MAJOR_DEV
	 */
	public int getMAX_MAJOR_DEV() {
		return MAX_MAJOR_DEV;
	}

}

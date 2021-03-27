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
		if (ownedBy == null)
			return super.toString() + String.format("%15s\t%d\nHours for research milestone:\t%d\nHours for construction:\t%d\n%15s\t%d\n",
					"Hours requred to begin research:", purchaseCost, minorDevCost, majorDevCost, "Hours for help request:", rentCost);

		return super.toString() + String.format(
				"%15s\t%s\nCurrent research stage:\t%d\nHours for research milestone:\t%d\nCurrent construction level:\t%d\nHours for construction:\t%d\n%15s\t%d\n",
				"Research started by:", ownedBy.getName(), currentMinorDevLevel, minorDevCost, currentMajorDevLevel, majorDevCost,
				"Hours for help request:", rentCost);

	}

	/**
	 * Increases dev level of element
	 * 
	 * @throws Exception
	 */
	public void increaseDev() {

		if (currentMinorDevLevel < MAX_MINOR_DEV) {

			currentMinorDevLevel++;
			System.out.println(
					"You have complete research stage " + (currentMinorDevLevel - 1) + ". It's now time to begin stage " + currentMinorDevLevel);

		} else if (currentMajorDevLevel < MAX_MAJOR_DEV) {

			currentMajorDevLevel++;
			System.out.println("You have completed all research on this element! its now time to start the construction");

		} else if(currentMajorDevLevel == MAX_MAJOR_DEV) {
			
			System.out.println("There is nothing left for you to do on this element, construction is already underway!");
			
		} else {

			System.out.println("Invalid dev increase");

		}

		increaseRent();

	}

	/**
	 * increases rent based on current dev level
	 * 
	 * @throws NullPointerException
	 */
	public void increaseRent() throws NullPointerException {

		int totalDevLevel = this.currentMinorDevLevel + this.currentMajorDevLevel;

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
	 * displays current details to screen
	 */
	public void displayDetails() {
		System.out.println("Cost:" + this.purchaseCost);
		System.out.println("Hours for Help Request:" + this.rentCost);
		if (this.currentMajorDevLevel == MAX_MAJOR_DEV) {
			System.out.println("Fully researched and construction underway");
		} else {
			System.out.println("Current Research Stage:" + this.currentMinorDevLevel);
			System.out.println("Research Cost:" + this.minorDevCost);
		}
		if (ownedBy != null) {
			System.out.println("Research underway by: " + this.ownedBy);
		} else {
			System.out.println("Research has not started on this element");
		}

	}
	
	/**
	 * checks if element has reached max development
	 * @return true if development is maxed
	 */
	public boolean isMaxDevelopment() {
		boolean isMax = false;
		
		if(MAX_MAJOR_DEV == currentMajorDevLevel) {
			isMax = true;
		} 
		
		return isMax;
		
	}
	
	/**
	 * checks if element is owned by specified player
	 * @param player to check
	 * @return true if element is owned by player
	 */
	public boolean isOwnedBy(Player player) {
		boolean isOwnedByPlayer = false;

		if(ownedBy!=null && ownedBy.equals(player)) {
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
	 * @param purchaseCost the purchaseCost to set
	 */
	public void setPurchaseCost(int purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	/**
	 * @return the minorDevCost
	 */
	public int getMinorDevCost() {
		return minorDevCost;
	}

	/**
	 * @param minorDevCost the minorDevCost to set
	 */
	public void setMinorDevCost(int minorDevCost) {
		this.minorDevCost = minorDevCost;
	}

	/**
	 * @return the majorDevCost
	 */
	public int getMajorDevCost() {
		return majorDevCost;
	}

	/**
	 * @param majorDevCost the majorDevCost to set
	 */
	public void setMajorDevCost(int majorDevCost) {
		this.majorDevCost = majorDevCost;
	}

	/**
	 * @return the currentMinorDevLevel
	 */
	public int getCurrentMinorDevLevel() {
		return currentMinorDevLevel;
	}

	/**
	 * @param currentMinorDevLevel the currentMinorDevLevel to set
	 */
	public void setCurrentMinorDevLevel(int currentMinorDevLevel) {
		this.currentMinorDevLevel = currentMinorDevLevel;
	}

	/**
	 * @return the currentMajorDevLevel
	 */
	public int getCurrentMajorDevLevel() {
		return currentMajorDevLevel;
	}

	/**
	 * @param currentMajorDevLevel the currentMajorDevLevel to set
	 */
	public void setCurrentMajorDevLevel(int currentMajorDevLevel) {
		this.currentMajorDevLevel = currentMajorDevLevel;
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

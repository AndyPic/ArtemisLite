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
public class StandardSquare extends Square {

	// Constants

	private final int MIN_DEV_LEVEL = 0;
	private final int MAX_MINOR_DEV = 3;
	private final int MAX_MAJOR_DEV = 1;

	// Instance Vars

	private int purchaseCost, minorDevCost, majorDevCost, currentMinorDevLevel, currentMajorDevLevel, rentCost;
	private boolean isOwned;
	private Player ownedBy;

	// Constructors

	/**
	 * Default constructor
	 */
	public StandardSquare() {

	}

	/**
	 * Constructor with args
	 * 
	 * @param boardPosition
	 * @param squareName
	 * @param purchaseCost
	 * @param minorDevCost
	 * @param majorDevCost
	 * @param maxMajorDev
	 * @param maxMinorDev
	 * @param rentCost
	 * @param isOwned
	 * @param ownedBy
	 */
	public StandardSquare(int boardPosition, String squareName, SystemType squareSystem, int purchaseCost,
			int minorDevCost, int majorDevCost, int rentCost, boolean isOwned) {
		super(boardPosition, squareName, squareSystem);
		this.purchaseCost = purchaseCost;
		this.minorDevCost = minorDevCost;
		this.majorDevCost = majorDevCost;
		this.currentMinorDevLevel = MIN_DEV_LEVEL;
		this.currentMajorDevLevel = MIN_DEV_LEVEL;
		this.rentCost = rentCost;
		this.isOwned = false;
	}

	// Methods

	/**
	 * Formatted toString to return dynamic info based on current game state
	 */
	@Override
	public String toString() {
		if (!isOwned)
			return super.toString() + String.format("%15s\t%d\nMinor dev cost:\t%d\nMajor dev cost:\t%d\n%15s\t%d",
					"Cost to buy:", purchaseCost, minorDevCost, majorDevCost, "Rent cost:", rentCost);

		return super.toString()
				+ String.format("%15s\t%s\nMinor dev levl:\t%d\nMinor dev cost:\t%d\nMajor dev levl:\t%d\nMajor dev cost:\t%d\n%15s\t%d",
						"Owned by:", ownedBy.getName(), currentMinorDevLevel, minorDevCost, currentMajorDevLevel,
						majorDevCost, "Rent cost:", rentCost);

	}

	/**
	 * Increases dev level of square
	 * 
	 * @throws Exception
	 */
	public void increaseDev() throws Exception {

		if (this.currentMinorDevLevel < this.MAX_MINOR_DEV) {

			this.currentMinorDevLevel++;
			System.out.println(
					"Minor dev increased from " + (this.currentMinorDevLevel - 1) + " to " + this.currentMinorDevLevel);

		} else if (this.currentMajorDevLevel < this.MAX_MAJOR_DEV) {

			this.currentMajorDevLevel++;
			System.out.println(
					"Major dev increased from " + (this.currentMajorDevLevel - 1) + " to " + this.currentMajorDevLevel);

		} else {

			throw new Exception("Already at max dev level");

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
		SquareDetails currentSquareDetails = null;
		for (SquareDetails squareDetails : SquareDetails.values()) {
			if (getSquareName().equals(squareDetails.getName())) {
				currentSquareDetails = squareDetails;
				break;
			}
		}

		// Confirms that the ENUM was found
		if (currentSquareDetails == null) {
			throw new NullPointerException("ENUM details not found");
		}

		// increases rent cost
		switch (totalDevLevel) {
		case 1:
			this.setRentCost(currentSquareDetails.getRentMinor1());
			break;
		case 2:
			this.setRentCost(currentSquareDetails.getRentMinor2());
			break;
		case 3:
			this.setRentCost(currentSquareDetails.getRentMinor3());
			break;
		case 4:
			this.setRentCost(currentSquareDetails.getRentMajor1());
			break;
		}

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
	 * @return the isOwned
	 */
	public boolean isOwned() {
		return isOwned;
	}

	/**
	 * @param isOwned the isOwned to set
	 */
	public void setOwned(boolean isOwned) {
		this.isOwned = isOwned;
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

}
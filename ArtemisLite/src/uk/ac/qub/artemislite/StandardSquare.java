/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * @author Jordan Davis
 *
 */
public class StandardSquare extends Square {

	/**
	 * 
	 */
	private final int MAX_MINOR_DEV = 3;
	
	/**
	 * 
	 */
	private final int MAX_MAJOR_DEV = 1;
	
	/**
	 * 
	 */
	private int purchaseCost;

	/**
	 * 
	 */
	private int minorDevCost;

	/**
	 * 
	 */
	private int majorDevCost;

	/**
	 * 
	 */
	private int currentMinorDevLevel;

	/**
	 * 
	 */
	private int currentMajorDevLevel;

	/**
	 * 
	 */
	private int rentCost;

	/**
	 * 
	 */
	private boolean isOwned;

	/**
	 * 
	 */
	private Player ownedBy;

	/**
	 * 
	 */
	private int systemID; // *********What is this var for??***************

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
	 * @param systemID
	 */
	public StandardSquare(int boardPosition, String squareName, ElementType squareElement, int purchaseCost,
			int minorDevCost, int majorDevCost, int rentCost, boolean isOwned /*, int systemID*/) {
		super(boardPosition, squareName, squareElement);
		this.purchaseCost = purchaseCost;
		this.minorDevCost = minorDevCost;
		this.majorDevCost = majorDevCost;
		this.currentMinorDevLevel = 0; // defaults value to 0
		this.currentMajorDevLevel = 0; // defaults value to 0
		this.rentCost = rentCost;
		this.isOwned = false;
		//this.systemID = systemID; what is this? 
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
					"Minor dev increased from" + (this.currentMinorDevLevel - 1) + " to " + this.currentMinorDevLevel);

		} else if (this.currentMajorDevLevel < this.MAX_MAJOR_DEV) {

			this.currentMajorDevLevel++;
			System.out.println(
					"Major dev increased from" + (this.currentMajorDevLevel - 1) + " to " + this.currentMajorDevLevel);

		} else {

			throw new Exception("Already at max dev level");

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

	/**
	 * @return the systemID
	 */
	public int getSystemID() {
		return systemID;
	}

	/**
	 * @param systemID the systemID to set
	 */
	public void setSystemID(int systemID) {
		this.systemID = systemID;
	}

	@Override
	public String toString() {
		return "StandardSquare [purchaseCost=" + purchaseCost + ", minorDevCost=" + minorDevCost + ", majorDevCost="
				+ majorDevCost + ", currentMinorDevLevel=" + currentMinorDevLevel + ", currentMajorDevLevel="
				+ currentMajorDevLevel + ", maxMinorDev=" + MAX_MINOR_DEV + ", maxMajorDev=" + MAX_MAJOR_DEV + ", rentCost="
				+ rentCost + ", isOwned=" + isOwned + ", ownedBy=" + ownedBy + ", systemID=" + systemID + "]";
	}

}

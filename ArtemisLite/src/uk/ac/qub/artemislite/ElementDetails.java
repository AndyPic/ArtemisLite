/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * lists all details for the elements on the game board
 * elements are added to the board in decending order
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public enum ElementDetails {

	// position, elementName, elementSystem, purchaseCost, minorDevCost,
	// majorDevCost, rent,
	// rentMinor1, rentMinor2, rentMinor3, rentMajor1
	RESOURCE_ELEMENT(0, "Recruitment Agency", SystemType.RESOURCE),
	CREW_MODULE(1, "Crew Module", SystemType.ORION, 100, 50, 50, 30, 90, 270, 400, 550),
	LAS(2, "Launch Abort System", SystemType.ORION, 120, 50, 50, 40, 100, 300, 450, 600),
	SRB(3, "Solid Rocket Boosters", SystemType.SLS, 180, 100, 100, 70, 200, 550, 750, 950),
	RS25(4, "RS-25 Engines", SystemType.SLS, 180, 100, 100, 70, 200, 550, 750, 950),
	SLS_AVIONICS(5, "SLS Avionics", SystemType.SLS, 200, 100, 100, 80, 220, 600, 800, 1000),
	BLANK_ELEMENT(6, "Public Holiday", SystemType.BLANK),
	CRAWLER(7, "Crawlers", SystemType.EGS, 260, 150, 150, 110, 330, 800, 975, 1150),
	ML(8, "Mobile Launchers", SystemType.EGS, 260, 150, 150, 110, 330, 800, 975, 1150),
	LP39B(9, "Launch Pad 39B", SystemType.EGS, 280, 150, 150, 120, 360, 850, 1025, 1200),
	PP(10, "Power and Propulsion", SystemType.GATEWAY, 350, 200, 50, 175, 500, 1100, 1300, 1500),
	HLO(11, "Habitation and Logistics Outpost", SystemType.GATEWAY, 400, 200, 150, 200, 600, 1400, 1700, 2000);

	private final int elementPos;
	private final String name;
	private final SystemType system;
	private int cost;
	private int minorCost;
	private int majorCost;
	private int rent;
	private int rentMinor1;
	private int rentMinor2;
	private int rentMinor3;
	private int rentMajor1;

	/**
	 * reduced args constructor
	 * 
	 * @param elementPos
	 * @param name
	 * @param system
	 * @param cost
	 * @param minorCost
	 * @param majorCost
	 * @param rent
	 * @param rentMinor1
	 * @param rentMinor2
	 * @param rentMinor3
	 * @param rentMajor1
	 */
	private ElementDetails(int elementPos, String name, SystemType system) {
		this.elementPos = elementPos;
		this.name = name;
		this.system = system;
	}

	/**
	 * Full args constructor
	 * 
	 * @param elementPos
	 * @param name
	 * @param system
	 * @param cost
	 * @param minorCost
	 * @param majorCost
	 * @param rent
	 * @param rentMinor1
	 * @param rentMinor2
	 * @param rentMinor3
	 * @param rentMajor1
	 */
	private ElementDetails(int elementPos, String name, SystemType system, int cost, int minorCost, int majorCost,
			int rent, int rentMinor1, int rentMinor2, int rentMinor3, int rentMajor1) {
		this.elementPos = elementPos;
		this.name = name;
		this.system = system;
		this.cost = cost;
		this.minorCost = minorCost;
		this.majorCost = majorCost;
		this.rent = rent;
		this.rentMinor1 = rentMinor1;
		this.rentMinor2 = rentMinor2;
		this.rentMinor3 = rentMinor3;
		this.rentMajor1 = rentMajor1;
	}

	/**
	 * @return the elementPos
	 */
	public int getElementPos() {
		return elementPos;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the system
	 */
	public SystemType getSystem() {
		return system;
	}

	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @return the minorCost
	 */
	public int getMinorCost() {
		return minorCost;
	}

	/**
	 * @return the majorCost
	 */
	public int getMajorCost() {
		return majorCost;
	}

	/**
	 * @return the rent
	 */
	public int getRent() {
		return rent;
	}

	/**
	 * @return the rentMinor1
	 */
	public int getRentMinor1() {
		return rentMinor1;
	}

	/**
	 * @return the rentMinor2
	 */
	public int getRentMinor2() {
		return rentMinor2;
	}

	/**
	 * @return the rentMinor3
	 */
	public int getRentMinor3() {
		return rentMinor3;
	}

	/**
	 * @return the rentMajor1
	 */
	public int getRentMajor1() {
		return rentMajor1;
	}
	
	

}

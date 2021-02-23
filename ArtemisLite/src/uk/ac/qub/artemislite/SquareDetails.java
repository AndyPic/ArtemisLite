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
public enum SquareDetails {

	// position, squareName, squareSystem, purchaseCost, minorDevCost,
	// majorDevCost, rent,
	// rentMinor1, rentMinor2, rentMinor3, rentMajor1
	// TODO still need to decide values and names for these sqaures
	SQUARERESOURCE(1, "Resource Square", SystemType.RESOURCE),
	SQUAREX(2, "SquareX", SystemType.SYSTEM1, 100, 200, 400, 10, 20, 30, 40, 80),
	SQUAREY(3, "SquareY", SystemType.SYSTEM1, 200, 200, 400, 10, 20, 30, 40, 80),
	SQUAREA(4, "SquareA", SystemType.SYSTEM2, 300, 400, 800, 30, 40, 50, 60, 120),
	SQUAREB(5, "SquareB", SystemType.SYSTEM2, 400, 400, 800, 30, 40, 50, 60, 120),
	SQUAREC(6, "SquareC", SystemType.SYSTEM2, 500, 400, 800, 30, 40, 50, 60, 120),
	SQUAREBLANK(7, "Blank Square", SystemType.BLANK),
	SQUARE1(8, "Square1", SystemType.SYSTEM3, 600, 600, 1200, 60, 70, 80, 90, 180),
	SQUARE2(9, "Square2", SystemType.SYSTEM3, 700, 600, 1200, 60, 70, 80, 90, 180),
	SQUARE3(10, "Square3", SystemType.SYSTEM3, 800, 600, 1200, 60, 70, 80, 90, 180),
	SQUAREi(11, "Squarei", SystemType.SYSTEM4, 900, 800, 1600, 90, 100, 110, 120, 240),
	SQUAREii(12, "Squareii", SystemType.SYSTEM4, 1000, 800, 1600, 90, 100, 110, 120, 240);

	private final int squarePos;
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
	 * @param squarePos
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
	private SquareDetails(int squarePos, String name, SystemType system) {
		this.squarePos = squarePos;
		this.name = name;
		this.system = system;
	}

	/**
	 * Full args constructor
	 * 
	 * @param squarePos
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
	private SquareDetails(int squarePos, String name, SystemType system, int cost, int minorCost, int majorCost,
			int rent, int rentMinor1, int rentMinor2, int rentMinor3, int rentMajor1) {
		this.squarePos = squarePos;
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
	 * @return the squarePos
	 */
	public int getSquarePos() {
		return squarePos;
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

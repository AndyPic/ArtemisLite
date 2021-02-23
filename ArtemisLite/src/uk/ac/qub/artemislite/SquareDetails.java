/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * @author Jordan Davis
 *
 */
public enum SquareDetails {

	// squareName, squareElement, purchaseCost, minorDevCost, majorDevCost, rent,
	// rentMinor1, rentMinor2, rentMinor3, rentMajor1
	SQUAREX("SquareX", ElementType.ELEMENT1, 100, 200, 400, 10, 20, 30, 40, 80),
	SQUAREY("SquareY", ElementType.ELEMENT1, 200, 200, 400, 10, 20, 30, 40, 80),
	SQUAREA("SquareA", ElementType.ELEMENT2, 300, 400, 800, 30, 40, 50, 60, 120),
	SQUAREB("SquareB", ElementType.ELEMENT2, 400, 400, 800, 30, 40, 50, 60, 120),
	SQUAREC("SquareC", ElementType.ELEMENT2, 500, 400, 800, 30, 40, 50, 60, 120),
	SQUARE1("Square1", ElementType.ELEMENT3, 600, 600, 1200, 60, 70, 80, 90, 180),
	SQUARE2("Square2", ElementType.ELEMENT3, 700, 600, 1200, 60, 70, 80, 90, 180),
	SQUARE3("Square3", ElementType.ELEMENT3, 800, 600, 1200, 60, 70, 80, 90, 180),
	SQUAREi("Squarei", ElementType.ELEMENT4, 900, 800, 1600, 90, 100, 110, 120, 240),
	SQUAREii("Squareii", ElementType.ELEMENT4, 1000, 800, 1600, 90, 100, 110, 120, 240);

	private final String name;
	private final ElementType element;
	private final int cost;
	private final int minorCost;
	private final int majorCost;
	private final int rent;
	private final int rentMinor1;
	private final int rentMinor2;
	private final int rentMinor3;
	private final int rentMajor1;

	/**
	 * @param name
	 * @param element
	 * @param cost
	 * @param minorCost
	 * @param majorCost
	 * @param rent
	 * @param rentMinor1
	 * @param rentMinor2
	 * @param rentMinor3
	 * @param rentMajor1
	 */
	private SquareDetails(String name, ElementType element, int cost, int minorCost, int majorCost, int rent,
			int rentMinor1, int rentMinor2, int rentMinor3, int rentMajor1) {
		this.name = name;
		this.element = element;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the element
	 */
	public ElementType getElement() {
		return element;
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

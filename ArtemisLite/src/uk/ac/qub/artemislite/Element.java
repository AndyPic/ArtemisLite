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
public class Element {

	// Instance Vars

	private int boardPosition;
	private String elementName;
	private SystemType elementSystem;

	// Constructors

	/**
	 * Element default constructor
	 */
	public Element() {

	}

	/**
	 * Element constructor with args
	 * 
	 * @param boardPosition
	 * @param elementName
	 * @param elementSystem
	 */
	public Element(int boardPosition, String elementName, SystemType elementSystem) {
		this.boardPosition = boardPosition;
		this.elementName = elementName;
		this.elementSystem = elementSystem;
	}

	// Methods

	/**
	 * 
	 */
	@Override
	public String toString() {
		return String.format("[%02d][%s][%s]:\n\n", boardPosition, elementName, elementSystem.getName());
	}

	/**
	 * @return the boardPosition
	 */
	public int getBoardPosition() {
		return boardPosition;
	}

	/**
	 * @param boardPosition the boardPosition to set
	 */
	public void setBoardPosition(int boardPosition) {
		this.boardPosition = boardPosition;
	}

	/**
	 * @return the elementName
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * @param elementName the elementName to set
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	/**
	 * @return the elementSystem
	 */
	public SystemType getElementSystem() {
		return elementSystem;
	}

	/**
	 * @param elementSystem the elementSystem to set
	 */
	public void setElementSystem(SystemType elementSystem) {
		this.elementSystem = elementSystem;
	}

}

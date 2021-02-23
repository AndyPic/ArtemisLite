/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * @author Jordan Davis
 *
 */
public class Square {

	/**
	 * board order var
	 */
	private int boardPosition;

	/**
	 * square name var
	 */
	private String squareName;
	
	/**
	 * 
	 */
	private ElementType squareElement;

	/**
	 * Square default constructor
	 */
	public Square() {

	}

	/**
	 * Square constructor with args
	 * 
	 * @param boardPosition
	 * @param squareName
	 * @param squareElement 
	 */
	public Square(int boardPosition, String squareName, ElementType squareElement) {
		this.boardPosition = boardPosition;
		this.squareName = squareName;
		this.setSquareElement(squareElement);
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
	 * @return the squareName
	 */
	public String getSquareName() {
		return squareName;
	}

	/**
	 * @param squareName the squareName to set
	 */
	public void setSquareName(String squareName) {
		this.squareName = squareName;
	}

	/**
	 * @return the squareElement
	 */
	public ElementType getSquareElement() {
		return squareElement;
	}

	/**
	 * @param squareElement the squareElement to set
	 */
	public void setSquareElement(ElementType squareElement) {
		this.squareElement = squareElement;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "Square [boardPosition=" + boardPosition + ", squareName=" + squareName + ", squareElement="
				+ squareElement + "]";
	}

	

}

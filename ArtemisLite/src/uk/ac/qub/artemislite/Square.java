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
	private SystemType squareSystem;

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
	 * @param squareSystem
	 */
	public Square(int boardPosition, String squareName, SystemType squareSystem) {
		this.boardPosition = boardPosition;
		this.squareName = squareName;
		this.setSquareSystem(squareSystem);
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
	 * @return the squareSystem
	 */
	public SystemType getSquareSystem() {
		return squareSystem;
	}

	/**
	 * @param squareSystem the squareSystem to set
	 */
	public void setSquareSystem(SystemType squareSystem) {
		this.squareSystem = squareSystem;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "Square [boardPosition=" + boardPosition + ", squareName=" + squareName + ", squareSystem="
				+ squareSystem + "]";
	}

}

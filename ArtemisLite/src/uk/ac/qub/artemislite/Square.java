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
	 * Square default constructor
	 */
	public Square() {

	}

	/**
	 * Square constructor with args
	 * 
	 * @param boardPosition
	 * @param squareName
	 */
	public Square(int boardPosition, String squareName) {
		this.boardPosition = boardPosition;
		this.squareName = squareName;
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

	@Override
	public String toString() {
		return "Square [boardPosition=" + boardPosition + ", squareName=" + squareName + "]";
	}

}

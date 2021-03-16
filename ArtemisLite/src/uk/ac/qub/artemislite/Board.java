package uk.ac.qub.artemislite;

import java.util.ArrayList;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class Board {

	// Instance Vars

	private ArrayList<Square> squares = new ArrayList<Square>();

	// Constructors

	/**
	 * Default constructor, builds the game board
	 */
	public Board() {
		for (SquareDetails squareDetails : SquareDetails.values()) {
			SystemType systemType = squareDetails.getSystem();
			switch (systemType) {

			case RESOURCE:
				ResourceSquare resourceSquare = new ResourceSquare(squareDetails.getSquarePos(),
						squareDetails.getName(), squareDetails.getSystem());
				squares.add(resourceSquare);
				break;

			case BLANK:
				Square blankSquare = new Square(squareDetails.getSquarePos(), squareDetails.getName(),
						squareDetails.getSystem());
				squares.add(blankSquare);
				break;

			default:
				StandardSquare standardSquare = new StandardSquare(squareDetails.getSquarePos(),
						squareDetails.getName(), squareDetails.getSystem(), squareDetails.getCost(),
						squareDetails.getMinorCost(), squareDetails.getMajorCost(), squareDetails.getRent());
				squares.add(standardSquare);
			}

		}
	}

	// Methods

	/**
	 * @return the squares
	 */
	public ArrayList<Square> getSquares() {
		return squares;
	}

	/**
	 * Method to check whether all systems have been fully developed
	 * 
	 * @return
	 */
	public boolean allSystemComplete() {

		boolean complete;
		StandardSquare stdSquare;

		complete = true;

		for (Square square : this.squares) {

			if (square instanceof StandardSquare) {

				stdSquare = (StandardSquare) square;

				if (stdSquare.getCurrentMajorDevLevel() != stdSquare.getMAX_MAJOR_DEV()) {

					complete = false;
					break;

				}
			}
		}

		return complete;

	}

}

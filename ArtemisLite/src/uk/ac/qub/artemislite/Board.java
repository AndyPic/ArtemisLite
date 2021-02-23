package uk.ac.qub.artemislite;

import java.util.ArrayList;

public class Board {

	/**
	 * 
	 */
	private ArrayList<Square> squares;

	/**
	 * @return the squares
	 */
	public ArrayList<Square> getSquares() {
		return squares;
	}

	/**
	 * @param squares the squares to set
	 */
	public void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}

	/**
	 * Builds the board based on ENUM values
	 */
	public void buildGameBoard() {

		for (SquareDetails squareDetails : SquareDetails.values()) {

			switch (squareDetails.getElement()) {

			case RESOURCE:
				ResourceSquare resourceSquare = new ResourceSquare(squareDetails.getSquarePos(),
						squareDetails.getName(), squareDetails.getElement());
				squares.add(resourceSquare);
				break;

			case BLANK:
				Square blankSquare = new Square(squareDetails.getSquarePos(), squareDetails.getName(),
						squareDetails.getElement());
				squares.add(blankSquare);
				break;

			default:
				StandardSquare standardSquare = new StandardSquare(squareDetails.getSquarePos(),
						squareDetails.getName(), squareDetails.getElement(), squareDetails.getCost(),
						squareDetails.getMinorCost(), squareDetails.getMajorCost(), squareDetails.getRent(), false);
				squares.add(standardSquare);
			}

		}

	}

}

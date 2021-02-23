package uk.ac.qub.artemislite;

import java.util.ArrayList;

public class Board {

	/**
	 * 
	 */
	private ArrayList<Square> squares = new ArrayList<Square>();

	public Board() {

	}

	/**
	 * @return the squares
	 */
	public ArrayList<Square> getSquares() {
		return squares;
	}

	/**
	 * Builds the board based on ENUM values
	 */
	public void buildGameBoard() {

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
						squareDetails.getMinorCost(), squareDetails.getMajorCost(), squareDetails.getRent(), false);
				squares.add(standardSquare);
			}
			
		}
		
	}

}

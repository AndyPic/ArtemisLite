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

	/**
	 * Method to display all elements ownership status
	 * 
	 */
	public void viewElementOwnership() {

		for (Square sq : this.squares) {

			if (sq instanceof StandardSquare) {
				StandardSquare stdSq = (StandardSquare) sq;

				if (stdSq.getOwnedBy() != null) {
					System.out.printf("[%02d][%s][%s]: Owned by %s.\n", stdSq.getBoardPosition(), stdSq.getSquareName(),
							stdSq.getSquareSystem().getName(), stdSq.getOwnedBy().getName());
				} else {
					System.out.printf("[%02d][%s][%s]: Not owned.\n", stdSq.getBoardPosition(), stdSq.getSquareName(),
							stdSq.getSquareSystem().getName());
				}
			}
		}
	}// END

	/**
	 * Method to display the elements owned by a player
	 * 
	 * @param board
	 * @param activePlayer
	 */
	public void viewMyElements(Player activePlayer) {

		System.out.println("You own the following elements: ");

		for (Square sq : this.squares) {

			if (sq instanceof StandardSquare) {
				StandardSquare stdSq = (StandardSquare) sq;

				if (stdSq.getOwnedBy() == activePlayer) {
					System.out.println(stdSq.toString());
				}
			}
		}
	}// END

}

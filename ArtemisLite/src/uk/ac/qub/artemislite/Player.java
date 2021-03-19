/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class Player {

	// Instance Vars
	// increases starting resources for testing - JSM

	private String name;
	private int balanceOfResources = 100000; // Set starting resources 
	private int currentPosition = 0; // Set starting position

	// Constructors

	/**
	 * Default Player constructor
	 */
	public Player() {

	}

	/**
	 * Player constructor with args
	 * 
	 * @param name
	 * @param isCurrentPlayer
	 */
	public Player(String name) {
		this.name = name;
	}

	// Methods

	/**
	 * Method to check if the player owns any squares
	 * 
	 * @param board
	 * @param player
	 * @return
	 */
	public boolean isOwner(Board board) {

		for (int loop = 0; loop < board.getSquares().size(); loop++) {
			if (board.getSquares().get(loop) instanceof StandardSquare) {
				StandardSquare stSq = (StandardSquare) board.getSquares().get(loop);
				if (stSq.getOwnedBy() == this) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * checks if player owns all elements in a system
	 * @param board
	 * @param reqSq
	 * @param activePlayer
	 * @return
	 */
	public boolean systemFullyOwned(Board board, Square reqSq, Player activePlayer){
		ArrayList<Square> sqs = board.getSquares();
		ArrayList<StandardSquare> elementSquares = new ArrayList<StandardSquare>();
		ArrayList<StandardSquare> ownedElementSquares = new ArrayList<StandardSquare>();
		for (Square sq : sqs) {
			if(sq instanceof StandardSquare) {
				StandardSquare strdSq = (StandardSquare) sq;
				if(strdSq.getSquareSystem()==reqSq.getSquareSystem() && strdSq.getOwnedBy()!=activePlayer) {
					elementSquares.add(strdSq);
				} else if(strdSq.getSquareSystem()==reqSq.getSquareSystem() && strdSq.getOwnedBy()==activePlayer) {
					ownedElementSquares.add(strdSq);
					elementSquares.add(strdSq);
				}
			}
		}
		System.out.println(ownedElementSquares.size());
		System.out.println(elementSquares.size());
		if(ownedElementSquares.size()==elementSquares.size()) {
			return true;
		}
		return false;
	}

	/**
	 * Method that returns details about the players currently occupied square
	 */
	public void getCurrentPositionDetails(Board board) {
		if (board.getSquares().get(currentPosition) instanceof StandardSquare) {
			StandardSquare ssq = (StandardSquare) board.getSquares().get(currentPosition);
			System.out.println(ssq.toString());
		} else if (board.getSquares().get(currentPosition) instanceof ResourceSquare) {
			ResourceSquare rsq = (ResourceSquare) board.getSquares().get(currentPosition);
			System.out.println(rsq.toString());
		} else {
			System.out.println(board.getSquares().get(currentPosition).toString());
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name, the name to set
	 */
	public void setName(String name) {
		this.name = name.trim();
	}

	/**
	 * 
	 * @return the balance of resources
	 */
	public int getBalanceOfResources() {
		return balanceOfResources;
	}

	/**
	 * 
	 * @param balanceOfResources, the balance of resources to set
	 */
	public void setBalanceOfResources(int balanceOfResources) {
		this.balanceOfResources = balanceOfResources;
	}

	/**
	 * @return the currentPosition
	 */
	public int getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @param currentPosition the currentPosition to set
	 */
	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

}

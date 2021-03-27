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
	private int balanceOfResources = 200; // Set starting resources 
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
	 * Method to check if the player owns any elements
	 * 
	 * @param board
	 * @param player
	 * @return
	 */
	public boolean isOwner(Board board) {

		for (int loop = 0; loop < board.getElements().size(); loop++) {
			if (board.getElements().get(loop) instanceof StandardElement) {
				StandardElement stSq = (StandardElement) board.getElements().get(loop);
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
	public boolean systemFullyOwned(Board board, Element reqSq, Player activePlayer){
		ArrayList<Element> allElements = board.getElements();
		ArrayList<StandardElement> elementList = new ArrayList<StandardElement>();
		ArrayList<StandardElement> ownedElements = new ArrayList<StandardElement>();
		for (Element element : allElements) {
			if(element instanceof StandardElement) {
				StandardElement strdSq = (StandardElement) element;
				if(strdSq.getElementSystem()==reqSq.getElementSystem() && strdSq.getOwnedBy()!=activePlayer) {
					elementList.add(strdSq);
				} else if(strdSq.getElementSystem()==reqSq.getElementSystem() && strdSq.getOwnedBy()==activePlayer) {
					ownedElements.add(strdSq);
					elementList.add(strdSq);
				}
			}
		}
		System.out.println(ownedElements.size());
		System.out.println(elementList.size());
		if(ownedElements.size()==elementList.size()) {
			return true;
		}
		return false;
	}

	/**
	 * Method that returns details about the players currently occupied element
	 */
	public void getCurrentPositionDetails(Board board) {
		if (board.getElements().get(currentPosition) instanceof StandardElement) {
			StandardElement ssq = (StandardElement) board.getElements().get(currentPosition);
			System.out.println(ssq.toString());
		} else if (board.getElements().get(currentPosition) instanceof ResourceElement) {
			ResourceElement rsq = (ResourceElement) board.getElements().get(currentPosition);
			System.out.println(rsq.toString());
		} else {
			System.out.println(board.getElements().get(currentPosition).toString());
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

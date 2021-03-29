package uk.ac.qub.artemislite;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class Board {

	// Instance Vars

	private ArrayList<Element> elements = new ArrayList<Element>();

	// Constructors

	/**
	 * Default constructor, builds the game board
	 */
	public Board() {
		for (ElementDetails elementDetails : ElementDetails.values()) {
			SystemType systemType = elementDetails.getSystem();
			switch (systemType) {

			case RESOURCE:
				ResourceElement resourceElement = new ResourceElement(elementDetails.getElementPos(),
						elementDetails.getName(), elementDetails.getSystem());
				elements.add(resourceElement);
				break;

			case BLANK:
				Element blankElement = new Element(elementDetails.getElementPos(), elementDetails.getName(),
						elementDetails.getSystem());
				elements.add(blankElement);
				break;

			default:
				StandardElement standardElement = new StandardElement(elementDetails.getElementPos(),
						elementDetails.getName(), elementDetails.getSystem(), elementDetails.getCost(),
						elementDetails.getMinorCost(), elementDetails.getMajorCost(), elementDetails.getRent());
				elements.add(standardElement);
			}

		}
	}

	// Methods

	/**
	 * @return the elements
	 */
	public ArrayList<Element> getElements() {
		return elements;
	}

	/**
	 * checks if player owns all elements in a system
	 * 
	 * @param board
	 * @param reqElement
	 * @param player
	 * @return
	 */
	public boolean systemFullyOwned(Element reqElement, Player player) {
		boolean fullyOwned = true;
		StandardElement stdElement;

		for (Element element : elements) {
			if (element instanceof StandardElement) {
				stdElement = (StandardElement) element;
				if (stdElement.getElementSystem().equals(reqElement.getElementSystem())
						&& !stdElement.getOwnedBy().equals(player)) {
					fullyOwned = false;
					break;
				}
			}
		}
		return fullyOwned;
	}

	/**
	 * Method to check whether all systems have been fully developed
	 * 
	 * @return
	 */
	public boolean allSystemComplete() {

		boolean complete;
		StandardElement stdElement;

		complete = true;

		for (Element element : this.elements) {

			if (element instanceof StandardElement) {

				stdElement = (StandardElement) element;

				if (stdElement.getCurrentMajorDevLevel() != stdElement.getMAX_MAJOR_DEV()) {

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
		for (Element element : this.elements) {

			if (element instanceof StandardElement) {
				StandardElement stdElement = (StandardElement) element;

				if (stdElement.getOwnedBy() != null) {
					System.out.printf("[%s] - [%s]: Research started by %s.\n", stdElement.getElementSystem().getName(),
							stdElement.getElementName(), stdElement.getOwnedBy().getName());
				} else {
					System.out.printf("%s - [%s]: Research has not begun.\n", stdElement.getElementSystem().getName(),
							stdElement.getElementName(), stdElement.getElementSystem().getName());
				}
			}
		}
	}// END

	/**
	 * displays titles of elements owned by a specified player
	 * 
	 * @param player
	 */
	public void viewMyElements(Player player) {
		int count = 1;
		StandardElement stdElement;
		boolean hasElement = false;
		
		

		for (Element element : this.elements) {
			if (element instanceof StandardElement) {
				stdElement = (StandardElement) element;
				stdElement.setOwnedBy(null);

				if (stdElement.isOwnedBy(player)) {
					if(!hasElement) {
						System.out.println("\nYou own the following elements: ");
						hasElement = true;
					}
					System.out.printf("%d. %s [%s - %s]\n", count++, stdElement.getElementName(),
							stdElement.getElementSystem().getName(),checkNumberOwned(stdElement, player));
				}
			}
		}

	}

	/**
	 * checks how many elements in a system is owned by a player
	 * @param element
	 * @param player
	 * @return
	 */
	public String checkNumberOwned(StandardElement element, Player player) {

		int total, owned;
		StandardElement stdElement;

		total = 0;
		owned = 0;

		for (Element checkElement : elements) {
			if (checkElement instanceof StandardElement) {
				stdElement = (StandardElement) checkElement;

				if (stdElement.getElementSystem().equals(element.getElementSystem())) {
					total++;
					if (stdElement.isOwnedBy(player)) {
						owned++;
					}
				}

			}
		}
		return owned + " of " + total;
	}

	/**
	 * displays all details of elements owned by a specified player
	 * 
	 * @param board
	 * @param player
	 */
	public void viewMyElementsDetails(Player player) {
		System.out.println("You own the following elements: ");

		for (Element element : this.elements) {

			if (element instanceof StandardElement) {
				StandardElement stdElement = (StandardElement) element;

				if (stdElement.getOwnedBy().equals(player)) {
					System.out.println(stdElement.toString());
				}
			}
		}
	}// END
	


	/**
	 * Method to check if any other player has started to research in the same system.
	 * 
	 * @param board
	 * @param player
	 * @return
	 */
	public void isSystemStarted(Player player, Element checkElement) {

		boolean hasOwners = false;
		Player purchasedPlayer = player;
		StandardElement stdElement; 
		
		for(Element element : elements) {
			if(element instanceof StandardElement) {
				stdElement = (StandardElement) element;
				if(stdElement.getOwnedBy()!=null && !stdElement.getOwnedBy().equals(player)) {
					hasOwners = true;
					purchasedPlayer = stdElement.getOwnedBy();
					break;
				}
			}
		}
		
		if(hasOwners) {
			System.out.println("Hint: "+purchasedPlayer.getName().toUpperCase()+" has started research in this system already.\nOnce you begin research there is no way out and will mean the mission can never be complete.\nAre you in it for personal gain or to see the mission succeed?\n");
		} else {
			System.out.println("Hint: No other companies have started to research this system yet, its a good investment!\n");
		}

	}

}

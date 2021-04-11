package uk.ac.qub.artemislite;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class Board {

	// Instance Vars

	private List<Element> elements = new ArrayList<Element>();

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
	 * returns a List of all elements on the board
	 * 
	 * @return the elements
	 */
	public List<Element> getElements() {
		return elements;
	}

	/**
	 * returns a list of all StandardElements on the board
	 * 
	 * @return all stdElements
	 */
	public List<StandardElement> getStdElements() {

		List<StandardElement> stdElements = new ArrayList<StandardElement>();
		StandardElement stdElement;

		for (Element element : elements) {
			if (element instanceof StandardElement) {
				stdElement = (StandardElement) element;
				stdElements.add(stdElement);
			}
		}
		return stdElements;
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

		for (StandardElement stdElement : getStdElements()) {
			if (stdElement.getElementSystem().equals(reqElement.getElementSystem()) && !stdElement.isOwnedBy(player)) {
				fullyOwned = false;
				break;
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

		boolean complete = true;

		for (StandardElement stdElement : getStdElements()) {
			if (!stdElement.isMaxDevelopment()) {
				complete = false;
				break;
			}
		}
		return complete;
	}

	/**
	 * Method to display all elements ownership status
	 * 
	 */
	public void viewElementOwnership() {
		for (StandardElement stdElement : getStdElements()) {

			if (stdElement.getOwnedBy() != null) {
				System.out.printf("%s - [%s]: Research started by %s.\n", stdElement.getElementSystem().getName(),
						stdElement.getElementName(), stdElement.getOwnedBy().getName());
			} else {
				System.out.printf("%s - [%s]: Research has not begun.\n", stdElement.getElementSystem().getName(),
						stdElement.getElementName(), stdElement.getElementSystem().getName());
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
		boolean hasElement = false;

		for (StandardElement stdElement : getStdElements()) {

			if (stdElement.isOwnedBy(player)) {
				// only prints header if player has atleast 1 property
				if (!hasElement) {
					System.out.println("\nYou own the following elements: ");
					hasElement = true;
				}
				System.out.printf("%d. %s [%s - %s]\n", count++, stdElement.getElementName(),
						stdElement.getElementSystem().getName(), checkNumberOwned(stdElement, player));
			}

		}

	}

	/**
	 * checks how many elements in a system is owned by a player
	 * 
	 * @param element
	 * @param player
	 * @return
	 */
	public String checkNumberOwned(StandardElement element, Player player) {

		int total, owned;

		total = 0;
		owned = 0;

		for (StandardElement stdElement : getStdElements()) {

			if (stdElement.getElementSystem().equals(element.getElementSystem())) {
				total++;
				if (stdElement.isOwnedBy(player)) {
					owned++;
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

		for (StandardElement stdElement : getStdElements()) {

			if (stdElement.isOwnedBy(player)) {
				System.out.println(stdElement.toString());
			}

		}
	}// END

	/**
	 * Method to check if any other player has started to research in the same
	 * system.
	 * 
	 * @param board
	 * @param player
	 * @return
	 */
	public void isSystemStarted(Player player, Element checkElement) {

		boolean hasOwners = false;
		Player purchasedPlayer = player;

		for (StandardElement stdElement : getStdElements()) {

			if (checkElement.getElementSystem() != stdElement.getElementSystem()) {
				continue;
			}
			if (stdElement.getOwnedBy() != null && !stdElement.isOwnedBy(player)) {
				hasOwners = true;
				purchasedPlayer = stdElement.getOwnedBy();
				break;
			}

		}

		if (hasOwners) {
			System.out.println("Hint: " + purchasedPlayer.getName().toUpperCase()
					+ " has started research in this system already.\nOnce you begin research there is no way out and will mean the mission can never be complete.\nAre you in it for personal gain or to see the mission succeed?\n");
		} else {
			System.out.println(
					"Hint: No other companies have started to research this system yet, its a good investment!\n");
		}

	}

	/**
	 * method to find an element based on player owned elements
	 * 
	 * @param player
	 * @param index
	 * @return
	 */
	public StandardElement getPlayerOwnedIndex(Player player, int index) {

		StandardElement result = null;
		int count = 0;

		for (StandardElement stdElement : getStdElements()) {
			if (systemFullyOwned(stdElement, player)) {
				count++;
				if (count == index) {
					result = stdElement;
					break;
				}
			}
		}

		return result;

	}

	/**
	 * method to find an element based on player owned elements
	 * 
	 * @param player
	 * @param index
	 * @return
	 */
	public StandardElement getPlayerElement(Player player, int index) {

		StandardElement result = null;
		int count = 0;

		for (StandardElement stdElement : getStdElements()) {
			if (stdElement.getOwnedBy() == player) {
				count++;
				if (count == index) {
					result = stdElement;
					break;
				}
			}
		}

		return result;

	}

	/**
	 * displays elements that can be developed and their dev level and returns the
	 * number of elements displayed
	 * 
	 * @param player
	 * @return int for the number of elements displayed
	 */
	public int availableForDev(Player player) {

		int count = 0;
		boolean hasElement = false;
		System.out.println();

		for (StandardElement stdElement : getStdElements()) {

			if (stdElement.isOwnedBy(player)) {
				if (!systemFullyOwned(stdElement, player)) {
					continue;
				}
				if (!hasElement) {
					System.out.println("=====| ELEMENTS |=====");
					hasElement = true;
				}
				if (stdElement.isMaxDevelopment()) {
					System.out.printf(
							"\n%d. %s [%s - %s] \nResearch and development complete! no further action needed\n",
							++count, stdElement.getElementName(), stdElement.getElementSystem().getName(),
							checkNumberOwned(stdElement, player));
				} else {
					System.out.printf(
							"\n%d. %s [%s - %s] \nResearch level: [%d] Cost: (%d) | Construction Level: [%d] Cost: (%d)\n",
							++count, stdElement.getElementName(), stdElement.getElementSystem().getName(),
							checkNumberOwned(stdElement, player), stdElement.getCurrentMinorDevLevel(),
							stdElement.getMinorDevCost(), stdElement.getCurrentMajorDevLevel(),
							stdElement.getMajorDevCost(), stdElement);
				}
			}
		}
		System.out.println();
		return count;
	}

	/**
	 * Method to print to screen all elements eligible for auction, and return the
	 * number (int) of them.
	 * 
	 * @param player
	 * @return
	 */
	public int availableForAuction(Player player) {

		int count = 0;
		boolean hasElement = false;
		System.out.println();

		List<StandardElement> eligibleElements = new ArrayList<>();
		Set<SystemType> systemUnderDevelopment = new TreeSet<>();

		// Find elements owned, and systems where at least 1 element is under
		// development
		for (StandardElement stdElement : getStdElements()) {
			if (stdElement.isOwnedBy(player)) {
				eligibleElements.add(stdElement);
				if (stdElement.getCurrentMinorDevLevel() > 0) {
					systemUnderDevelopment.add(stdElement.getElementSystem());
				}
			}
		}

		// Guard clause to avoid looping if not necessary
		if (!systemUnderDevelopment.isEmpty()) {
			// Remove elements whose system is under development
			for (int loop = 0; loop <= eligibleElements.size(); loop++) {
				if (systemUnderDevelopment.contains(eligibleElements.get(loop).getElementSystem())) {
					eligibleElements.remove(loop);
				}
			}
		}

		// List all eligible elements
		for (StandardElement eligibleElement : eligibleElements) {
			if (eligibleElement.getCurrentMinorDevLevel() == 0) {
				System.out.printf("\n%d. %s [%s - %s]\n", ++count, eligibleElement.getElementName(),
						eligibleElement.getElementSystem().getName(), checkNumberOwned(eligibleElement, player));
			}
		}
		System.out.println();
		return count;
	}

	/**
	 * Gets the first resource element on the game board (resource element may not
	 * always been at index 0 if board layout changes)
	 * 
	 * @return first resource element
	 */
	public ResourceElement getResourceElement() {
		ResourceElement resourceElement = null;
		for (Element element : elements) {
			if (element instanceof ResourceElement) {
				resourceElement = (ResourceElement) element;
			}
		}
		return resourceElement;
	}

}

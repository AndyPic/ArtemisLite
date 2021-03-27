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
		for (Element sq : this.elements) {

			if (sq instanceof StandardElement) {
				StandardElement stdSq = (StandardElement) sq;

				if (stdSq.getOwnedBy() != null) {
					System.out.printf("[%02d][%s][%s]: Owned by %s.\n", stdSq.getBoardPosition(), stdSq.getElementName(),
							stdSq.getElementSystem().getName(), stdSq.getOwnedBy().getName());
				} else {
					System.out.printf("[%02d][%s][%s]: Not owned.\n", stdSq.getBoardPosition(), stdSq.getElementName(),
							stdSq.getElementSystem().getName());
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

		for (Element sq : this.elements) {

			if (sq instanceof StandardElement) {
				StandardElement stdSq = (StandardElement) sq;

				if (stdSq.getOwnedBy() == activePlayer) {
					System.out.println(stdSq.toString());
				}
			}
		}
	}// END

}

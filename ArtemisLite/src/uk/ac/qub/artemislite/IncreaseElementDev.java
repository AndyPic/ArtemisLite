/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;

/**
 * Increases development level and modifies player resources accordingly
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class IncreaseElementDev {
	

	public void increaseElementDev(Board board, Player activePlayer){

		// TODO also check if they have enough money to develop
		// Increase development level
		
		boolean finishedDeveloping = false;

		do {
			// ask which element to develop
			System.out.println("Which element would you like to develop?");
			// maybe display elements owned by active player here

			int chosenSq = 0;
			chosenSq = UserInput.getUserInputInt();
			ArrayList<Element> sqs = board.getElements();
			for (Element sq : sqs) {
							
				if (sq.getBoardPosition() == chosenSq && sq instanceof StandardElement) {
					StandardElement strdSq = (StandardElement) sq;
					
					// check if system is fully owned
					if (board.systemFullyOwned(strdSq, activePlayer)) {
						
						strdSq.increaseDev();
						strdSq.increaseRent();
						
						if(strdSq.getCurrentMinorDevLevel() < strdSq.getMAX_MINOR_DEV()){
							ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -strdSq.getMinorDevCost());
						} else if(strdSq.getCurrentMajorDevLevel() < strdSq.getMAX_MAJOR_DEV()) {
							ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -strdSq.getMajorDevCost());
						}
						
						
					} else {
						
						System.out.println("You do not yet own all elements in this system");
					}
				}
			}

			// ask if they want to develop another elements
			System.out.println("Would you like to develop another element?");
			switch (UserInterface.yesNoMenu()) {
			case 1:
				finishedDeveloping = false;
				break;
			case 2:
				finishedDeveloping = true;
				break;
			default:
				System.out.println("That shouldn't happen");
			}

		} while (!finishedDeveloping);
	}

}

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
public class IncreaseSquareDev {
	

	public void increaseSquareDev(Board board, Player activePlayer){

		// TODO also check if they have enough money to develop
		// Increase development level
		
		boolean finishedDeveloping = false;

		do {
			// ask which square to develop
			System.out.println("Which element would you like to develop?");
			// maybe display squares owned by active player here

			String chosenSq = null;
			chosenSq = UserInput.getUserInputString();
			ArrayList<Square> sqs = board.getSquares();
			for (Square sq : sqs) {
				if (sq.getSquareName().equalsIgnoreCase(chosenSq) && sq instanceof StandardSquare) {
					StandardSquare strdSq = (StandardSquare) sq;
					
					// check if system is fully owned
					if (activePlayer.systemFullyOwned(board, strdSq, activePlayer)) {
						
						
						if(strdSq.getCurrentMinorDevLevel() < strdSq.getMAX_MINOR_DEV()){
							strdSq.increaseDev();
							ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -strdSq.getMinorDevCost());
						} else if(strdSq.getCurrentMajorDevLevel() < strdSq.getMAX_MAJOR_DEV()) {
							strdSq.increaseDev();
							ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -strdSq.getMajorDevCost());
						} else if(strdSq.getCurrentMajorDevLevel() == strdSq.getMAX_MAJOR_DEV()) {
							strdSq.increaseDev();
						}
					} else {
						System.out.println("You do not yet own all elements in this system");
					}
				}
			}

			// ask if they want to develop another square
			System.out.println("Would you like to develop another square?");
			switch (GUI.yesNoMenu()) {
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

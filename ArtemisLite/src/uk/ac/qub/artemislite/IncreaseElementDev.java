/**
 * 
 */
package uk.ac.qub.artemislite;

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
		Element chosenSq;

		do {
			board.displayAvailableForDev(activePlayer);
			
			System.out.println("Which element would you like to develop?");

			chosenSq = board.getPlayerOwnedIndex(activePlayer, UserInput.getUserInputInt());
			
			for (Element element : board.getElements()) {
							
				if (chosenSq.equals(element) && element instanceof StandardElement) {
					StandardElement strdSq = (StandardElement) element;
					
					// check if system is fully owned
					if (board.systemFullyOwned(strdSq, activePlayer)) {
						
						strdSq.increaseDev();
						strdSq.increaseRent();
						
						if(strdSq.getCurrentMinorDevLevel() < strdSq.getMAX_MINOR_DEV()){
							ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -strdSq.getMinorDevCost());
						} else if(strdSq.getCurrentMajorDevLevel() < strdSq.getMAX_MAJOR_DEV()) {
							ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -strdSq.getMajorDevCost());
						}
						
						// add an item to to the game history
						TurnLauncher.gameHistoryStorage.addMoveToHistory(activePlayer.getName(), chosenSq.getBoardPosition(), GameHistoryAction.DEVELOP_PORTFOLIO);
						
						
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
			UserInterface.clearConsole();

		} while (!finishedDeveloping);
	}

}

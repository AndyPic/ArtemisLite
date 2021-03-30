/**
 * 
 */
package uk.ac.qub.artemislite;

/**
 * Increases development level and modifies player resources accordingly
 * 
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class IncreaseElementDev {

	public void increaseElementDev(Board board, Player activePlayer) {

		// TODO also check if they have enough money to develop
		// Increase development level

		boolean finishedDeveloping = false;
		Element chosenSq;

		do {
			System.out.println("Which element would you like to develop?");

			chosenSq = board.getPlayerOwnedIndex(activePlayer, UserInput.getUserInputInt());

			for (Element element : board.getElements()) {

				if (chosenSq.equals(element) && element instanceof StandardElement) {
					StandardElement strdSq = (StandardElement) element;

					// check if system is fully owned
					if (board.systemFullyOwned(strdSq, activePlayer)) {


						if (strdSq.getCurrentMinorDevLevel() < strdSq.getMAX_MINOR_DEV()) {
							if (activePlayer.getBalanceOfResources() >= strdSq.getMinorDevCost()) {
								strdSq.increaseDev();
								strdSq.increaseRent();
								ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer,
										-strdSq.getMinorDevCost());
							} else {
								System.out.printf("Resources down to %d. That means you cannot develop further at this moment\n",activePlayer.getBalanceOfResources());
								finishedDeveloping = true;
							}
						} else if (strdSq.getCurrentMajorDevLevel() < strdSq.getMAX_MAJOR_DEV()) {
							if (activePlayer.getBalanceOfResources() >= strdSq.getMajorDevCost()) {
								strdSq.increaseDev();
								strdSq.increaseRent();
								ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer,
										-strdSq.getMajorDevCost());
							} else {
								System.out.printf("Resources down to %d. That means you cannot develop further at this moment\n",activePlayer.getBalanceOfResources());
								finishedDeveloping = true;
							}
						}

						// add an item to to the game history
						TurnLauncher.gameHistoryStorage.addMoveToHistory(activePlayer.getName(),
								chosenSq.getBoardPosition(), GameHistoryAction.DEVELOP_PORTFOLIO);

					} else {

						System.out.println("You do not yet own all elements in this system");
					}
				}
			}
			if (!finishedDeveloping) {
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
			}

		} while (!finishedDeveloping);
	}

}

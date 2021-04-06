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

	/**
	 * Displays possible development options and menu for user
	 * 
	 * @param board
	 * @param activePlayer
	 */
	public static void increaseElementDev(Board board, Player activePlayer) {

		boolean finishedDeveloping = false;
		StandardElement chosenElement;
		int devCost, userInput, numOfDevElements;

		do {
			
			numOfDevElements = board.availableForDev(activePlayer);

			System.out.println("Which element would you like to develop? (Enter 0 to stop developing)");

			userInput = UserInput.getUserInputInt();

			if (userInput == 0) {
				finishedDeveloping = true;
			} else if(userInput < 0 || userInput > numOfDevElements) {
				UserInterface.clearConsole();
				GameLauncher.mainHeadder();
				System.out.println("That is an invalid input, please try again");
			} else {
				
				UserInterface.clearConsole();
				GameLauncher.mainHeadder();

				chosenElement = board.getPlayerOwnedIndex(activePlayer, userInput);

				if (!board.systemFullyOwned(chosenElement, activePlayer)) {
					System.out.println("You do not yet own all elements in this system");

				} else if (chosenElement.isMaxDevelopment()) {
					System.out.println(
							"This element has already been fully constructed, you dont need to develop further!");

				} else if (!chosenElement.canAffordDev(activePlayer)) {
					System.out.printf("Resources down to %d. That means you cannot continue with R&D at this moment\n",
							activePlayer.getBalanceOfResources());
					finishedDeveloping = true;

				} else {
					devCost = chosenElement.getDevCost();
					chosenElement.increaseDev();
					chosenElement.increaseRent();
					ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -devCost);
				}

				// add an item to to the game history
				GameHistoryStorage.addMoveToHistory(activePlayer.getName(),
						chosenElement.getBoardPosition(), GameHistoryAction.DEVELOP_PORTFOLIO);

				if (board.allSystemComplete()) {
					finishedDeveloping = true;
					GameLauncher.declareGameOver();
				}

			}

		} while (!finishedDeveloping);
//		System.out.println("-----> CONTINUE <-----");
//		UserInput.getUserInputString();
	}

}

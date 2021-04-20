/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class TurnLauncher {

	// Constants

	private final int NUM_OF_DICE = 2;

	// Variables
	private boolean turnOver = false;
	private List<Player> players;
	private Player activePlayer;
	private Die die;
	private int turnNumber = 0;
	private LinkedHashMap<MenuOption, Boolean> menu;
	private final String CONTINUE_HEADER = "\n-----> CONTINUE <-----\n";

	// Constructors

	/**
	 * default constructor
	 */
	public TurnLauncher() {
		players = new ArrayList<Player>();
		this.die = new Die();
		this.menu = new LinkedHashMap<MenuOption, Boolean>();

		for (MenuOption menuOption : MenuOption.values()) {
			menu.put(menuOption, true);
		}

	}

	// Methods

	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the activePlayer
	 */
	public Player getActivePlayer() {
		return activePlayer;
	}

	/**
	 * @param activePlayer the activePlayer to set
	 */
	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}

	/**
	 * Adds a new player
	 */
	public void addPlayer() {

		Player player = new Player();
		promptName(player);
		players.add(player);
		UserInterface.clearConsole();

	}

	/**
	 * Prints all players to screen
	 */
	public void displayPlayers() {

		for (int loop = 1; loop <= players.size(); loop++) {
			System.out.println(loop + ". " + players.get(loop - 1).getName());
		}

	}

	/**
	 * Finds first player based on highest roll.
	 */
	public void findPlayerOrder() {

		Player firstToPlay;
		System.out.println(
				"Lets roll the dice to find out who plays first...\n\nHint: when you see --> <-- just press enter!\n");

		firstToPlay = allPlayersRoll(players);

		UserInterface.clearConsole();

		// Rearranges the player array so that the correct player is first
		while (firstToPlay != players.get(0)) {

			players.add(players.get(0));
			players.remove(0);

		}

		activePlayer = firstToPlay;
		UserInterface.clearConsole();
	}

	/**
	 * Method rolls the game dice and returns the result
	 * 
	 * @return int array containing dice rolls, last index is roll total
	 */
	public String rollDice() {

		String result;
		int totalRoll, currentRoll;

		result = " rolled a ";
		totalRoll = 0;

		for (int loop = 1; loop <= NUM_OF_DICE; loop++) {
			currentRoll = this.die.rollDie();
			totalRoll += currentRoll;

			if (loop == NUM_OF_DICE) {
				result += currentRoll + " " + "for a total of : " + totalRoll;
			} else {
				result += currentRoll + " and ";
			}
		}
		return result;
	}

	/**
	 * Converts string from dice roll into an int value
	 * 
	 * @param previous dice roll string
	 * @return int value of total roll
	 */
	public int getRollValue(String roll) {
		/*
		 * removes all non-digit chars and whitespace. Regex explanation: [^ ] = match
		 * any character not in the brackets \d = any digit + = match adjacent
		 * characters
		 */
		roll = roll.replaceAll("[^\\d]+", " ").trim();

		String[] rollArr = roll.split(" ");

		return Integer.parseInt(rollArr[rollArr.length - 1]);

	}

	/**
	 * Prompts user for name and validates against existing players
	 * 
	 * @param player name to add
	 */
	public void promptName(Player player) {
		String name;
		boolean valid;

		System.out.println("Please pick a name for your new company:");

		do {
			valid = true;
			name = UserInput.getUserInputString();

			if (name.trim().equals("")) {
				valid = false;
				System.out.println("That name is invalid, please enter a different name");
			}

			for (Player user : players) {
				if (!user.equals(player)) {
					if (user.getName().equalsIgnoreCase(name.trim())) {
						valid = false;
						System.out.println("A company with that name already exists, please enter something different");
					}
				}
			}

		} while (!valid);

		player.setName(name);

	}

	/**
	 * end game warning
	 * 
	 * @param boardPosition
	 */
	public void endGame() {
		System.out.println("Are you sure you want to shut down your company? this will end the game for all others");

		if (UserInterface.yesNoMenu() == 1) {
			GameLauncher.declareGameOver();
			// Set player bankrupt
			activePlayer.setBalanceOfResources(-1);
			GameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
					GameHistoryAction.QUIT);
		}

	}

	/**
	 * Modify or delete a player
	 */
	public void modifyPlayer() {

		int userInput = 0;
		boolean valid = false;
		Player player = null;
		String playerName;

		if (players.size() > 1) {
			do {
				System.out.println("=====| MODIFY COMPANY |=====");
				displayPlayers();
				userInput = UserInput.getUserInputInt() - 1;

				if (userInput >= 0 && userInput < players.size()) {
					valid = true;
				}

				if (!valid) {
					System.out.println("Your selection was invalid, please try again");
				}

			} while (!valid);
		}

		player = players.get(userInput);
		playerName = player.getName();
		valid = true;

		UserInterface.clearConsole();

		do {
			System.out.println("=====| COMPANY OPTIONS |=====" + "\n1. Rename \"" + playerName + "\"\n2. Delete \""
					+ playerName + "\"\n3. Go back");

			switch (UserInput.getUserInputInt()) {
			case 1:
				promptName(player);
				break;
			case 2:
				players.remove(userInput);
				System.out.println(playerName + " has been deleted");
				break;
			case 3:
				UserInterface.clearConsole();
				break;
			default:
				System.out.println("Invalid Menu Option, please try again");
				valid = false;
			}
		} while (!valid);

		UserInterface.clearConsole();
	}

	/**
	 * 
	 * Method to auction a element to all players, except the player auctioning it
	 * 
	 * @param reasonIndex     :
	 * 
	 *                        <pre>
	 *                        0 = "doesn't have enough staff-hours available to
	 *                        begin research." <br>
	 *                        1 = "decided not to invest time in the project." <br>
	 *                        2 = "wants to sell the element." <br>
	 * @param standardElement - The element being auctioned
	 */
	public void auctionElement(int reasonIndex, StandardElement standardElement, Board board) {

		String[] reasonsToAuction = new String[3];
		reasonsToAuction[0] = "doesn't have enough staff-hours available to begin research.";
		reasonsToAuction[1] = "decided not to invest time in the project.";
		reasonsToAuction[2] = "wants to sell the element.";

		int purchaseCost;
		String elementName, activePlayerName;
		Player highRollPlayer;
		// Arraylist of players that want the element
		List<Player> playersWant;

		highRollPlayer = null;
		purchaseCost = standardElement.getPurchaseCost();
		elementName = standardElement.getElementName();
		activePlayerName = activePlayer.getName();
		playersWant = new ArrayList<Player>();
		UserInterface.clearConsole();
		System.out.printf(
				"=====| AUCTION BEGINS |=====\nNASA has an obligation to get the element underway to ensure sucess of Artemis. \nThey have begun to look for new companies for %s research because %s %s\n",
				elementName, activePlayerName, reasonsToAuction[reasonIndex]);

		for (int loop = 0; loop < players.size(); loop++) {
			// Do nothing if player is active player
			if (players.get(loop).equals(activePlayer)) {
				continue;
			}
			// Check if player has enough resources to buy property
			if (players.get(loop).getBalanceOfResources() >= purchaseCost) {

				// board.viewMyElements(activePlayer);

				System.out.println("\n=====| ELEMENT DETAILS |=====");
				System.out.println(standardElement.toString());

				board.isSystemStarted(activePlayer, standardElement);

				// Ask player what they want to do
				System.out.printf(
						"\n%s: you currently have %d staff-hours remaining, you would need to allocate %d hours to begin research off %s. \nWould you like to proceed?\n",
						players.get(loop).getName().toUpperCase(), players.get(loop).getBalanceOfResources(),
						purchaseCost, elementName);

				// Add player responses to arraylist
				switch (UserInterface.yesNoMenu()) {
				case 1:
					playersWant.add(players.get(loop));
					UserInterface.clearConsole();
					System.out.printf("\n%s DOES want to be invloved in the researching of %s\n",
							players.get(loop).getName(), elementName);
					break;
				case 2:
					UserInterface.clearConsole();
					System.out.printf("\n%s DOES NOT want to be invloved in the researching of %s\n",
							players.get(loop).getName(), elementName);
					break;
				}

			} else {
				System.out.printf("\n%s doesn't have enough staff-hours remaining to take on the %s project\n",
						players.get(loop).getName(), elementName);
			}

		}

		// Check that at least 1 player wanted to buy the property
		if (playersWant.isEmpty()) {
			System.out.printf("\nNASA failed to secure a company to begin research on %s.\n", elementName);

		} else if (playersWant.size() == 1) {
			// If only 1 players wants it, then they are index 0
			highRollPlayer = playersWant.get(0);

		} else {
			// else roll dice to see who wins the property
			System.out.printf("\n%d companies have shown their intrest in %s\n\n", playersWant.size(), elementName);

			System.out.printf(
					"\nNASA have requrested a pitch from each company on why they should win the %s contract. Roll the dice to see how the pitch went!.\n",
					elementName);

			highRollPlayer = allPlayersRoll(playersWant);

		}

		// If someone wanted the element, do some maths
		if (highRollPlayer != null) {
			// Announce winner of auction
			System.out.printf("\n=====| WINNER: %s |=====\n", highRollPlayer.getName());

			// add move to the game history
			GameHistoryStorage.addMoveToHistory(highRollPlayer.getName(), standardElement.getBoardPosition(),
					GameHistoryAction.PURCHASE_THIS_ELEMENT_AT_AUCTION);

			// Update element ownership
			standardElement.setOwnedBy(highRollPlayer);

			if (board.systemFullyOwned(standardElement, highRollPlayer)) {
				GameHistoryStorage.addMoveToHistory(highRollPlayer.getName(), standardElement.getBoardPosition(),
						GameHistoryAction.STARTED_RESEARCH_ON_SYSTEM);
			}

			// Update player currency + Tell players what happened
			ModifyPlayerResources.modifyResourcesSinglePlayer(highRollPlayer, -purchaseCost);
			System.out.printf(
					"\n%s is now responsible for the research and development of %s, and has %d free staff-hours remaining.\n",
					highRollPlayer.getName(), elementName, highRollPlayer.getBalanceOfResources());
			if (reasonIndex == 2) {
				ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, purchaseCost);
				System.out.printf(
						"\n%s is no longer responsible for the research and development of %s, and has %d free staff-hours remaining.\n",
						activePlayerName, elementName, activePlayer.getBalanceOfResources());
			}
		}

	}// END

	/**
	 * Moves players on the game board based on dice roll
	 * 
	 * @param board
	 */
	public void moveMethod(Board board) {

		String roll, newElementName;
		int currentPos, newPos, totalElements;
		Element newElement;
		boolean completedLap;

		currentPos = activePlayer.getCurrentPosition();
		completedLap = false;

		System.out.printf("\nYou are currently visiting %s\n", board.getElements().get(currentPos).getElementName());
		System.out.println("\n-----> ROLL THE DICE <-----");
		UserInput.getUserInputString();
		UserInterface.clearConsole();

		roll = rollDice();

		newPos = activePlayer.getCurrentPosition() + getRollValue(roll);

		totalElements = board.getElements().size();

		if (newPos > (totalElements - 1)) {
			newPos -= totalElements;
			completedLap = true;
		}

		// Update player position
		activePlayer.setCurrentPosition(newPos);

		GameLauncher.mainHeadder();

		System.out.printf("You%s\n", roll);

		newElement = board.getElements().get(newPos);
		newElementName = newElement.getElementName();

		if (completedLap) {
			board.getResourceElement().giveInvestment(activePlayer);
		}

		board.viewMyElements(activePlayer);

		System.out.printf("\nYou have arrived at %s.", newElementName);

		if (newElement instanceof StandardElement) {
			StandardElement stdSrquare = (StandardElement) newElement;
			if (stdSrquare.getOwnedBy() != null) {
				System.out.printf(" Research & construction on this element is already underway by %s.\n",
						stdSrquare.getOwnedBy().getName());
			} else {
				System.out.printf(
						" No teams have started researching this element yet. NASA have predicted it would take %d to start initial research\n",
						stdSrquare.getPurchaseCost());
			}
		}

		System.out.println("\n=====| ELEMENT DETAILS |=====");
		System.out.println(newElement.toString());

	} // END

	/**
	 * checks the element for any responsibilities when landed on
	 */
	public void checkElement(Board board) {
		int pos;
		Element newElement;
		StandardElement standardElement;

		pos = activePlayer.getCurrentPosition();
		newElement = board.getElements().get(pos);

		// Check if element is standard
		if (newElement instanceof StandardElement) {

			standardElement = (StandardElement) newElement;

			// Check if the element is owned by someone already
			if (standardElement.getOwnedBy() != null) {
				chargeRent(standardElement);
			} else if (activePlayer.getBalanceOfResources() >= standardElement.getPurchaseCost()) {
				offerElement(standardElement, board);
			} else {
				// Only start auction if there is a player that can afford it
				for (int loop = 0; loop < players.size(); loop++) {
					if (players.get(loop).getBalanceOfResources() >= standardElement.getPurchaseCost()) {
						auctionElement(0, standardElement, board);
						break;
					} else if (loop == (players.size() - 1)) {
						System.out.printf("%s and no other companies have enough free hours to begin research %s.\n",
								activePlayer.getName(), standardElement.getElementName());
						// add a non-action move to gameHistory
						GameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
								GameHistoryAction.NO_ACTION);
					}
				}

			}

		}
			System.out.println(CONTINUE_HEADER);
			UserInput.getUserInputString();
			UserInterface.clearConsole();

	}

	/**
	 * Give player the option to charge rent from the active player
	 *
	 * @param standardElement
	 * 
	 */
	public void chargeRent(StandardElement standardElement) {
		String activePlayerName, elementOwnerName;
		int rentCost;
		Player elementOwner;

		activePlayerName = activePlayer.getName();
		rentCost = standardElement.getRentCost();
		elementOwner = standardElement.getOwnedBy();
		elementOwnerName = elementOwner.getName();

		if (elementOwner.equals(activePlayer)) {
			System.out.printf("%s, you have already started to research this element.\n", activePlayerName);
			return;
		}

		System.out.printf("PASS CONTROL TO [%s]\n\n", elementOwnerName.toUpperCase());

		if (activePlayer.getBalanceOfResources() <= rentCost) {
			System.out.printf(
					"You have the option to headhunt some engineers (+%d hours) from %s.\nYou have %d staff hours remaining but they only have %d hours. \nIf you hire these staff %s will fail to complete their own projects on time and ultimately the Artemis program will end. \n%s Are you prepared to let the mission fail for personal gain?\n",
					rentCost, activePlayerName, elementOwner.getBalanceOfResources(),
					activePlayer.getBalanceOfResources(), activePlayerName, elementOwnerName.toUpperCase());

		} else {
			System.out.printf(
					"You have the option to headhunt some engineers (+%d hours) from %s\nYou currently have %d hours remaining and %s has %d.\n would you like to hire their engineers?\n",
					rentCost, activePlayerName, elementOwner.getBalanceOfResources(), activePlayerName,
					activePlayer.getBalanceOfResources());
		}

		switch (UserInterface.yesNoMenu()) {
		case 1:
			// Take rent off active player
			ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -rentCost);
			// add move to game history
			GameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
					GameHistoryAction.FORFEIT_RESOURCES);

			// Give rent to element owner
			ModifyPlayerResources.modifyResourcesSinglePlayer(elementOwner, rentCost);
			if(!GameLauncher.getGameOver()) {
				System.out.printf(
						"%s took engineers from %s giving them an additional [%d hours].\n%s now has a total of [%d hours].\n%s now has a total of [%d hours].\n",
						elementOwnerName, activePlayerName, rentCost, elementOwnerName,
						elementOwner.getBalanceOfResources(), activePlayerName, activePlayer.getBalanceOfResources());
			}

			break;
		case 2:
			System.out.printf("%s chose to not take any engineers from %s.\n", elementOwnerName, activePlayerName);
			// add a non-action move to game history
			GameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
					GameHistoryAction.NO_ACTION);
			break;
		}

		if(!GameLauncher.getGameOver()) {
			System.out.printf("PASS CONTROL BACK TO %s\n", activePlayerName);
		}

	}

	/**
	 * Offers element to active player to purchase
	 * 
	 * @param StandardElement to be offered
	 */
	public void offerElement(StandardElement standardElement, Board board) {

		board.isSystemStarted(activePlayer, standardElement);
		// Offer player the element
		System.out.printf("You currently have %d hours remaining, would you like to begin researching the element?\n",
				activePlayer.getBalanceOfResources());

		switch (UserInterface.yesNoMenu()) {
		case 1:
			// Charge player for element
			ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -standardElement.getPurchaseCost());
			// add move to game history
			GameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
					GameHistoryAction.PURCHASE_THIS_ELEMENT);

			// Update element owner
			standardElement.setOwnedBy(activePlayer);

			if (board.systemFullyOwned(standardElement, activePlayer)) {
				GameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
						GameHistoryAction.STARTED_RESEARCH_ON_SYSTEM);
			}

			System.out.printf("%s has now begun R&D on %s, and has %d free hours remaining.\n", activePlayer.getName(),
					standardElement.getElementName(), activePlayer.getBalanceOfResources());
			break;
		case 2:
			// add a non-action move to game history
			GameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
					GameHistoryAction.DID_NOT_INVEST);
			// Auction the element, doesn't want to buy
			auctionElement(1, standardElement, board);
			break;
		}

	}

	/**
	 * Rolls dice for each player in an array and returns the highest scoring player
	 * 
	 * @param List of players to roll
	 * @return Player with highest dice roll
	 */
	public Player allPlayersRoll(Collection<Player> players) {

		String roll;
		Player highestRollPlayer;
		int highestRoll, playerRoll;
		boolean matchingRoll;

		List<Player> playersToRoll = new ArrayList<>(players);
		List<Player> playersToRemove = new ArrayList<>();

		highestRollPlayer = null;
		playerRoll = 0;
		matchingRoll = false;
		System.out.println("-----> ROLL THE DICE <-----");

		do {

			playersToRoll.removeAll(playersToRemove);

			highestRoll = 0;
			playersToRemove.clear();

			UserInput.getUserInputString();
			UserInterface.clearConsole();
			for (Player player : playersToRoll) {
				roll = rollDice();
				playerRoll = getRollValue(roll);

				System.out.println(player.getName() + " " + roll);

				if (playerRoll > highestRoll) {

					playersToRemove.add(highestRollPlayer);

					highestRollPlayer = player;
					highestRoll = playerRoll;
					matchingRoll = false;
				} else if (playerRoll == highestRoll) {

					matchingRoll = true;
				} else {
					playersToRemove.add(player);
				}

			}
			if (matchingRoll == true) {
				System.out.println("\n-----> DRAW, ROLL AGAIN <-----");
			}

		} while (matchingRoll);

		System.out.printf("\n=====| WINNER: %s |=====\n%s\n", highestRollPlayer.getName(), CONTINUE_HEADER);
		UserInput.getUserInputString();

		UserInterface.clearConsole();
		return highestRollPlayer;

	}

	/**
	 * Method that increments the calendar date and turnNumber by 1 and displays an
	 * end of round message to players.
	 */
	public void roundEnd() {

		turnNumber++;

		double progress = GameStatistics.missionProgress(GameLauncher.board);

		ArtemisCalendar.getCalendar().incrementDate();

		UserInterface.clearConsole(2);

		System.out.printf("Month %d has ended. The date is now %s, %d.\n", turnNumber,
				ArtemisCalendar.getMonthName(ArtemisCalendar.getCalendar().get(2)),
				ArtemisCalendar.getCalendar().get(1));
		if (progress > 75) {
			System.out.printf("Artemis Project Update: %.1f%s complete. You are getting close to completion!\n",
					progress, "%");
		} else if (progress > 50) {
			System.out.printf("Artemis Project Update: %.1f%s complete. We are over half way, keep up the good work\n",
					progress, "%");
		} else if (progress > 25) {
			System.out.printf(
					"Artemis Project Update: %.1f%s complete. Progress has been made but there is still a long way to go!\n",
					progress, "%");
		} else {
			System.out.printf(
					"Artemis Project Update: %.1f%s complete. You are still in the early stages, lets make this mission a success\n",
					progress, "%");
		}

		UserInterface.clearConsole(1);

	}

	/**
	 * @return the turnNumber
	 */
	public int getTurnNumber() {
		return turnNumber;
	}

	/**
	 * Method to end the current players turn.
	 * 
	 * @param board
	 */
	public void endTurn() {

		int activePlayerIndex = players.indexOf(activePlayer);
		if (!GameLauncher.getGameOver()) {
			if (activePlayerIndex != players.size() - 1) {
				setActivePlayer(players.get(activePlayerIndex + 1));
			} else {
				setActivePlayer(players.get(0));
				roundEnd();
				System.out.println(CONTINUE_HEADER);
				UserInput.getUserInputString();
			}
		}

		turnOver = true;

	}

	/**
	 * @return the endTurn
	 */
	public boolean isTurnOver() {
		return turnOver;
	}

	/**
	 * Displays the menu for a players turn
	 * 
	 * @param board
	 */
	public void playerTurnMenu(Board board) {
		turnOver = false;
		boolean firstMenuOfTurn = true;
		while (!turnOver) {
			int menuNum, userInput;
			boolean validUserInput;
			MenuOption userMenuSelection;
			List<MenuOption> keysList = new ArrayList<MenuOption>();

			if (firstMenuOfTurn) {
				GameLauncher.mainHeadder();
				board.viewMyElements(activePlayer);
			}

			checkPossibleMenuOptions(board);

			menuNum = 1;
			validUserInput = false;

			System.out.printf("\nIt's still your turn %s! Please select one of the following options:\n%s",
					activePlayer.getName(), GameLauncher.getMenuHeader());

			for (Entry<MenuOption, Boolean> option : menu.entrySet()) {
				if (option.getValue()) {
					System.out.printf("%s. %s\n", menuNum++, option.getKey().getMenuOption());
					keysList.add(option.getKey());
				}

			}

			do {
				userInput = UserInput.getUserInputInt();

				if (userInput > 0 && userInput <= keysList.size()) {
					validUserInput = true;
				} else {
					System.out.println("Invalid option - try again");
				}

			} while (!validUserInput);

			userMenuSelection = keysList.get(userInput - 1);

			UserInterface.clearConsole();

			if (userMenuSelection.equals(MenuOption.VIEW_ALL_ELEMENTS)) {
				GameLauncher.mainHeadder();
				System.out.printf("\nAll game elements:\n\n");
				board.viewElementOwnership();
			} else if (userMenuSelection.equals(MenuOption.VIEW_PLAYER_ELEMENTS)) {
				GameLauncher.mainHeadder();
				board.viewMyElementsDetails(activePlayer);
			} else if (userMenuSelection.equals(MenuOption.GET_ELEMENT_DETAILS)) {
				GameLauncher.mainHeadder();
				System.out.printf("\nYou are currently on : ");
				activePlayer.getCurrentPositionDetails(board);
			} else if (userMenuSelection.equals(MenuOption.INCREASE_DEVELOPMENT)) {
				GameLauncher.mainHeadder();
				IncreaseElementDev.increaseElementDev(board, activePlayer);

			} else if (userMenuSelection.equals(MenuOption.AUCTION_ELEMENT)) {

				GameLauncher.mainHeadder();
				playerInitiatedAuction(board);

			} else if (userMenuSelection.equals(MenuOption.END_TURN)) {
				endTurn();
			} else if (userMenuSelection.equals(MenuOption.END_GAME)) {
				endGame();
			}

			firstMenuOfTurn = false;

		}

		UserInterface.clearConsole();

	}

	/**
	 * Method to hold an auction, initiated by the player
	 * 
	 * @param board
	 */
	public void playerInitiatedAuction(Board board) {

		int userInput, numElements;
		boolean finishedAuction = false;
		StandardElement chosenElement;

		do {
			chosenElement = null;
			numElements = board.availableForAuction(activePlayer).size();
			board.listAuctionElements(board.availableForAuction(activePlayer), activePlayer);
			
			if (numElements == 0) {
				finishedAuction = true;
				continue;
			}

			System.out.println("Which element would you like to auction? (Enter 0 to return)");
			userInput = UserInput.getUserInputInt();

			if (userInput == 0) {
				finishedAuction = true;
			} else if (userInput < 0 || userInput > numElements) {
				UserInterface.clearConsole();
				GameLauncher.mainHeadder();
				System.out.println("That is an invalid input, please try again");
			} else {

				chosenElement = board.getPlayerElement(activePlayer, userInput);

				auctionElement(2, chosenElement, board);

			}

		} while (!finishedAuction);

	}

	public void checkPossibleMenuOptions(Board board) {

		boolean canDevelop = false;
		boolean ownsElement = false;
		boolean canAuction = false;

		for (StandardElement stdElement : board.getStdElements()) {

			if (stdElement.isOwnedBy(activePlayer)) {
				ownsElement = true;
			}
			
			// Check if any auctionable elements
			if (board.availableForAuction(activePlayer).size() > 0) { 
				canAuction = true;
			} 

			if (stdElement.isOwnedBy(activePlayer) && !stdElement.isMaxDevelopment()) {
				if (board.systemFullyOwned(stdElement, activePlayer)) {
					canDevelop = true;
				}
			}

		}
		menu.put(MenuOption.INCREASE_DEVELOPMENT, canDevelop);
		menu.put(MenuOption.VIEW_PLAYER_ELEMENTS, ownsElement);
		menu.put(MenuOption.AUCTION_ELEMENT, canAuction);

	}

}

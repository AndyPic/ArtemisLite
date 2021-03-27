/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
	private final int RESOURCE_VALUE_LONG_GAME = -100;

	// Variables

	protected static GameHistoryStorage gameHistoryStorage = new GameHistoryStorage();
	private boolean turnOver = false;
	private static ArrayList<Player> players;
	private Player activePlayer;
	private Die die;
	private int turnNumber = 0;
	// view all element, view my element, get element detail, increase dev level,
	// end
	// turn, end game
	private LinkedHashMap<MenuOption, Boolean> menu;
	private final static String CONTINUE_HEADER = "\n-----> CONTINUE <-----\n";

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
//	
//	for (ElementDetails elementDetails : ElementDetails.values()) {
//		SystemType systemType = elementDetails.getSystem();
//		switch (systemType) {

	// Methods

	/**
	 * @return the gameHistoryStorage
	 */
	public static GameHistoryStorage getGameHistoryStorage() {
		return gameHistoryStorage;
	}

	/**
	 * @return the players
	 */
	public static ArrayList<Player> getPlayers() {
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

		System.out.println("Please enter your company name:");

		do {
			valid = true;
			name = UserInput.getUserInputString();
			// TODO: is there any other name validation needed? JD
			if (players != null) {

				if (name.equals("")) {
					valid = false;
					System.out.println("That name is invalid, please enter a different name");
				}

				for (Player user : players) {
					if (!user.equals(player)) {
						if (user.getName().equalsIgnoreCase(name.trim())) {
							valid = false;
							System.out.println(
									"A company with that name already exists, please enter something different");
						}
					}
				}

			}

		} while (!valid);

		player.setName(name);

	}

	/**
	 * end game warning
	 */
	public void endGame() {
		// TODO: need to think of better text for here
		System.out.println("Are you sure you want to declare bankruptcy and end the game?");

		if (UserInterface.yesNoMenu() == 1) {
			turnOver = true;
			GameLauncher.declareGameOver();
			// Set player bankrupt
			ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -activePlayer.getBalanceOfResources());
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
	 * Decreases the initial value of resources for all players for a longer game
	 */
	public void setupLongGame() {
		// TODO: this should also reduce the resources gained on each lap of the board?
		// JD
		ModifyPlayerResources.modifyResourcesAllPlayers(players, this.RESOURCE_VALUE_LONG_GAME);
	}

	/**
	 * 
	 * Method to auction a element to all players, except the player auctioning it
	 * 
	 * @param reasonToAuction - eg. "not enough resources"
	 * @param standardElement - The element being auctioned
	 */
	public void auctionElement(String reasonToAuction, StandardElement standardElement) {

		int purchaseCost;
		String elementName, activePlayerName;
		// Default to the active player
		Player highRollPlayer;
		// Arraylist of players that want the element
		ArrayList<Player> playersWant;

		highRollPlayer = null;
		purchaseCost = standardElement.getPurchaseCost();
		elementName = standardElement.getElementName();
		activePlayerName = activePlayer.getName();
		playersWant = new ArrayList<Player>();
		UserInterface.clearConsole();
		System.out.printf(
				"=====| AUCTION BEGINS |=====\nNASA has an obligation to get the element underday to ensure sucess of Artemis. \nThey have begun to look for new companies for %s research because %s %s\n",
				elementName, activePlayerName, reasonToAuction);

		for (int loop = 0; loop < players.size(); loop++) {
			// Do nothing if player is active player
			if (players.get(loop) != activePlayer) {
				// Check if player has enough resources to buy property
				if (players.get(loop).getBalanceOfResources() >= purchaseCost) {
					// Ask player what they want to do
					System.out.printf(
							"\n%s: you currently have %d staff-hours remaining, you would need to allocate %d hours to begin research off %s. Would you like to proceed?\n",
							players.get(loop).getName().toUpperCase(), players.get(loop).getBalanceOfResources(),
							purchaseCost, elementName);

					// Add player responses to arraylist
					switch (UserInterface.yesNoMenu()) {
					case 1:
						playersWant.add(players.get(loop));
						System.out.printf("\n%s DOES want to be invloved in the researching of %s\n",
								players.get(loop).getName(), elementName);
						break;
					case 2:
						System.out.printf("\n%s DOES NOT want to be invloved in the researching of %s\n",
								players.get(loop).getName(), elementName);
						break;
					}

				} else {
					System.out.printf("\n%s doesn't have enough staff-hours remaining to take on the %s project\n",
							players.get(loop).getName(), elementName);
				}
			}
		}

		// Check that at least 1 player wanted to buy the property
		if (playersWant.isEmpty()) {
			// No one wanted it
			System.out.printf("\nNASA failed to secure a company to begin research on %s.\n", elementName);

		} else if (playersWant.size() == 1) {
			// If only 1 players wants it, then they are index 0
			highRollPlayer = playersWant.get(0);

		} else {
			// else roll dice to see who wins the property
			System.out.printf("\n%d companies have shown thier intrest in %s\n\n", playersWant.size(), elementName);

			System.out.printf(
					"\nNASA have requrested a pitch from each company on why they should win the %s contract. Roll the dice to see how the pitch went!.\n",
					elementName);

			highRollPlayer = allPlayersRoll(playersWant);

		}

		// If someone wanted the element, do some maths
		if (highRollPlayer != null) {
			// Announce winner of auction
			System.out.printf("\n=====| WINNER: %s |=====\n", highRollPlayer.getName());

			// Update player currency
			ModifyPlayerResources.modifyResourcesSinglePlayer(highRollPlayer, -purchaseCost);
			// add move to the game history
			gameHistoryStorage.addMoveToHistory(highRollPlayer.getName(), highRollPlayer.getCurrentPosition(),
					GameHistoryAction.PURCHASE_THIS_ELEMENT_AT_AUCTION);

			// Update element ownership
			standardElement.setOwnedBy(highRollPlayer);

			// Tell players what happened
			System.out.printf(
					"\n%s is now responsible for the research and development of %s, and has %d free staff-hours remaining.\n",
					highRollPlayer.getName(), elementName, highRollPlayer.getBalanceOfResources());
		}

		// TODO: update UI for auction winner

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
		// TODO: we somehow need to workout how to show the player more info about what
		// elements are ahead so they feel like they are actually playing a game and not
		// just hitting roll dice JD
		System.out.println("\n-----> ROLL THE DICE <-----");
		UserInput.getUserInputString();
		UserInterface.clearConsole();

		roll = rollDice();

		newPos = activePlayer.getCurrentPosition() + getRollValue(roll);

		totalElements = board.getElements().size();

		// TODO: needs tested
		if (newPos > (totalElements - 1)) {
			newPos -= totalElements;
			completedLap = true;
		}

		// Update player position
		activePlayer.setCurrentPosition(newPos);

		GameLauncher.mainHeadder();

		System.out.printf("\nYou%s\n", roll);

		if (completedLap) {
			// TODO Resources for passing GO - needs doing properly!
			System.out.println("\nYou passed GO +200 resources woooop!\n");
			ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, 200);
		}

		newElement = board.getElements().get(newPos);
		newElementName = newElement.getElementName();

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
		// TODO: we need the details shown to the player to be dynamic, currently all
		// details are shown. No point showing dev costs of a element if it is already
		// owned by another player JD
		// TODO: should find some way to display all rent costs for a element rather
		// than
		// just the current one JD
		System.out.println(newElement.toString());

	} // END

	/**
	 * checks the element for any responsibilities when landed on
	 */
	public void checkElement(Board board) {
		int pos;
		Element newElement;

		pos = activePlayer.getCurrentPosition();
		newElement = board.getElements().get(pos);

		// Check if element is standard
		if (newElement instanceof StandardElement) {

			StandardElement standardElement = (StandardElement) newElement;

			// Check if the element is owned by someone already
			if (standardElement.getOwnedBy() != null) {
				chargeRent(standardElement);
			} else if (activePlayer.getBalanceOfResources() >= standardElement.getPurchaseCost()) {
				offerElement(standardElement);
			} else {
				// Only start auction if there is a player that can afford it
				for (int loop = 0; loop < players.size(); loop++) {
					if (players.get(loop).getBalanceOfResources() >= standardElement.getPurchaseCost()) {
						auctionElement("doesn't have enough staff-hours available to begin research.", standardElement);
						break;
					} else if (loop == (players.size() - 1)) {
						System.out.printf("%s and no other player have enough RESOURCES to begin research %s.\n",
								activePlayer.getName(), standardElement.getElementName());
						// add a non-action move to gameHistory
						gameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
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
	 * @throws BankruptException whenever the deduction of resources causes the
	 *                           player balance to become negative. Exception caught
	 *                           in Admin
	 */
	public void chargeRent(StandardElement standardElement) throws BankruptException {
		String activePlayerName, elementOwnerName;
		int rentCost;
		Player elementOwner;

		activePlayerName = activePlayer.getName();
		rentCost = standardElement.getRentCost();
		elementOwner = standardElement.getOwnedBy();
		elementOwnerName = elementOwner.getName();

		if (elementOwner == activePlayer) {
			System.out.printf("%s, you have already started to research this element.\n", activePlayerName);
			return;
		}

		System.out.printf("PASS CONTROL TO [%s]\n\n", elementOwnerName.toUpperCase());

		if (activePlayer.getBalanceOfResources() <= rentCost) {
			// TODO Better message here, implement game end?
			System.out.printf(
					"You have the option to headhunt some engineers (+%d hours) from %s.\nYou have %d staff hours remaining but they only have %d hours. \nIf you hire these staff %s will fail to complete thier own projects on time and ultimately the Artemis program will end. \n%s Are you prepared to let the mission fail for personal gain?\n",
					rentCost, activePlayerName, elementOwner.getBalanceOfResources(),
					activePlayer.getBalanceOfResources(), activePlayerName, elementOwnerName.toUpperCase());

		} else {
			System.out.printf(
					"You have the option to headhunt some engineers (+%d hours) from %s\nYou currently have %d hours remaining and %s has %d.\n would you like to hire thier engineers?\n",
					rentCost, activePlayerName, elementOwner.getBalanceOfResources(), activePlayerName,
					activePlayer.getBalanceOfResources());
		}

		switch (UserInterface.yesNoMenu()) {
		case 1:
			// Take rent off active player
			ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -rentCost);
			// add move to game history
			gameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
					GameHistoryAction.FORFEIT_RESOURCES);

			// Give rent to element owner
			ModifyPlayerResources.modifyResourcesSinglePlayer(elementOwner, rentCost);
			System.out.printf("%s took engineers from %s giving them an additional [%d hours].\n%s now has a total of [%d hours].\n%s now has a total of [%d hours].\n", elementOwnerName,
					activePlayerName, rentCost, elementOwnerName, elementOwner.getBalanceOfResources(),
					activePlayerName, activePlayer.getBalanceOfResources());
			break;
		case 2:
			System.out.printf("%s chose to not take any engineers from %s.\n", elementOwnerName, activePlayerName);
			// add a non-action move to game history
			gameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
					GameHistoryAction.NO_ACTION);
			break;
		}
		
		System.out.printf("PASS CONTROL BACK TO %s\n", activePlayerName);

	}

	/**
	 * Offers element to active player to purchase
	 * 
	 * @param StandardElement to be offered
	 */
	public void offerElement(StandardElement standardElement) {
		// Offer player the element
		System.out.printf(
				// TODO rename resources to whatever we decide to call it
				"You currently have %d hours remaining, would you like to begin researching the element?\n",
				activePlayer.getBalanceOfResources());

		switch (UserInterface.yesNoMenu()) {
		case 1:
			// Charge player for element
			ModifyPlayerResources.modifyResourcesSinglePlayer(activePlayer, -standardElement.getPurchaseCost());
			// add move to game history
			gameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
					GameHistoryAction.PURCHASE_THIS_ELEMENT);

			// Update element owner
			standardElement.setOwnedBy(activePlayer);
			// TODO resources name
			System.out.printf("%s has now begun R&D on %s, and has %d free hours remaining.\n", activePlayer.getName(),
					standardElement.getElementName(), activePlayer.getBalanceOfResources());
			break;
		case 2:
			// Auction the element, doesn't want to buy
			auctionElement("decided not to invest time in the project.", standardElement);
			// add a non-action move to game history
			gameHistoryStorage.addMoveToHistory(activePlayer.getName(), activePlayer.getCurrentPosition(),
					GameHistoryAction.NO_ACTION);
			break;
		}

	}

	/**
	 * Rolls dice for each player in an array and returns the highest scoring player
	 * 
	 * @param arrayList of players to roll
	 * @return Player with highest dice roll
	 */
	public Player allPlayersRoll(ArrayList<Player> playersToRoll) {
		String roll;
		int highestRoll, playerRoll;
		Player highestRollPlayer;
		boolean matchingRoll;

		playerRoll = 0;
		highestRollPlayer = playersToRoll.get(0);
		matchingRoll = false;
		// TODO: do you want this so each player has to press to roll dice? or just 1
		// press rolls for all players JD
		System.out.println("-----> ROLL THE DICE <-----");

		do {
			highestRoll = 0;

			UserInput.getUserInputString();
			UserInterface.clearConsole();
			for (Player player : playersToRoll) {
				roll = rollDice();
				playerRoll = getRollValue(roll);

				System.out.println(player.getName() + " " + roll);

				if (playerRoll > highestRoll) {
					highestRoll = playerRoll;
					highestRollPlayer = player;
					matchingRoll = false;
				} else if (playerRoll == highestRoll) {
					matchingRoll = true;
				}

			}
			if (matchingRoll == true) {
				System.out.println("\n-----> DRAW, ROLL AGAIN <-----");
			}

		} while (matchingRoll);
		// TODO: do you think using "--> <--" every time the player need to press enter
		// is
		// intuitive enough? or do we need to write "press enter" every time
		System.out.printf("\n=====| WINNER: %s |=====\n%s\n", highestRollPlayer.getName(), CONTINUE_HEADER);
		UserInput.getUserInputString();

		UserInterface.clearConsole();
		return highestRollPlayer;

	}

	/**
	 * Method that increments the calendar date and turnNumber by 1 and displays an
	 * end of round message to players.
	 */
	public void roundEnd(Board board) {

		double progress = GameStatistics.missionProgress(board);

		ArtemisCalendar.getCalendar().incrementDate();

		UserInterface.clearConsole(2);

		System.out.printf("Round %d has ended. The date is now %s, %d.\n", turnNumber,
				ArtemisCalendar.getMonthName(ArtemisCalendar.getCalendar().get(2)),
				ArtemisCalendar.getCalendar().get(1));
		if (progress > 0) {
			System.out.printf("The Artemis Project is %.1f%s complete.\n", progress, "%");
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
	public void endTurn(Board board) {

		int activePlayerIndex = players.indexOf(activePlayer);

		if (activePlayerIndex != players.size() - 1) {
			setActivePlayer(players.get(activePlayerIndex + 1));
		} else {
			setActivePlayer(players.get(0));
			roundEnd(board);
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
			// TODO: refactor needed JD
			int menuNum, userInput;
			boolean validUserInput;
			MenuOption userMenuSelection;
			ArrayList<MenuOption> keysList = new ArrayList<MenuOption>();

			if (firstMenuOfTurn) {
				GameLauncher.mainHeadder();
				board.viewMyElements(activePlayer);
			}

			checkPossibleMenuOptions(board);

			menuNum = 1;
			validUserInput = false;

			System.out.printf("\nIts still your turn! Please select one of the following options:\n%s",
					GameLauncher.getMenuHeader());

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

			// surround with try / catch to catch BankruptcyException when modifying player
			// resources would result in a negative balance
			try {
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
					board.viewMyElements(activePlayer);
					IncreaseElementDev id = new IncreaseElementDev();
					id.increaseElementDev(board, activePlayer);

				} else if (userMenuSelection.equals(MenuOption.END_TURN)) {
					endTurn(board);
				} else if (userMenuSelection.equals(MenuOption.END_GAME)) {
					endGame();
				}

			} catch (BankruptException bankruptExc) {
				// declare the game over at a BankruptException
				bankruptExc.getLocalizedMessage();
				GameLauncher.declareGameOver();
			}

			firstMenuOfTurn = false;

		}

		UserInterface.clearConsole();

	}

	public void checkPossibleMenuOptions(Board board) {

		boolean canDevelop = false;
		boolean ownsElement = false;
		for (Element element : board.getElements()) {
			if (element instanceof StandardElement) {
				StandardElement stdElement = (StandardElement) element;

				if (stdElement.isOwnedBy(activePlayer)) {
					ownsElement = true;
				}

				if (stdElement.isOwnedBy(activePlayer) && !stdElement.isMaxDevelopment()) {
					if(board.systemFullyOwned(stdElement, activePlayer)) {
						canDevelop = true;
					}
				}

			}
		}
		menu.put(MenuOption.INCREASE_DEVELOPMENT, canDevelop);
		menu.put(MenuOption.VIEW_PLAYER_ELEMENTS, ownsElement);

	}

}

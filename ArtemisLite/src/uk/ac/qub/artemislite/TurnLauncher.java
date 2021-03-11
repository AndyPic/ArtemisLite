/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.ArrayList;

/**
 * @author Jordan Davis
 *
 */
public class TurnLauncher {

	// TODO: This should prob be private to decrease coupling JD
	protected ArrayList<Player> players;

	private Die die;

	private final int NUM_OF_DICE = 2;

	/**
	 * default constructor
	 */
	public TurnLauncher() {
		this.players = new ArrayList<Player>();
		this.die = new Die();
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Adds a new player
	 */
	public void addPlayer() {

		Player player = new Player();

		System.out.println("Please enter your name");
		player.setName(UserInput.getUserInputString());

		players.add(player);
		
		GUI.clearConsole(4);

	}

	/**
	 * Prints all players to screen
	 */
	public void displayPlayers() {

		for(int loop =1; loop<=this.players.size(); loop++) {
			System.out.println(loop+". "+players.get(loop-1).getName());
		}

	}

	/**
	 * Finds first player based on highest roll.
	 */
	public void findPlayerOrder() {

		int highestRoll, playerRoll;
		Player firstToPlay;
		String roll;
		boolean matchingRoll;

		highestRoll = 0;
		playerRoll = 0;
		firstToPlay = this.players.get(0);
		matchingRoll = false;

		GUI.clearConsole(4);
		System.out.println("Its time to find out who goes first...\nPress enter to roll the dice!");
		UserInput.getUserInputString();
		GUI.clearConsole(4);

		do {
			
			for (Player player : this.players) {
				roll = rollDice();
				playerRoll = getRollValue(roll);

				System.out.println(player.getName() + " " + roll);

				if (playerRoll > highestRoll) {
					highestRoll = playerRoll;
					firstToPlay = player;
					matchingRoll = false;
				} else if(playerRoll == highestRoll) {
					matchingRoll = true;
					
				}

			}
			
			if(matchingRoll == true) {
				System.out.println("\nThe roll was a draw, lets try again. Please press enter to roll");
				UserInput.getUserInputString();
			}

		} while (matchingRoll);

		System.out.println("\n"+firstToPlay.getName()+" is first to play with the highest roll of " +highestRoll+"\nPress enter to continue");
		UserInput.getUserInputString();
		
		// rearanges the player array so that the correct player is first
		while (firstToPlay != this.players.get(0)) {

			this.players.add(this.players.get(0));
			this.players.remove(0);

		}

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

		// removes all non-digit chars and whitespace
		roll = roll.replaceAll("[^\\d]+", " ").trim();
		
		String[] rollArr = roll.split(" ");

		return Integer.parseInt(rollArr[rollArr.length - 1]);

	}
	
	
	public void modifyPlayer() {
		
		int userInput;
		boolean valid = false;
		
		do {
			System.out.println("Select a player to modify:");
			displayPlayers();
			userInput = UserInput.getUserInputInt()-1;

			if(userInput>=0 && userInput<this.players.size()) {
				valid = true;
			}
			
			if(!valid) {
				System.out.println("Your selection was invalid, please try again");
			}
			
		} while (!valid);
		
		Player player = this.players.get(userInput);

		System.out.println("What would you like to do with "+player.getName()+"?\n1. Modify Name\n2. Delete Player\n3. Go back");
		
		do {
			
			switch(UserInput.getUserInputInt()) {
			case 1:
				System.out.println("Please enter the new name");
				player.setName(UserInput.getUserInputString());
				break;
			case 2:
				this.players.remove(userInput);
				System.out.println("Player has been deleted");
				break;
			case 3:
				break;			
			default:
				System.out.println("Invalid Menu Option, please try again");
				valid = false;
			}
			System.out.println("loop end");
		} while(!valid);
		System.out.println("end");
	}
	

}





























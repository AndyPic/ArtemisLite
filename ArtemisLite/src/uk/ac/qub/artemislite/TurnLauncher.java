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

	protected ArrayList<Player> players = new ArrayList<Player>();
	
	
	/**
	 * default constructor
	 */
	public TurnLauncher() {
		
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
		
	}
	
	/**
	 * Prints all players to screen
	 */
	public void displayPlayers() {
		
		for(Player player : players) {
			System.out.println(player.getName());
		}
		System.out.println();
		
	}
	
	
	
}

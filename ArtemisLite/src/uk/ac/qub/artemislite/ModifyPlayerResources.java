package uk.ac.qub.artemislite;

import java.util.ArrayList;


/**
 * Modifies the resource balance of one or all players
 * 
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class ModifyPlayerResources {
	
	Player player;
	ArrayList<Player> players;
	
	/**
	 * Modifies the resources of a single player
	 * @param player
	 * @param resourceValue the resource value, positive or negative
	 */
	public static void modifyResourcesSinglePlayer (Player player, int resourceValue) {
		int newBalance = player.getBalanceOfResources() + resourceValue;
		player.setBalanceOfResources(newBalance);
	}
	
	/**
	 * Modifies the resources of all players at once
	 * @param players
	 * @param resourceValue - the value of the resources
	 */
	public static void modifyResourcesAllPlayers(ArrayList<Player> players, int resourceValue) {
		int newBalance = 0;
		for(Player player: players) {
			newBalance = player.getBalanceOfResources() + resourceValue;
			player.setBalanceOfResources(newBalance);
		}
	}
	

}

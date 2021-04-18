package uk.ac.qub.artemislite;

import java.util.List;


/**
 * Modifies the resource balance of one or all players
 * 
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */
public class ModifyPlayerResources {
	
	/**
	 * Modifies the resources of a single player. Calls declareGameOver if a player's resources fall below zero
	 * @param player
	 * @param resourceValue the resource value, positive or negative
	 * 
	 */
	public static void modifyResourcesSinglePlayer (Player player, int resourceValue) {
		int newBalance = player.getBalanceOfResources() + resourceValue;
		if (newBalance < 0) {
			// this is a game-over state!
			System.out.printf("%s has run out of available hours to complete thier projects and they have caused the Artemis project to fail!", player.getName());
			GameLauncher.declareGameOver();
		} else {
			player.setBalanceOfResources(newBalance);
		}
		
		
	}
	
	/**
	 * Modifies the resources of all players at once. Calls declareGameOver if a player's resources fall below zero
	 * @param players
	 * @param resourceValue - the value of the resources
	 * 
	 */
	public static void modifyResourcesAllPlayers(List<Player> players, int resourceValue) {
		int newBalance = 0;
		for(Player player: players) {
			newBalance = player.getBalanceOfResources() + resourceValue;
			if (newBalance < 0) {
				// this is a game-over state!
				System.out.printf("%s has run out of available hours to complete thier projects and they have caused the Artemis project to fail!");
				GameLauncher.declareGameOver();
			} else {
				player.setBalanceOfResources(newBalance);
			}
		}
	}
	

}

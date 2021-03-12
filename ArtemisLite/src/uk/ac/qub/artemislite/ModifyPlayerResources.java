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
	 * @throws BankruptException if player's resources fall below zero as a result of this action
	 */
	public static void modifyResourcesSinglePlayer (Player player, int resourceValue) throws BankruptException {
		int newBalance = player.getBalanceOfResources() + resourceValue;
		if (newBalance < 0) {
			// this is a game-over state! - need way of reaching Admin.GAME_OVER to set its state as true
			// throw new exception as reminder!!
			throw new BankruptException("Player: " + player.getName() + " is bankrupt!");
		} else {
			player.setBalanceOfResources(newBalance);
		}
		
		
	}
	
	/**
	 * Modifies the resources of all players at once
	 * @param players
	 * @param resourceValue - the value of the resources
	 * @throws BankruptException if player's resources fall below zero as a result of this action
	 */
	public static void modifyResourcesAllPlayers(ArrayList<Player> players, int resourceValue) throws BankruptException {
		int newBalance = 0;
		for(Player player: players) {
			newBalance = player.getBalanceOfResources() + resourceValue;
			if (newBalance < 0) {
				// this is a game-over state! need way of reaching Admin.GAME_OVER to set its state as true
				// throw new exception as reminder!!
				throw new BankruptException("Player: " + player.getName() + " is bankrupt!");
			} else {
				player.setBalanceOfResources(newBalance);
			}
		}
	}
	

}

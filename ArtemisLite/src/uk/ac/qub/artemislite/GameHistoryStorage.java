package uk.ac.qub.artemislite;

import java.util.ArrayList;

/**
 * Keeps a history of all game landing squares, and the action taken on those squares
 * @author Jordan Davis
 * @author David Finlay
 * @author Joseph Mawhinney
 * @author Andrew Pickard
 */

public class GameHistoryStorage {
	
	private static GameHistoryItem ghi;

	private static ArrayList<GameHistoryItem> gameHistory = new ArrayList<GameHistoryItem>();
	
	
	/**
	 * default constructor
	 */
	public GameHistoryStorage() {
		
	}
	
	/**
	 * adds a game history item to the game history
	 * @param gameHistoryItem
	 */
	public void addMoveToHistory(String playerName, int boardLandingPosition, GameHistoryAction gameHistoryAction) {
		// create a history item object
		
		ghi = new GameHistoryItem(playerName, boardLandingPosition, gameHistoryAction);
		
		// add this history item to the game history
		gameHistory.add(ghi);
	}
	
	/**
	 * outputs to console each gameHistoryItem in the gameHistory arrayList
	 */
	public void displayMoveHistory() {
		System.out.println("Game Move History...");
		for (GameHistoryItem gameHistoryItem: gameHistory) {
			gameHistoryItem.displayAll();
		}
	}
	
	
}

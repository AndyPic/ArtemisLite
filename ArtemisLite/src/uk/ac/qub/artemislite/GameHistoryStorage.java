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
	 * adds a game history item to the game history
	 * @param gameHistoryItem
	 */
	public static void addMoveToHistory(String playerName, int boardLandingPosition, GameHistoryAction gameHistoryAction, int diceRollTotal) {
		// create a history item object
		
		ghi = new GameHistoryItem(playerName, boardLandingPosition, gameHistoryAction, diceRollTotal);
		
		// add this history item to the game history
		gameHistory.add(ghi);
	}
	
	/**
	 * outputs to console each gameHistoryItem in the gameHistory arrayList
	 */
	public static void displayMoveHistory() {
		for (GameHistoryItem gameHistoryItem: gameHistory) {
			gameHistoryItem.displayAll();
		}
	}
	
	
}

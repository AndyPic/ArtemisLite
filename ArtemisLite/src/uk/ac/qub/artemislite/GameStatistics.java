package uk.ac.qub.artemislite;

import java.util.Collections;
import java.util.List;

public class GameStatistics {

	// Methods

	/**
	 * Calculates the total price to purchase and fully upgrade every element on the
	 * game board.
	 * 
	 * @return
	 */
	public static double totalElementCost() {

		// Get resource amount for completion
		double totalCost = 0;

		for (ElementDetails element : ElementDetails.values()) {
			totalCost += element.getCost();
			totalCost += element.getMajorCost();
			totalCost += (element.getMinorCost() * 3);
		}

		return totalCost;
	} // END

	/**
	 * Calculates the current amount of resources contributed toward completing the
	 * game (ie. purchasing and upgrading elements) from all players.
	 * 
	 * @param board
	 * @return
	 */
	public static double overallElementInvestment(Board board) {
		// Get resource amount currently invested
		double currentProgress = 0;

		for (StandardElement stdElement : board.getStdElements()) {

			if (stdElement.getOwnedBy() != null) {
				currentProgress += stdElement.getPurchaseCost();
				currentProgress += (stdElement.getCurrentMajorDevLevel() * stdElement.getMajorDevCost());
				currentProgress += (stdElement.getCurrentMinorDevLevel() * stdElement.getMinorDevCost());
			}
		}

		return currentProgress;
	} // END

	/**
	 * 
	 * Calculate the amount of resources contributed to the mission from a single
	 * player.
	 * 
	 * @param board
	 * @param player
	 * @return
	 */
	public static int playerElementInvestment(Board board, Player player) {

		int playerInvestment = 0;

		for (StandardElement stdElement : board.getStdElements()) {

			if (stdElement.getOwnedBy() == player) {
				playerInvestment += stdElement.getPurchaseCost();
				playerInvestment += (stdElement.getCurrentMajorDevLevel() * stdElement.getMajorDevCost());
				playerInvestment += (stdElement.getCurrentMinorDevLevel() * stdElement.getMinorDevCost());
			}
		}
		return playerInvestment;

	} // END

	/**
	 * Calculate the progress towards completing the mission.
	 * 
	 * @param board
	 * @return
	 */
	public static double missionProgress(Board board) {
		return (overallElementInvestment(board) / totalElementCost()) * 100;
	} // END

	/**
	 * Calculate a single players contribution toward completing the game.
	 * 
	 * @param board
	 * @param player
	 * @return
	 */
	public static double overallContribution(Board board, Player player) {
		return (playerElementInvestment(board, player) / totalElementCost()) * 100;
	} // END

	/**
	 * Method to calculate the proportion of the current progress a single player
	 * has contributed.
	 * 
	 * @param board
	 * @param player
	 * @return
	 */
	public static double currentContribution(Board board, Player player) {
		return (playerElementInvestment(board, player) / overallElementInvestment(board)) * 100;
	} // END

	/**
	 * Finds the ending scores of all players
	 */
	public static void endingPlayerScore(Board board) {

		System.out.println("The scores are as follows:");
		List<Player> players = GameLauncher.turnLauncher.getPlayers();

		// checks if player is not bankrupt then calculates score
		for (Player player : players) {
			if (player.getBalanceOfResources() >= 0) {
				ModifyPlayerResources.modifyResourcesSinglePlayer(player, playerElementInvestment(board, player));
			}
		}

		// Orders the players in decending order
		Collections.sort(players, Collections.reverseOrder(new ResourcesComparator()));

		// displays all player scores
		for (Player player : players) {
			if (player.getBalanceOfResources() >= 0) {
				System.out.printf("%s : %d", player.getName(), player.getBalanceOfResources());
			} else
				System.out.printf("%s : Company was closed, however they", player.getName());
			if (missionProgress(board) > 0) {
				System.out.printf(" contributed %.1f%s to the mission.\n", currentContribution(board, player), "%");
			} else
				System.out.printf("\n");
		}

	}

	/**
	 * Calculates the time (in months) until the mission launch date from the
	 * current date.<br>
	 * <b>NOTE:</b> Does not account for days.
	 */
	public static double timeToLaunch() {

		double currentYear = ArtemisCalendar.getCalendar().get(1);
		double currentMonth = ArtemisCalendar.getCalendar().get(2);

		double launchYear = ArtemisCalendar.getEndDate().get(1);
		double launchMonth = ArtemisCalendar.getEndDate().get(2);

		double monthsToLaunch = ((launchYear - currentYear) * 12) + launchMonth - currentMonth;

		return monthsToLaunch;
	} // END

}

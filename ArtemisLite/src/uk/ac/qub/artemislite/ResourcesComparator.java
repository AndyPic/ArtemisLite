/**
 * 
 */
package uk.ac.qub.artemislite;

import java.util.Comparator;

/**
 * @author Jordan Davis
 *
 */
public class ResourcesComparator implements Comparator<Player> {

	/**
	 * Compares Players by resource value
	 * 
	 * @return returns a negative int, zero, or a positive int as the first argument
	 *         is less than, equal to, or greater than the second
	 */
	@Override
	public int compare(Player p1, Player p2) {

		return p1.getBalanceOfResources() - p2.getBalanceOfResources();

	}

}

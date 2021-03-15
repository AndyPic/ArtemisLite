/**
 * 
 */
package uk.ac.qub.artemislite;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * @author Andy
 *
 */
public class ArtemisCalendar extends GregorianCalendar {

	// Variables

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ArtemisCalendar calendar = new ArtemisCalendar();

	private static int startYear = 0;
	private static int startMonth = 0;
	private static int startDay = 0;

	// Defines by what date the mission should be complete
	// private int completionYear = startYear + 2;
	// private int completionMonth = 11;

	private int currentYear = 0;
	private int currentMonth = 0;
	private int currentDay = 0;

	// Constructors

	/**
	 * Default constructor
	 */
	public ArtemisCalendar() {
		super();
	}

	// Methods

	/**
	 * 
	 * Increments the date of the given calendar by 1 month, if that would push in
	 * to a new year instead increase the year and set the month to 0 (January)
	 * 
	 * @param calendar
	 */
	public void incrementDate() {

		// Set-up based on current date.
		if (startYear == 0) {

			startYear = calendar.get(1);
			startMonth = calendar.get(2);
			startDay = calendar.get(5);

			currentYear = startYear;
			currentMonth = startMonth;
			currentDay = startDay;
		}

		// Increment the date
		if (currentMonth + 1 >= 12) {

			currentMonth = 0;
			currentYear += 1;
			currentDay = 0;

			calendar.set(1, currentYear);
			calendar.set(5, currentDay);

		} else {
			currentMonth += 1;
		}

		calendar.set(2, currentMonth);

	}

	/**
	 * Method to increase the day of month for each player that takes a turn within
	 * 1 round
	 * 
	 * @param players
	 */
	public void turnEndDate(ArrayList<Player> players) {

		Random rand = new Random();

		int noPlayers = players.size();
		int monthSize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		// Increase days per player turn by a factor of days per month / number of
		// players (with a bit of randomness added, so it isn't always exactly the same)
		int daysPerTurn = rand.nextInt((monthSize / noPlayers) - (12 / noPlayers)) + (12 / noPlayers);

		// Increase date by no. of days for turn
		calendar.set(5, (currentDay += daysPerTurn));

	}

	/**
	 * Method to return the month name from an int value
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthName(int month) {

		String monthName = new DateFormatSymbols().getMonths()[month];
		// Capitalise the 1st letter
		monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();

		return monthName;
	}

	/**
	 * @return the calendar
	 */
	public static ArtemisCalendar getCalendar() {
		return calendar;
	}

}

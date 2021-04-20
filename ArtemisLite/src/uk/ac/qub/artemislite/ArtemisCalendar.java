/**
 * 
 */
package uk.ac.qub.artemislite;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * @author Andy
 *
 */
public class ArtemisCalendar extends GregorianCalendar {

	// Constants

	private static final long serialVersionUID = 1L;
	// Defines by what date the mission should be complete, (YYYY, MM, D)
	private static final ArtemisCalendar END_DATE = new ArtemisCalendar(2024, 11, 1);

	private static final int CALENDAR_YEAR = 1;
	private static final int CALENDAR_MONTH = 2;
	private static final int CALENDAR_DAY = 5;

	// Variables

	private Random rand = new Random();
	private int currentYear = 2020;
	private int currentMonth = 0;
	private int currentDay = 1;
	// Set the starting date, (YYYY, MM, D)
	private static ArtemisCalendar calendar = new ArtemisCalendar(2020, 0, 1);

	// Constructors

	/**
	 * Default constructor
	 */
	public ArtemisCalendar() {
		super();
	}

	/**
	 * Constructor with args
	 */
	public ArtemisCalendar(int year, int month, int day) {
		this.set(year, month, day);
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

		// Increment the date
		if (currentMonth + 1 >= 12) {

			currentMonth = 0;
			currentYear += 1;
			currentDay = 1;
			
			calendar.set(CALENDAR_YEAR, currentYear);

		} else {
			currentMonth += 1;
			currentDay = 1;
		}

		calendar.set(CALENDAR_MONTH, currentMonth);
		calendar.set(CALENDAR_DAY, currentDay);

	}

	/**
	 * Method to return the month name from an int value
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthName(int month) {

		String monthName = new DateFormatSymbols().getMonths()[month];

		UserInterface.capitaliseFirstLetter(monthName);

		return monthName;
	}

	/**
	 * @return the calendar
	 */
	public static ArtemisCalendar getCalendar() {
		return calendar;
	}

	/**
	 * Displays current game date
	 */
	public static String getDate() {
		return String.format("Date: %s, %s.\n", getMonthName(getCalendar().get(CALENDAR_MONTH)),
				getCalendar().get(CALENDAR_YEAR));
	}

	/**
	 * @return the endDate
	 */
	public static ArtemisCalendar getEndDate() {
		return END_DATE;
	}

}

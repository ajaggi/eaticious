package org.eaticious.common;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This interface represents Seasons meaning timespans between two dates not regarding the year of the dates.
 * It may be used to defined periodic / recurring seasons such as growing seasons of vegetables that only depend on day and month.
 * This will not take account of years, hours, minutes or seconds
 * @author Sven Peetz
 *
 */
public interface Season extends Serializable {
	
	/**
	 * Returns the start of the Season
	 * @return The start of the {@link Season} as a {@link GregorianCalendar} 
	 */
	GregorianCalendar getBeginning();
	/**
	 * Sets the start of the {@link Season}
	 * @param beginning The start of the Season as a {@link GregorianCalendar}
	 */
	void setBeginning(GregorianCalendar beginning);
	/**
	 * Sets the start of the {@link Season}
	 * @param beginning The start of the Season as a {@link Date}
	 */
	void setBeginning(Date beginning);
	/**
	 * Sets the start of the Season indicated by day of month and month (as defined by {@link Calendar})
	 * @param day The day of the month
	 * @param month The month as defined by {@link Calendar}
	 */
	void setBeginning(int day, int month);
	/**
	 * Sets the start of the Season, 1st will be set as day
	 * @param month The month as defined by {@link Calendar}
	 */
	void setBeginning(int month);
	/**
	 * Returns the end of the Season
	 * @return The end of the Season as a {@link GregorianCalendar}
	 */
	GregorianCalendar getEnd();
	/**
	 * Sets the end of the Season
	 * @param end The end of the Season as a {@link GregorianCalendar}
	 */ 
	void setEnd(GregorianCalendar end);
	/**
	 * Sets the end of the Season
	 * @param end The end of the Season as a {@link Date}
	 */
	void setEnd(Date end);
	/**
	 * Sets the end of the Season
	 * @param day The day of month the Season will end
	 * @param month The month the Season will end as defined by {@link Calendar}
	 */
	void setEnd(int day, int month);
	/**
	 * Sets the end of the Season, day of month will be set to the last day of the month
	 * @param month The month as defined by {@link Calendar}
	 */
	void setEnd(int month);
	/**
	 * Returns the {@link SeasonType}
	 * @return The {@link SeasonType} of this Season
	 */
	SeasonType getSeasonType();
	/**
	 * Sets the {@link SeasonType}
	 * @param type The {@link SeasonType} of this Season
	 */
	void setSeasonType(SeasonType type);
	/**
	 * Returns true if the {@link GregorianCalendar} is within the Season's boundaries
	 * This will neglect year, hours, minute and second and only check if day and month are within the defined boundaries of the Season
	 * @param cal The day to be checked as a {@link GregorianCalendar}
	 * @return true if the {@link GregorianCalendar} is within the Season's boundaries
	 */
	boolean isInSeason(GregorianCalendar cal);
	/**
	 * Returns true if the {@link Date} is within the Season's boundaries
	 * This will neglect year, hours, minute and second and only check if day and month are within the defined boundaries of the Season
	 * @param date The day to be checked as a {@link Date}
	 * @return true if the {@link Date} is within the Season's boundaries
	 */
	boolean isInSeason(Date date);

	/**
	 * Takes a {@link String} representing a date as well as a {@link SimpleDateFormat} to parse the {@link String}
	 * @param datestring The date
	 * @param format The {@link SimpleDateFormat}
	 * @return A {@link GregorianCalendar} representing the date passed
	 * @throws ParseException if the String cannot be parsed with the {@link SimpleDateFormat} passed as a parameter
	 */
	GregorianCalendar makeCalendar(String datestring, SimpleDateFormat format) throws ParseException;

}

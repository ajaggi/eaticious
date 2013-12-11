package org.eaticious.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class represents Seasons meaning timespans between two dates not regarding the year of the dates.
 * It may be used to defined periodic / recurring seasons such as growing seasons of vegetables that only depend on day and month.
 * This will not take account of years, hours, minutes or seconds
 * @author Sven Peetz
 *
 */
public class SeasonImpl implements Season {

	private static final long serialVersionUID = -2223462345733478L;
	
	/**
	 * The default year this will be working with
	 */
	private static final int referenceYear = 2001;
	/**
	 * The reference date used by this SeasonImpl
	 */
	private static final GregorianCalendar refCal = new GregorianCalendar(referenceYear, Calendar.JANUARY, 1, 0, 0, 0);
	/**
	 * The start of this Season
	 */
	GregorianCalendar begin;
	/** 
	 * The duration of this Season in milliseconds
	 */
	Long duration;
	/**
	 * The offset of this Season to the 1st of January in milliseconds
	 */
	Long offset;
	/**
	 * The {@link SeasonType} of this Season
	 */
	private SeasonType type;

	/**
	 * Will construct a SeasonImpl with a duration of zero beginning and ending the 1st of January 2001
	 */
	public SeasonImpl() {
		GregorianCalendar cal = refCal;
		this.begin = cal;
		this.duration = 0L;
		this.offset = 0L;
	}
	
	/**
	 * Copy Constructor
	 */
	public SeasonImpl(SeasonImpl other) {
		this.begin = (GregorianCalendar) other.begin.clone();
		this.duration = new Long(other.duration);
		this.offset = new Long(other.offset);
		this.type = other.type;
	}

	@Override
	public GregorianCalendar getBeginning() {
		return this.begin;
	}

	/**
	 * Returns the time in milliseconds between the two {@link Calendar}
	 * @param from the first date
	 * @param till the second date
	 * @return The time in milliseconds between the two dates, negative values may be returned
	 */
	private Long getTimeBetween(Calendar from, Calendar till) {
		return (till.getTimeInMillis() - from.getTimeInMillis()); 
	}

	@Override
	public void setBeginning(GregorianCalendar newBegin) {
		GregorianCalendar begin = new GregorianCalendar();
		begin.setTime(newBegin.getTime());
		if (begin.get(Calendar.MONTH) == Calendar.FEBRUARY && begin.get(Calendar.DATE) == 29) {
			begin.set(Calendar.DATE, 28);
		}
		begin.set(Calendar.YEAR, referenceYear);
		begin.set(Calendar.HOUR, 0);
		begin.set(Calendar.MINUTE, 0);
		begin.set(Calendar.SECOND, 0);
		begin.set(Calendar.MILLISECOND, 0);
		// add difference from newBegin and old begin to duration
		Long durationDiff = this.getTimeBetween(this.begin, begin);
		this.duration += durationDiff;
		this.begin = begin;
		// set offset of begin date
		this.offset = this.getTimeBetween(refCal, this.begin);
	}

	@Override
	public void setBeginning(Date beginning) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(beginning);
		this.setBeginning(cal);
	}

	@Override
	public void setBeginning(int day, int month) {
		if (month == Calendar.FEBRUARY && day == 29) {
			day = 28;
		}
		this.setBeginning(new GregorianCalendar(referenceYear, month, day));
	}

	@Override
	public void setBeginning(int month) {
		this.setBeginning(new GregorianCalendar(referenceYear, month, 1));
	}

	@Override
	public GregorianCalendar getEnd() {
		GregorianCalendar end = new GregorianCalendar();
		end.setTime(this.begin.getTime());
		end.add(Calendar.SECOND, (int)(this.duration / 1000));
		return end;
	}

	@Override
	public void setEnd(GregorianCalendar newEnd) {
		GregorianCalendar end = new GregorianCalendar();
		end.setTime(newEnd.getTime());
		if (end.get(Calendar.MONTH) == Calendar.FEBRUARY && end.get(Calendar.DATE) == 29) {
			end.set(Calendar.DATE, 28);
		}
		end.set(Calendar.YEAR, referenceYear);
		end.set(Calendar.HOUR, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);
		if (end.before(this.begin)) {
			end.set(Calendar.YEAR, referenceYear + 1);
		}
		this.duration = this.getTimeBetween(this.begin, end);

	}

	@Override
	public void setEnd(Date end) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(end);
		this.setEnd(cal);

	}

	@Override
	public void setEnd(int day, int month) {
		if (month == Calendar.FEBRUARY && day == 29) {
			day = 28;
		}
		this.setEnd(new GregorianCalendar(referenceYear, month, day));

	}

	@Override
	public void setEnd(int month) {
		GregorianCalendar end = new GregorianCalendar(referenceYear, month, 1);
		end.set(Calendar.DATE, end.getActualMaximum(Calendar.DATE));
		this.setEnd(end);
	}

	@Override
	public SeasonType getSeasonType() {
		return this.type;
	}

	@Override
	public void setSeasonType(SeasonType type) {
		this.type = type;
	}

	@Override
	public boolean isInSeason(GregorianCalendar date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date.getTime());
		if(date.get(Calendar.MONTH) == Calendar.FEBRUARY && date.get(Calendar.DATE) == 29){
			cal.set(Calendar.DATE, 28);
		}
		cal.set(Calendar.YEAR, referenceYear);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		// substract offset from date
		// faking the begin of this Season is 1st of January and adapting the compared date to this
		// this is done to avoid complexity when end of season is in the next year
		cal.add(Calendar.SECOND, -(int)(this.offset / 1000));
		Long comparedOffset = this.getTimeBetween(refCal, cal);
		return comparedOffset >= 0 && comparedOffset <= this.duration;
	}

	@Override
	public boolean isInSeason(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return this.isInSeason(cal);

	}

	@Override
	public GregorianCalendar makeCalendar(String datestring, SimpleDateFormat format) throws ParseException {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(format.parse(datestring));
		return cal;
	}
}

package org.eaticious.common;

import java.io.Serializable;

public interface Quantity extends Serializable {
	
	/**
	 * 
	 * @return the numeric amount 
	 */
	Double getAmount();
	
	/**
	 * 
	 * @param amount the numeric amount
	 */
	void setAmount(double amount);
	
	/**
	 * 
	 * @return the unit used
	 */
	Unit getUnit();

	/**
	 * 
	 * @param unit the numeric amount
	 */
	void setUnit(Unit unit);
	
	/**
	 * 
	 * @param unit
	 * @return
	 */
	Quantity convert(Unit unit) throws IllegalArgumentException;
	
	/**
	 * 
	 * @param comparator
	 * @return true if this is greater than comparator, false otherwise
	 */
	public boolean greaterThan(Object comparator) ;

	void add(Quantity co2eForRoute);

	void substract(Quantity co2eForRoute);

}

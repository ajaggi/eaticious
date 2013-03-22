package org.eaticious.common;

import java.util.Locale;


public interface Device {

	/**
	 * 
	 * @param language
	 * @return name of the device in the locale language
	 */
	String getName(Locale locale);
	
	/**
	 * 
	 * @return The subtype of the device, e.g. small or big Microwave
	 */
	String getSpecification(Locale locale);
	
	/**
	 * 
	 * @return The energy consumption in kW
	 */
	Double getConsumption();
	
	/**
	 * 
	 * @return The energy source used to run the appliance
	 */
	EnergySource getEnergySource();
}

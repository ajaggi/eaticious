package org.eaticious.common;

import java.io.Serializable;


public interface EmissionFactor extends Serializable {
	
	/**
	 * 
	 * @return Name of the energy factor / energy mix (e.g. ewz.naturepower)
	 */
	String getName();
	
	/**
	 * 
	 * @return The emission factor in [kg CO2e / Unit]
	 */
	Double getFactor();
	
	/**
	 * The Unit that this factor can be applied to
	 * @return
	 */
	Unit getReferencedUnit();
	
	/**
	 * Returns the amount of GHG in kgCO2e for the Quantity 
	 * @param reference The Quantity for which the greenhouse potential should be calculated
	 * @return The greenhouse potential of the referenced Quantity
	 * @throws IllegalArgumentException if the referenced Quantity is not compatible with this EmissionFactor
	 */
	Quantity calcCO2e(Quantity reference) throws IllegalArgumentException; 
	

}

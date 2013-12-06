package org.eaticious.common;

public enum PhysicalDimension {

	MASS(Unit.KILOGRAM), VOLUME(Unit.LITRE), DISTANCE(Unit.KILOMETER), ENERGY(Unit.KILOWATTHOUR), OTHER(Unit.NONE), WARMING_POTENTIAL(Unit.CO2E), CO2E(Unit.KG_CO2E);
	
	/**
	 * The standard Unit for this PhysicalDimension of this
	 */
	private Unit baseUnit;
	
	/**
	 * 
	 * @param baseunit The standard Unit for the PhysicalDimension to be constructed
	 */
	private PhysicalDimension(final Unit baseunit){
		this.baseUnit = baseunit;
	}
	
	/**
	 * Returns the standard Unit of this PhysicalDimension
	 * @return the standard Unit of this PhysicalDimension
	 */
	public Unit getBaseUnit(){
		return this.baseUnit;
	}
}

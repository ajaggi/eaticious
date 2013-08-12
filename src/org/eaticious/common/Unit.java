package org.eaticious.common;
//APPROVE Sven schau dir die Erweiterung mit der Abkuerzung an / problem ist z.T. ist englisch und deutsch verschieden...
public enum Unit {
	
	// base for mass is kilogram
	TON("t", PhysicalDimension.MASS, 0.001), KILOGRAM("kg", PhysicalDimension.MASS, 1d), METRIC_POUND("mlb", PhysicalDimension.MASS, 2d), GRAM("g", PhysicalDimension.MASS, 1000d), MILlIGRAM("mg", PhysicalDimension.MASS, 1000000d), MIKROGRAM("µg", PhysicalDimension.MASS, 1000000000d), 
	POUND("lb", PhysicalDimension.MASS, 2.2046226218488), OUNCE("oz", PhysicalDimension.MASS, 35.27396194958), GRAIN("gr", PhysicalDimension.MASS, 15432.358352941),
	
	// base unit for volume is liter
	HEKTOLITRE("hl", PhysicalDimension.VOLUME, 0.01), 
	LITRE("l", PhysicalDimension.VOLUME, 1d), 
	DECILITRE("dl", PhysicalDimension.VOLUME, 10d), 
	CENTILITRE("cl", PhysicalDimension.VOLUME, 100d),
	BARREL("bbl", PhysicalDimension.VOLUME, 0.0062848352758387), 
	GALLON_LIQUID("gal", PhysicalDimension.VOLUME, 0.26417205235815), GALLON_DRY("gal", PhysicalDimension.VOLUME, 0.22702074606721), 
	CUP_LIQUID("cup", PhysicalDimension.VOLUME, 4.2267528377304), CUP_DRY("cup", PhysicalDimension.VOLUME, 3.6323319370754), 
	GILL_LIQUID("gill", PhysicalDimension.VOLUME, 8.4535056754607), GILL_DRY("gill", PhysicalDimension.VOLUME, 7.2646638741508),
	
	TABLESPOON_LIQUID("EL", PhysicalDimension.VOLUME, 67.6280454036864), TABLESPOON_DRY("EL", PhysicalDimension.VOLUME, 58.1173109932064), 
	TEASPOON_LIQUID("TL", PhysicalDimension.VOLUME, 202.8841362110592), TEASPOON_DRY("TL", PhysicalDimension.VOLUME, 174.3519329796192),
	
	// base unit is kilometer 
	KILOMETER("km", PhysicalDimension.DISTANCE, 1d), METER("m", PhysicalDimension.DISTANCE, 1000d),
	YARD("yd", PhysicalDimension.DISTANCE, 0.00091440), MILE("mi", PhysicalDimension.DISTANCE, 1.6093),
	NAUTICAL_MILE("nau mi", PhysicalDimension.DISTANCE, 1.852),
	
	// base unit for energy is kWh
	KILOWATTHOUR("KWh",PhysicalDimension.ENERGY, 1d), 
	WATTHOUR("Wh", PhysicalDimension.ENERGY, 1000d), 
	JOULE("J", PhysicalDimension.ENERGY, 3600000d), 
	KILOCALORIES("kcal", PhysicalDimension.ENERGY, 859.84522785899),
	BRITISH_THERMAL_UNIT("BTU", PhysicalDimension.ENERGY, 3412.1416331279),
	
	CO2E("CO2e", PhysicalDimension.WARMING_POTENTIAL, 1d), CO2("CO2", PhysicalDimension.WARMING_POTENTIAL, 1d), CH4("CH4", PhysicalDimension.WARMING_POTENTIAL, 25d), N2O("N2O", PhysicalDimension.WARMING_POTENTIAL, 298d),
	
	KG_CO2E("kg CO2e", PhysicalDimension.CO2E, 1d), G_CO2E("g CO2e", PhysicalDimension.CO2E, 1000d), T_CO2E("t CO2e", PhysicalDimension.CO2E, 0.001),
	
	// these are not really physical units, just used in recipes. We might want to handle them differently
	PIECE("piece", PhysicalDimension.OTHER, 0d), SLICE("slice", PhysicalDimension.OTHER, 0d), NONE("", PhysicalDimension.OTHER, 0d);
	
	// The short version of the measuring unit for display
	private String abbreviation;
	
	// The physical dimension measured with this unit, e.g. mass, volume, energy
	private PhysicalDimension dimension;
	
	// the conversion factor to convert this unit into its base unit
	protected Double factor;
	
	/**
	 * 
	 * @param dimension The dimension used (hardcoded as String)
	 * @param factor The conversion factor to convert into the base unit
	 */
	private Unit(String abbreviation, final PhysicalDimension dimension, final Double factor){
		this.dimension = dimension;
		this.factor = factor;
		this.abbreviation = abbreviation;
	}
	
	/**
	 * 
	 * @return Returns the physical dimension of this unit as a String
	 */
	public PhysicalDimension getDimension(){
		return this.dimension;
	}
	
	/**
	 * 
	 * @return The short version of the measuring unit for display 
	 */
	public String getAbbreviation() {
		return this.abbreviation;
	}
	
	/**
	 * @param unit The unit to convert to
	 * @return true if the target unit has the same dimension as this unit and UnitDimension is NOT OTHER, false otherwise
	 */
	public boolean isConvertable(final Unit unit) {
		return this.dimension.equals(unit.getDimension()) && !this.dimension.equals(PhysicalDimension.OTHER);
	}

	/**
	 * 
	 * @param value The value (amount) of this unit to be converted
	 * @param targetUnit The unit to convert to
	 * @return The value (amount) for the target unit
	 * @throws IllegalArgumentException if units (source / target) are not convertable
	 */
	public Double convert(final Double value, final Unit targetUnit) throws IllegalArgumentException {
		if(!this.isConvertable(targetUnit)){
			throw new IllegalArgumentException("Cannot convert " + targetUnit + " into " + this + " since the describe different dimensions of our world.");
		}
		return value * this.getConversionFactor(targetUnit);
	}
	
	/**
	 * 
	 * @param targetUnit The unit into which to convert using the factor returned
	 * @return The factor used to convert into the targetUnit
	 * @throws IllegalArgumentException thrown if units are not convertable
	 */
	public Double getConversionFactor(final Unit targetUnit) throws IllegalArgumentException{
		if(!this.isConvertable(targetUnit)){
			throw new IllegalArgumentException("Cannot convert " + targetUnit + " into " + this + " since the describe different dimensions of our world.");
		}
		return  targetUnit.factor / this.factor;
	}

}
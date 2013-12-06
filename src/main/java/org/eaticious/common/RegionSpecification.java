package org.eaticious.common;

public interface RegionSpecification {

	/**
	 * used to describe the typical landscape of a region, used mainly when calculating co2e from commercial transport
	 */
	public enum Landscape {
		FLAT, HILL, MOUNTAIN;
	}

	/**
	 * Returns the standard gross ton weight of train wagons in this region
	 * 
	 * international average: 1500[t]; USA, Canada, Australia, China: 4000[t])
	 * 
	 * @return The standard gross ton weight of train wagons in this region
	 */
	int getTrainGTW();

	/**
	 * Returns the typical Landscape of this Region
	 * 
	 * @return The typical Landscape of this Region or Lanscape.NONE if unknown
	 */
	Landscape getLandscape();

}

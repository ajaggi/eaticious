package org.eaticious.eaternity;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eaticious.common.Nutrient;
import org.eaticious.common.ProductClassification;
import org.eaticious.common.Quantity;
import org.eaticious.common.Region;
import org.eaticious.common.Season;
import org.eaticious.common.co2e.transport.*;


public interface FoodProduct {

	String getName(Locale locale);
	
	/**
	 * @return The code of this product in the requested product classification or null if unknown
	 */
	String getCode(ProductClassification classification);
	
	/**
	 * 
	 * @return A Map holding amounts of nutrients per serving of the associated recipe
	 */
	Map<Nutrient, Quantity> getNutritionData();
	
	
	List<FoodProduct> getAlternatives();
	
	/**
	 * probably implementable with Growth Degree Days
	 * @return
	 */
	Map<Region,Season> getSeasons();
	
	/**
	 * 
	 * @return density of ingredient for correct volume to weight transformation (EL, TL to grams)
	 */
	Double getDensity();
	
	List<String> getTags();
	
	Quantity getStdWeight();
	
	/**
	 * 
	 * @param consumer The consuming region
	 * @return A Map holding all known producers for this product in the consuming region and the corresponding percentage the producer adds to the consumer's market
	 */
	Map<Region, Integer> getProducingRegions(Region consumer);
	
	
	
	// eaternity specific
	
	//probably (if not possible via ProductClassification)
	String getCategory();
	
	CO2Value getCO2Value();
	
	// not necessary with faostat data
	Region getStdRegion();
	
	
	List<Production> getProductions();
	
	List<Transportation> getTransportations();
	
	public List<Conservation> getConservations();
	
	
}

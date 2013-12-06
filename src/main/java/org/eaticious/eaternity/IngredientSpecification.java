package org.eaticious.eaternity;

import java.util.Map;

import org.eaticious.common.Nutrient;
import org.eaticious.common.Quantity;
import org.eaticious.common.Region;
import org.eaticious.common.Season;
import org.eaticious.common.co2e.transport.*;


public interface IngredientSpecification {
	
	FoodProduct getFoodProduct();
	
	/**
	 * 
	 * @return The Amount of this Ingredient needed in the associated Recipe, the original unit is preserved
	 */
	Quantity getAmount();
	
	/**
	 * 
	 * @return The normalized Amount of this Ingredient needed in the associated Recipe, the Amount is standardized in grams / milliliters
	 */
	Quantity getNormalizedAmount();
	
	/**
	 * 
	 * @return A Map holding amounts of nutrients per serving of the associated recipe
	 */
	Map<Nutrient, Quantity> getNutritionData();

	
	// eaternity specific
	
	/**
	 * Eaternity probably doesn't need the detailed vessel enum
	 * 
	 * @param origin The producing location
	 * @param target The consuming location
	 * @return The distances by vessel used for transport
	 */
	Map<String, Double> getTransportDistances(String origin, String target);
	
	CO2Value getCO2Value();
	
	Season getSeason();
	
	Region getRegion();
	
	Production getProduction();
	
	Conservation getConservation();
	
	Double getCost();
	
}

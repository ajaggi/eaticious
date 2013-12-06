package org.eaticious.common;

import java.io.Serializable;
import java.util.Map;

public interface Ingredient extends Serializable {
	
	/**
	 * 
	 * @return The food product from which this ingredient is derived
	 */
	FoodProduct getFoodProduct();
	
	/**
	 * 
	 * @return The Amount of this Ingredient needed in the associated Recipe, the original unit is preserved
	 */
	Quantity getAmount();
	
	/**
	 * 
	 * @return The normalized Amount of this Ingredient needed in the associated Recipe.
	 */
	Quantity getNormalizedAmount();
	
	/**
	 * 
	 * @return A Map holding amounts of nutrients per serving of the associated recipe
	 */
	Map<Nutrient, Quantity> getNutritionData();

	
}

package org.eaticious.common;

import java.net.URL;
import java.util.List;
import java.util.Locale;

import java.util.Map;

public interface Recipe {

	/**
	 * 
	 * @param language
	 * @return The name of this Recipe
	 */
	String getTitel(Language language);
	
	/**
	 * 
	 * @param language
	 * @return The accompanying phrase of this Recipe
	 */
	String getSubTitel(Language language);

	
	Integer getStdServingSize();
	
	/**
	 * 
	 * @return A List of all Ingredients used by this Recipe
	 */
	List<Ingredient> getIngredients(Integer servings);
	
	/**
	 * 
	 * @return The cooking time for each appliance type used in this recipe
	 */
	Map<Device, Integer> getDeviceUsage();
	
	/**
	 * 
	 * @param language
	 * @return The cooking instructions for this recipe
	 */
	String getInstructions(Language language);
	
	/**
	 * 
	 * @return A Map holding amounts of nutrients per serving of this recipe
	 */
	Map<Nutrient, Quantity> getNutritionData();
	
	/**
	 * 
	 * @return The user rating for this recipe
	 */
	Integer getRating();
	
	List<String> getComments();
	
	List<SavingPotential> getSavingPotentials();
	
	Long getAncestorId();
	
	boolean isPublished();
	
	boolean isDeleted();
	
	Long getUserID();
	
	String getAuthor();
	
	 /** 
	 * @return The URL for the Image of this Recipe or null if not available
	 */
	URL getImagePath();

}

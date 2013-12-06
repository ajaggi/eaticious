package org.eaticious.eaternity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.eaticious.common.EmissionFactor;
import org.eaticious.common.Recipe;
import org.eaticious.eaternity.IngredientSpecification;

public interface Dish extends Serializable {
	
	Recipe getRecipe();
	
	Integer getServings();
	
	Date getCookingDate();
	
	// getUser()
	
	// eaternity specific
	
	String getLocation();
	
	List<IngredientSpecification> getIngredients(Integer servings);
	
	Long getUserID();
	
	Long getKitchenId();
	
	// make Recipe a member field for not persisting it as well seperatly
	
	EmissionFactor getEmissionFactor();
	
	/**
	 * 
	 * @return false if the Ingredients are not specified and thus weighted averages are taken
	 */
	boolean isSpecified();
	
	/**
	 * depends on isSpecified which values is returned
	 * @return
	 */
	Double getCO2Value();
}

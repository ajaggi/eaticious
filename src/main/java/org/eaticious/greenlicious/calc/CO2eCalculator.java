package org.eaticious.greenlicious.calc;

import java.util.Date;

import org.eaticious.common.FoodProduct;
import org.eaticious.common.Quantity;
import org.eaticious.common.Recipe;
import org.eaticious.common.Region;
import org.eaticious.common.co2e.transport.Vessel;
import org.eaticious.greenlicious.CoolingType;
import org.eaticious.greenlicious.Shoppinglist;


public interface CO2eCalculator {
	
	Quantity getFoodProductionValue(FoodProduct product, Region producer, Date date);
	
	Quantity getFoodProductionValue(FoodProduct product, Region producer, Date date, Quantity amount);
	
	Quantity getTransportValue(FoodProduct product, Region consumerRegion, Quantity amount);
	
	Quantity getCoolingValue(FoodProduct product, CoolingType type);
	
	Quantity getShoppingValue(Shoppinglist list, Vessel vessel);
	
	Quantity getCookingValue(Recipe recipe, Region consumerRegion, Date date);
		
	Quantity getRecipeValue(Recipe recipe, Region userRegion, Date date);
	
	Quantity getRecipeValue(Recipe recipe, Region userRegion, Date date, Integer servings);
	
	

}

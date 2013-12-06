package org.eaticious.greenlicious;

import java.util.List;
import java.util.Map;

import org.eaticious.common.Dish;
import org.eaticious.common.FoodProduct;
import org.eaticious.common.Quantity;
import org.eaticious.common.UserInfo;

public interface Shoppinglist {
	
	Map<FoodProduct, Quantity> getEntries();
	
	List<Dish> getMeals();
	
	void addMeal(Dish meal);
	
	void removeMeal(Dish meal);
	
	String getShoppingInfo(FoodProduct product);
	
	boolean getStatus();
	
	Boolean getStatus(FoodProduct product);
	
	void setStatus (FoodProduct product);
	
	UserInfo getUser();
	
	void setUser(UserInfo user);

}

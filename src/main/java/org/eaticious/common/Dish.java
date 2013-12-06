package org.eaticious.common;

import java.io.Serializable;
import java.util.Date;

public interface Dish extends Serializable {
	
	Recipe getRecipe();
	
	Integer getServings();
	
	Date getCookingDate();
	
	Quantity getCO2eValue();
	
	UserInfo getUser();
	
	Long getKitchenId();
}

package org.eaticious.common;

import java.io.Serializable;
import java.util.Locale;

public interface Nutrient extends Serializable {
	
	String getName();
	
	String getName(Language language);
	
	Quantity getRecommendedDailyConsumption();

}

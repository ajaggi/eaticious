package org.eaticious.greenlicious.calc;

import org.eaticious.common.FoodProduct;
import org.eaticious.common.Region;

public interface RouteManager {

	Route getRoute(FoodProduct product, Region producer, Region consumer);

}

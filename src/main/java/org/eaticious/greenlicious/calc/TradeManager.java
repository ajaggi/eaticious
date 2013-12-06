package org.eaticious.greenlicious.calc;

import java.util.Map;

import org.eaticious.common.FoodProduct;
import org.eaticious.common.Region;

public interface TradeManager {

	Map<Region, Double> getProducers(FoodProduct product, Region consumer);

}

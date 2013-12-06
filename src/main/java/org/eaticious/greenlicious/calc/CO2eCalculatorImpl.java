package org.eaticious.greenlicious.calc;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eaticious.common.FoodProduct;
import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Recipe;
import org.eaticious.common.Region;
import org.eaticious.common.Unit;
import org.eaticious.common.co2e.transport.Vessel;
import org.eaticious.greenlicious.CoolingType;
import org.eaticious.greenlicious.Shoppinglist;
import org.eaticious.greenlicious.vessels.Parameter;

public class CO2eCalculatorImpl implements CO2eCalculator {

	private static final TradeManager tradeManager = new TradeManagerImpl();

	private static final RouteManager routeManager = new RouteManagerImpl();

	@Override
	public Quantity getTransportValue(FoodProduct product, Region consumer, Quantity amount) {
		// 1. get all producers for consumerRegion
		Map<Region, Double> imports = tradeManager.getProducers(product, consumer);
		// 2. determine routes of FoodProducts for consumer regions
		Quantity co2eTransport = new QuantityImpl(0d, Unit.KG_CO2E);
		for (Region producer : imports.keySet()) {
			Route route = routeManager.getRoute(product, producer, consumer);
			// allocate amount by percentage of import from producer
			// TODO check if 100% of import in Route or if percentage needs to be normalized to 100%
			Quantity allocAmount = new QuantityImpl(amount.getAmount() * imports.get(producer), Unit.KILOGRAM);
			co2eTransport.add(this.getCO2eForRoute(product, allocAmount, route));
		}
		// 3. calculate co2e for each route

		return co2eTransport;
	}

	private Quantity getCO2eForRoute(FoodProduct product, Quantity amount, Route route) {
		Quantity co2e = new QuantityImpl(0d, Unit.KG_CO2E);
		Quantity normAmount = amount.convert(Unit.KILOGRAM);
		List<RouteSegment> segments = route.getSegments();
		for (RouteSegment segment : segments) {
			Vessel vessel = segment.getVessel();
		}
		// TODO calculate co2e for each segment
		return null;
	}

	@Override
	public Quantity getCoolingValue(FoodProduct product, CoolingType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity getShoppingValue(Shoppinglist list, Vessel vessel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity getCookingValue(Recipe recipe, Region consumerRegion, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity getRecipeValue(Recipe recipe, Region userRegion, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity getRecipeValue(Recipe recipe, Region userRegion, Date date, Integer servings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity getFoodProductionValue(FoodProduct product, Region producer, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity getFoodProductionValue(FoodProduct product, Region producer, Date date, Quantity amount) {
		// TODO Auto-generated method stub
		return null;
	}

}

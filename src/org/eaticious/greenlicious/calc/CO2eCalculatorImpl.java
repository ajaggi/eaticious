package org.eaticious.greenlicious.calc;

import java.util.Date;

import org.eaticious.common.FoodProduct;
import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Recipe;
import org.eaticious.common.Region;
import org.eaticious.common.Unit;
import org.eaticious.greenlicious.CO2ePrint;
import org.eaticious.greenlicious.CoolingType;
import org.eaticious.greenlicious.Shoppinglist;
import org.eatlicious.greenlicious.vessels.Vessel;


public class CO2eCalculatorImpl implements CO2eCalculator {


	@Override
	public CO2ePrint getTransportValue(FoodProduct product, Region consumer_region) {
		return this.getTransportValue(product, consumer_region, new QuantityImpl(
				1.0, Unit.KILOGRAM));
	}

	@Override
	public CO2ePrint getTransportValue(FoodProduct product, Region consumer_region, Quantity amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CO2ePrint getTransportValue(FoodProduct product, Vessel vessel,
			Integer distance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CO2ePrint getTransportValue(FoodProduct product, Vessel vessel,
			Integer distance, Quantity amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CO2ePrint getCoolingValue(FoodProduct product, CoolingType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CO2ePrint getShoppingValue(Shoppinglist list, Vessel vessel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CO2ePrint getCookingValue(Recipe recipe, Region consumer_region,
			Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CO2ePrint getRecipeValue(Recipe recipe, Region user_region, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CO2ePrint getRecipeValue(Recipe recipe, Region user_region,
			Date date, Integer servings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CO2ePrint getFoodProductionValue(FoodProduct FoodProduct,
			Region producer, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CO2ePrint getFoodProductionValue(FoodProduct FoodProduct,
			Region producer, Date date, Quantity amount) {
		// TODO Auto-generated method stub
		return null;
	}

}
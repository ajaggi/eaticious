package org.eaticious.greenlicious.vessels;

import static org.junit.Assert.*;

import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.eaticious.common.FoodProduct.TransportClass;
import org.eaticious.greenlicious.vessels.CargoShip.ShippingRoute;

public class CargoShipTest {
	
	private static CargoShip ship;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ship = new CargoShip();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetCO2ePerKGKM(){
		TransportClass tc = TransportClass.AVERAGE;
		ShippingRoute route = ShippingRoute.GLOBAL_AVERAGE;
		
		double expected = 0.000016;
		double actual = ship.getCO2ePerKGKM(tc, route).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetCO2ePerKGKMBulk(){
		TransportClass tc = TransportClass.BULK;
		ShippingRoute route = ShippingRoute.GLOBAL_AVERAGE;
		
		double expected = 0.00001159;
		double actual = ship.getCO2ePerKGKM(tc, route).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetCO2ePerKGKMVolume(){
		TransportClass tc = TransportClass.VOLUME;
		ShippingRoute route = ShippingRoute.GLOBAL_AVERAGE;
		
		double expected = 0.000028;
		double actual = ship.getCO2ePerKGKM(tc, route).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetCO2ePerKGKMRouted(){
		TransportClass tc = TransportClass.AVERAGE;
		ShippingRoute route = ShippingRoute.PANAMA;
		
		double expected = 0.000016 * ShippingRoute.PANAMA.getEmissionFactor();
		double actual = ship.getCO2ePerKGKM(tc, route).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, 1e-8);
	}

	@Test
	public void testGetCO2e(){
		Quantity weight = new QuantityImpl(1d, Unit.KILOGRAM);;
		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);
		TransportClass tc = TransportClass.AVERAGE;
		ShippingRoute route = ShippingRoute.GLOBAL_AVERAGE;
		
		double expected = 0.000016;
		double actual = ship.getCO2e(weight, tc, distance, route).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetCO2eBulk(){
		Quantity weight = new QuantityImpl(1d, Unit.KILOGRAM);;
		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);
		TransportClass tc = TransportClass.BULK;
		ShippingRoute route = ShippingRoute.GLOBAL_AVERAGE;
		
		double expected = 0.00001159;
		double actual = ship.getCO2e(weight, tc, distance, route).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetCO2eVolume(){
		Quantity weight = new QuantityImpl(1d, Unit.KILOGRAM);;
		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);
		TransportClass tc = TransportClass.VOLUME;
		ShippingRoute route = ShippingRoute.GLOBAL_AVERAGE;
		
		double expected = 0.00002800;
		double actual = ship.getCO2e(weight, tc, distance, route).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetCO2eRouted(){
		Quantity weight = new QuantityImpl(1d, Unit.KILOGRAM);;
		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);
		TransportClass tc = TransportClass.AVERAGE;
		ShippingRoute route = ShippingRoute.TRANSPACIFIC;
		
		double expected = 0.000016 * ShippingRoute.TRANSPACIFIC.getEmissionFactor();
		double actual = ship.getCO2e(weight, tc, distance, route).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, 1e-8);
	}

}
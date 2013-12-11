package org.eaticious.common.co2e.transport;

import static org.junit.Assert.*;

import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;
import org.eaticious.common.co2e.transport.Truck;
import org.eaticious.common.co2e.transport.TruckSpecification.TruckModel;
import org.junit.BeforeClass;
import org.junit.Test;

public class TruckTest {

	private static Truck truck40000;

	private static Truck truck7500;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		truck40000 = new Truck(TruckModel.TR_40000);
		truck7500 = new Truck(TruckModel.TR_7500);
	}

	@Test
	public void testGetTotalCO2eBigTruck() {
		Double distance  = 6325d;
		Double loadFactor = 0.75;
		Double emptyTripFactor = 0.5;

		Double expected = truck40000.getTotalCO2ePerKM(loadFactor, emptyTripFactor).convert(Unit.KG_CO2E).getAmount() * distance;
		Double actual = truck40000.getTotalCO2e(distance, loadFactor, emptyTripFactor).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetTotalCO2eSmallTruck() {
		Double distance  = 6325d;
		Double loadFactor = 0.75;
		Double emptyTripFactor = 0.5;

		Double expected = truck7500.getTotalCO2ePerKM(loadFactor, emptyTripFactor).convert(Unit.KG_CO2E).getAmount() * distance;
		Double actual = truck7500.getTotalCO2e(distance, loadFactor, emptyTripFactor).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetTotalCO2ePerKmBigTruck() {
		Double loadFactor = 0.75;
		Double emptyTripFactor = 0.5;

		// test 1
		Double expected = 1.120395; // taken from calculations in excel sheet / EcoTransit
		Double actual = truck40000.getTotalCO2ePerKM(loadFactor, emptyTripFactor).getAmount();
		assertEquals(expected, actual, 1e-6);
	}
	
	@Test
	public void testGetTotalCO2ePerKmSmallTruck() {
		Double loadFactor = 0.75;
		Double emptyTripFactor = 0.5;

		Double expected = 0.513358; // taken from calculations in excel sheet / EcoTransit
		Double actual = truck7500.getTotalCO2ePerKM(loadFactor, emptyTripFactor).getAmount();
		assertEquals(expected, actual, 1e-6);
	}

	@Test
	public void testGetCO2eBigTruck() {
		Quantity payload = new QuantityImpl(12d, Unit.TON);;
		Double distance  = 3819d;
		Double loadFactor = 0.8;
		Double emptyTripFactor  = 0.4;

		Double expected = truck40000.getCO2ePerKM(payload, loadFactor, emptyTripFactor).convert(Unit.KG_CO2E).getAmount() * distance;
		Double actual = truck40000.getCO2e(payload, distance, loadFactor, emptyTripFactor).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetCO2eSmallTruck() {
		Quantity payload = new QuantityImpl(3d, Unit.TON);;
		Double distance  = 3819d;
		Double loadFactor = 0.8;
		Double emptyTripFactor  = 0.4;
		
		Double expected = truck7500.getCO2ePerKM(payload, loadFactor, emptyTripFactor).convert(Unit.KG_CO2E).getAmount() * distance;
		Double actual = truck7500.getCO2e(payload, distance, loadFactor, emptyTripFactor).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual);

	}

	
	@Test (expected=IllegalArgumentException.class)
	public void testGetCO2eException() {
		Quantity payload = new QuantityImpl(12d, Unit.LITRE);
		truck40000.getCO2e(payload, 12d, 0.5, 0.5);
	}

	@Test
	public void testGetCO2ePerKmBigTruck() {
		Quantity payload = new QuantityImpl(753d, Unit.KILOGRAM);;
		Double loadFactor = 0.8;
		Double emptyTripFactor = 0.4;

		Double expected = 0.041956;
		Double actual = truck40000.getCO2ePerKM(payload, loadFactor, emptyTripFactor).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, 1e-6);
	}
	
	@Test
	public void testGetCO2ePerKmSmallTruck() {
		Quantity payload = new QuantityImpl(512d, Unit.KILOGRAM);;
		Double loadFactor = 0.8;
		Double emptyTripFactor = 0.4;

		Double expected = 0.094556;
		Double actual = truck7500.getCO2ePerKM(payload, loadFactor, emptyTripFactor).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, 1e-6);

	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetCO2ePerKMException() {
		Quantity payload = new QuantityImpl(753d, Unit.CENTILITRE);
		truck40000.getCO2ePerKM(payload, 0.8, 0.5);
	}
	
	@Test
	public void testGetFuelConsumptionBigTruck() {
		Double loadFactor = 0.8;
		Double emptyTripFactor = 0.4;

		Double expected = 43.3;
		Double actual = truck40000.getFuelConsumption(loadFactor, emptyTripFactor).getAmount();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetFuelConsumptionSmallTruck() {
		Double loadFactor = 0.4;
		Double emptyTripFactor = 0.7;

		Double expected = 18.66;
		Double actual = truck7500.getFuelConsumption(loadFactor, emptyTripFactor).getAmount();
		assertEquals(expected, actual, 1e-2);
	}

}

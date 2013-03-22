package org.eaticious.greenlicious.vessels;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;
import org.eaticious.greenlicious.vessels.AirplaneSpecification.AirplaneSize;
import org.eaticious.greenlicious.vessels.AirplaneSpecification.StandardModel;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.log.Log;

public class AirplaneTest {

	private static Map<Double, Double> profile;
	private static AirplaneSpecification specs;
	private static Airplane plane;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		profile = new HashMap<Double, Double>();
		profile.put(100.0, 10.0);
		profile.put(200.0, 20.0);
		profile.put(1000.0, 100.0);
		specs = new AirplaneSpecification(AirplaneSize.BIG, 0, 10000, 50, profile);
		plane = new Airplane(specs);
	}

	@Test
	public void testGetTotalCO2eNoRFI() {

		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);

		// matching defined value
		distance.setAmount(100d);
		Double expected = plane.getFuelConsumption(distance).getAmount() * Airplane.KEROSENE_FACTOR;
		Double actual = plane.getTotalCO2e(distance, false).getAmount();
		assertTrue("Expected: " + expected + " - Actual: " + actual, expected.equals(actual));

		// in between values
		distance.setAmount(147.9);
		expected = plane.getFuelConsumption(distance).getAmount() * Airplane.KEROSENE_FACTOR;
		actual = plane.getTotalCO2e(distance, false).getAmount();
		assertTrue("Expected: " + expected + " - Actual: " + actual, expected.equals(actual));

		// 25% of lowest value
		distance.setAmount(2.5);
		expected = plane.getFuelConsumption(distance).getAmount() * Airplane.KEROSENE_FACTOR;
		actual = plane.getTotalCO2e(distance, false).getAmount();
		assertTrue("Expected: " + expected + " - Actual: " + actual, expected.equals(actual));

		// 100% of highest value
		distance.setAmount(200.0);
		expected = plane.getFuelConsumption(distance).getAmount() * Airplane.KEROSENE_FACTOR;
		actual = plane.getTotalCO2e(distance, false).getAmount();
		assertTrue("Expected: " + expected + " - Actual: " + actual, expected.equals(actual));
	}

	@Test
	public void testGetTotalCO2eRFI() {
		// no RFI-influence for short flights
		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);
		Double first = plane.getTotalCO2e(distance, false).getAmount();
		Double second = plane.getTotalCO2e(distance, true).getAmount();
		assertTrue("Very short distance. Expected: " + first + " - Actual: " + second
				+ " should be equal for very short flights.", first.equals(second));

		// rfi-factor should rise the co2e emission
		distance.setAmount(1000d);
		first = plane.getTotalCO2e(distance, false).getAmount();
		second = plane.getTotalCO2e(distance, true).getAmount();
		assertTrue("Calc with RFI vs without RFI: Expected: " + first + " - Actual: " + second
				+ " should not be the same.", first < second);

		// bigger distance should mean bigger emission
		Quantity distance2 = new QuantityImpl(2000d, Unit.KILOMETER);
		// in the middle of two defined values
		first = plane.getTotalCO2e(distance, true).getAmount();
		second = plane.getTotalCO2e(distance2, true).getAmount();
		assertTrue("Longer distance - higher emission: Expected: " + first + " - Actual: " + second
				+ "should not be the same.", first < second);
	}

	@Test
	public void testGetTotalCO2ePerKM() {
		// emissions for both methods should be the same as 1km is set
		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);
		Double total = plane.getTotalCO2e(distance, false).getAmount();
		Double perKM = plane.getTotalCO2ePerKM(distance, false).getAmount();
		assertTrue(total.equals(perKM));

		// total emission should be the same as per km emission times distance
		distance.setAmount(153.2);
		total = plane.getTotalCO2e(distance, false).getAmount();
		perKM = plane.getTotalCO2ePerKM(distance, false).getAmount();
		assertTrue(total.equals(perKM * distance.getAmount()));
	}

	@Test
	public void testGetCO2e() {
		Log.getLog().setDebugEnabled(true);
		// testing with calculated date from excel-prototype, might have slightly different results,
		// therefore errormargin is defined
		// 747F: 5000km, 1t load, using RFI
		Double errormargin = 0.03; // error should be smaller than 1%
		Double error;
		Double expected;
		Double actual;
		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);
		Double weight;
		Airplane plane;
		Quantity payload = new QuantityImpl();
		payload.setUnit(Unit.KILOGRAM);

		// Test 1 - cargo freight
		plane = new Airplane(StandardModel.F_747_400F);
		distance.setAmount(5000d);
		weight = 1000d;
		expected = 6296.2;

		payload.setAmount(weight);
		actual = Math.round(plane.getCO2e(distance, payload, true).getAmount() * 10) / 10.0;
		error = Math.abs(1 - (actual / expected));
		Log.debug("Expected: " + expected + "\n");
		Log.debug("Actual: " + actual + "\n");
		Log.debug("Error: " + error + "\n");
		assertTrue(error < errormargin);

		// Test 2 - cargo freight
		plane = new Airplane(StandardModel.F_747_400F);
		distance.setAmount(8000d);
		weight = 500d;
		expected = 5304.3;

		payload = new QuantityImpl(weight, Unit.KILOGRAM);
		actual = Math.round(plane.getCO2e(distance, payload, true).getAmount() * 10) / 10.0;
		error = Math.abs(1 - (actual / expected));
		Log.debug("Expected: " + expected + "\n");
		Log.debug("Actual: " + actual + "\n");
		Log.debug("Error: " + error + "\n");
		assertTrue(error < errormargin);

		// Test 3 - belly freight
		plane = new Airplane(StandardModel.P_747_400);
		distance.setAmount(7408d);
		weight = 500d;
		expected = 8376.5;

		payload = new QuantityImpl(weight, Unit.KILOGRAM);
		actual = Math.round(plane.getCO2e(distance, payload, true).getAmount() * 10) / 10.0;
		error = Math.abs(1 - (actual / expected));
		Log.debug("Expected: " + expected + "\n");
		Log.debug("Actual: " + actual + "\n");
		Log.debug("Error: " + error + "\n\n");
		assertTrue(error < errormargin);

		// Test 4 - belly freight
		plane = new Airplane(StandardModel.P_747_400);
		distance.setAmount(15287d);
		weight = 500d;
		expected = 17372.5;

		payload = new QuantityImpl(weight, Unit.KILOGRAM);
		actual = Math.round(plane.getCO2e(distance, payload, true).getAmount() * 10) / 10.0;
		error = Math.abs(1 - (actual / expected));
		Log.debug("Expected: " + expected + "\n");
		Log.debug("Actual: " + actual + "\n");
		Log.debug("Error: " + error + "\n\n");
		assertTrue(error < errormargin);

		// Test 5 - belly freight short haul & small airplane
		plane = new Airplane(StandardModel.P_FOKKER100);
		distance.setAmount(1711d);
		weight = 500d;
		expected = 2965.8;

		payload = new QuantityImpl(weight, Unit.KILOGRAM);
		actual = Math.round(plane.getCO2e(distance, payload, true).getAmount() * 10) / 10.0;
		error = Math.abs(1 - (actual / expected));
		Log.debug("Expected: " + expected + "\n");
		Log.debug("Actual: " + actual + "\n");
		Log.debug("Error: " + error + "\n\n");
		assertTrue(error < errormargin);

		Log.getLog().setDebugEnabled(false);
	}

	@Test
	public void testGetCO2ePerKM() {
		Quantity distance = new QuantityImpl();
		distance.setUnit(Unit.KILOMETER);
		Double total;
		Double perKM;
		Quantity payload = new QuantityImpl(1500d, Unit.KILOGRAM);
		Quantity payload2 = new QuantityImpl(1.5, Unit.TON);

		// emissions for both methods should be the same as 1km is set
		distance.setAmount(1d);
		total = plane.getCO2e(distance, payload, false).getAmount();
		perKM = plane.getCO2ePerKM(distance, payload, false).getAmount();
		assertTrue(total.equals(perKM));

		// total emission should be the same as per km emission times distance
		distance.setAmount(15300.2);
		total = plane.getCO2e(distance, payload, true).getAmount();
		perKM = plane.getCO2ePerKM(distance, payload, true).getAmount();
		assertTrue(total.equals(perKM * distance.getAmount()));

		// test proper handling of Units
		assertTrue(plane.getCO2ePerKM(distance, payload, true).getAmount()
				.equals(plane.getCO2ePerKM(distance, payload2, true).getAmount()));

	}

	@Test
	public void testGetFuelConsumption() {
		
		Quantity distance = new QuantityImpl();
		distance.setUnit(Unit.KILOMETER);

		// matching defined value
		distance.setAmount(100d);
		Double expected = new Double(10);
		Double actual = plane.getFuelConsumption(distance).getAmount();
		assertTrue("Expected: " + expected + " - Actual: " + actual, expected.equals(actual));

		// in the middle of two defined values
		distance.setAmount(150d);
		expected = new Double(15);
		actual = plane.getFuelConsumption(distance).getAmount();
		assertTrue("Expected: " + expected + " - Actual: " + actual, expected.equals(actual));

		// somewhere in between values
		distance.setAmount(300d);
		expected = new Double(30);
		actual = plane.getFuelConsumption(distance).getAmount();
		assertTrue("Expected: " + expected + " - Actual: " + actual, expected.equals(actual));

		// 25% of lowest value
		distance.setAmount(25d);
		expected = new Double(2.5);
		actual = plane.getFuelConsumption(distance).getAmount();
		assertTrue("Expected: " + expected + " - Actual: " + actual, expected.equals(actual));

		// 100% of highest value
		distance.setAmount(2000d);
		expected = new Double(200);
		actual = plane.getFuelConsumption(distance).getAmount();
		assertTrue("Expected: " + expected + " - Actual: " + actual, expected.equals(actual));
	}

}

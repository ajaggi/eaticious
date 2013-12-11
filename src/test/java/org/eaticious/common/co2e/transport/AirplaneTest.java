package org.eaticious.common.co2e.transport;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;
import org.eaticious.common.co2e.transport.Airplane;
import org.eaticious.common.co2e.transport.AirplaneSpecification;
import org.eaticious.common.co2e.transport.AirplaneSpecification.AirplaneSize;
import org.eaticious.common.co2e.transport.AirplaneSpecification.StandardModel;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void testGetTotalCO2eNoRFIDefinedValue() {
		// matching defined value
		Quantity distance = new QuantityImpl(100d, Unit.KILOMETER);
		Double expected = plane.getFuelConsumption(distance).getAmount() * Airplane.KEROSENE_FACTOR;
		Double actual = plane.getTotalCO2e(distance, false).convert(Unit.KG_CO2E).getAmount();
		assertEquals("Expected: " + expected + " - Actual: " + actual, expected, actual);

	}

	@Test
	public void testGetTotalCO2eNoRFIBetweenValues() {
		// in between values
		Quantity distance = new QuantityImpl(147.9, Unit.KILOMETER);
		Double expected = plane.getFuelConsumption(distance).getAmount() * Airplane.KEROSENE_FACTOR;
		Double actual = plane.getTotalCO2e(distance, false).convert(Unit.KG_CO2E).getAmount();
		assertEquals("Expected: " + expected + " - Actual: " + actual, expected, actual);
	}

	@Test
	public void testGetTotalCO2eNoRFIBetweenValues2() {
		// 25% of lowest value
		Quantity distance = new QuantityImpl(2.5, Unit.KILOMETER);
		Double expected = plane.getFuelConsumption(distance).getAmount() * Airplane.KEROSENE_FACTOR;
		Double actual = plane.getTotalCO2e(distance, false).convert(Unit.KG_CO2E).getAmount();
		assertEquals("Expected: " + expected + " - Actual: " + actual, expected, actual);
	}

	@Test
	public void testGetTotalCO2eNoRFIAboveValues() {
		// 100% of highest value
		Quantity distance = new QuantityImpl(200d, Unit.KILOMETER);
		Double expected = plane.getFuelConsumption(distance).getAmount() * Airplane.KEROSENE_FACTOR;
		Double actual = plane.getTotalCO2e(distance, false).convert(Unit.KG_CO2E).getAmount();
		assertEquals("Expected: " + expected + " - Actual: " + actual, expected, actual);
	}

	@Test
	public void testGetTotalCO2eRFIShortFlights() {
		// no RFI-influence for short flights
		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);
		Double first = plane.getTotalCO2e(distance, false).convert(Unit.KG_CO2E).getAmount();
		Double second = plane.getTotalCO2e(distance, true).convert(Unit.KG_CO2E).getAmount();
		assertEquals("Very short distance. Expected: " + first + " - Actual: " + second
				+ " should be equal for very short flights.", first, second);
	}

	@Test
	public void testGetTotalCO2eRFIMidrangeFlights() {
		// rfi-factor should rise the co2e emission
		Quantity distance = new QuantityImpl(1000d, Unit.KILOMETER);
		Double first = plane.getTotalCO2e(distance, false).convert(Unit.KG_CO2E).getAmount();
		Double second = plane.getTotalCO2e(distance, true).convert(Unit.KG_CO2E).getAmount();
		assertTrue("Calc with RFI vs without RFI: Expected: " + first + " - Actual: " + second
				+ " should not be the same.", first < second);
	}

	@Test
	public void testGetTotalCO2eRFIRising() {
		// bigger distance should mean bigger emission
		Quantity distance = new QuantityImpl(1000d, Unit.KILOMETER);
		Quantity distance2 = new QuantityImpl(2000d, Unit.KILOMETER);
		Double first = plane.getTotalCO2e(distance, true).convert(Unit.KG_CO2E).getAmount();
		Double second = plane.getTotalCO2e(distance2, true).convert(Unit.KG_CO2E).getAmount();
		assertTrue("Longer distance - higher emission: Expected: " + first + " - Actual: " + second
				+ "should not be the same.", first < second);
	}

	@Test
	public void testGetTotalCO2eSamePerKM() {
		// emissions for both methods should be the same as 1km is set
		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);
		Double total = plane.getTotalCO2e(distance, false).convert(Unit.KG_CO2E).getAmount();
		Double perKM = plane.getTotalCO2ePerKM(distance, false).convert(Unit.KG_CO2E).getAmount();
		assertEquals(total, perKM);
	}

	@Test
	public void testGetTotalCO2eInterpolatePerKM() {
		// total emission should be the same as per km emission times distance
		Quantity distance = new QuantityImpl(153.2, Unit.KILOMETER);
		Double total = plane.getTotalCO2e(distance, false).convert(Unit.KG_CO2E).getAmount();
		Double perKM = plane.getTotalCO2ePerKM(distance, false).convert(Unit.KG_CO2E).getAmount();
		assertEquals(total, perKM * distance.getAmount(), 0d);
	}

	@Test
	public void testGetCO2eCargoFreight() {
		// testing with calculated date from excel-prototype, might have slightly different results,
		// therefore errormargin is defined
		// Test 1 - cargo freight
		Double errormargin = 0.02; // error should be smaller than 2%
		Quantity distance = new QuantityImpl(5000d, Unit.KILOMETER);
		Airplane plane = new Airplane(StandardModel.F_747_400F);
		Quantity payload = new QuantityImpl(1000d, Unit.KILOGRAM);

		double expected = 6296.2;
		double actual = plane.getCO2e(distance, payload, true).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual, errormargin*expected);
	}

	@Test
	public void testGetCO2eCargoFreight2() {
		// Test 2 - cargo freight
		Double errormargin = 0.02; // error should be smaller than 2%
		Quantity distance = new QuantityImpl(8000d, Unit.KILOMETER);
		Airplane plane = new Airplane(StandardModel.F_747_400F);
		Quantity payload = new QuantityImpl(500d, Unit.KILOGRAM);

		Double expected = 5304.3;
		Double actual = plane.getCO2e(distance, payload, true).getAmount();
		assertEquals(expected, actual, errormargin*expected);
	}

	@Test
	public void testGetCO2eBellyFreight() {
		// Test 3 - belly freight
		Double errormargin = 0.02; // error should be smaller than 2%
		Quantity distance = new QuantityImpl(7408d, Unit.KILOMETER);
		Airplane plane = new Airplane(StandardModel.P_747_400);
		Quantity payload = new QuantityImpl(500d, Unit.KILOGRAM);

		Double expected = 8376.5;
		Double actual = plane.getCO2e(distance, payload, true).getAmount();
		assertEquals(expected, actual, errormargin*expected);
	}

	@Test
	public void testGetCO2eBellyFreight2() {
		// Test 4 - belly freight
		Double errormargin = 0.02; // error should be smaller than 2%
		Quantity distance = new QuantityImpl(15287d, Unit.KILOMETER);
		Airplane plane = new Airplane(StandardModel.P_747_400);
		Quantity payload = new QuantityImpl(500d, Unit.KILOGRAM);

		Double expected = 17372.5;
		Double actual = plane.getCO2e(distance, payload, true).getAmount();
		assertEquals(expected, actual, errormargin*expected);
	}

	@Test
	public void testGetCO2eBellyFreight3() {
		// Test 5 - belly freight short haul & small airplane
		Double errormargin = 0.02; // error should be smaller than 2%
		Quantity distance = new QuantityImpl(1711d, Unit.KILOMETER);
		Airplane plane = new Airplane(StandardModel.P_FOKKER100);
		Quantity payload = new QuantityImpl(500d, Unit.KILOGRAM);

		Double expected = 2965.8;
		Double actual = plane.getCO2e(distance, payload, true).getAmount();
		assertEquals(expected, actual, errormargin*expected);
	}

	@Test
	public void testGetCO2ePerKMWithoutRFI() {
		// emissions for both methods should be the same as 1km is set
		Quantity distance = new QuantityImpl(1d, Unit.KILOMETER);
		Quantity payload = new QuantityImpl(1500d, Unit.KILOGRAM);

		Double perKM = plane.getCO2ePerKM(distance, payload, false).getAmount();
		Double total = plane.getCO2e(distance, payload, false).getAmount();
		assertEquals(total,  perKM);
	}

	@Test
	public void testGetCO2ePerKMWithRFI() {
		// total emission should be the same as per km emission times distance
		Double dist = 15300.2;
		Quantity distance = new QuantityImpl(dist, Unit.KILOMETER);
		Quantity payload = new QuantityImpl(1500d, Unit.KILOGRAM);

		Double perKM = plane.getCO2ePerKM(distance, payload, true).getAmount() * dist;
		Double total = plane.getCO2e(distance, payload, true).getAmount();
		assertEquals(total, perKM);
	}
	
	@Test
	public void testGetCO2ePerKMDiffUnits() {
		// test proper handling of Units
		Quantity distance = new QuantityImpl(1000d, Unit.KILOMETER);
		Quantity payload = new QuantityImpl(1500d, Unit.KILOGRAM);
		Quantity payload2 = new QuantityImpl(1.5, Unit.TON);

		Double val1 = plane.getCO2ePerKM(distance, payload, true).getAmount();
		Double val2 = plane.getCO2ePerKM(distance, payload2, true).getAmount();
		assertEquals(val1, val2, 0d);
	}

	@Test
	public void testGetFuelConsumptionExact() {
		// matching defined value
		Quantity distance = new QuantityImpl(100d, Unit.KILOMETER);
		Double expected = new Double(10);
		Double actual = plane.getFuelConsumption(distance).getAmount();
		assertEquals("Expected: " + expected + " - Actual: " + actual, expected, actual);
	}
	
	@Test
	public void testGetFuelConsumptionInterpolation1() {
		// in the middle of two defined values
		Quantity distance = new QuantityImpl(150d, Unit.KILOMETER);
		Double expected = new Double(15);
		Double actual = plane.getFuelConsumption(distance).getAmount();
		assertEquals("Expected: " + expected + " - Actual: " + actual, expected, actual);
	}
	
	@Test
	public void testGetFuelConsumptionInterpolation2() {
		// somewhere in between values
		Quantity distance = new QuantityImpl(300d, Unit.KILOMETER);
		Double expected = new Double(30);
		Double actual = plane.getFuelConsumption(distance).getAmount();
		assertEquals("Expected: " + expected + " - Actual: " + actual, expected, actual);
	}

	@Test
	public void testGetFuelConsumptionInterpolation3() {
		// 25% of lowest value
		Quantity distance = new QuantityImpl(25d, Unit.KILOMETER);
		Double expected = new Double(2.5);
		Double actual = plane.getFuelConsumption(distance).getAmount();
		assertEquals("Expected: " + expected + " - Actual: " + actual, expected, actual);
	}

	@Test
	public void testGetFuelConsumptionInterpolation4() {
		// 100% of highest value
		Quantity distance = new QuantityImpl(2000d, Unit.KILOMETER);
		Double expected = new Double(200);
		Double actual = plane.getFuelConsumption(distance).getAmount();
		assertEquals("Expected: " + expected + " - Actual: " + actual, expected, actual);
	}
}

package org.eaticious.common.co2e.transport;

import static org.junit.Assert.*;

import org.eaticious.common.FoodProduct;
import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;
import org.eaticious.common.co2e.transport.Barge;
import org.eaticious.common.co2e.transport.BargeSpecification.BargeModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BargeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testGetCO2ePerKgkmBulk() {
		Barge barge = new Barge(BargeModel.BARGE_CONTAINER_STD);	
		Double expected = 0.00003852; // Calculated with own algorithm (ExcelSheet)
		Double actual = barge.getCO2ePerKGKM(FoodProduct.TransportClass.BULK, false).getAmount();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetCO2ePerKgkmAverage() {
		Barge barge = new Barge(BargeModel.BARGE_CONTAINER_STD);
		Double expected = 0.00005320; // Calculated with own algorithm (ExcelSheet)
		Double actual = barge.getCO2ePerKGKM(FoodProduct.TransportClass.AVERAGE, false).getAmount();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetCO2ePerKgkmVolume() {
		Barge barge = new Barge(BargeModel.BARGE_CONTAINER_STD);
		Double expected = 0.00009310; // Calculated with own algorithm (ExcelSheet)
		Double actual = barge.getCO2ePerKGKM(FoodProduct.TransportClass.VOLUME, false).getAmount();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetCO2ePerKgkmBulkEco() {
		Barge barge = new Barge(BargeModel.BARGE_CONTAINER_STD);	
		Double expected = 0.00004817; // EcoTransitValue corrected with testdata
		Double actual = barge.getCO2ePerKGKM(FoodProduct.TransportClass.BULK, true).getAmount();
		assertEquals(expected, actual, 1e-8);
	}

	@Test
	public void testGetCO2ePerKgkmAverageEco() {
		Barge barge = new Barge(BargeModel.BARGE_CONTAINER_STD);
		Double expected = 0.00006653; // EcoTransitValue corrected with testdata
		Double actual = barge.getCO2ePerKGKM(FoodProduct.TransportClass.AVERAGE, true).getAmount();
		assertEquals(expected, actual, 1e-8);
	}

	@Test
	public void testGetCO2ePerKgkmVolumeEco() {
		Barge barge = new Barge(BargeModel.BARGE_CONTAINER_STD);
		Double expected = 0.00011642; // EcoTransitValue corrected with testdata
		Double actual = barge.getCO2ePerKGKM(FoodProduct.TransportClass.VOLUME, true).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetCO2eBulk() {
		Barge barge = new Barge(BargeModel.BARGE_CONTAINER_CLASS_V);
		Quantity distance = new QuantityImpl(331d, Unit.KILOMETER);
		Quantity weight = new QuantityImpl(10d, Unit.KILOGRAM);
		Double expected = 0.0762293; // Calculated with own algorithm (ExcelSheet)
		Double actual = barge.getCO2e(distance, weight, FoodProduct.TransportClass.BULK, false).getAmount();
		assertEquals(expected, actual, 1e-8);
	}

	@Test
	public void testGetCO2eAverage() {
		Barge barge = new Barge(BargeModel.BARGE_STD);
		Quantity distance = new QuantityImpl(816d, Unit.KILOMETER);
		Quantity weight = new QuantityImpl(1d, Unit.KILOGRAM);
		Double expected = 0.04996368; // Calculated with own algorithm (ExcelSheet)
		Double actual = barge.getCO2e(distance, weight, FoodProduct.TransportClass.AVERAGE, false).getAmount();
		assertEquals(expected, actual, 1e-8);
	}

	@Test
	public void testGetCO2eVolume() {
		Barge barge = new Barge(BargeModel.BARGE_CLASS_V);
		Quantity distance = new QuantityImpl(1828d, Unit.KILOMETER);
		Quantity weight = new QuantityImpl(10d, Unit.KILOGRAM);
		Double expected = 0.6966508; // Calculated with own algorithm (ExcelSheet)
		Double actual = barge.getCO2e(distance, weight, FoodProduct.TransportClass.VOLUME, false).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetCO2eBulkEco() {
		Barge barge = new Barge(BargeModel.BARGE_CONTAINER_STD);
		Quantity distance = new QuantityImpl(1828d, Unit.KILOMETER);
		Quantity weight = new QuantityImpl(100d, Unit.GRAM);
		Double expected = 0.008344828; // EcoTransitValue corrected with testdata
		Double actual = barge.getCO2e(distance, weight, FoodProduct.TransportClass.BULK, true).getAmount();
		assertEquals(expected, actual, 0.08 * expected);
	}

	@Test
	public void testGetCO2eAverageEco() {
		Barge barge = new Barge(BargeModel.BARGE_CONTAINER_CLASS_V);
		Quantity distance = new QuantityImpl(816d, Unit.KILOMETER);
		Quantity weight = new QuantityImpl(1d, Unit.TON);
		Double expected = 32.38095; // EcoTransitValue corrected with testdata
		Double actual = barge.getCO2e(distance, weight, FoodProduct.TransportClass.AVERAGE, true).getAmount();
		assertEquals(expected, actual, 0.08 * expected);
	}

	@Test
	public void testGetCO2eVolumeEco() {
		Barge barge = new Barge(BargeModel.BARGE_CONTAINER_STD);
		Quantity distance = new QuantityImpl(331d, Unit.KILOMETER);
		Quantity weight = new QuantityImpl(1d, Unit.TON);
		Double expected = 38.33333; // EcoTransitValue corrected with testdata
		Double actual = barge.getCO2e(distance, weight, FoodProduct.TransportClass.VOLUME, true).getAmount();
		assertEquals(expected, actual, 0.08 * expected);
	}

}

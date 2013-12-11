package org.eaticious.common.co2e.transport;

import static org.junit.Assert.*;

import org.eaticious.common.FoodProduct.TransportClass;
import org.eaticious.common.RegionSpecification.Landscape;
import org.eaticious.common.co2e.transport.ElectricTrain;
import org.eaticious.common.co2e.transport.ElectricTrainSpecification.TrainModel;
import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;
import org.junit.BeforeClass;
import org.junit.Test;

public class ElectricTrainTest {

	private static ElectricTrain stdTrain;
	private static ElectricTrain bigTrain;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		stdTrain = new ElectricTrain(TrainModel.EUROPEAN_STANDARD);
		bigTrain = new ElectricTrain(TrainModel.INTERNATIONAL_BIG);
	}

	@Test
	public void testGetFuelConsumptionHB() {
		Landscape landscape = Landscape.HILL;
		TransportClass tc = TransportClass.BULK;
		Double expected = 0.00002761;
		Double actual = stdTrain.getFuelConsumptionPerKGKM(landscape, tc).getAmount();
		assertEquals(expected, actual, 1e-8);		
	}
	
	@Test
	public void testGetFuelConsumptionFB() {
		Landscape landscape = Landscape.FLAT;
		TransportClass tc = TransportClass.BULK;
		Double expected = 0.00002485;
		Double actual =stdTrain.getFuelConsumptionPerKGKM(landscape, tc).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetFuelConsumptionMB() {
		Landscape landscape = Landscape.MOUNTAIN;
		TransportClass tc = TransportClass.BULK;
		Double expected = 0.00003037;
		Double actual = stdTrain.getFuelConsumptionPerKGKM(landscape, tc).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetFuelConsumptionMA() {
		Landscape landscape = Landscape.MOUNTAIN;
		TransportClass tc = TransportClass.AVERAGE;
		Double expected = 0.00003504;
		Double actual = stdTrain.getFuelConsumptionPerKGKM(landscape, tc).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetFuelConsumptionMU() {
		Landscape landscape = Landscape.MOUNTAIN;
		TransportClass tc = TransportClass.UNKNOWN;
		Double expected = 0.00003504;
		Double actual = stdTrain.getFuelConsumptionPerKGKM(landscape, tc).getAmount();
		assertEquals(expected, actual, 1e-8);
	}

	@Test
	public void testGetFuelConsumptionMV() {
		Landscape landscape = Landscape.MOUNTAIN;
		TransportClass tc = TransportClass.VOLUME;
		Double expected = 0.00004555;
		Double actual = stdTrain.getFuelConsumptionPerKGKM(landscape, tc).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetFuelConsumptionBigTrain() {
		Landscape landscape = Landscape.MOUNTAIN;
		TransportClass tc = TransportClass.VOLUME;
		Double expected = 0.00001929;
		Double actual = bigTrain.getFuelConsumptionPerKGKM(landscape, tc).getAmount();
		assertEquals(expected, actual, 1e-8);
	}
	
	@Test
	public void testGetCO2ePerKGKM(){
		Landscape landscape = Landscape.HILL;
		TransportClass tc = TransportClass.AVERAGE;
		Double energyMixFactor = 0.67;
		Double expected = stdTrain.getFuelConsumptionPerKGKM(landscape, tc).getAmount() * energyMixFactor;
		Double actual = stdTrain.getCO2ePerKGKM(energyMixFactor, landscape, tc).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetCO2ePerKG(){
		Landscape landscape = Landscape.HILL;
		TransportClass tc = TransportClass.AVERAGE;
		Double energyMixFactor = 0.67;
		Quantity distance = new QuantityImpl(250000d, Unit.METER);
		Double expected = stdTrain.getFuelConsumptionPerKGKM(landscape, tc).getAmount() * energyMixFactor * distance.convert(Unit.KILOMETER).getAmount();
		Double actual = stdTrain.getCO2ePerKG(distance, energyMixFactor, landscape, tc).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetCO2e(){
		Landscape landscape = Landscape.HILL;
		TransportClass tc = TransportClass.AVERAGE;
		Double energyMixFactor = 0.67;
		Quantity distance = new QuantityImpl(250000d, Unit.METER);
		Quantity weight = new QuantityImpl(1000d, Unit.GRAM);
		Double expected = stdTrain.getFuelConsumptionPerKGKM(landscape, tc).getAmount() * energyMixFactor * distance.convert(Unit.KILOMETER).getAmount() * weight.convert(Unit.KILOGRAM).getAmount();
		Double actual = stdTrain.getCO2e(weight, distance, energyMixFactor, landscape, tc).convert(Unit.KG_CO2E).getAmount();
		assertEquals(expected, actual);
	}
}

package org.eaticious.common.co2e.transport;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.eaticious.common.co2e.transport.AirplaneSpecification;
import org.eaticious.common.co2e.transport.AirplaneSpecification.AirplaneSize;
import org.eaticious.common.co2e.transport.AirplaneSpecification.StandardModel;
import org.junit.Before;
import org.junit.Test;

public class AirplaneSpecificationTest {
	
	private AirplaneSpecification spec;
	
	@Before
	public void setUp() throws Exception {
		this.spec = new AirplaneSpecification(AirplaneSize.SMALL, 1, 100, 2, new HashMap<Double, Double>() );
	}
	
	

	@Test
	public void testGetSize() {
		assertSame(AirplaneSize.SMALL, spec.getSize());
	}

	@Test
	public void testGetMaxRange() {
		assertTrue(100 == spec.getMaxRange());
	}

	@Test
	public void testGetMaxPayload() {
		assertTrue(2 == spec.getMaxPayload());
	}

	@Test
	public void testGetSeats() {
		assertTrue(1 == spec.getSeats());
	}

	@Test
	public void testHasConsumptionDataFalse() {
		assertFalse(spec.hasConsumptionData());
	}
	
	@Test
	public void testHasConsumptionDataTrue() {
		spec.addConsumptionEntry(10d, 100d);
		assertTrue(spec.hasConsumptionData());
	}

	@Test
	public void testAddConsumptionEntry() {
		spec.addConsumptionEntry(10d, 100d);
		spec.addConsumptionEntry(10d, 150d);
		spec.addConsumptionEntry(20d, 200d);
		assertEquals(2, spec.getConsumptionProfile().size(), 0d);
	}
	
	@Test
	public void testGetConsumptionData() {
		spec.addConsumptionEntry(10d, 100d);
		spec.addConsumptionEntry(10d, 150d);
		spec.addConsumptionEntry(20d, 200d);
		assertEquals(150d, spec.getConsumptionProfile().get(10d), 0d);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testAddConsumptionEntryNullKey() {
		spec.addConsumptionEntry(null, 100d);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddConsumptionEntryNullValue() {
		spec.addConsumptionEntry(10d, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddConsumptionEntryNegKey() {
		spec.addConsumptionEntry(-10d, 100d);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddConsumptionEntryNegValue() {
		spec.addConsumptionEntry(10d, -100d);
	}
	
	@Test
	public void testAirplaneSpecificationOtherConstructor(){
		AirplaneSpecification spec = new AirplaneSpecification(StandardModel.P_747_400);
		assertTrue("747 should be a big airplane.", spec.getSize() == AirplaneSize.BIG);
		assertTrue("747 should have more than 0 seats.", spec.getSeats() > 0);
		assertTrue("Range of 747 should be bigger than 0.", spec.getMaxRange() > 0);
		assertTrue("747 should have a bigger payload capacity than 0", spec.getMaxPayload() > 0);
		assertTrue("Standard model should have a consumption profile", spec.hasConsumptionData());
		assertTrue("Standard model should have a consumption profile", spec.getConsumptionProfile().size() > 0);
		
		
	}

}

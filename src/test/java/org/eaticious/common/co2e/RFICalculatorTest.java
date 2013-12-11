package org.eaticious.common.co2e;

import static org.junit.Assert.*;

import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;
import org.eaticious.common.co2e.RFICalculator;
import org.junit.Before;
import org.junit.Test;

public class RFICalculatorTest {
	
	private Quantity distance;

	@Before
	public void setUp() throws Exception {
		this.distance = new QuantityImpl(1d, Unit.KILOMETER);
	}

	@Test
	public void testGetRFIFactorStandardShort1() {
		// lower end values, no share of flight near stratosphere, ECOTransIT values
		distance.setAmount(499.0);
		assertEquals(1d, RFICalculator.getRFIFactor(distance), 0d);
	}
	
	@Test
	public void testGetRFIFactorStandardShort2() {
		distance.setAmount(500d);
		assertEquals(1d, RFICalculator.getRFIFactor(distance), 0d);
	}
	
	@Test
	public void testGetRFIFactorStandardShort3() {
		distance.setAmount(501d);
		assertTrue(1d < RFICalculator.getRFIFactor(distance));
	}
		
	@Test
	public void testGetRFIFactorStandardMidValue1() {
		distance.setAmount(749d);
		assertTrue(1.81 > RFICalculator.getRFIFactor(distance));
	}
	
	@Test
	public void testGetRFIFactorStandardMidValue2() {
		distance.setAmount(750d);
		assertEquals(1.81, RFICalculator.getRFIFactor(distance), 0d);
	}
	
	@Test
	public void testGetRFIFactorStandardMidValue3() {
		distance.setAmount(751d);
		assertTrue(1.81 < RFICalculator.getRFIFactor(distance));
	}
	
	@Test
	public void testGetRFIFactorStandardMidValue4() {
		distance.setAmount(1000d);
		assertEquals(2.18, RFICalculator.getRFIFactor(distance), 0d);
	}
	
	@Test
	public void testGetRFIFactorStandardMidValue5() {
		distance.setAmount(2000d);
		assertEquals(2.52, RFICalculator.getRFIFactor(distance), 0d);
	}
	
	@Test
	public void testGetRFIFactorStandardMidValue6() {
		distance.setAmount(4000d);
		assertEquals(2.73, RFICalculator.getRFIFactor(distance), 0d);
	}
	
	@Test
	public void testGetRFIFactorStandardHighValue1() {
		distance.setAmount(9999d);
		assertTrue(2.87 > RFICalculator.getRFIFactor(distance));
	}
	
	@Test
	public void testGetRFIFactorStandardHighValue2() {
		distance.setAmount(10000d);
		assertEquals(2.87, RFICalculator.getRFIFactor(distance), 0d);
	}
	@Test
	public void testGetRFIFactorStandardHighValue3() {
		distance.setAmount(200000d);
		assertEquals(2.87, RFICalculator.getRFIFactor(distance), 0d);
	}
	
	@Test
	public void testGetRFIFactorInterpolated1() {
		distance.setAmount(563d);
		assertEquals(1.20412, RFICalculator.getRFIFactor(distance), 0d);
	}
	
	@Test
	public void testGetRFIFactorInterpolated2() {
		distance.setAmount(2783d);
		assertEquals(2.602215, RFICalculator.getRFIFactor(distance), 0d);
	}
	
	@Test
	public void testGetRFIFactorInterpolated3() {
		distance.setAmount(10500d);
		assertEquals(2.87, RFICalculator.getRFIFactor(distance), 0d);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetRFIFactorExceptionZero() {
		distance.setAmount(0d);
		RFICalculator.getRFIFactor(distance);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetRFIFactorExceptionNegative() {
		distance.setAmount(-7d);
		RFICalculator.getRFIFactor(distance);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testWrongUnit() {
		distance.setAmount(10d);
		distance.setUnit(Unit.CO2);
		RFICalculator.getRFIFactor(distance);
	}

}

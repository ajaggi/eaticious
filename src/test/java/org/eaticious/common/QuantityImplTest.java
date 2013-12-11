package org.eaticious.common;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class QuantityImplTest {

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
	public void testGetAmount() {
		Quantity first = new QuantityImpl(1d, Unit.KILOGRAM);
		assertEquals(first.getAmount(), new Double(1d));
	}

	@Test
	public void testGetUnit() {
		Quantity first = new QuantityImpl(1d, Unit.KILOGRAM);
		assertEquals(first.getUnit(), Unit.KILOGRAM);
	}

	@Test
	public void testConvert() {
		Quantity first = new QuantityImpl(1d, Unit.KILOGRAM);
		Quantity second = new QuantityImpl(1000d, Unit.GRAM);
		second = second.convert(Unit.KILOGRAM);
		assertEquals(first.getAmount(), second.getAmount());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testConvertException() {
		Quantity second = new QuantityImpl(1000d, Unit.LITRE);
		second = second.convert(Unit.KILOGRAM);
	}

	@Test
	public void testSetAmount() {
		Quantity first = new QuantityImpl(1d, Unit.KILOGRAM);
		first.setAmount(100d);
		assertEquals(first.getAmount(), new Double(100d));
	}

	@Test
	public void testSetUnit() {
		Quantity first = new QuantityImpl(1d, Unit.KILOGRAM);
		first.setUnit(Unit.BARREL);
		assertEquals(first.getUnit(), Unit.BARREL);
	}

	@Test
	public void testEqualsObjectTrue() {
		Quantity first = new QuantityImpl(1d, Unit.KILOGRAM);
		Quantity second = new QuantityImpl(1000d, Unit.GRAM);
		assertEquals(first, second);
	}
	
	@Test
	public void testEqualsObjectWrongAmount() {
		Quantity first = new QuantityImpl(1d, Unit.KILOGRAM);
		Quantity second = new QuantityImpl(1000.1, Unit.GRAM);
		assertFalse(first.equals(second));
	}
	
	@Test
	public void testEqualsObjectWrongDimension() {
		Quantity first = new QuantityImpl(1d, Unit.KILOGRAM);
		Quantity second = new QuantityImpl(1000.1, Unit.LITRE);
		assertFalse(first.equals(second));
	}
	
	@Test
	public void testGreaterThan() {
		Quantity first = new QuantityImpl(1d, Unit.KILOGRAM);
		Quantity second = new QuantityImpl(1000.1, Unit.GRAM);
		assertTrue(!first.greaterThan(second));
	}

}

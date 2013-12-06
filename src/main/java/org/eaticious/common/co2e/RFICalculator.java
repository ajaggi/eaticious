package org.eaticious.common.co2e;

import java.util.Map;
import java.util.TreeMap;

import org.eaticious.common.Quantity;
import org.eaticious.common.Unit;

public enum RFICalculator {
	
	INSTANCE;

	private static Map<Double, Double> rfiValues;

	static {
		// values taken from EcoTransIT (2012)
		rfiValues = new TreeMap<Double, Double>();
		rfiValues.put(500d, 1d);
		rfiValues.put(750d, 1.81);
		rfiValues.put(1000d, 2.18);
		rfiValues.put(2000d, 2.52);
		rfiValues.put(4000d, 2.73);
		rfiValues.put(10000d, 2.87);
	}

	/**
	 * Returns the RFI-factor for airtravels. This factor is used to calculate the higher GHG-potential of GHG-emissions
	 * when emitted near the border of the atmosphere. The factor is calculated using the distance of a flight assuming
	 * that long distance flights are traveling in higher regions than short distance flights. Values taken from
	 * ECOTransIT (2012)
	 * 
	 * @param distance
	 *            The distance of the airtravel
	 * @return The rfi factor for the airtravel
	 */
	public static double getRFIFactor(Quantity distance) {
		double result = 1d;
		Double calcDistance = distance.convert(Unit.KILOMETER).getAmount();
		if (calcDistance <= 0) {
			throw new IllegalArgumentException("The distance has to be bigger than 0, was " + distance.toString());
		}

		if (rfiValues.containsKey(calcDistance)) {
			result = rfiValues.get(calcDistance);
		} else {
			Double minKey = null;
			Double maxKey = null;

			for (Double key : rfiValues.keySet()) {
				final double diff = calcDistance - key;
				if (diff < 0) {
					// Since TreeMap is sorted by keys and the actual key is bigger than the distance, maxKey is found
					// and the loop will be stopped
					maxKey = key;
					break;
				} else {
					// key is smaller than distance but bigger than the previous key
					minKey = key;
				}
			}

			if (minKey == null) {
				// Could happen if distance is smaller than the smallest defined key
				result = rfiValues.get(maxKey);
			} else if (maxKey == null) {
				// Could happen if distance is bigger than the highest defined key
				result = rfiValues.get(minKey);
			} else {
				// linear interpolation
				final double minValue = rfiValues.get(minKey);
				final double steep = (rfiValues.get(maxKey) - minValue) / (maxKey - minKey);
				result = minValue + steep * (calcDistance - minKey);
			}
		}

		return result;
	}
}

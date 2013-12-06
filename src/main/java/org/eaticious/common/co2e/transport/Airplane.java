package org.eaticious.common.co2e.transport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;
import org.eaticious.common.co2e.RFICalculator;
import org.eaticious.common.co2e.transport.AirplaneSpecification.StandardModel;
/**
 * This represents Airplanes as used by EcoTransIT for calculation of CO2E-emissions.
 * @author Sven Peetz
 *
 */
public class Airplane implements Vessel {

	/**
	 * A definition of short, medium and long hauls of Airplanes
	 * @author Sven Peetz
	 *
	 */
	enum HaulDistance {
		SHORT(0, 1000), MEDIUM(1000, 3700), LONG(3700, Integer.MAX_VALUE);

		/**
		 * The minimum distance of the HaulDistance cluster
		 */
		private Integer minDistance;
		/**
		 * The maximum distance of the HaulDistance cluster
		 */
		private Integer maxDistance;

		private HaulDistance(final int minDistance, final int maxDistance) {
			this.minDistance = minDistance;
			this.maxDistance = maxDistance;
		}

		/**
		 * Returns the maximum distance of the HaulDistance cluster. Flights having larger distances than this belong to another HaulDistance cluster.
		 * @return The maximum distance of this HaulDistance cluster
		 */
		public Integer getMaxDistance() {
			return this.maxDistance;
		}

		/**
		 * Returns the minimum distance of the HaulDistance cluster. Flights having lower distances than this belong to another HaulDistance cluster.
		 * @return The minimum distance of this HaulDistance cluster
		 */
		public Integer getMinDistance() {
			return this.minDistance;
		}
	}
	// TODO make set of conversion factors for co2e in other place, maybe enum or DB
	public static final Double KEROSENE_FACTOR = 3.128;	
	// TODO Move to config
	public static final int PASSENGER_WEIGHT = 100;

	/**
	 * The mean capacity utilization regarding the cargo of the Airplane
	 */
	private Map<HaulDistance, Double> freightCapacityUtilization;
	
	/**
	 * The mean capacity utilization regarding the number of passengers in the Airplane
	 */
	private Map<HaulDistance, Double> passengerCapacityUtilization;

	/**
	 * AirplaneSpecification holding data needed to make CO2e calculations for Airplanes
	 */
	private AirplaneSpecification specs;

	/**
	 * Standard constructor
	 * 
	 * @param specs
	 *            The AirplaneSpecification of the new Airplane
	 */
	public Airplane(final AirplaneSpecification specs) {
		// values from EcoTransIT
		// TODO might have to move this to calc class or to DB, was static but now is in constructor to avoid conflicts
		// in UnitTests
		this.freightCapacityUtilization = new HashMap<Airplane.HaulDistance, Double>();
		this.freightCapacityUtilization.put(HaulDistance.SHORT, 0.55);
		this.freightCapacityUtilization.put(HaulDistance.MEDIUM, 0.6);
		this.freightCapacityUtilization.put(HaulDistance.LONG, 0.65);
		this.passengerCapacityUtilization = new HashMap<Airplane.HaulDistance, Double>();
		this.passengerCapacityUtilization.put(HaulDistance.SHORT, 0.65);
		this.passengerCapacityUtilization.put(HaulDistance.MEDIUM, 0.7);
		this.passengerCapacityUtilization.put(HaulDistance.LONG, 0.8);

		this.specs = specs;
	}

	/**
	 * Standard constructor
	 * 
	 * @param model
	 *            The StandardModel to be constructed
	 */
	public Airplane(final StandardModel model) {
		this(new AirplaneSpecification(model));
	}

	/**
	 * Calculates the CO2e emission allocated to a particular part of the payload for the whole flight
	 * 
	 * @param distance
	 *            The distance traveled
	 * @param payload
	 *            the weight for which the allocation will be made (any Quantity of PhysicalDimension.MASS can be used)
	 * @param useRFI
	 *            true if the higher greenhouse potential for emission near the stratosphere should be used, false
	 *            otherwise
	 * @return The CO2e emission allocated to the weight of payload for the whole flight
	 */
	public Quantity getCO2e(final Quantity distance, final Quantity payload, final boolean useRFI) {
		Double co2e = this.getTotalCO2e(distance, useRFI).getAmount() / this.getTransportedWeight(distance) * payload.convert(Unit.KILOGRAM).getAmount();
		return new QuantityImpl(co2e, Unit.KG_CO2E);
	}

	/**
	 * Calculates the CO2e emission per kilometer allocated to a particular part of the payload for the whole flight
	 * 
	 * @param distance
	 *            The distance traveled
	 * @param payload
	 *            the weight for which the allocation will be made (any Quantity of PhysicalDimension.MASS can be used)
	 * @param useRFI
	 *            true if the higher greenhouse potential for emission near the stratosphere should be used, false
	 *            otherwise
	 * @return The CO2e emission allocated to the weight of payload per kilometer
	 */
	public Quantity getCO2ePerKM(final Quantity distance, final Quantity payload, final boolean useRFI) {
		final Quantity co2e = this.getCO2e(distance, payload, useRFI);
		co2e.setAmount(co2e.getAmount() / distance.convert(Unit.KILOMETER).getAmount());
		return co2e;
	}

	/**
	 * This method calculates the fuel consumption of this Airplane based on the distance traveled.
	 * 
	 * @param distance
	 *            The total distance traveled for the transport
	 * @return The amount of fuel burned by this Airplane when traveling the given distance.
	 */
	public Quantity getFuelConsumption(final Quantity distance) {
		Double result = 0d;
		final Double convDistance = distance.convert(Unit.KILOMETER).getAmount();

		// calculate with multiple trips if distance exceeds the max range of this airplane
		final double numTrips = Math.ceil(convDistance / this.specs.getMaxRange());
		final double calcDist = convDistance / numTrips;

		if (this.specs.hasConsumptionData()) {
			final Map<Double, Double> consumptionProfile = this.specs.getConsumptionProfile();
			if (consumptionProfile.containsKey(calcDist)) {
				result = consumptionProfile.get(calcDist);
			} else {
				Double minKey = null;
				Double maxKey = null;
				// make List from keyset
				final List<Double> keys = new ArrayList<Double>(consumptionProfile.keySet());
				// sort keys in ascending order
				Collections.sort(keys);
				// identify closest values
				for (final Double key : keys) {
					if (calcDist < key) {
						// once a key with a higher distance is found the maxValue is found. exit loop.
						maxKey = key;
						break;
					} else {
						// as long as distance is bigger than key a new minValue is found
						minKey = key;
					}
				}
				if (minKey == null) {
					// may happen if consumption profile holds only key-values bigger than distance
					// linear interpolation will be used
					result = calcDist * consumptionProfile.get(maxKey) / maxKey;
				} else if (maxKey == null) {
					// may happen if consumption profile hold only key-values smaller than distance
					// linear interpolation will be used
					result = calcDist * consumptionProfile.get(minKey) / minKey;
				} else {
					// linear interpolation
					final double steep = (consumptionProfile.get(maxKey) - consumptionProfile.get(minKey))
							/ (maxKey - minKey);
					result = consumptionProfile.get(minKey) + (calcDist - minKey) * steep;
				}

			}
		}
		return new QuantityImpl(result * numTrips, Unit.KILOGRAM);
	}

	/**
	 * Calculates the complete CO2e emission for the whole flight
	 * 
	 * @param distance
	 *            the distance traveled
	 * @param useRFI
	 *            true if the higher greenhouse potential for emission near the stratosphere should be used, false
	 *            otherwise
	 * @return The CO2e emission for the whole flight
	 */
	public Quantity getTotalCO2e(final Quantity distance, final boolean useRFI) {
		// TODO check if flight distance should be adjusted in here or if this is done by caller
		Double fuelConsumption = this.getFuelConsumption(distance).getAmount();
		if (useRFI) {
			// multiply with rfi factor
			fuelConsumption *= RFICalculator.getRFIFactor(distance);
		}
		// multiply with kerosene factor
		final double co2e = fuelConsumption * KEROSENE_FACTOR;
		// return result
		return new QuantityImpl(co2e, Unit.KG_CO2E);
	}

	/**
	 * Calculates the CO2e emission per kilometer for a flight
	 * 
	 * @param distance
	 *            The distance traveled
	 * @param useRFI
	 *            true if the higher greenhouse potential for emission near the stratosphere should be used, false
	 *            otherwise
	 * @return The CO2e emission per kilometer for the whole flight
	 */
	public Quantity getTotalCO2ePerKM(final Quantity distance, final boolean useRFI) {
		final Double calcDistance = distance.convert(Unit.KILOMETER).getAmount();
		final Quantity co2e = this.getTotalCO2e(distance, useRFI);
		co2e.setAmount(co2e.getAmount() / calcDistance);
		return co2e;
	}

	/**
	 * Returns the HaulDistance (cluster of distances for freight transports) for a given distance
	 * 
	 * @param distance
	 *            the real distance traveled
	 * @return The HaulDistance which matches the real distance traveled
	 */
	private HaulDistance getHaulDistance(final Quantity distance) {
		if (distance.getAmount() < 0) {
			throw new IllegalArgumentException("Distance has to be bigger than 0, was " + distance);
		}
		
		final Double calcDistance = distance.convert(Unit.KILOMETER).getAmount();

		HaulDistance result = null;
		for (final HaulDistance dist : HaulDistance.values()) {
			if (dist.minDistance <= calcDistance && dist.maxDistance > calcDistance) {
				result = dist;
				break;
			}
		}
		return result;
	}

	/**
	 * Calculates the average payload (transported weight) in kilogram using standard capacity utilization factors for
	 * freight and passengers
	 * 
	 * @param distance
	 *            The distance traveled
	 * @return The average payload in kilogram for flights over the given distance
	 */
	private double getTransportedWeight(final Quantity distance) {
		final HaulDistance hd = this.getHaulDistance(distance);
		final double freightWeight = this.specs.getMaxPayload() * freightCapacityUtilization.get(hd);
		final double passengerWeight = this.specs.getSeats() * passengerCapacityUtilization.get(hd) * PASSENGER_WEIGHT;
		return freightWeight + passengerWeight;
	}

}

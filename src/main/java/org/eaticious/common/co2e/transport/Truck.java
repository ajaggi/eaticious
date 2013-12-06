package org.eaticious.common.co2e.transport;

import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;
import org.eaticious.common.co2e.transport.TruckSpecification.TruckModel;

public class Truck implements Vessel {

	/**
	 * factor used to calculate CO2e from liter of diesel fuel This is an eco-transit specific value, normally it would
	 * be a factor bigger than 3. The ecotransit methodology uses a second factor to increase the fuel consumption which
	 * compensates the difference.
	 */
	private static final double CO2E_PER_LITER_DIESEL = 2.676528;

	/**
	 * An EcoTransIT specific factor used to increase the fuelconsumption.
	 */
	private static final double FUEL_CONSUMPTION_FACTOR = 1.4;

	/**
	 * The specification of the truck
	 */
	private TruckSpecification specs;

	/**
	 * Constructor taking a {@link TruckModel} to setup the specification
	 * @param model The TruckModel specifying this Truck
	 */
	public Truck(TruckModel model) {
		this(new TruckSpecification(model));
	}

	/**
	 * Constructor taking {@link TruckSpecification} to setup the specification
	 * @param specs The {@link TruckSpecification} specifying this Truck
	 */
	public Truck(TruckSpecification specs) {
		this.specs = specs;
	}

	/**
	 * Returns the average CO2e emission for this truck over the given distance when loaded with loadfactor of
	 * payloadcapacity and when having emptyTripFactor empty return trips
	 * 
	 * @param distance
	 *            The distance traveled
	 * @param loadFactor
	 *            the loadFactor, between 0.0 and 1.0 indicating the percentage of the payloadcapacity used
	 * @param emptyTripFactor
	 *            a factor between 0.0 and 1.0, percentage of empty return trips
	 * @return The average CO2e emission of this truck when used as described by parameters
	 */
	public Quantity getTotalCO2e(Double distance, Double loadFactor, Double emptyTripFactor) {
		Quantity co2e = this.getTotalCO2ePerKM(loadFactor, emptyTripFactor);
		co2e.setAmount(co2e.getAmount() * distance);
		return co2e;
	}

	/**
	 * Returns the average CO2e emission per km of this truck when loaded with loadfactor of payloadcapacity and when
	 * having emptyTripFactor empty return trips
	 * 
	 * @param loadFactor
	 *            the loadFactor, between 0.0 and 1.0 indicating the percentage of the payloadcapacity used
	 * @param emptyTripFactor
	 *            a factor between 0.0 and 1.0, percentage of empty return trips
	 * @return The average CO2e emission of this truck when used as described by parameters
	 */
	public Quantity getTotalCO2ePerKM(Double loadFactor, Double emptyTripFactor) {
		Double co2e = this.getFuelConsumption(loadFactor, emptyTripFactor).getAmount() / 100.0 * CO2E_PER_LITER_DIESEL;
		return new QuantityImpl(co2e, Unit.KG_CO2E);
	}

	/**
	 * Returns the amount of CO2e emission of the whole trip allocated to the given weight of a transported good
	 * 
	 * @param weight
	 *            the weight of a good for which the allocation is done
	 * @param loadFactor
	 *            the loadfactor between 0.0 and 1.0
	 * @param emptyTripFactor
	 *            the empty-trip-factor between 0.0 and 1.0
	 * @return The CO2e emissions of the whole trip allocated to the weight of a transported good
	 */
	public Quantity getCO2e(Quantity weight, Double distance, Double loadFactor, Double emptyTripFactor) {
		Quantity co2e = this.getCO2ePerKM(weight, loadFactor, emptyTripFactor);
		co2e.setAmount(co2e.getAmount() * distance);
		return co2e;
	}

	/**
	 * Returns the amount of CO2e emission per km allocated to the given weight of a transported good
	 * 
	 * @param weight
	 *            the weight of a good for which the allocation is done
	 * @param loadFactor
	 *            the loadfactor between 0.0 and 1.0
	 * @param emptyTripFactor
	 *            the empty-trip-factor between 0.0 and 1.0
	 * @return The CO2e emissions per km allocated to the weight of a transported good
	 */
	public Quantity getCO2ePerKM(Quantity weight, Double loadFactor, Double emptyTripFactor) {
		Quantity co2e = this.getTotalCO2ePerKM(loadFactor, emptyTripFactor);
		Double allocationFactor = weight.convert(Unit.KILOGRAM).getAmount()
				/ (loadFactor * this.specs.getPayloadCapacity());
		co2e.setAmount(co2e.getAmount() * allocationFactor);
		return co2e;

	}

	/**
	 * Returns the average fuel consumption per 100km of this truck
	 * 
	 * @param loadFactor
	 *            A factor between 0.0 and 1.0 describing how much of the payloadCapacity is used
	 * @param emptyTripFactor
	 *            A factor between 0.0 and 1.0 describing how many empty return trips occur
	 * @return The average fuel consumption per 100km
	 */
	public Quantity getFuelConsumption(Double loadFactor, Double emptyTripFactor) {
		double consumption = (this.specs.getFuelConsumptionEmpty() + this.getCapacityUtilization(loadFactor,
				emptyTripFactor) * (this.specs.getFuelConsumptionFull() - this.specs.getFuelConsumptionEmpty()))
				* FUEL_CONSUMPTION_FACTOR;
		return new QuantityImpl(consumption, Unit.LITRE);
	}

	/**
	 * Helper method for decomplexation, calculation of CapacityUtilization
	 * 
	 * @param loadFactor
	 *            The loadfactor
	 * @param emptyTripFactor
	 *            The empty-trip-factor
	 * @return The capacity-utilization
	 */
	private double getCapacityUtilization(Double loadFactor, Double emptyTripFactor) {
		return loadFactor / (1 + emptyTripFactor);
	}

}

package org.eaticious.common.co2e.transport;

import java.util.HashMap;
import java.util.Map;

import org.eaticious.common.FoodProduct.TransportClass;
import org.eaticious.common.RegionSpecification.Landscape;
import org.eaticious.common.co2e.transport.ElectricTrainSpecification.TrainModel;
import org.eaticious.common.PhysicalDimension;
import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;

public class ElectricTrain implements Vessel {
	/**
	 * A {@link Map} holding {@link Landscape} as key and according fuel consumption factors as value
	 */
	public final static Map<Landscape, Double> landscapeFactor = new HashMap<Landscape, Double>();
	/**
	 * A {@link Map} holding {@link TransportClass} as key and according fuel consumption factors as value
	 */
	public final static Map<TransportClass, Double> transportClassFactor = new HashMap<TransportClass, Double>();

	static {
		// values from EcoTransIT
		landscapeFactor.put(Landscape.FLAT, 0.9);
		landscapeFactor.put(Landscape.HILL, 1.0);
		landscapeFactor.put(Landscape.MOUNTAIN, 1.1);
		// values from EcoTransIT
		transportClassFactor.put(TransportClass.BULK, 0.6);
		transportClassFactor.put(TransportClass.AVERAGE, 0.52);
		transportClassFactor.put(TransportClass.VOLUME, 0.4);
		transportClassFactor.put(TransportClass.UNKNOWN, 0.52);
	}

	/**
	 * The specification of the truck
	 */
	private ElectricTrainSpecification specs;

	/**
	 * Constructor initializing an ElectricTrain by using a {@link TrainModel}
	 * 
	 * @param model
	 *            The TrainModel used to initialize this ElectricTrain
	 */
	public ElectricTrain(TrainModel model) {
		this(new ElectricTrainSpecification(model));
	}

	/**
	 * Constructor using {@link ElectricTrainSpecification} to initialize the ElectricTrain
	 * 
	 * @param specs
	 *            The {@link ElectricTrainSpecification} used to initialize
	 */
	public ElectricTrain(ElectricTrainSpecification specs) {
		this.specs = specs;
	}

	/**
	 * Returns the fuel consumption (kWh)
	 * 
	 * @param landscape
	 *            The {@link Landscape} in which the Electrictrain is used
	 * @param tc
	 *            The {@link TransportClass} of the goods transported
	 * @return The fuel consumption in kWh per kgkm when transporting {@link TransportClass} goods in a certain
	 *         {@link Landscape}
	 */
	public Quantity getFuelConsumptionPerKGKM(final Landscape landscape, final TransportClass tc) {
		final Quantity result = new QuantityImpl();
		result.setUnit(Unit.KILOWATTHOUR);

		final double lsFactor = getLandscapeFactor(landscape);
		final double tcFactor = getTransportClassFactor(tc);

		final double value = 0.0012 * Math.pow(this.specs.getGTW(), -0.62) * lsFactor / tcFactor;
		result.setAmount(value);

		return result;
	}

	/**
	 * Returns the CO2E emissions in kg per kgkm in dependence of {@link Landscape}, {@link TransportClass}, and energy
	 * mix of the country where the transport is taking place
	 * 
	 * @param energyMixFactor
	 *            The CO2E factor for the energy mix of the country where the transport is taking place
	 * @param landscape
	 *            The general {@link Landscape} of the country where the transport is taking place
	 * @param tc
	 *            The {@link TransportClass} of the transported good
	 * @return The mean CO2E emission in kg per kgkm for the transport
	 */
	public Quantity getCO2ePerKGKM(Double energyMixFactor, Landscape landscape, TransportClass tc) {
		Double amount = this.getFuelConsumptionPerKGKM(landscape, tc).getAmount() * energyMixFactor;
		return new QuantityImpl(amount, Unit.KG_CO2E);
	}

	/**
	 * Returns the CO2E emissions in kg per kg of transported good in dependence of {@link Landscape},
	 * {@link TransportClass}, energy mix of the country where the transport is taking place and transport distance
	 * 
	 * @param distance
	 *            The distance of the transport as {@link Quantity} with {@link PhysicalDimension} LENGTH
	 * @param energyMixFactor
	 *            The CO2E factor for the energy mix of the country where the transport is taking place
	 * @param landscape
	 *            The general {@link Landscape} of the country where the transport is taking place
	 * @param tc
	 *            The {@link TransportClass} of the transported good
	 * @return The mean CO2E emission in kg per kg of transported good for the transport
	 */
	public Quantity getCO2ePerKG(Quantity distance, Double energyMixFactor, Landscape landscape, TransportClass tc) {
		Double calcDist = distance.convert(Unit.KILOMETER).getAmount();
		Double amount = this.getFuelConsumptionPerKGKM(landscape, tc).getAmount() * energyMixFactor * calcDist;
		return new QuantityImpl(amount, Unit.KG_CO2E);
	}

	/**
	 * Returns the total CO2E emissions in kg in dependence of {@link Landscape}, {@link TransportClass}, energy mix of
	 * the country where the transport is taking place, the transport distance, and the weight of the transported good
	 * 
	 * @param weight
	 *            The weight of the transported good as a {@link Quantity} with {@link PhysicalDimension} MASS
	 * @param distance
	 *            The distance of the transport as {@link Quantity} with {@link PhysicalDimension} LENGTH
	 * @param energyMixFactor
	 *            The CO2E factor for the energy mix of the country where the transport is taking place
	 * @param landscape
	 *            The general {@link Landscape} of the country where the transport is taking place
	 * @param tc
	 *            The {@link TransportClass} of the transported good
	 * @return The mean CO2E emission in kg for the transport
	 */
	public Quantity getCO2e(Quantity weight, Quantity distance, Double energyMixFactor, Landscape landscape,
			TransportClass tc) {
		Double calcDist = distance.convert(Unit.KILOMETER).getAmount();
		Double calcWeight = weight.convert(Unit.KILOGRAM).getAmount();
		Double amount = this.getFuelConsumptionPerKGKM(landscape, tc).getAmount() * energyMixFactor * calcDist
				* calcWeight;
		return new QuantityImpl(amount, Unit.KG_CO2E);
	}

	/**
	 * The emission factor for ElectricTrain transports when traveling in a certain {@link Landscape}
	 * 
	 * @param landscape
	 *            The landscape where the ElectricTrain is traveling
	 * @return The emission factor for the {@link Landscape}
	 */
	private static double getLandscapeFactor(final Landscape landscape) {
		return landscapeFactor.get(landscape);
	}

	/**
	 * The factor for the {@link TransportClass} of a transported good used to calculate the CO2E values
	 * 
	 * @param tc
	 *            The {@link TransportClass} of the transported good
	 * @return The factor used in the calculation
	 */
	private static double getTransportClassFactor(final TransportClass tc) {
		return transportClassFactor.get(tc);
	}

}

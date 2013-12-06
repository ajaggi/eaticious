package org.eaticious.common.co2e.transport;

import org.eaticious.common.FoodProduct.TransportClass;
import org.eaticious.common.co2e.transport.BargeSpecification.BargeModel;
import org.eaticious.common.PhysicalDimension;
import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;

public class Barge implements Vessel {
	/**
	 * The {@link BargeSpecification} of this Barge
	 */
	private BargeSpecification specs;

	/**
	 * Constructor for Barge taking a standardized {@link BargeModel} as parameter
	 * 
	 * @param model
	 */
	public Barge(BargeModel model) {
		this(new BargeSpecification(model));
	}

	/**
	 * Constructor taking {@link BargeSpecification} as parameter
	 * 
	 * @param bargeSpecification
	 */
	public Barge(BargeSpecification bargeSpecification) {
		this.specs = bargeSpecification;
	}
	
	/**
	 * Returns the CO2E emission in kg per KGKM of this Barge when transporting goods of type
	 * {@link TransportClass} tc
	 * 
	 * @param tc The {@link TransportClass} of the transported good
	 * @param useEcoTransitCorrection true if this should use a factor trying to calculate values that matcch EcoTransIT values closer
	 * @return The CO2E emission in kg per kgkm when transporting a good of {@link TransportClass} tc with this BargeSpecification
	 */
	public Quantity getCO2ePerKGKM(TransportClass tc, boolean useEcoTransitCorrection) {
		Quantity result = new QuantityImpl();
		result.setUnit(Unit.KG_CO2E);
		Double co2e = useEcoTransitCorrection ? this.specs.getCO2eMap().get(tc) * this.specs.getEcotransitFactor() : this.specs.getCO2eMap().get(tc);
		result.setAmount(co2e);
		return result;
	}

	/**
	 * The total calculated CO2E emission of this Barge transporting a given amount of a product of
	 * {@link TransportClass} over the given distance
	 * 
	 * @param distance
	 *            The distance the product will be transported
	 * @param weight
	 *            The amount of the product being transported as a Quantity with the {@link PhysicalDimension} MASS
	 * @param tc
	 *            The {@link TransportClass} of the product being transported
	 * @param ecoTransitCorrection
	 *            true if this method should apply factors lowering the difference between the results of the
	 *            implemented algorithm and the results from EcoTransIT, false otherwise
	 * @return The total calculated amount of CO2E emission in kg for the transport
	 */
	public Quantity getCO2e(Quantity distance, Quantity weight, TransportClass tc, boolean ecoTransitCorrection) {
		Double calcDistance = distance.convert(Unit.KILOMETER).getAmount();
		Double calcWeight = weight.convert(Unit.KILOGRAM).getAmount();
		Double amount = this.getCO2ePerKGKM(tc, ecoTransitCorrection).getAmount() * calcDistance * calcWeight;
		return new QuantityImpl(amount, Unit.KG_CO2E);
	}

}

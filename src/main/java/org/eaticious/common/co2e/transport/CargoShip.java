package org.eaticious.common.co2e.transport;

import org.eaticious.common.FoodProduct.TransportClass;
import org.eaticious.common.PhysicalDimension;
import org.eaticious.common.Quantity;
import org.eaticious.common.QuantityImpl;
import org.eaticious.common.Unit;

public class CargoShip implements Vessel {

	/**
	 * A collection of different routes that have an effect on the fuel consumption / CO2E emission of a cargo ship
	 * transport. The effect on the fuel consumption is mainly based on speed limits / size limits within these
	 * ShippingRoutes
	 * 
	 * @author Sven Peetz
	 * 
	 */
	public enum ShippingRoute {
		GLOBAL_AVERAGE(1d), GLOBAL(1.083333333), INTRA_CONTINENTAL(1.148809524), INTRA_CONTINENTAL_EU(1.380952381), PANAMA(
				0.994047619), TRANSPACIFIC(0.976190476), TRANSATLANTIC(0.994047619), SUEZ(0.857142857);

		/**
		 * The emission factor for this ShippingRoute
		 */
		private Double emissionFactor;

		/**
		 * Constructor for ShippingRoute
		 * 
		 * @param emissionFactor
		 *            The emission factor for this ShippingRoute
		 */
		private ShippingRoute(Double emissionFactor) {
			this.emissionFactor = emissionFactor;
		}

		/**
		 * Returns the emission factor for this ShippingRoute
		 * 
		 * @return the emission factor for this ShippingRoute
		 */
		public Double getEmissionFactor() {
			return this.emissionFactor;
		}
	}

	/**
	 * The base emission of CO2E in kg per TEUkm used for further calculations
	 */
	private static final Double KGCO2ePerTEUKM = 0.168;

	/**
	 * Returns the CO2E emission in kg in dependence of the weight of the transported good as a {@link Quantity} with
	 * {@link PhysicalDimension} MASS, the {@link TransportClass} of the transported good, the distance of the transport
	 * as a {@link Quantity} with {@link PhysicalDimension} LENGTH, and the {@link ShippingRoute} of the transport
	 * 
	 * @param weight
	 *            The weight of the transported good as a {@link Quantity} with {@link PhysicalDimension} MASS
	 * @param tc
	 *            The {@link TransportClass} of the transported good
	 * @param distance
	 *            The distance of the transport as a {@link Quantity} with {@link PhysicalDimension} LENGTH
	 * @param route
	 *            The {@link ShippingRoute} of the transport
	 * @return The CO2E emission for the transport allocated by distance and weight
	 */
	public Quantity getCO2e(Quantity weight, TransportClass tc, Quantity distance, ShippingRoute route) {
		final Double factor = distance.convert(Unit.KILOMETER).getAmount() * weight.convert(Unit.KILOGRAM).getAmount();
		final Quantity co2e = this.getCO2ePerKGKM(tc, route);
		co2e.setAmount(co2e.getAmount() * factor);

		return co2e;
	}

	/**
	 * Returns the CO2E emission in kg per kgkm in dependence of the {@link TransportClass} of the transported good and
	 * the {@link ShippingRoute} of the transport
	 * 
	 * @param tc
	 *            The {@link TransportClass} of the transported good
	 * @param route
	 *            The {@link ShippingRoute} of the transport
	 * @return The CO2E emission in kg per kgkm for the transport
	 */
	public Quantity getCO2ePerKGKM(TransportClass tc, ShippingRoute route) {
		final Double co2ePerKGKM = KGCO2ePerTEUKM * route.getEmissionFactor() / (tc.getTonsPerTEUKM() * 1000d);
		return new QuantityImpl(co2ePerKGKM, Unit.KG_CO2E);
	}

}

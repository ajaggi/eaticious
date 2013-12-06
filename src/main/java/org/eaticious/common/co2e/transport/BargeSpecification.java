package org.eaticious.common.co2e.transport;

import java.util.HashMap;
import java.util.Map;

import org.eaticious.common.FoodProduct.TransportClass;

public class BargeSpecification {

	/**
	 * Definition of standard models.
	 * 
	 * TODO transfer this to database.
	 * 
	 * @author Sven Peetz
	 */
	public enum BargeModel {
		BARGE_STD(0.00006123, 0.00006123, 0.00006123, 0.80058154),

		BARGE_CLASS_V(0.00003811, 0.00003811, 0.00003811, 0.90038639),

		BARGE_CONTAINER_STD(0.00003852, 0.00005320, 0.00009310, 1.25050020),

		BARGE_CONTAINER_CLASS_V(0.00002303, 0.00003180, 0.00005565, 1.24787861);

		/**
		 * The CO2E emission in kg per kgkm of this BargeModel when transporting goods of {@link TransportClass} BULK
		 */
		private Double co2eBulk;
		/**
		 * The CO2E emission in kg per kgkm of this BargeModel when transporting goods of {@link TransportClass} AVERAGE
		 */
		private Double co2eAvg;
		/**
		 * The CO2E emission in kg per kgkm of this BargeModel when transporting goods of {@link TransportClass} VOLUME
		 */
		private Double co2eVolume;
		/**
		 * The factor to be applied to the calculated CO2E emission in order to get a value that matches closer to the
		 * EcoTransIT value when using this model
		 */
		private double ecotransitFactor;

		/**
		 * Constructor for BargeModel
		 * 
		 * @param co2eBulk
		 *            CO2E emission in kg per kgkm when transporting goods of type {@link TransportClass} BULK
		 * @param co2eAVG
		 *            CO2E emission in kg per kgkm when transporting goods of type {@link TransportClass} AVERAGE
		 * @param co2eVolume
		 *            CO2E emission in kg per kgkm when transporting goods of type {@link TransportClass} VOLUME
		 * @param ecotransitFactor
		 *            The factor to be applied to the calculated CO2E emission in order to get a value that matches
		 *            closer to the EcoTransIT value when using this model
		 */
		private BargeModel(Double co2eBulk, Double co2eAVG, Double co2eVolume, Double ecotransitFactor) {
			this.co2eBulk = co2eBulk;
			this.co2eAvg = co2eAVG;
			this.co2eVolume = co2eVolume;
			this.ecotransitFactor = ecotransitFactor;
		}

		/**
		 * Returns the CO2E emission in kg per KGKM of this BargeModel when transporting goods of type
		 * {@link TransportClass} tc
		 * 
		 * @param tc
		 *            The {@link TransportClass} of the transported goods
		 * @return The CO2E emission in kg per KGKM of this BargeModel when transporting goods of type
		 *         {@link TransportClass} tc
		 */
		public Double getCO2ePerKGKM(TransportClass tc) {
			Double result;
			switch (tc) {
			case BULK:
				result = this.co2eBulk;
				break;
			case VOLUME:
				result = this.co2eVolume;
				break;
			case AVERAGE:
			default:
				result = this.co2eAvg;
				break;
			}
			return result;
		}

		/**
		 * Returns a factor to be applied to the calculated CO2E emission in order to get a value that matches closer to
		 * the EcoTransIT value when using this model
		 * 
		 * @return The CO2E emission factor for a better matching with EcoTransIT values
		 */
		public Double getEcoTransitCorrectionFactor() {
			return this.ecotransitFactor;
		}
	}

	/**
	 * A {@link Map} holding {@link TransportClass} as key and CO2E emission values in kg per kgkm for the
	 * TransportClass
	 */
	private Map<TransportClass, Double> co2eMap;
	/**
	 * The factor to be applied to the calculated CO2E emission in order to get a value that matches closer to the
	 * EcoTransIT value when using this model
	 */
	private Double ecotransitFactor;

	/**
	 * Constructor taking a {@link BargeModel} as parameter
	 * 
	 * @param model
	 *            The BargeModel to be used to set the specification values
	 */
	public BargeSpecification(BargeModel model) {
		this(model.getCO2ePerKGKM(TransportClass.BULK), model.getCO2ePerKGKM(TransportClass.AVERAGE), model
				.getCO2ePerKGKM(TransportClass.VOLUME), model.getEcoTransitCorrectionFactor());
	}

	/**
	 * Constructor for BargeSpecification
	 * 
	 * @param co2eBulk
	 *            CO2E emission in kg per kgkm when transporting goods of type {@link TransportClass} BULK
	 * @param co2eAvg
	 *            CO2E emission in kg per kgkm when transporting goods of type {@link TransportClass} AVERAGE
	 * @param co2eVolume
	 *            CO2E emission in kg per kgkm when transporting goods of type {@link TransportClass} VOLUME
	 * @param ecotransitFactor
	 *            The factor to be applied to the calculated CO2E emission in order to get a value that matches closer
	 *            to the EcoTransIT value when using this model
	 */
	public BargeSpecification(Double co2eBulk, Double co2eAvg, Double co2eVolume, Double ecotransitFactor) {
		this.co2eMap = new HashMap<TransportClass, Double>();
		co2eMap.put(TransportClass.BULK, co2eBulk);
		co2eMap.put(TransportClass.AVERAGE, co2eAvg);
		co2eMap.put(TransportClass.VOLUME, co2eVolume);
		this.ecotransitFactor = ecotransitFactor;
	}

	/**
	 * 
	 * @return The factor to be applied to the calculated CO2E emission in order to get a value that matches closer to
	 *         the EcoTransIT value when using this model
	 */
	public Double getEcotransitFactor() {
		return this.ecotransitFactor;
	}

	/**
	 * 
	 * @return A {@link Map} holding {@link TransportClass} as key and CO2E emission values in kg per kgkm for the
	 *         TransportClass
	 */
	public Map<TransportClass, Double> getCO2eMap() {
		return this.co2eMap;
	}

}

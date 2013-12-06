package org.eaticious.common.co2e.transport;

public class TruckSpecification {
	/**
	 * Definition of standard models.
	 * 
	 * TODO transfer this to database.
	 * 
	 * @author Sven Peetz
	 */
	public enum TruckModel {
		TR_60000(22000, 38000, 27.1, 52.3), TR_40000(14000, 26000, 22.7, 37.1), TR_24000(12000, 12000, 19.3, 23.5), TR_12000(
				6000, 6000, 16.9, 20.0), TR_7500(4000, 3500, 13.0, 14.4);
		/**
		 * The empty weight in kg of this TruckModel
		 */
		private Integer emptyWeight;
		/**
		 * The maximum payload capacity in kg of this TruckModel
		 */
		private Integer payloadCapacity;
		/**
		 * The fuel consumption in liter (diesel) when no cargo is loaded
		 */
		private Double consumptionEmpty;
		/**
		 * The fuel consumption in liter (diesel) when maximum cargo is loaded
		 */
		private Double consumptionFull;

		/**
		 * Constructor of TruckModel
		 * 
		 * @param emptyWeight
		 *            The empty weight in kg of the TruckModel
		 * @param payloadCapacity
		 *            The maximum payload capacity in kg of the TruckModel
		 * @param consumptionEmpty
		 *            The fuel consumption in liter (diesel) when no cargo is loaded
		 * @param consumptionFull
		 *            The fuel consumption in liter (diesel) when maximum cargo is loaded
		 */
		private TruckModel(Integer emptyWeight, Integer payloadCapacity, Double consumptionEmpty, Double consumptionFull) {
			this.emptyWeight = emptyWeight;
			this.payloadCapacity = payloadCapacity;
			this.consumptionEmpty = consumptionEmpty;
			this.consumptionFull = consumptionFull;
		}

		/**
		 * Returns the empty weight of the Truck in kg
		 * 
		 * @return the empty weight of the Truck in kg
		 */
		public Integer getEmptyWeight() {
			return this.emptyWeight;
		}

		/**
		 * The maximum payload capacity in kg
		 * 
		 * @return The maximum payload capacity in kg
		 */
		public Integer getPayloadCapacity() {
			return this.payloadCapacity;
		}

		/**
		 * The fuel consumption in liter (diesel) when Truck is empty
		 * 
		 * @return The fuel consumption in liter (diesel) when Truck is empty
		 */
		public Double getFuelConsumptionEmpty() {
			return this.consumptionEmpty;
		}

		/**
		 * The fuel consumption in liter (diesel) when Truck is fully loaded
		 * 
		 * @return The fuel consumption in liter (diesel) when Truck is fully loaded
		 */
		public Double getFuelConsumptionFull() {
			return this.consumptionFull;
		}
	}

	/**
	 * The empty weight in kg of this TruckSpecification
	 */
	private Integer emptyWeight;
	/**
	 * The maximum payload capacity in kg of this TruckSpecification
	 */
	private Integer payloadCapacity;
	/**
	 * The fuel consumption in liter (diesel) when no cargo is loaded
	 */
	private Double consumptionEmpty;
	/**
	 * The fuel consumption in liter (diesel) when fully loaded
	 */
	private Double consumptionFull;

	/**
	 * Constructor taking a TruckModel to set specification
	 * 
	 * @param model
	 *            The {@link TruckModel} used to set the specification
	 */
	public TruckSpecification(TruckModel model) {
		this(model.getEmptyWeight(), model.getPayloadCapacity(), model.getFuelConsumptionEmpty(), model
				.getFuelConsumptionFull());
	}

	/**
	 * Constructor taking single parameters to set specification
	 * 
	 * @param emptyWeight
	 *            The empty weight in kg of this TruckSpecification
	 * @param payloadCapacity
	 *            The maximum payload capacity in kg of this TruckSpecification
	 * @param consumptionEmpty
	 *            The fuel consumption in liter (diesel) when no cargo is loaded
	 * @param consumptionFull
	 *            The fuel consumption in liter (diesel) when fully loaded
	 */
	public TruckSpecification(Integer emptyWeight, Integer payloadCapacity, Double consumptionEmpty,
			Double consumptionFull) {
		this.emptyWeight = emptyWeight;
		this.payloadCapacity = payloadCapacity;
		this.consumptionEmpty = consumptionEmpty;
		this.consumptionFull = consumptionFull;
	}

	/**
	 * Returns the empty weight of the Truck in kg
	 * 
	 * @return the empty weight of the Truck in kg
	 */
	public Integer getEmptyWeight() {
		return this.emptyWeight;
	}

	/**
	 * The maximum payload capacity in kg
	 * 
	 * @return The maximum payload capacity in kg
	 */
	public Integer getPayloadCapacity() {
		return this.payloadCapacity;
	}

	/**
	 * The fuel consumption in liter (diesel) when no cargo is loaded
	 */
	public Double getFuelConsumptionEmpty() {
		return this.consumptionEmpty;
	}

	/**
	 * The fuel consumption in liter (diesel) when fully loaded
	 */
	public Double getFuelConsumptionFull() {
		return this.consumptionFull;
	}
}

package org.eaticious.common.co2e.transport;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Specification of Airplanes as used by EcoTransIT for calculation of CO2E-emissions
 * @author Sven Peetz
 *
 */
public class AirplaneSpecification implements Vessel {

	/**
	 * Cluster of Airplane sizes
	 * @author Sven Peetz
	 *
	 */
	public enum AirplaneSize {
		SMALL, MEDIUM, BIG;
	}

	/**
	 * Definition of standard models.
	 * 
	 * TODO transfer this to database.
	 * 
	 * @author Sven Peetz
	 */
	public enum StandardModel {
		F_747_400F(AirplaneSize.BIG, 112600, 8230, 0, Arrays.asList(6331d, 9058d, 13405d, 17751d, 22097d, 30922d,
				40267d, 49480d, 59577d, 69888d, 80789d, 91986d, 103611d, 115553d, 128171d, 141254d, 155563d, 169088d)),

		F_767_300F(AirplaneSize.MEDIUM, 53700, 6025, 0, Arrays.asList(3030d, 4305d, 6485d, 8665d, 10845d, 15409d,
				20287d, 24804d, 29909d, 35239d, 40631d, 46314d, 52208d, 58557d, 64501d)),

		F_737_200C(AirplaneSize.SMALL, 17300, 2240, 0, Arrays.asList(1800d, 2495d, 3727d, 4950d, 6191d, 8722d, 11438d)),

		P_747_400(AirplaneSize.BIG, 14000, 13450, 416, Arrays.asList(6331d, 9058d, 13405d, 17751d, 22097d, 30922d, 40267d,
				49480d, 59577d, 69888d, 80789d, 91986d, 103611d, 115553d, 128171d, 141254d, 155563d, 169088d)),

		P_757_200(AirplaneSize.MEDIUM, 4000, 7222, 200, Arrays.asList(2423d, 3410d, 5070d, 6724d, 8391d, 11846d, 15407d,
				19026d, 22348d, 25683d, 28968d)),

		P_FOKKER100(AirplaneSize.SMALL, 1000, 3170, 85, Arrays.asList(1468d, 2079d, 3212d, 4286d, 5480d, 7796d, 10400d));

		/**
		 * Number of seats that may be used to transport passengers
		 */
		private int seats;
		/**
		 * The maximum distance this Airplane can travel with one flight
		 */
		private int maxRange;
		/**
		 * The maximum cargo payload this Airplane can load
		 */
		private int maxPayload;
		/**
		 * The {@link AirplaneSize} of this Airplane
		 */
		private AirplaneSize size;
		/**
		 * A fuel consumption profile holding distances in km as keys and according fuel consumptions in kg 
		 */
		private Map<Double, Double> consumptionProfile;
		/**
		 * Standard distances used to define fuel consumption as given by EcoTransIT
		 */
		private List<Double> consumptionDistances = Arrays.asList(232d, 463d, 926d, 1389d, 1852d, 2778d, 3704d, 4630d, 5556d,
				6482d, 7408d, 8334d, 9260d, 10186d, 11112d, 12038d, 12964d, 13890d);

		/**
		 * Constructor of AirplaneModel
		 * @param size The {@link AirplaneSize} of this AirplaneModel
		 * @param maxPayload The maximum cargo payload of this AirplaneModel
		 * @param maxRange The maximum distance this AirplaneModel can travel in one flight
		 * @param seats The number of seats for passengers transport of this AirplaneModel
		 * @param consumption The consumption profile with distances in km as key and fuel consumptions in kg as value
		 */
		private StandardModel(AirplaneSize size, int maxPayload, int maxRange, int seats, List<Double> consumption) {
			this.seats = seats;
			this.maxRange = maxRange;
			this.maxPayload = maxPayload;
			this.size = size;
			this.consumptionProfile = new HashMap<Double, Double>();
			Iterator<Double> it = consumptionDistances.iterator();
			for (Double value : consumption) {
				consumptionProfile.put(it.next(), value);
			}
		}

		/**
		 * Returns the number of seats for passengers of this StandardModel
		 * 
		 * @return the number of seats for passengers of this StandardModel
		 */
		public Integer getSeats() {
			return this.seats;
		}

		/**
		 * Returns the maximum payload capacity in kilogram of this StandardModel
		 * 
		 * @return the maximum payload capacity in kilogram of this StandardModel
		 */
		public Integer getMaxPayload() {
			return this.maxPayload;
		}

		/**
		 * Returns the maximum flight range of this StandardModel in kilometer
		 * 
		 * @return the maximum flight range of this StandardModel in kilometer
		 */
		public Integer getMaxRange() {
			return this.maxRange;
		}

		/**
		 * Returns the AirplaneSize of this StandardModel
		 * 
		 * @return the AirplaneSize of this StandardModel
		 */
		public AirplaneSize getSize() {
			return this.size;
		}

		/**
		 * 
		 * @return the consumption profile of this StandardModel as a Map having distances in kilometer as keys and
		 *         kerosene consumption in kg as value
		 */
		public Map<Double, Double> getConsumptionProfile() {
			return this.consumptionProfile;
		}
	}

	/**
	 * A map having distance in kilometer as key and consumption in kg kerosene as value
	 */
	private Map<Double, Double> consumptionProfile;
	/**
	 * The maximum flight range of the Airplane
	 */
	private Integer maxRange;
	/**
	 * The maximum payload capacity of the Airplane
	 */
	private Integer maxPayload;
	/**
	 * The maximum number of passengers that can be transported
	 */
	private Integer seats;
	/**
	 * The AirplaneSize (cluster) of the Airplane
	 */
	private AirplaneSize size;

	/**
	 * Standard constructor
	 * 
	 * @param model
	 *            The Standard model used to set the specification data of the Airplane
	 */
	public AirplaneSpecification(StandardModel model) {
		this.size = model.getSize();
		this.seats = model.getSeats();
		this.maxPayload = model.getMaxPayload();
		this.maxRange = model.getMaxRange();
		this.consumptionProfile = model.getConsumptionProfile();
	}

	/**
	 * 
	 * @param size
	 *            size class of the Airplane
	 * @param seats
	 *            maximum number of passengers that can be transported
	 * @param maxRange
	 *            maximum flight range in kilometer
	 * @param maxPayload
	 *            maximum payload capacity in kilogram
	 * @param consumptionProfile
	 *            profile having distances in kilometer as key and kerosene consumption in kg as value
	 */
	public AirplaneSpecification(AirplaneSize size, Integer seats, Integer maxRange, Integer maxPayload,
			Map<Double, Double> consumptionProfile) {
		this.size = size;
		this.seats = seats;
		this.maxRange = maxRange;
		this.maxPayload = maxPayload;
		this.consumptionProfile = consumptionProfile;
	}

	/**
	 * 
	 * @return The AirplaneSize (cluster) of this Airplane
	 */
	public AirplaneSize getSize() {
		return this.size;
	}

	/**
	 * 
	 * @return the maximum flight range for this Airplane
	 */
	public Integer getMaxRange() {
		return this.maxRange;
	}

	/**
	 * 
	 * @return the maximum payload capacity in kg for this Airplane
	 */
	public Integer getMaxPayload() {
		return this.maxPayload;
	}

	/**
	 * Returns the maximum number of passengers for this Airplane
	 * 
	 * @return the maximum number of passengers for this Airplane
	 */
	public Integer getSeats() {
		return this.seats;
	}

	/**
	 * @return true if the consumptionProfile is not empty
	 */
	public boolean hasConsumptionData() {
		return !this.consumptionProfile.isEmpty();
	}

	/**
	 * This returns a Map holding distances in kilometer as keys and kerosene consumption over the distance as value
	 * 
	 * @return The consumption profile of this Airplane
	 */
	public Map<Double, Double> getConsumptionProfile() {
		return this.consumptionProfile;
	}

	/**
	 * Adds an entry into the consumptionProfile of this Airplane
	 * @param distance the distance of the flight in kilometer
	 * @param value the kerosene consumption in kg over the distance
	 */
	public void addConsumptionEntry(Double distance, Double value) {
		if(distance == null || value == null || distance < 0 || value < 0){
			throw new IllegalArgumentException("Parameters distance and value have to be not null and >= 0");
		}
		this.consumptionProfile.put(distance, value);
	}

}

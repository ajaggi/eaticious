package org.eaticious.common.co2e.transport;

public class ElectricTrainSpecification {
	/**
	 * Definition of standard models.
	 * 
	 * TODO transfer this to database.
	 * 
	 * @author Sven Peetz
	 */
	public enum TrainModel{
		EUROPEAN_STANDARD(1000), INTERNATIONAL_BIG(4000);
		/**
		 * The gross ton weight in tons of a single wagon
		 */
		private Integer gtw; 
		/**
		 * Constructor just taking the gross ton weight of a single wagon in tons
		 * @param gtw The gross ton weight in tons of a single wagon 
		 */
		private TrainModel(Integer gtw){
			this.gtw = gtw;
		}
		/**
		 * Returns the gross ton weight of a single wagon in tons
		 * @return The gross ton weight of a single wagon in tons
		 */
		public Integer getGTW(){
			return this.gtw;
		}
	}

	/**
	 * The gross ton weight in tons of a single wagon
	 */
	private Integer gtw;
	/**
	 * Constructor taking a {@link TrainModel} as parameter
	 * @param model The {@link TrainModel} used to build this ElectricTrainSpecification
	 */
	public ElectricTrainSpecification(TrainModel model){
		this(model.getGTW());
	}
	/**
	 * Constructor taking the gross ton weight in tons as a parameter
	 * @param gtw The gross ton weight of a single wagon in tons
	 */
	public ElectricTrainSpecification(Integer gtw){
		this.gtw = gtw;
	}
	/**
	 * Returns the gross ton weight of a single wagon in tons
	 * @return The gross ton weight of a single wagon in tons
	 */
	public Integer getGTW(){
		return this.gtw;
	}
}
package org.eaticious.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface FoodProduct extends Serializable {

	/**
	 * Describing classes of goods regarding the transport
	 * 
	 * BULK: Goods such as coal, grains, or bananas that are not packaged in any type of container and are stored,
	 * transported, and sold in large quantities. Transporting these goods normally will come with a high capacity
	 * utilization of the vessel.
	 * 
	 * VOLUME: Goods such as cars, vehicle parts, etc that take a lot of volume within the vessel and lead to a low
	 * capacity utilization
	 * 
	 * AVERAGE: statistically determined average value for all transports of a given carrier in a reference year
	 */
	public enum TransportClass {
		BULK(14.5), AVERAGE(10.5), VOLUME(6.0), UNKNOWN(10.5);
		
		private Double tonsPerTEU;
		
		private TransportClass(Double tonsPerTEUKM){
			this.tonsPerTEU = tonsPerTEUKM;
		}
		
		public Double getTonsPerTEUKM(){
			return this.tonsPerTEU;
		}
	}

	/**
	 * Returns the name of this FoodProduct in the requested language as a String or null if no translation is available
	 * 
	 * @param locale
	 *            Locale holding the requested language
	 * @return A String representing this FoodProduct or null
	 */
	String getName(Language language);

	/**
	 * @return The code of this product in the requested product classification or null if unknown
	 */
	String getCode(ProductClassification classification);

	/**
	 * 
	 * @return A Map holding amounts of nutrients 
	 */
	Map<Nutrient, Quantity> getNutritionData();

	/**
	 * 
	 * @return A List of all the food products which can be used in a recipe to replace this product
	 */
	List<FoodProduct> getSubstitutes();

	/**
	 * 
	 * @param region
	 *            The region for which the season is requested
	 * @param date
	 *            The date for which the season should be determined
	 * @return The season in the requested region at the requested date or null if unknown
	 */
	Season getSeason(Region region, Date date);

	/**
	 * 
	 * @param region
	 *            The region for which the seasons are requested
	 * @return A List with all known production seasons for the ingredient in the requested country
	 */
	List<Season> getSeasons(Region region);

	/**
	 * @return true if this product cannot be stored long time and thus is subject to a certain season
	 */
	Boolean isSeasonDependant();
	
	/**
	 * 
	 * @return density of ingredient for correct volume to weight transformation (EL, TL to grams)
	 */
	Double getDensity();

	/**
	 * 
	 * @return A list of tags describing any parameter of this product (e.g. contains gluten, lactose...)
	 */
	List<String> getTags();

	/**
	 * 
	 * @return The standart amount of this product in a recipe
	 */
	Quantity getStdWeight();

	/**
	 * 
	 * @param consumer
	 *            The consuming region
	 * @return A Map holding all known producers for this product in the consuming region and the corresponding
	 *         percentage the producer adds to the consumer's market
	 */
	Map<Region, Integer> getProducingRegions(Region consumer);

	/**
	 * The scientific name of the product or null if the scientific name is not known
	 * 
	 * @return
	 */
	String getScientificName();

	/**
	 * 
	 * @return A List of all the Synonyms in the standard locale. Later argument for locale to be added.
	 */
	List<String> getSynonyms();

	/**
	 * @return A String giving additional information about what to bear in mind when buying this product
	 */
	String getShoppingInfo();

	// probably (if not possible via ProductClassification), return type ev. enum
	String getCategory();

	/**
	 * Returns the TransportClass (BULK, AVERAGE, VOLUME, UNKNOWN) of this FoodProduct. If the TransportClass is not known UNKNOWN will
	 * be returned.
	 * 
	 * @return The TransportClass of this FoodProduct or TransportClass.UNKNOWN if not specified
	 */
	TransportClass getTransportClass();

}

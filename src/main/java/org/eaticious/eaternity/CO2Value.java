package org.eaticious.eaternity;

import org.eaticious.common.Quantity;
import org.eaticious.common.co2e.transport.*;


//co2 equivalent in g, splitted up by all the different quotas/factors
public interface CO2Value{
	
	Quantity getProductionValue();
	Quantity getConservationValue();
	Quantity getTransportationValue();
	Quantity getNoFactorsValue();
	Quantity getTotalValue();
	

	CO2Value add(CO2Value other);

	CO2Value mult(Double factor);

}
package org.eaticious.common;


import java.io.Serializable;


public interface SavingPotential extends Serializable, Cloneable{

	String getPotential();
	
	Double getNumber();
}
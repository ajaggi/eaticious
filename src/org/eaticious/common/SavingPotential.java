package org.eaticious.common;


import java.io.Serializable;


public interface SavingPotential extends Serializable, Cloneable{

	public String getPotential();
	
	public Double getNumber();
}
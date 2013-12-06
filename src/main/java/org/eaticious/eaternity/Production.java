package org.eaticious.eaternity;

import java.io.Serializable;

import javax.persistence.Id;

public class Production implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4042328283688002109L;
	@Id Long id;
	public String symbol;
	public Double factor;
	
	/**
	 * What is this constructor for? 
	 */
	private Production()
	{
		
	}
	
	public Production(final Production toClone) {
		symbol = new String(toClone.symbol);
		factor = new Double(toClone.factor);
	}
	
    public Production(final String symbol) {
		this.symbol = symbol;
	}
}

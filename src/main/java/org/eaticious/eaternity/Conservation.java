package org.eaticious.eaternity;

import java.io.Serializable;

import javax.persistence.Id;

public class Conservation implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6977365500555091257L;
	@Id Long id;
	public String symbol;
	public Double factor;
	
	private Conservation()	{ /* needed for objectify */  }
	
	public Conservation(final Conservation toClone) {
		symbol = new String(toClone.symbol);
		factor = new Double(toClone.factor);
	}
	
    public Conservation(final String symbol) {
		this.symbol = symbol;
	}
    
    public Conservation(final String symbol, final Double factor) {
    	this.symbol = symbol;
    	this.factor = factor;
	}
}

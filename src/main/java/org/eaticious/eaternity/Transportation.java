package org.eaticious.eaternity;

import java.io.Serializable;

import javax.persistence.Id;

public class Transportation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971128872903171922L;
	@Id Long id;
    public String symbol;
	public Double factor;
    
	/**
	 * What is this constructor for?
	 */
    private Transportation()
    {
    	
    }
    
    public Transportation(final Transportation toClone) {
		symbol = new String(toClone.symbol);
		factor = new Double(toClone.factor);
	}

    public Transportation(final String symbol) {
    	this.symbol = symbol;
	}
    
    public Transportation(final String symbol, final Double factor) {
    	this.symbol = symbol;
    	this.factor = factor;
	}
}

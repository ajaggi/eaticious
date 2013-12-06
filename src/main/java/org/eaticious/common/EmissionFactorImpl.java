package org.eaticious.common;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

@Entity
public class EmissionFactorImpl implements EmissionFactor {
	@Id
	private Integer id;
	@Index
	private String name;
	@Unindex
	private Double factor;
	@Embed
	private Unit referencedUnit;
	
	public EmissionFactorImpl(){
		/* needed by objectify */
	}

	public EmissionFactorImpl(String name, Double factor, Unit reference){
		this.name = name;
		this.factor = factor;
		this.referencedUnit = reference;
	}

	@Override
	public Quantity calcCO2e(Quantity reference) throws IllegalArgumentException {
		Double amount = reference.convert(this.referencedUnit).getAmount() * this.factor;
		return new QuantityImpl(amount, Unit.KG_CO2E);
	}

	@Override
	public Double getFactor() {
		return this.factor;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public Unit getReferencedUnit() {
		return this.referencedUnit;
	}

	public void setFactor(Double factor) {
		this.factor = factor;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReferencedUnit(Unit referencedUnit) {
		this.referencedUnit = referencedUnit;
	}

}

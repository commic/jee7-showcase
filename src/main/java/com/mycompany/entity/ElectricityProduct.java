package com.mycompany.entity;

import javax.persistence.Entity;

@Entity
public class ElectricityProduct extends Product {

	private VoltageRange voltageRange;
	
	public ElectricityProduct() {
		super();
	}

	public VoltageRange getVoltageRange() {
		return voltageRange;
	}

	public void setVoltageRange(VoltageRange voltageRange) {
		this.voltageRange = voltageRange;
	}
	
}

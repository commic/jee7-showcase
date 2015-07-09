package com.mycompany.entity;

import javax.persistence.Entity;

@Entity
public class ElectricityProduct extends Product {

	private VoltageRange type;
	
	public ElectricityProduct() {
		super();
	}

	public VoltageRange getType() {
		return type;
	}

	public void setType(VoltageRange type) {
		this.type = type;
	}
	
}

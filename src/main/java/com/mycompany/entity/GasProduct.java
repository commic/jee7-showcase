package com.mycompany.entity;

import javax.persistence.Entity;
import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

@Entity
public class GasProduct extends Product {

	private double volumne; 
	
	public GasProduct() {}

	public double getVolumne() {
		return volumne;
	}

	public void setVolumne(double volumne) {
		this.volumne = volumne;
	}
	
	@FormParam("volumne")
	@PartType("html/text")
	public void setVolumneText(String volumne) {
		try{
			setVolumne(Double.parseDouble(volumne));
		}catch(NumberFormatException e) {
			setVolumne(0);
		}
	}
	
}

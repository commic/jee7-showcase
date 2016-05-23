package com.mycompany.entity;

import javax.persistence.Entity;
import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

@Entity
public class GasProduct extends Product {

	private double volume; 
	
	public GasProduct() {
		super();
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	@FormParam("volumne")
	@PartType("html/text")
	public void setVolumeText(String volumne) {
		try{
			setVolume(Double.parseDouble(volumne));
		}catch(NumberFormatException e) {
			setVolume(0);
		}
	}
	
}

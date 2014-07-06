package com.mycompany.entity;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Product {

	@Id
	private long id;
	private String name;
	
	@ManyToOne
	private ProductCategory category;
	
	private BigDecimal price;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(length=100000)
	private byte[] thumbnail;
	
	public Product() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public byte[] getThumbnail() {
		return thumbnail;
	}
	
	@FormParam("thumbnail")
	@PartType("application/octet-stream")
	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Product [id=%s, name=%s, category=%s, price=%s]",
									   id, 	  name,    category, 	price);
	}
	
}

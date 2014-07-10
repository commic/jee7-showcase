package com.mycompany.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Product {

	@Id
	private long id;
	@NotNull(message="Bitte geben Sie einen Namen an")
	private String name;
	
	@ManyToOne(optional=true)
	private ProductCategory category;
	
	@Transient
	private String categoryName;
	
	@DecimalMin(value="0", message="Der Preis darf nicht negativ sein")
	private Integer price;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(length=100000, nullable=true)
	private byte[] thumbnail;
	
	public Product() {}

	public String getName() {
		return name;
	}

	@FormParam("productName")
	@PartType("html/text")
	public void setName(String name) {
		this.name = name;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	
	@FormParam("categoryName")
	@PartType("html/text")
	public void setCategoryName(String name) {
		this.categoryName = name;
	}

	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@FormParam("price")
	@PartType("html/text")
	public void setPriceText(String price) {
		try{
			setPrice(Integer.parseInt(price));
		}catch(NumberFormatException e) {
			setPrice(0);
		}
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

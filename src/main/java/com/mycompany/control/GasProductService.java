package com.mycompany.control;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mycompany.entity.GasProduct;
import com.mycompany.entity.Product;
import com.mycompany.entity.QGasProduct;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

@Stateless
@Local
public class GasProductService {

	@PersistenceContext
	private EntityManager entityManager;

	public void saveGasProduct(Product product) {
		entityManager.persist(product);
	}

	public List<GasProduct> findGasProducts() {
		
		return new ArrayList<GasProduct>();
	}

	public GasProduct findGasProductById(Long id) {
		return entityManager.find(GasProduct.class, id);
	}

	public void updateGasProduct(GasProduct gasProduct) {
		entityManager.merge(gasProduct);
	}
	
	public List<GasProduct> findGasProducts(String searchString) {
		
		return new ArrayList<GasProduct>();
	}

	private String[] splitSearchString(String searchString) {
		String[] searchTerms = new String[] { searchString };
		if (searchString.contains(", ")) {
			searchTerms = searchString.split(", ");
		} else if (searchString.contains("; ")) {
			searchTerms = searchString.split("; ");
		} else if (searchString.contains(",")) {
			searchTerms = searchString.split(",");
		} else if (searchString.contains(";")) {
			searchTerms = searchString.split(";");
		} else if (searchString.contains(" ")) {
			searchTerms = searchString.split(" ");
		}
		return searchTerms;
	}

	public void deleteGasProduct(Long id) {
		GasProduct gasProduct = findGasProductById(id);
		if (gasProduct != null) {
			entityManager.remove(gasProduct);
		}
	}

}

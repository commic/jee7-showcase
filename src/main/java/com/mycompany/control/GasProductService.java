package com.mycompany.control;

import java.util.List;

import javax.ejb.EJB;
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
	
	@EJB
	private ProductService productService;

	public void saveGasProduct(GasProduct product) {
		entityManager.persist(product);
	}

	@SuppressWarnings("unchecked")
	public List<GasProduct> findGasProducts() {
		return (List<GasProduct>) productService.findAllProductsOfType(QGasProduct.gasProduct);
	}

	public GasProduct findGasProductById(Long id) {
		return new JPAQuery(entityManager)
		   .from(QGasProduct.gasProduct) 
		   .where(QGasProduct.gasProduct.id.eq(id))
		   .uniqueResult(QGasProduct.gasProduct	);
	}

	public void updateGasProduct(GasProduct gasProduct) {
		entityManager.merge(gasProduct);
	}
	
	public List<GasProduct> findGasProducts(String searchString) {
		String[] searchTerms = splitSearchString(searchString);
		QGasProduct qGasProduct = QGasProduct.gasProduct;
		JPAQuery query = new JPAQuery(entityManager);
		BooleanBuilder builder = new BooleanBuilder();
		for (String term : searchTerms) {
			builder.or(qGasProduct.name.eq(term));
			builder.or(qGasProduct.category.name.eq(term));
		}
		return query.from(qGasProduct).where(builder).list(qGasProduct);
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

package com.mycompany.control;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mycompany.entity.Product;
import com.mycompany.entity.QProduct;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

@Stateless
@Local
public class ProductService {

	@PersistenceContext
	private EntityManager entityManager;

	public void createProduct(Product p) {
		entityManager.persist(p);
	}

	public void updateProduct(Product p) {
		entityManager.merge(p);
	}

	public void deleteProductById(long id) {
		entityManager.remove(findProductById(id));
	}
	
	public Product findProductById(long id){
		return new JPAQuery(entityManager)
		 		   .from(QProduct.product) 
				   .where(QProduct.product.id.eq(id))
				   .uniqueResult(QProduct.product);
	}

	public List<? extends Product> findAllProducts() {
		return new JPAQuery(entityManager).from(QProduct.product).listResults(QProduct.product).getResults();
	}
	
	public List<? extends Product> findAllProductsOfType(EntityPathBase<? extends Product> productType) {
		JPAQuery query = new JPAQuery(entityManager);
		return query.from(productType)
					.listResults(productType)
					.getResults();
					
	}
	
}

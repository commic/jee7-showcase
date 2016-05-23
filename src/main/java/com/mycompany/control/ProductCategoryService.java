package com.mycompany.control;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mycompany.entity.ProductCategory;
import com.mycompany.entity.QProductCategory;
import com.mysema.query.jpa.impl.JPAQuery;

@Stateless
@Local
public class ProductCategoryService {

	@PersistenceContext
	private EntityManager entityManager;

	public void createProductCategory(ProductCategory p) {
		entityManager.persist(p);
	}

	public void updateProductCategory(ProductCategory p) {
		entityManager.merge(p);
	}

	public void deleteProductCategoryById(long id) {
		entityManager.remove(findProductById(id));
	}
	
	public ProductCategory findProductById(long id){
		return new JPAQuery(entityManager)
		 		   .from(QProductCategory.productCategory) 
				   .where(QProductCategory.productCategory.id.eq(id))
				   .uniqueResult(QProductCategory.productCategory);
	}
	
	public List<ProductCategory> findAllProductCategories() {
		return new JPAQuery(entityManager).from(QProductCategory.productCategory).listResults(QProductCategory.productCategory).getResults();
	}
	
}

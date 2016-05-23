package com.mycompany.boundary;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.mycompany.control.ProductCategoryService;
import com.mycompany.entity.ProductCategory;

public class ProductCategoryResource implements IProductCategoryResource {

	
	@EJB
	private ProductCategoryService categoryService;
	
	@Override
	public Response saveCategory(UriInfo uriInfo, ProductCategory category) throws URISyntaxException {
		categoryService.createProductCategory(category);
		return Response.created(new URI(uriInfo.getRequestUri() + "/" + category.getId()))
					   .build() ;
	}

	@Override
	public Response findProductCategoryById(String categoryId) {
		ProductCategory category = categoryService.findProductById(Long
				.parseLong(categoryId));

		if (category != null) {
			return Response.ok().entity(category).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response findCategories(String searchString) {
		return Response.ok(categoryService.findAllProductCategories()).build();
	}

	@Override
	public Response deleteCategoryById(Long categoryId) {
		categoryService.deleteProductCategoryById(categoryId);
		return Response.ok().build();
	}

	@Override
	public Response updateProductCategory(Long categoryId, ProductCategory category) {
		categoryService.updateProductCategory(category);
		return Response.status(Status.ACCEPTED).build();
	}

}

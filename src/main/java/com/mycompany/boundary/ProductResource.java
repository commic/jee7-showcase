package com.mycompany.boundary;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.control.ProductService;

public class ProductResource implements IProductResource{

	@EJB
	private ProductService productService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductCategories(String searchString){
		return Response.ok(productService.findAllProductCategories()).build();
	}
}

package com.mycompany.boundary;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.mycompany.control.GasProductService;
import com.mycompany.control.ProductService;
import com.mycompany.entity.GasProduct;
import com.mycompany.entity.Product;

public class ProductResource implements IProductResource{

	@EJB
	private ProductService productService;
	
	@EJB
	private GasProductService gasProductService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductCategories(String searchString){
		return Response.ok(productService.findAllProductCategories()).build();
	}
	
	public Response saveProduct(@Context UriInfo uriInfo, Product product) throws URISyntaxException {
		if(product instanceof GasProduct) {
			gasProductService.saveGasProduct((GasProduct)product);
		}
		return Response.created(new URI(uriInfo.getRequestUri() + "/" + product.getId()))
					   .build() ;
	}
	
	public Response updateProduct(Long productId, Product product){		
		if(product instanceof GasProduct) {
			gasProductService.saveGasProduct((GasProduct)product);
		}
		return Response.status(Status.ACCEPTED).build();
	}
	
	public Response findProducts(@QueryParam("searchString") String searchString){
		return Response.ok().build();
	}

	public Response findProductById(String productId) {
		Product product = productService.findProductById(Long
				.parseLong(productId));

		if (product != null) {
			return Response.ok().entity(product).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response deleteProductById(Long productId) {
		productService.deleteProductById(productId);
		return Response.ok().build();
	}
}
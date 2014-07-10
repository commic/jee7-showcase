package com.mycompany.boundary;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.EJB;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.mycompany.control.GasProductService;
import com.mycompany.control.ProductService;
import com.mycompany.entity.GasProduct;

public class GasProductResouce implements IGasProductResource{

	@EJB
	private GasProductService gasProductService;
	
	@EJB
	private ProductService productService;
	
	public Response saveGasProduct(@Context UriInfo uriInfo, GasProduct product) throws URISyntaxException {
		gasProductService.saveGasProduct(product);
		return Response.created(new URI(uriInfo.getRequestUri() + "/" + product.getId()))
					   .build() ;
	}
	
	Response updateGasProduct(GasProduct product){
		gasProductService.updateGasProduct(product);
		return Response.status(Status.ACCEPTED).build();
	}
	
	public Response deleteGasProduct(Long productId){
		gasProductService.deleteGasProduct(productId);
		return Response.ok().build();
	}
	
	public Response findGasProducts(@QueryParam("searchString") String searchString){
		return Response.ok().build();
	}

	public Response findGasProductById(String productId) {
		GasProduct gasProduct = gasProductService.findGasProductById(Long
				.parseLong(productId));

		if (gasProduct != null) {
			return Response.ok().entity(gasProduct).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	
	
	
}

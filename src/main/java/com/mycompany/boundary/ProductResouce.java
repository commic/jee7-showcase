package com.mycompany.boundary;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.mycompany.control.ProductService;
import com.mycompany.entity.ElectricityProduct;
import com.mycompany.entity.GasProduct;

@Path("/product")
@Stateless
public class ProductResouce {

	private static final String PRODUCT_ID_PLAIN = "productId";
	private static final String PRODUCT_ID = "/{" + PRODUCT_ID_PLAIN + "}";
	@EJB
	private ProductService service;
	
	@POST
	@Path(PRODUCT_ID)
	public Response saveGasProduct(@Context UriInfo uriInfo, GasProduct product) throws URISyntaxException {
		service.createProduct(product);
		return Response.created(new URI(uriInfo.getRequestUri() + "/" + product.getId()))
					   .build() ;
	}
	
	@POST
	@Path(PRODUCT_ID)
	public Response saveElectricityProduct(@Context UriInfo uriInfo, ElectricityProduct product) throws URISyntaxException {
		service.createProduct(product);
		return Response.created(new URI(uriInfo.getRequestUri() + "/" + product.getId()))
					   .build() ;
	}
	
	@PUT
	@Path(PRODUCT_ID)
	Response updateCustomer(GasProduct product){
		service.updateProduct(product);
		return Response.status(Status.ACCEPTED).build();
	}
	
	@PUT
	@Path(PRODUCT_ID)
	Response updateCustomer(ElectricityProduct product){
		service.updateProduct(product);
		return Response.status(Status.ACCEPTED).build();
	}
	
	@DELETE
	@Path(PRODUCT_ID)
	public Response deleteProduct(@PathParam(PRODUCT_ID_PLAIN) Long productId){
		service.deleteProductById(productId);
		return Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllProducts(){
		return Response.ok(service.findAllProducts()).build();
	}
	
	
}

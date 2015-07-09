package com.mycompany.boundary;

import java.net.URISyntaxException;

import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.mycompany.entity.GasProduct;

@Path("/product")
@Stateless
public interface IProductResource {

	@GET
	@Path("/category")
	@Produces(MediaType.APPLICATION_JSON)
	Response findProductCategories(@QueryParam("searchString") String searchString);
	
	@POST
	public Response saveProduct(@Context UriInfo uriInfo,
			GasProduct product) throws URISyntaxException;
	
	@GET
	@Path("/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	Response findProductById(@PathParam(value = "productId") String productId);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response findProducts(@QueryParam("searchString") String searchString);
	
	@DELETE
	@Path("/{productId}")
	Response deleteProductById(@PathParam("productId") Long productId);
	
	@PUT
	@Path("/{productId}")
	Response updateProduct(@PathParam(value = "productId") Long productId, GasProduct product);
}

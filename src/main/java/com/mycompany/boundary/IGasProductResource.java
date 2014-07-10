package com.mycompany.boundary;

import java.net.URISyntaxException;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.mycompany.entity.GasProduct;
@Path("/gasproduct")
@Stateless
public interface IGasProductResource {
	
	@POST
	@Path("/save")
	@Consumes("multipart/form-data")
	public Response saveGasProduct(@Context UriInfo uriInfo,
			@MultipartForm GasProduct product) throws URISyntaxException;
	
	@GET
	@Path("/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	Response findGasProductById(@PathParam(value = "productId") String productId);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response findGasProducts(@QueryParam("searchString") String searchString);
	
	@DELETE
	@Path("/{productId}")
	Response deleteGasProduct(@PathParam("productId") Long productId);	

}

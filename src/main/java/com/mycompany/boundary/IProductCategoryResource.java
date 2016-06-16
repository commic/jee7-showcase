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

import com.mycompany.entity.ProductCategory;

@Path("/category")
@Stateless
public interface IProductCategoryResource {

	@POST
	public Response saveCategory(@Context UriInfo uriInfo,
			ProductCategory category) throws URISyntaxException;
	
	@GET
	@Path("/{categoryId}")
	@Produces(MediaType.APPLICATION_JSON)
	Response findProductCategoryById(@PathParam(value = "categoryId") Long categoryId);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response findCategories(@QueryParam("searchString") String searchString);
	
	@DELETE
	@Path("/{categoryId}")
	Response deleteCategoryById(@PathParam("categoryId") Long categoryId);
	
	@PUT
	@Path("/{categoryId}")
	Response updateProductCategory(@PathParam(value = "categoryId") Long categoryId, ProductCategory category);
}

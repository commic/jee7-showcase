package com.mycompany.boundary;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/product")
@Stateless
public interface IProductResource {

	@GET
	@Path("/category")
	@Produces(MediaType.APPLICATION_JSON)
	Response findProductCategories(@QueryParam("searchString") String searchString);
}

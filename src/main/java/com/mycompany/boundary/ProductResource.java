package com.mycompany.boundary;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
import com.mycompany.entity.Customer;
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
	
	public Response saveProduct(@Context UriInfo uriInfo, GasProduct product) throws URISyntaxException {
		gasProductService.saveGasProduct(product);
		return Response.created(new URI(uriInfo.getRequestUri() + "/" + product.getId()))
					   .build() ;
	}
	
	public Response updateProduct(Long productId, GasProduct product){		
		if(product instanceof GasProduct) {
			gasProductService.saveGasProduct((GasProduct)product);
		}
		return Response.status(Status.ACCEPTED).build();
	}
	
	public Response findProducts(@QueryParam("searchString") String searchString){
		List<Product> products;
		if (searchString != null) {
			products = productService.findProducts(searchString);
		} else {
			products = (List<Product>) productService.findAllProducts();
		}
		return Response.ok(products).build();
	}

	public Response findProductById(String productId) {
		GasProduct product = gasProductService.findGasProductById(Long
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

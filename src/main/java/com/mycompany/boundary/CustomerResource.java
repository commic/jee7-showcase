package com.mycompany.boundary;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.mycompany.control.CustomerService;
import com.mycompany.control.ProductService;
import com.mycompany.control.ValidationException;
import com.mycompany.entity.Customer;

public class CustomerResource implements ICustomerResource {

	@EJB
	private CustomerService customerService;
	
	@EJB
	private ProductService productService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.boundary.ICustomerResource#findCustomers(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Response findCustomers(String searchString) {
		List<Customer> customers;
		if (searchString != null) {
			customers = customerService.findCustomers(searchString);
		} else {
			customers = customerService.findCustomers();
		}
		return Response.ok(customers).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.boundary.ICustomerResource#saveCustomer(javax.ws.rs.core
	 * .UriInfo, com.mycompany.entity.Customer)
	 */
	@Override
	public Response saveCustomer(UriInfo uriInfo, Customer customer)
			throws URISyntaxException {
		try{
			customerService.saveCustomer(customer);
		}catch(ValidationException e) {
			
		}		
		return Response.created(
				new URI(uriInfo.getRequestUri() + "/" + customer.getId()))
				.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.boundary.ICustomerResource#findCustomerById(java.lang.String
	 * )
	 */
	@Override
	public Response findCustomerById(String customerId) {
		Customer customer = customerService.findCustomerById(Long
				.parseLong(customerId));

		if (customer != null) {
			return Response.ok().entity(customer).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.boundary.ICustomerResource#updateCustomer(com.mycompany
	 * .entity.Customer)
	 */
	@Override
	public Response updateCustomer(Customer customer) {
		try{
		customerService.updateCustomer(customer);
		}catch(ValidationException e) {
			
		}
		return Response.status(Status.ACCEPTED).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.boundary.ICustomerResource#deleteCustomer(java.lang.Long)
	 */
	@Override
	public Response deleteCustomer(Long customerId) {
		customerService.deleteCustomer(customerId);
		return Response.ok().build();
	}

	@Override
	public Response sayHello() {
		return Response.ok(new String[]{"Hello ", " world"}).build();
	}

	@Override
	public Response sendProductListToCustomer(Long customerId) throws MessagingException {
		customerService.sendProductNewsLetterToCustomer(customerId, productService.findAllProducts());
		return Response.ok("mail sent successfully").build();
	}
	
}

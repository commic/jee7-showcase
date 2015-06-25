package com.mycompany.boundary;

import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.mycompany.control.CustomerService;
import com.mycompany.entity.Customer;

public class CustomerResource implements ICustomerResource {

	@EJB
	private CustomerService customerService;

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
	// TODO
			throws URISyntaxException {
		return Response.ok().entity(customer).build();
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
		// TODO
		Customer customer = null;
		return Response.ok().entity(customer).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.boundary.ICustomerResource#updateCustomer(com.mycompany
	 * .entity.Customer)
	 */
	@Override
	public Response updateCustomer(Long customerId, Customer customer) {
		// TODO
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
		// TODO
		return Response.ok().build();
	}

	@Override
	public Response sayHello() {
		return Response.ok(new String[]{"Hello ", " world"}).build();
	}	
}

package com.mycompany.boundary;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mycompany.control.CompanyService;
import com.mycompany.control.CustomerService;
import com.mycompany.control.ValidationException;
import com.mycompany.entity.Customer;

public class CustomerResource implements ICustomerResource {

	@EJB
	private CustomerService customerService;
	
	@EJB
	private CompanyService companyService;

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
	public Response saveCustomer(Customer customer) throws URISyntaxException{
		try {
			customerService.saveCustomer(customer);
		}catch(ValidationException e){
			e.printViolations();
		}
		return Response.created(URI.create("/customer/" + customer.getId())).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.boundary.ICustomerResource#findCustomerById(java.lang.String
	 * )
	 */
	@Override
	public Response findCustomerById(Long customerId) {
		Customer customer = customerService.findCustomerById(customerId);
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
	public Response updateCustomer(Customer customer) {
		try{
		customerService.updateCustomer(customer);
		}catch(ValidationException e) {
			Response.status(Status.CONFLICT).build();
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
}

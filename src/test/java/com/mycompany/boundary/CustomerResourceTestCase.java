package com.mycompany.boundary;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycompany.control.CompanyService;
import com.mycompany.control.ValidationException;
import com.mycompany.entity.Company;
import com.mycompany.entity.Customer;
import com.mycompany.entity.Sex;
import com.mycompany.testutils.DeploymentDescriptor;


@RunWith(Arquillian.class)
public class CustomerResourceTestCase {

	@Deployment(testable = true, order = 1, name = "crmDemoWebArchive", managed = true)
	public static WebArchive createDeployment() {
		return DeploymentDescriptor.createDeployment();
	}

    /**
     * The context path of the deployed application.
     */
    @ArquillianResource
    private URL contextPath;
    
    @EJB
    private CompanyService companyService;


    /**
     * Sets up the test environment.
     */
    @BeforeClass
    public static void setUpClass() {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
    }

    /**
     * Sets up class.
     * @throws ValidationException 
     */
    @Before
    public void setUp() throws ValidationException {
    	companyService.saveCompany(new Company("GISA GmbH"));
    }

    @Test
    @InSequence(1)
    public void testSaveCustomer(@ArquillianResteasyResource("rest") ICustomerResource customerResource, @ArquillianResteasyResource("rest") ICompanyResource companyResource) throws URISyntaxException {
    	Customer customer = new Customer("Max", "Mustermann", 
				"max@mustermann.de", "0123456789", "01234561234",
				Sex.male, "Germany", Locale.GERMANY, new Date(), 
				((List<Company>)companyResource.findCompanies(null).getEntity()).get(0));
        Response response = customerResource.saveCustomer(customer);
        assertEquals("The request didn't succeeded.", Response.Status.CREATED.getStatusCode(), response.getStatus());
    }
    
    @Test
    @InSequence(2)
    public void testUpdateCustomer(@ArquillianResteasyResource("rest") ICustomerResource customerResource, @ArquillianResteasyResource("rest") ICompanyResource companyResource) throws URISyntaxException {
    	Response customersResponse = customerResource.findCustomers("Mustermann");
    	List<Customer> customers = (List<Customer>)customersResponse.getEntity();
    	assertEquals("Size of Customers is not correct.", 1, customers.size());
    	Customer customer = customers.get(0);
    	Long id = customer.getId();
    	customer.setSurname("Liebermann");
    	Response response = customerResource.updateCustomer(customer);
        Response customerResponse = customerResource.findCustomerById(id);
        Customer customerUpdated = (Customer)customerResponse.getEntity();
        assertEquals("Updated customer is not correct.", "Liebermann", customer.getSurname());
    }
    
    @Test
    @InSequence(3)
    public void testDeleteCustomer(@ArquillianResteasyResource("rest") ICustomerResource customerResource, @ArquillianResteasyResource("rest") ICompanyResource companyResource) throws URISyntaxException {
    	Response customersResponse = customerResource.findCustomers("Liebermann");
    	List<Customer> customers = (List<Customer>)customersResponse.getEntity();
    	assertEquals("Size of Customers is not correct.", 1, customers.size());
    	Customer customer = customers.get(0);
    	Long id = customer.getId();
    	customerResource.deleteCustomer(id);
    	Response customersResponse2 = customerResource.findCustomers("Liebermann");
    	List<Customer> customers2 = (List<Customer>)customersResponse2.getEntity();
        assertEquals("Delete did not work.", 0, customers2.size());
    }
    
}

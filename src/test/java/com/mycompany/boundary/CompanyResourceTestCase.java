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
public class CompanyResourceTestCase {

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
    
    private static String COMPANY_NAME = "Gisa GmbH";


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
    }

    @Test
    @InSequence(1)
    public void testSaveCompany(@ArquillianResteasyResource("rest") ICompanyResource companyResource) throws URISyntaxException {
    	Company company = new Company(COMPANY_NAME);				
        Response response = companyResource.saveCompany(company);
        assertEquals("The request didn't succeeded.", Response.Status.CREATED.getStatusCode(), response.getStatus());
    }
    
    @Test
    @InSequence(2)
    public void testfindCompanies(@ArquillianResteasyResource("rest") ICompanyResource companyResource) throws URISyntaxException {
    	Response response = companyResource.findCompanies(COMPANY_NAME);
    	assertEquals("The response doesn't match the expected type.", true, response.getEntity() instanceof List);
    	List<Company> result = (List<Company>)response.getEntity();
    	assertEquals("Only one company expected.", 1, result.size());
    	Response response2 = companyResource.findCompanyById(result.get(0).getId());
    	assertEquals("The response doesn't match the expected type.", true, response.getEntity() instanceof Company);
    	Company result2 = (Company)response2.getEntity();
    	assertEquals("Expect correct company name.", COMPANY_NAME, result2.getName());
    }
    
    @Test
    @InSequence(3)
    public void testdeleteCompanies(@ArquillianResteasyResource("rest") ICompanyResource companyResource) throws URISyntaxException {
    	
    }
}

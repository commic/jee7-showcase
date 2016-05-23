package com.mycompany.boundary;

import static org.junit.Assert.assertEquals;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.warp.WarpTest;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycompany.control.CompanyService;
import com.mycompany.control.CustomerService;
import com.mycompany.control.ValidationException;
import com.mycompany.entity.Company;
import com.mycompany.entity.Customer;
import com.mycompany.entity.Sex;
import com.mycompany.testutils.DeploymentDescriptor;


@RunWith(Arquillian.class)
public class HelloWorldResourceTestCase {

	@Deployment(testable = true, order = 1, name = "crmDemoWebArchive", managed = true)
	public static WebArchive createDeployment() {
		return DeploymentDescriptor.createDeployment();
	}

    /**
     * The context path of the deployed application.
     */
    @ArquillianResource
    private URL contextPath;


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
    public void testSayHello(@ArquillianResteasyResource("rest") IHelloWorldResource helloWorldResource) {
        Response response = helloWorldResource.sayHello("Max", "Mustermann");
        assertEquals("The request didn't succeeded.", Response.Status.OK.getStatusCode(), response.getStatus());
    }
}

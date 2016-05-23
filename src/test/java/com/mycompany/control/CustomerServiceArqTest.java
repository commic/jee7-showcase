package com.mycompany.control;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycompany.entity.Company;
import com.mycompany.entity.Customer;
import com.mycompany.entity.Sex;
import com.mycompany.testutils.DeploymentDescriptor;

@RunWith(Arquillian.class)
public class CustomerServiceArqTest {

	@ArquillianResource
	private URL contextPath;

	@EJB
	private CustomerService customerService;

	@EJB
	private CompanyService companyService;

	@Deployment(testable = true, order = 1, name = "crmDemoWebArchive", managed = true)
	public static WebArchive createDeployment() {
		return DeploymentDescriptor.createDeployment();
	}

	@Before
	public void initMasterData() throws ValidationException {
		Company company = new Company("GISA GmbH");
		companyService.saveCompany(company);
		Customer customer = new Customer("Max", "Mustermann", 
				"max@mustermann.de", "0123456789", "01234561234",
				Sex.male, "Germany", Locale.GERMANY, new Date(), 
				companyService.listAllCompanies().get(0));
		customerService.saveCustomer(customer);
	}

	@Test
	@InSequence(1)
	public void testSyncUserList() {
		assertEquals(1, customerService.findAllCustomers().size());
	}

}

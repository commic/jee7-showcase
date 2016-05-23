package com.mycompany.control;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

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
import com.mycompany.testutils.DeploymentDescriptor;

@RunWith(Arquillian.class)
public class CompanyServiceArqTest {
	

	@ArquillianResource
	private URL contextPath;

	@EJB
	private CompanyService companyService;

	@Deployment(testable = true, order = 1, name = "crmDemoWebArchive", managed = true)
	public static WebArchive createDeployment() {
		return DeploymentDescriptor.createDeployment();
	}

	@Before
	public void initMasterData() {
		try {
			companyService.saveCompany(new Company("GISA GmbH"));
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}

	

	@Test
	@InSequence(1)
	public void testFindAllCompanies() {
		assertEquals(1, companyService.listAllCompanies().size());
	}

}

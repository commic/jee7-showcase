package com.mycompany.testutils;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class DeploymentDescriptor {

	private DeploymentDescriptor(){}
	
	/**
	 * The location of the WebApp source folder so we know where to find the
	 * web.xml when deploying using Arquillian.
	 */
	private static final String WEBAPP_SRC = "src/main/webapp";
	/**
	 * The name of the WAR Archive that will be used by Arquillian to deploy the
	 * application.
	 */
	private static final String APP_NAME = "crm-demo";
	
	public static WebArchive createDeployment() {
		File[] libs = Maven.configureResolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies()
				.resolve().withTransitivity().asFile();

		return ShrinkWrap.create(WebArchive.class, APP_NAME + ".war").addPackages(true, "com.mycompany")
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addAsWebInfResource("wildfly-ds.xml").addAsLibraries(libs);
				// .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/web.xml"));
	}
}

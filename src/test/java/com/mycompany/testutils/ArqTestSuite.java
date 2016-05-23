package com.mycompany.testutils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.mycompany.boundary.HelloWorldResourceTestCase;
import com.mycompany.control.CompanyServiceArqTest;
import com.mycompany.control.CustomerServiceArqTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	   CompanyServiceArqTest.class,
	   CustomerServiceArqTest.class,
	   HelloWorldResourceTestCase.class
	})
public class ArqTestSuite {

}

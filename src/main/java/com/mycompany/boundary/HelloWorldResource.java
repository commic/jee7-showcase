package com.mycompany.boundary;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import com.mycompany.control.CustomerService;

public class HelloWorldResource implements IHelloWorldResource {
	
	@EJB
	private CustomerService customerService;
	
	@Override
	public Response sayHello(String name, String nachname) {
		return Response.ok("Hello  world, Hello " + name + " " + nachname).build();
	}	
	
	@Override
	public Response sendMail() {
		customerService.send("micha.bgl@gmx.de", "Das ist eine Testmail", "Ein kleiner Demotext");
		return Response.ok("Mail sent").build();
	}
}

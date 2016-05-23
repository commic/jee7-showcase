package com.mycompany.boundary;

import javax.ws.rs.core.Response;

public class HelloWorldResource implements IHelloWorldResource {
	
	@Override
	public Response sayHello(String name, String nachname) {
		return Response.ok("Hello  world, Hello " + name + " " + nachname).build();
	}	
}

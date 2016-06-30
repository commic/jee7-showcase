package com.mycompany.boundary;

import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
@Consumes({MediaType.TEXT_PLAIN})
@Produces({MediaType.TEXT_PLAIN})
public interface IHelloWorldResource {
	
	/**
	 * Einfache Testmethode um die Verfügbarkeit des Services zu überprüfen.
	 * 
	 * @return Eine einfache Zeichenkette z.B. "Hello Java EE!"
	 */
	@GET
	@Path("/{name}")
	public abstract Response sayHello(@PathParam("name") String name, @QueryParam("nachname")String nachname);
	
	/**
	 * Versenden der E-Mail über eine POST
	 * @return
	 * @throws URISyntaxException
	 */
	@POST
	@Path("/sendmail")
	Response sendMail() throws URISyntaxException;

}

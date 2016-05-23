package com.mycompany.boundary; 

import java.net.URISyntaxException;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.entity.Company;

/**
 *	Diese Klasse dient zur Anbindung des Frontends. 
 *	Es werden RESTful Endpunkte (= Resourcen) definiert, 
 *	ueber welche das Frontend die Nutzerinteraktion weitergeben kann. 
 *
 * 	Delegieren Sie die Logik der zu implementierenden Methoden an eine EJB (CustomerService).
 *  Dies koennte wie folgt aussehen:
 *  <p>
 *  <code>
   		// import statements vernachlaessigt <br/>
   		public class CustomerResource implements ICustomerResource {<br/>
		&#064;EJB<br/>
		private CustomerService customerService;<br/>
		<br/>
			&#064;Override<br/>
			public Response saveCustomer(UriInfo uriInfo, Customer customer) throws URISyntaxException {<br/>
				customerService.saveCustomer(customer);<br/>
				// ...<br/> 
			}<br/>
		//...<br/>
		}<br/>
	</code>
	</p>
 *  
 *  Damit koennten auch andere Frontends angebunden werden bzw. das Frontend und Backend ist austauschbar!
 *  Weitere Informationen finden Sie im JavaDoc der einzelnen Methoden.
 */
@Path("/company")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Stateless
public interface ICompanyResource {

	public static final String COMPANY_ID_PLAIN = "companyId";
	public static final String COMPANY_ID = "{" + COMPANY_ID_PLAIN + "}";
	
	
	@GET
	@Path("/" + COMPANY_ID)
	Response findCompanyById(@PathParam(value = COMPANY_ID_PLAIN) String compamyId);

	/**
	 * <p>Diese Methode dient dem Auffinden von Kunden, welchen einem oder mehreren Kriterien entsprechen.
	 * Ist kein Kriterium in Parameter searchString angegeben wurden, sollen alle Kunden zurueckgegeben werden. 
	 * Die Kriterien werden konsistent mit einem Zeichen aus der Menge <b>{","," ",";" }</b>, wobei auch ein Whitespace< 
	 * nach dem Zeichen enthalten sein kann.</p>
	 * 
	 * <p>Fuer alle so angegebenen Kriterien sollen JPA-Like Klauseln mit OR verknuepft werden, 
	 * welche jeweils sowohl auf dem firstName als auch auf den lastName des Kunden angewendet werden kann.</p>
	 * 
	 * <p>Beispiel:
	 * <br/>
	 * enthaelt das Kriterium den String: 
	 * 
	 * 	<p><b><i>Max;Mustermann;John;Doe</i></b></p> 
	 * 
	 * soll folgende JPA-Query auf die Datenbank geschickt werden: </p> 
	 * <code>
	 * 	SELECT e FROM Customer e where  																<br/>
	 *  e.firstName LIKE 'Max' OR e.lastName LIKE 'Max' OR												<br/>
	 *  e.firstName LIKE 'Mustermann' OR e.lastName LIKE 'Mustermann' OR								<br/>
	 *  e.firstName LIKE 'John' OR e.lastName LIKE 'John' OR											<br/>
	 *  e.firstName LIKE 'Doe' OR e.lastName LIKE 'Doe'													<br/>
	 * </code>
	 * 
	 * @param searchString Die Kriterien 
	 * @return Eine Liste von gefundenen Kunden, die den Kriterien entsprechen:
	 * 
	 * <code>
	 * 	Response.ok(customers).build()
	 * </code>
	 * 
	 */
	@GET
	Response findCompanies(@QueryParam("searchString") String searchString);
	
	/**
	 * Diese Methode aktualisiert einen Kunden (U from CRUD). 
	 * Es soll bei einem erfolgreichem Loeschvorgang der HTTP Statuscode 200 zurueckgegeben werden:
	 * <p><code>
	 * Response.status(Status.ACCEPTED).build()
	 * </code></p>
	 * 
	 * @param customer Der zu aktualisierende Kunde als Java-Objekt
	 * @return HTTP Code 202
	 * @throws URISyntaxException 
	 */
	@PUT
	Response updateCompany(Company company) throws URISyntaxException;

	/**
	 * Diese Methode loescht einen Kunden (D from CRUD).
	 * Es soll bei einem erfolgreichem Loeschvorgang der HTTP Statuscode 200 zurueckgegeben werden:
	 * <p><code>
	 * Response.ok().build()
	 * </code></p>
	 * 
	 * @param customerId Die Id des Kunden, der geloescht werden soll
	 * @return HTTP Code 200
	 */
	@DELETE
	@Path("/" + COMPANY_ID)
	Response deleteCompany(@PathParam(COMPANY_ID_PLAIN) Long companyId);
}
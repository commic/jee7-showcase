package com.mycompany.boundary;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mycompany.control.CompanyService;
import com.mycompany.control.ValidationException;
import com.mycompany.entity.Company;

public class CompanyResource implements ICompanyResource {
	
	@EJB
	private CompanyService companyService;

	@Override
	public Response findCompanyById(Long companyId) {
		Company company = companyService.findCompanyById(companyId);
		return Response.ok().entity(company).build();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.boundary.ICompanyResource#saveCompany(javax.ws.rs.core
	 * .UriInfo, com.mycompany.entity.Company)
	 */
	@Override
	public Response saveCompany(Company company) throws URISyntaxException{
		try {
			if(company.getId() != null) {
				companyService.updateCompany(company);
			}else{
				companyService.saveCompany(company);
			}
		}catch(ValidationException e){
			e.printViolations();
		}
		return Response.created(URI.create("/company/" + company.getId())).build();
	}
	
	@Override
	public Response updateCompany(Company company) {
		try{
		companyService.updateCompany(company);
		}catch(ValidationException e) {
			
		}
		return Response.status(Status.ACCEPTED).build();
	}
	
	@Override
	public Response deleteCompany(Long companyId) {
		companyService.deleteCompany(companyId);
		return Response.ok().build();
	}
	
	@Override
	public Response findCompanies(String searchString) {
		List<Company> companies;
		if (searchString != null) {
			companies = companyService.findCompanies(searchString);
		} else {
			companies = companyService.listAllCompanies();
		}
		return Response.ok(companies).build();
	}
}

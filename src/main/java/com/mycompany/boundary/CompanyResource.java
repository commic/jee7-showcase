package com.mycompany.boundary;

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
	public Response findCompanyById(String companyId) {
		Company company = companyService.findCompanyById(Long.parseLong(companyId));
		return Response.ok().entity(company).build();
	}

	@Override
	public Response updateCompany(Company company) throws URISyntaxException {
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

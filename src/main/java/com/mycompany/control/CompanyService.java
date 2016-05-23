package com.mycompany.control;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.mycompany.entity.Company;
import com.mycompany.entity.QCompany;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

@Stateless
@Local
public class CompanyService {

	@PersistenceContext
	private EntityManager entityManager;
	
	// Get configured validator directly from JBoss AS 7 environment
    @Inject
    private Validator validator;
	
	public void saveCompany(Company company) throws ValidationException {
		Set<ConstraintViolation<Company>> violations = validator.validate(company);
		if(violations.size() == 0) {
			entityManager.persist(company);	
		}else{
			throw new ValidationException(extractViolationMessages(violations));			
		}		
	}
	
	private <T> String[] extractViolationMessages(Set<ConstraintViolation<T>> violations) {
		String[] messages = new String[violations.size()];
		int i = 0;
		for(ConstraintViolation<T> c: violations) {
			messages[i] = c.getPropertyPath() + " " + c.getMessage();
			i++;
		}
		return messages;
	}

	public List<Company> listAllCompanies() {
		TypedQuery<Company> query = entityManager.createQuery(
				"SELECT e FROM Company e", Company.class);
		return query.getResultList();
	}

	public Company findCompanyById(Long id) {
		return entityManager.find(Company.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCompany(Company company)  throws ValidationException {
		Set<ConstraintViolation<Company>> violations = validator.validate(company);
		if(violations.size() == 0) {
			entityManager.merge(company);	
		}else{
			throw new ValidationException(extractViolationMessages(violations));			
		}
	}

	public void deleteCompany(Long id) {
		Company company = findCompanyById(id);
		if(company != null) {
			entityManager.remove(company);	
		}
	}
	
	public List<Company> findCompanies(String searchString) {
		String[] searchTerms = ServiceUtils.splitSearchString(searchString);
		QCompany qCompany = QCompany.company;
		JPAQuery query = new JPAQuery(entityManager);
		BooleanBuilder builder = new BooleanBuilder();
		for (String term : searchTerms) {
			builder.or(qCompany.name.contains(term));
		}
		return query.from(qCompany).where(builder).list(qCompany);
	}
}

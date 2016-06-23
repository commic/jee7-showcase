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
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.mycompany.entity.Customer;

@Stateless
@Local
public class CustomerService {

	@PersistenceContext
	private EntityManager entityManager;
	
	// Get configured validator directly from JBoss AS 7 environment
    @Inject
    private Validator validator;
	
    @Transactional
	public void saveCustomer(Customer customer) throws ValidationException {
		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		if(violations.size() == 0) {
			entityManager.persist(customer);	
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

	public List<Customer> findAllCustomers() {
		TypedQuery<Customer> query = entityManager.createQuery(
				"SELECT e FROM Customer e", Customer.class);
		return query.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<Customer> findCustomers() {
		TypedQuery<Customer> query = entityManager.createQuery(
				"SELECT e FROM Customer e", Customer.class);
		return (List<Customer>) query.getResultList();
	}

	public Customer findCustomerById(Long id) {
		return entityManager.find(Customer.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCustomer(Customer customer)  throws ValidationException {
		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		if(violations.size() == 0) {
			entityManager.merge(customer);	
		}else{
			throw new ValidationException(extractViolationMessages(violations));			
		}
	}

	/**
	 * Standard-JPA-Query
	 * 
	 * @param searchString: John - John Smith
	 * @return
	 */
	public List<Customer> findCustomers(String searchString) {
		String queryString = "SELECT e FROM Customer e where";
		int i = 0;
		if(searchString == null || searchString.isEmpty()) {
			queryString = "SELECT e FROM Customer e";
		}else{
			String[] searchParams = ServiceUtils.splitSearchString(searchString);
			for(String s: searchParams) {
				if(searchParams.length-1 == i) {
					queryString += " e.firstName ='" + s + "'";
				}else{
					queryString += " e.firstName ='" + s + "' or";
				}
				i++;
			}
		}		
		TypedQuery<Customer> query = entityManager.createQuery(queryString,
				Customer.class);
		return query.getResultList();
	}
	
	/*
	public List<Customer> findCustomers(String searchString) {
		String[] searchTerms = ServiceUtils.splitSearchString(searchString);
		QCustomer qCustomer = QCustomer.customer;
		JPAQuery query = new JPAQuery(entityManager);
		BooleanBuilder builder = new BooleanBuilder();
		for (String term : searchTerms) {
			builder.or(qCustomer.firstName.contains(term));
			builder.or(qCustomer.company.name.contains(term));
			builder.or(qCustomer.lastName.contains(term));
		}
		return query.from(qCustomer).where(builder).list(qCustomer);
	}
	*/

	public void deleteCustomer(Long id) {
		Customer customer = findCustomerById(id);
		if(customer != null) {
			entityManager.remove(customer);	
		}
	}
}

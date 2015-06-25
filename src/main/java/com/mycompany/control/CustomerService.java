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

import com.mycompany.entity.Customer;

@Stateless
@Local
public class CustomerService {

	@PersistenceContext
	private EntityManager entityManager;
	
	// Get configured validator directly from JBoss AS 7 environment
    @Inject
    private Validator validator;
	
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
		// TODO ...
	}

	/**
	 * Standard-JPA-Query
	 * 
	 * @param searchString
	 * @return
	 */
	public List<Customer> findCustomers(String searchString) {
		String queryString = "SELECT e FROM Customer e where";
		// TODO ...
		TypedQuery<Customer> query = entityManager.createQuery(queryString,
				Customer.class);
		return query.getResultList();
	}

	public void deleteCustomer(Long id) {
		// TODO ...
	}
}

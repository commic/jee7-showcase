package com.mycompany.control;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mycompany.entity.Customer;
import com.mycompany.entity.Product;
import com.mycompany.entity.QCustomer;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

@Stateless
@Local
public class CustomerService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource(mappedName="java:jboss/mail/Default")
	private Session mailSession;
	
	// TODO: catch JPA validation exceptions and expose ValidationException 
	public void saveCustomer(Customer customer) throws ValidationException {
		entityManager.persist(customer);
	}

	public List<Customer> findQCustomers(String searchString) {
		String[] searchTerms = splitSearchString(searchString);
		QCustomer qCustomer = QCustomer.customer;
		JPAQuery query = new JPAQuery(entityManager);
		BooleanBuilder builder = new BooleanBuilder();
		for (String term : searchTerms) {
			builder.or(qCustomer.firstName.eq(term));
			builder.or(qCustomer.lastName.eq(term));
		}
		return query.from(qCustomer).where(builder).list(qCustomer);
	}

	public List<Customer> findAllCustomers() {
		TypedQuery<Customer> query = entityManager.createQuery(
				"SELECT e FROM Customer e", Customer.class);
		return query.getResultList();
	}

	public List<Customer> findAllQCustomers() {
		QCustomer qCustomer = QCustomer.customer;
		JPAQuery query = new JPAQuery(entityManager);
		return query.from(qCustomer).list(qCustomer);
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

	// TODO: handle JPA ValidationExceptions
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCustomer(Customer customer)  throws ValidationException {
		entityManager.merge(customer);
	}

	private String[] splitSearchString(String searchString) {
		String[] searchTerms = new String[] { searchString };
		if (searchString.contains(", ")) {
			searchTerms = searchString.split(", ");
		} else if (searchString.contains("; ")) {
			searchTerms = searchString.split("; ");
		} else if (searchString.contains(",")) {
			searchTerms = searchString.split(",");
		} else if (searchString.contains(";")) {
			searchTerms = searchString.split(";");
		} else if (searchString.contains(" ")) {
			searchTerms = searchString.split(" ");
		}
		return searchTerms;
	}

	/**
	 * Diese Art der Query ist schwierig zu warten gefährlich: SQL-Injection:
	 * Beispiel: Max' or where e.lastName like '%
	 * 
	 * @param searchString
	 * @return
	 */
	public List<Customer> findCustomers(String searchString) {
		String queryString = "SELECT e FROM Customer e where ";

		queryString += "e.firstName ='" + searchString + "'";

		TypedQuery<Customer> query = entityManager.createQuery(queryString,
				Customer.class);

		return query.getResultList();
	}

	public void deleteCustomer(Long id) {
		Customer customer = findCustomerById(id);
		if (customer != null) {
			entityManager.remove(customer);
		}
	}

	public void sendProductNewsLetterToCustomer(Long customerId, List<? extends Product> products) throws MessagingException {
		Customer targetCustomer = findCustomerById(customerId);
		MimeMessage message = new MimeMessage(mailSession);
		Address from = new InternetAddress("jee7@dev.commic.eu");
		Address[] to = new InternetAddress[] {new InternetAddress(targetCustomer.getEmail()) };
		message.setFrom(from);
		message.setRecipients(Message.RecipientType.TO, to);
		message.setSubject("Newsletter of our current products!");
		message.setSentDate(new java.util.Date());
		StringBuilder contentBuilder = new StringBuilder();
		contentBuilder.append("Mail sent from JBoss AS 7");
		for(Product pro : products){
			contentBuilder.append(pro.toString());
		}
		message.setContent(contentBuilder.toString(),"text/plain");
		Transport.send(message);
		
	}

}

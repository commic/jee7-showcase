package com.mycompany.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

/**
 * Company entity, representing a company object that can be persisted to a
 * relational database and, at the same time, be used as a transfer object for
 * company entities that are used in the user interface.
 * 
 * 
 */
@Entity
public class Company {

	private static final String COMPANY_GEN = "Company_Gen";

	@TableGenerator(name = COMPANY_GEN, initialValue = 1, allocationSize = 100)
	@Id
	@GeneratedValue(generator = COMPANY_GEN)
	private Long id;
	@NotNull
	private String name;
	
	@OneToMany(cascade={CascadeType.REMOVE})
	private List<Customer> customers;

	/**
	 * Default constructor for JAX-RS (object <> JSON serialization)
	 */
	public Company() {
	}

	public Company(String name) {
		this.name = name;
	}

	// -------- getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

}

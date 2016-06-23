package com.mycompany.entity;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.mycompany.entity.constraint.Email;

/**
 * Customer entity, representing a customer object that can be persisted to a
 * relational database and, at the same time, be used as a transfer object for
 * customer entities that are used in the user interface.
 * 
 */
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@NotNull(message = "Der Vorname darf nicht leer sein")
	@Size(min = 3, message = "Bitte mindestens 3 Buchstaben eingeben")
	@Pattern(regexp = "^[A-Z][a-z]+(?:[\\s-][A-Z][a-z]+)*", message = "Bitte einen gültigen Vornamen eingeben")
	private String firstName;
	@NotNull(message = "Der Nachname darf nicht leer sein")
	@Size(min = 2, message = "Bitte mindestens 2 Buchstaben eingeben")
	@Pattern(regexp = "^[A-Z][a-z]+(?:[\\s-][A-Z][a-z]+)*", message = "Bitte einen gültigen Nachnamen eingeben")
	private String surname;
	@NotNull(message = "Die E-Mail-Adresse darf nicht leer sein")
	@Email(message = "Bitte geben Sie eine gültige E-Mail-Adresse ein")
	private String email;
	//@Phone(message = "Bitte geben Sie eine gültige Telefonnummer ein")
	private String phone;
	//@Phone(message = "Bitte geben Sie eine gültige Faxnummer ein")
	private String fax;
	@Enumerated(EnumType.ORDINAL)
	private Sex sex;
	private String country;
	private Locale locale;
	private Date createDate;
	
	@ManyToOne(optional=false)
	@NotNull(message = "Company darf nicht leer sein")
	private Company company;

	/**
	 * Default constructor for JAX-RS (object <> JSON serialization)
	 */
	public Customer() {}

	public Customer(String firstName, String lastName, String email,
			String phone, String fax, Sex sex, String country, Locale locale,
			Date createDate, Company company) {
		this.firstName = firstName;
		this.surname = lastName;
		this.email = email;
		this.phone = phone;
		this.fax = fax;
		this.sex = sex;
		this.country = country;
		this.locale = locale;
		this.createDate = createDate;
		this.company = company;
	}

	// -------- getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String lastName) {
		this.surname = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}

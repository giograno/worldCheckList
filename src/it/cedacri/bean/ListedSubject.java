package it.cedacri.bean;

import java.util.Date;
import java.util.List;

/**
 * A class that represents a subject who is present in a list
 * 
 * @author CR01046
 *
 */
public class ListedSubject {

	private String category;
	private String subCategory;
	private String sex;
	private String firstName;
	private String lastName;
	private String heading;
	private List<String> alias;
	private List<String> alternativeSpelling;
	private Date dateOfBirth;
	private String fiscalCode;
	private List<String> passports;
	private List<String> placeOfBirth;
	private List<String> countries;

	public String getCategory() {
		return category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public String getSex() {
		return sex;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public List<String> getAlias() {
		return alias;
	}

	public List<String> getAlternativeSpelling() {
		return alternativeSpelling;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public List<String> getPassports() {
		return passports;
	}

	public List<String> getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public void setAlias(List<String> alias) {
		this.alias = alias;
	}

	public void setAlternativeSpelling(List<String> alternativeSpelling) {
		this.alternativeSpelling = alternativeSpelling;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setPassports(List<String> passports) {
		this.passports = passports;
	}

	public void setPlaceOfBirth(List<String> placeOfBith) {
		this.placeOfBirth = placeOfBith;
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	public List<String> getCountries() {
		return countries;
	}

	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

}

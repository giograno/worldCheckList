package it.cedacri.bean;

import java.util.Date;

public class LookingSubject {

	private final String heading;
	private final String fiscalCode;
	private final String sex;
	private final String country;
	private final Date dateOfBirth;

	public String getHeading() {
		return heading;
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public String getSex() {
		return sex;
	}

	public String getCountry() {
		return country;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public boolean isOnlyHeading() {
		if (fiscalCode == null && sex == null && country == null && dateOfBirth == null)
			return true;
		else
			return false;
	}

	public static class SubjectBuilder {

		private final String heading;
		private String fiscalCode = null;
		private String sex = null;
		private String country = null;
		private Date dateOfBirth = null;

		/**
		 * Constructor of a subject
		 * 
		 * @param heading
		 */
		public SubjectBuilder(String heading) {
			this.heading = heading;
		}

		public SubjectBuilder fiscalCode(String fiscal) {
			this.fiscalCode = fiscal;
			return this;
		}

		public SubjectBuilder sex(String sex) {
			this.sex = sex;
			return this;
		}

		public SubjectBuilder country(String country) {
			this.country = country;
			return this;
		}

		public SubjectBuilder date(Date date) {
			this.dateOfBirth = date;
			return this;
		}

		public LookingSubject build() {
			return new LookingSubject(this);
		}

	}

	private LookingSubject(SubjectBuilder builder) {

		this.heading = builder.heading;
		this.fiscalCode = builder.fiscalCode;
		this.sex = builder.sex;
		this.country = builder.country;
		this.dateOfBirth = builder.dateOfBirth;
	}

}

package it.cedacri.parsing;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.cedacri.bean.ListedSubject;

/**
 * Parse a record of a world check list, filling a list of
 * <code>ListedSubject</code>
 * 
 * @author cr01046
 *
 */
public class XmlHandler extends DefaultHandler {

	// list to hold ListSubject elements
	private List<ListedSubject> subjectsList = null;
	private ListedSubject subject = null;

	private List<String> aliasList = null;
	private List<String> spellingList = null;
	private List<String> passportList = null;
	private List<String> birthPlacesList = null;
	private List<String> countries = null;
	private StringBuilder fiscalCode = new StringBuilder();

	private boolean bFirstName = false;
	private boolean bLastName = false;
	private boolean bAlias = false;
	private boolean bSpelling = false;
	private boolean bDate = false;
	private boolean bPassport = false;
	private boolean bBirth = false;
	private boolean bInfo = false;
	private boolean bCountries = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {

		if (qName.equalsIgnoreCase("record")) {
			// create a new subject in a list
			subject = new ListedSubject();

			String category = attributes.getValue("category");
			subject.setCategory(category);

			// initialize the list of subjects
			if (subjectsList == null)
				subjectsList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("person")) {
			String sex = attributes.getValue("e-i");
			if (sex != null && (sex.equals("M") || (sex.equals("F"))))
				subject.setSex(sex);
		} else if (qName.equalsIgnoreCase("first_name")) {
			bFirstName = true;
		} else if (qName.equalsIgnoreCase("last_name")) {
			bLastName = true;
		} else if (qName.equalsIgnoreCase("aliases")) {
			// initialize the list of aliases
			if (aliasList == null)
				aliasList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("alias")) {
			String header = (subject.getFirstName() + " " + subject.getLastName()).trim();
			subject.setHeading(header);
			bAlias = true;
		} else if (qName.equalsIgnoreCase("alternative_spelling")) {

			String attribute = attributes.getValue("xsi:nil");
			if (attribute == null)
				bSpelling = true;
		} else if (qName.equalsIgnoreCase("place_of_birth")) {

			String attribute = attributes.getValue("xsi:nil");
			if (attribute == null)
				bBirth = true;

		} else if (qName.equalsIgnoreCase("dob")) {

			String attribute = attributes.getValue("xsi:nil");
			if (attribute == null)
				bDate = true;

		} else if (qName.equalsIgnoreCase("passport")) {

			String attribute = attributes.getValue("xsi:nil");
			if (attribute == null)
				bPassport = true;

			if (bPassport && passportList == null)
				passportList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("further_information")) {
			bInfo = true;
			fiscalCode.setLength(0);
		} else if (qName.equalsIgnoreCase("country")) {

			String attribute = attributes.getValue("xsi:nil");
			if (attribute == null)
				bCountries = true;

			if (bCountries && countries == null)
				countries = new ArrayList<>();
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("record")) {
			// add the subject to the list of subject
			subjectsList.add(subject);
			// re-initialize all arrays and boolean values
			endSingleRecord();
		} else if (qName.equalsIgnoreCase("aliases")) {
			if (!aliasList.isEmpty())
				subject.setAlias(aliasList);
		} else if (qName.equalsIgnoreCase("passports")) {
			subject.setPassports(passportList);
		} else if (qName.equalsIgnoreCase("place_of_birth")) {
			subject.setPlaceOfBirth(birthPlacesList);
		} else if (qName.equalsIgnoreCase("alternative_spelling")) {
			subject.setAlternativeSpelling(spellingList);
		} else if (qName.equalsIgnoreCase("countries")) {
			subject.setCountries(countries);
		} else if (qName.equalsIgnoreCase("further_information")) {
			String s = fiscalCode.toString();
			String regex = "(?<=Fiscal Code: )([^.]+)";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(s);
			if (matcher.find())
				subject.setFiscalCode(matcher.group(1));

			bInfo = false;
		}
	}

	private void endSingleRecord() {
		aliasList = null;
		spellingList = null;
		passportList = null;
		birthPlacesList = null;
		countries = null;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String s = new String(ch, start, length).trim();

		if (bFirstName) {
			if (s.length() != 0) {
				subject.setFirstName(s);
			}
			bFirstName = false;
		} else if (bLastName) {
			subject.setLastName(new String(ch, start, length));
			bLastName = false;
		} else if (bAlias) {
			if (s.length() != 0) {
				aliasList.add(s);
			}
			bAlias = false;
		} else if (bSpelling) {
			if (spellingList == null) {
				spellingList = Arrays.asList(s.split(";"));
			}
			bSpelling = false;
		} else if (bDate) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;

			try {
				date = dateFormat.parse(s);
			} catch (ParseException e) {
				System.err.println("Invalid date to parse");
			}

			subject.setDateOfBirth(date);
			bDate = false;
		} else if (bPassport) {
			passportList.add(s);
			bPassport = false;
		} else if (bBirth) {
			if (birthPlacesList == null) {
				birthPlacesList = Arrays.asList(s.split(";"));
			}
			bBirth = false;
		} else if (bInfo) {
			fiscalCode.append(ch, start, length);
		} else if (bCountries) {
			countries.add(s);
			bCountries = false;
		}
	}

	/**
	 * Returns the list of all subjects in world check list
	 * 
	 * @return
	 */
	public List<ListedSubject> getSubjects() {
		return subjectsList;
	}

}

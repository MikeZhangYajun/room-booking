package cst8284.asgmt2.roomScheduler;
/* Course Name: CST8284 Object Oriented Programming (Java)
   Student Name: Yajun ZHANG 040958689
   Class name: ContactInfo
   Date: 2020-01-28
*/

import java.io.Serializable;

public class ContactInfo implements Serializable{
	public static final long serialVersionUID = 1L;

	private String firstName, lastName, phoneNumber, organization;
	
	public ContactInfo(String firstName, String lastName, String phoneNumber) {
		this(firstName, lastName, phoneNumber, "Algonquin College");
	}

	public ContactInfo(String firstName, String lastName, String phoneNumber, String organization) {
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setOrganization(organization);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrganization() {
		return organization;
	}

	@Override
	public String toString() {
		return "Contact Information: " + getFirstName() + " " + getLastName() + "\n" + "Phone: " + getPhoneNumber()
				+ "\n" + ((getOrganization().equals("")) ? "" : (getOrganization() + "\n"));
	}

}

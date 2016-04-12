package edu.byu.robpneu.cs240.familymap.model;

import edu.byu.robpneu.cs240.familymap.ui.SearchActivity;

/**
 * @author Robert P Neu
 * @version 1.0 3/17/16
 */
public class Person implements SearchActivity.Item{
	private String mPersonID;
	private String mFirstName;
	private String mLastName;
	private String mGender;
	private String mFatherID;
	private String mMotherID;
	private String mSpouseID;
	private String mFamilySide;

	/**
	 * Constructor for a person object
	 *
	 * @param personID  the personID
	 * @param firstName the person's first name
	 * @param lastName  the person's last name
	 * @param gender    the person's gender
	 * @param fatherID  the personID of their father
	 * @param motherID  the personID of their mother
	 * @param spouseID  the personID of their spouse
	 */
	public Person(String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
		mPersonID = personID;
		mFirstName = firstName;
		mLastName = lastName;
		mGender = gender;
		mFatherID = fatherID;
		mMotherID = motherID;
		mSpouseID = spouseID;
	}

	/**
	 * Get the person's personID
	 * @return string of the personID
	 */
	public String getPersonID() {
		return mPersonID;
	}

	/**
	 * Get the person's first name
	 * @return string of the person's first name
	 */
	public String getFirstName() {
		return mFirstName;
	}

	/**
	 * Get the person's last name
	 * @return string of the person's last name
	 */
	public String getLastName() {
		return mLastName;
	}

	/**
	 * Get the person's gender
	 * @return string of the persons gender
	 */
	public String getGender() {
		return mGender;
	}

	/**
	 * Get the personID of the person's father
	 * @return string of the personID
	 */
	public String getFatherID() {
		return mFatherID;
	}

	/**
	 * Get the personID of the person's mother
	 * @return string of the personID
	 */
	public String getMotherID() {
		return mMotherID;
	}

	/**
	 * Get the full name of the person.
	 * Just the first and last name formatted
	 * @return formatted string of the full name
	 */
	public String getFullName(){
		return mFirstName + " " + mLastName;
	}

	/**
	 * Get the personID of the person's spouse
	 * @return string of the personID
	 */
	public String getSpouseID() {
		return mSpouseID;
	}

	/**
	 * Get which side of the family the person falls on
	 * They will be on the father's, mother's, or neither side (such as the root person and their spouse.
	 * @return the string of the side of the family
	 */
	public String getFamilySide() {
		return mFamilySide;
	}

	/**
	 * Sets the side of the family
	 * @param familySide the string of which side of the family they're on
	 */
	public void setFamilySide(String familySide) {
		mFamilySide = familySide;
	}

	/**
	 * Searches to see if the person contains a given string
	 * Searches the first and last name
	 * Case does not matter
	 * @param search the string to be searched
	 * @return boolean if the person object contained the string
	 */
	@Override
	public boolean contains(String search) {
		if (mFirstName.toLowerCase().contains(search.toLowerCase()))
			return true;
		return mLastName.toLowerCase().contains(search.toLowerCase());
	}
}

package edu.byu.robpneu.cs240.familymap.model;

import edu.byu.robpneu.cs240.familymap.ui.SearchActivity;

/**
 * @author Robert P Neu
 * @version 1.0 3/17/16
 */
public class Person implements SearchActivity.Item{
	private String mDescendant;
	private String mPersonID;
	private String mFirstName;
	private String mLastName;
	private String mGender;
	private String mFatherID;
	private String mMotherID;
	private String mSpouseID;
	private String mFamilySide;


	public Person() {}

	public Person(String descendant, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
		mDescendant = descendant;
		mPersonID = personID;
		mFirstName = firstName;
		mLastName = lastName;
		mGender = gender;
		mFatherID = fatherID;
		mMotherID = motherID;
		mSpouseID = spouseID;
//		mFamilySide = "none";
	}

	public String getDescendant() {
		return mDescendant;
	}

	public void setDescendant(String descendant) {
		mDescendant = descendant;
	}

	public String getPersonID() {
		return mPersonID;
	}

	public void setPersonID(String personID) {
		this.mPersonID = personID;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public void setFirstName(String firstName) {
		mFirstName = firstName;
	}

	public String getLastName() {
		return mLastName;
	}

	public void setLastName(String lastName) {
		mLastName = lastName;
	}

	public String getGender() {
		return mGender;
	}

	public void setGender(String gender) {
		mGender = gender;
	}

	public String getFatherID() {
		return mFatherID;
	}

	public void setFatherID(String fatherID) {
		mFatherID = fatherID;
	}

	public String getMotherID() {
		return mMotherID;
	}

	public void setMotherID(String motherID) {
		mMotherID = motherID;
	}

	public String getFullName(){
		return mFirstName + " " + mLastName;
	}

	public String getSpouseID() {
		return mSpouseID;
	}

	public void setSpouseID(String spouseID) {
		mSpouseID = spouseID;
	}

	public void setFamilySide(String familySide){
		mFamilySide = familySide;
	}

	public String getFamilySide() {
		return mFamilySide;
	}

	@Override
	public boolean contains(String search) {
		if (mFirstName.contains(search))
			return true;
		if(mLastName.contains(search))
			return true;
		return false;
	}
}

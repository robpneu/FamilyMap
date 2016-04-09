package edu.byu.robpneu.cs240.familymap.model;

import edu.byu.robpneu.cs240.familymap.ui.SearchActivity;

/**
 * @author Robert P Neu
 * @version 1.0 3/26/16
 */
public class Event implements SearchActivity.Item{
	private String mEventID;
	private String mPersonID;
	private double mLatitude;
	private double mLongitude;
	private String mCountry;
	private String mCity;
	private String mDescription;
	private Double mYear;
	private String mDescendant;

	public Event(String eventID, String personID, double latitude, double longitude, String country, String city, String description, Double year, String descendant) {
		mEventID = eventID;
		mPersonID = personID;
		mLatitude = latitude;
		mLongitude = longitude;
		mCountry = country;
		mCity = city;
		mDescription = description;
		mYear = year;
		mDescendant = descendant;
	}

	public String getEventID() {
		return mEventID;
	}

	public void setEventID(String eventID) {
		mEventID = eventID;
	}

	public String getPersonID() {
		return mPersonID;
	}

	public void setPersonID(String personID) {
		mPersonID = personID;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(double latitude) {
		mLatitude = latitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(double longitude) {
		mLongitude = longitude;
	}

	public String getCountry() {
		return mCountry;
	}

	public void setCountry(String country) {
		mCountry = country;
	}

	public String getCity() {
		return mCity;
	}

	public void setCity(String city) {
		mCity = city;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public Double getYear() {
		return mYear;
	}

	public void setYear(Double year) {
		mYear = year;
	}

	public String getDescendant() {
		return mDescendant;
	}

	public void setDescendant(String descendant) {
		mDescendant = descendant;
	}

	public String toString(){
		return mDescription.toLowerCase() + ": " + mCity + ", " + mCountry + " (" + mYear.intValue() + ")";
	}

	@Override
	public boolean contains(String search) {
		if(mCountry.contains(search))
			return true;
		if(mCity.contains(search))
			return true;
		if(mYear.toString().contains(search))
			return true;
		if(mDescription.contains(search))
			return true;
		return false;
	}
}

package edu.byu.robpneu.cs240.familymap.model;

import com.amazon.geo.mapsv2.model.LatLng;

import edu.byu.robpneu.cs240.familymap.ui.SearchActivity;

/**
 * @author Robert P Neu
 * @version 1.0 3/26/16
 */
public class Event implements SearchActivity.Item, Comparable<Event> {
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

	public LatLng getLatLng() {
		return new LatLng(mLatitude, mLongitude);
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

	public String getDescendant() {
		return mDescendant;
	}

	public void setDescendant(String descendant) {
		mDescendant = descendant;
	}

	public String toString() {
		return mDescription.toLowerCase() + ": " + mCity + ", " + mCountry + " (" + mYear.intValue() + ")";
	}

	@Override
	public boolean contains(String search) {
		if (mCountry.toLowerCase().contains(search.toLowerCase()))
			return true;
		if (mCity.toLowerCase().contains(search.toLowerCase()))
			return true;
		if (mYear.toString().toLowerCase().contains(search.toLowerCase()))
			return true;
		return mDescription.toLowerCase().contains(search.toLowerCase());
	}

	/**
	 * Compares this object to the specified object to determine their relative
	 * order.
	 *
	 * @param another the object to compare to this instance.
	 * @return a negative integer if this instance is less than {@code another};
	 * a positive integer if this instance is greater than
	 * {@code another}; 0 if this instance has the same order as
	 * {@code another}.
	 * @throws ClassCastException if {@code another} cannot be converted into something
	 *                            comparable to {@code this} instance.
	 */
	@Override
	public int compareTo(Event another) {
		if (mDescription.equals("birth"))
			return -1;
		else if (mDescription.equals("death"))
			return 1;
		else {
			if (mYear != null & another.getYear() != null) {
				if (mYear > another.getYear())
					return 1;
				else if (mYear < another.getYear())
					return -1;
				else
					return mDescription.compareTo(another.getDescription());
			} else if (mYear == null)
				return -1;
			else if (another.getYear() == null)
				return 1;
			else
				return mDescription.compareTo(another.getDescription());
		}
	}

	public Double getYear() {
		return mYear;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public void setYear(Double year) {
		mYear = year;
	}
}

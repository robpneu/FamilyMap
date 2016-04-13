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

	/**
	 * Default constructor for an Event object
	 *
	 * @param eventID     the eventID of the object. should be a unique identifier
	 * @param personID    the personID of the Person that this events references
	 * @param latitude    the latitude that the event took place
	 * @param longitude   the longitude that the event took place
	 * @param country     the country that the event took place
	 * @param city        the city that the event took place
	 * @param description the description of the event
	 * @param year        the year that the event took place
	 * @param descendant  the descendant of the person that the event references.
	 */
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

	/**
	 * Gets an easier to work with LatLang object.
	 * The LatLng object includes the latitude and longitude of the event in a single object
	 *
	 * @return The LatLng object
	 */
	public LatLng getLatLng() {
		return new LatLng(mLatitude, mLongitude);
	}

	/**
	 * Checks if an event contains a certain boolean string
	 * If the coutnry, city, or description contain the string, it returns yes
	 * @param search the string to be searched for
	 * @return if the event contains the string in it.
	 */
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

	/**
	 * Compares this instance with the specified object and indicates if they
	 * are equal. In order to be equal, {@code o} must represent the same object
	 * as this instance using a class-specific comparison. The general contract
	 * is that this comparison should be reflexive, symmetric, and transitive.
	 * Also, no object reference other than null is equal to null.
	 * <p>
	 * <p>The default implementation returns {@code true} only if {@code this ==
	 * o}. See <a href="{@docRoot}reference/java/lang/Object.html#writing_equals">Writing a correct
	 * {@code equals} method</a>
	 * if you intend implementing your own {@code equals} method.
	 * <p>
	 * <p>The general contract for the {@code equals} and {@link
	 * #hashCode()} methods is that if {@code equals} returns {@code true} for
	 * any two objects, then {@code hashCode()} must return the same value for
	 * these objects. This means that subclasses of {@code Object} usually
	 * override either both methods or neither of them.
	 *
	 * @param o the object to compare this instance with.
	 * @return {@code true} if the specified object is equal to this {@code
	 * Object}; {@code false} otherwise.
	 * @see #hashCode
	 */
	@Override
	public boolean equals(Object o) {
		if (!o.getClass().equals(this.getClass()))
			return false;

		Event event = (Event) o;
		if (!mPersonID.equals(event.getPersonID()))
			return false;
		if (!mCity.equals(event.getCity()))
			return false;
		if (!mDescription.equals(event.getDescription()))
			return false;
		if (!mYear.equals(event.getYear()))
			return false;
		if (!mCountry.equals(event.getCountry()))
			return false;
		if (!Double.valueOf(mLatitude).equals(Double.valueOf(event.getLatitude())))
			return false;
		if (!Double.valueOf(mLongitude).equals(Double.valueOf(event.getLongitude())))
			return false;
		return mEventID.equals(event.getEventID());
	}

	public String getPersonID() {
		return mPersonID;
	}

	public String getCity() {
		return mCity;
	}

	public String getCountry() {
		return mCountry;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public String getEventID() {
		return mEventID;
	}

	/**
	 * Puts together a useable string for display the basic infomraiton about an event.
	 * Includes the city country, and year.
	 * the format is: City, Country (Year)
	 * @return
	 */
	public String toString() {
		return mDescription.toLowerCase() + ": " + mCity + ", " + mCountry + " (" + mYear.intValue() + ")";
	}
}

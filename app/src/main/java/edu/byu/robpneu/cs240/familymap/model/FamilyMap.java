package edu.byu.robpneu.cs240.familymap.model;

import com.joanzapata.android.iconify.IconDrawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.robpneu.cs240.familymap.dao.Login;
import edu.byu.robpneu.cs240.familymap.ui.SearchActivity;

/**
 * @author Robert P Neu
 * @version 1.0 3/26/16
 */
public class FamilyMap {
	private static FamilyMap instance;
	private boolean mLoggedIn;
	private String mRootID;
	private Map<String, Person> mPersonMap;
	private Map<String, Event> mEventMap;
	private Map<String, Filter> mCustomFilters;
	private List<Filter> mConstantFilters;
	private Event mCurrentEvent;

	private IconDrawable maleIcon;
	private IconDrawable femaleIcon;
	private IconDrawable androidIcon;
	private IconDrawable eventIcon;
	private IconDrawable searchIcon;
	private IconDrawable filterIcon;
	private IconDrawable gearIcon;
	private IconDrawable doubleUpIcon;

	/**
	 * Sets up a new familymap object.
	 */
	private FamilyMap(){
		mLoggedIn = false;
		mPersonMap = new HashMap<>();
		mEventMap = new HashMap<>();
		mConstantFilters = new ArrayList<>(4);
		mCustomFilters = new HashMap<>();


		// Add in the constant filters
		Filter filter = new Filter("Mother's Side", "mother");
		mConstantFilters.add(filter);

		filter = new Filter("Father's Side", "father");
		mConstantFilters.add(filter);

		filter = new Filter("Female", "f");
		mConstantFilters.add(filter);

		filter = new Filter("Male", "m");
		mConstantFilters.add(filter);
	}

	/**
	 * Gets the singleton instance
	 * @return the instance of the FamilyMap object
	 */
	public static FamilyMap getInstance(){
		if (instance == null){
			instance = new FamilyMap();
		}
		return instance;
	}

	/**
	 * Add a person to the member person map individually, by its personID and the person object
	 * @param person the person to be added.
	 */
	public void addPerson(Person person){
		mPersonMap.put(person.getPersonID(), person);
	}

	/**
	 * Add a  map of people to the member person map all at once.
	 * @param people personID and person key value/object pair.
	 */
	public void addPeople(Map<String, Person> people){
		mPersonMap.putAll(people);
		mRootID = Login.getInstance().getPersonID();
		assignFamilySide();
	}

	/**
	 * Gets an individual person by a given personID
	 * @param personID the personID of the person object
	 * @return the Person objet of the given personID
	 */
	public Person getPerson(String personID){
		return mPersonMap.get(personID);
	}

	/**
	 * Gets the entire map of the people all at once.
	 * @return The map of personID string keys and Person objects value pairs.
	 */
	public Map<String, Person> getPersonMap(){
		return mPersonMap;
	}

	/**
	 * Get all of the family members of a specific person
	 * @param personID the personID of the person whose family is being searched for.
	 * @return a list of objects, which only contains person objects.
	 */
	public List<Object> getFamilyByPerson(String personID) {
		Person person = mPersonMap.get(personID);
		List<Object> family = new ArrayList<>();
		if (person.getFatherID() != null) {
			family.add(mPersonMap.get(person.getFatherID()));
		}
		if (person.getMotherID() != null) {
			family.add(mPersonMap.get(person.getMotherID()));
		}
		if (person.getSpouseID() != null) {
			family.add(mPersonMap.get(person.getSpouseID()));
		}
		for (Person p : mPersonMap.values()) {
			if (p.getFatherID() != null) {
				if (p.getFatherID().equals(person.getPersonID()))
					family.add(p);
			} else if (p.getMotherID() != null) {
				if (p.getMotherID().equals(person.getPersonID()))
					family.add(p);
			}
		}
		return family;
	}

	/**
	 * Starts a recursive funciton to assign each person to a specific side of the family,
	 * mother's side or fathers side, based on the root person.
	 */
	private void assignFamilySide(){
		Person rootPerson = mPersonMap.get(mRootID);
		Person spouse = mPersonMap.get(rootPerson.getSpouseID());
		rootPerson.setFamilySide("none");
		if (spouse != null )
			spouse.setFamilySide("none");
		Map<String, Person> motherSide = new HashMap<>();
		getAncestors(getPerson(rootPerson.getMotherID()), motherSide);

		Map<String, Person> fatherSide = new HashMap<>();
		getAncestors(getPerson(rootPerson.getFatherID()), fatherSide);

		Iterator motherIterator = motherSide.entrySet().iterator();
		while (motherIterator.hasNext()){
			Map.Entry pair = (Map.Entry)motherIterator.next();
			Person person = (Person)pair.getValue();
			person.setFamilySide("mother");
		}

		Iterator fatherIterator = fatherSide.entrySet().iterator();
		while(fatherIterator.hasNext()){
			Map.Entry pair = (Map.Entry)fatherIterator.next();
			Person person = (Person)pair.getValue();
			person.setFamilySide("father");
		}
	}

	/**
	 * The recursive call to search through all of the people in the people map
	 * Assigns the poeple to a specific side of the family.
	 * @param p
	 * @param personMap
	 */
	private void getAncestors(Person p, Map<String, Person> personMap){
		personMap.put(p.getPersonID(), p);
		if (p.getMotherID() != null){
			getAncestors(getPerson(p.getMotherID()), personMap);
		}
		if (p.getFatherID() != null){
			getAncestors(getPerson(p.getFatherID()), personMap);
		}
	}

	/**
	 * Checks if the system is currently logged into (authenticaed with the familyMap server)
	 * @return a boolean if the system is logged in
	 */
	public boolean isLoggedIn() {
		return mLoggedIn;
	}

	/**
	 * set if the FamilyMap is currently logged in
	 * @param loggedIn
	 */
	public void setLoggedIn(boolean loggedIn) {
		mLoggedIn = loggedIn;
	}

	/**
	 * Adds events to the event map individually
	 * @param event
	 */
	public void addEvent(Event event) {
		mEventMap.put(event.getEventID(), event);
	}

	/**
	 * Add a map of events to the member event map all at once.
	 * @param events eventID and event key value/object pair map.
	 */
	public void addEvents(Map<String, Event> events){
		mEventMap.putAll(events);
	}

	/**
	 * Get an event by it's eventID
	 * @param eventID the unique string of the event
	 * @return the event that matches the string
	 */
	public Event getEvent(String eventID){
		return mEventMap.get(eventID);
	}

	/**
	 * Get the entire map of events all at once.
	 * @return the map of eventID string key and Event object value pairs
	 */
	public Map<String, Event> getEventMap() {
		return mEventMap;
	}

	/**
	 * Get all of the events tied to a specific person
	 * @param personID the personID of the person
	 * @return An object lists, containing all of the event objects tied to the personID
	 */
	public List<Object> getEventsByPerson(String personID) {
		List<Event> events = new ArrayList<>();
		for (Event e : mEventMap.values()) {
			if (personID.equals(e.getPersonID()))
				events.add(e);
		}

		Collections.sort(events);
		List<Object> list = new ArrayList<>();
		list.addAll(events);
		return list;
	}

	/**
	 * Adds a filter to the list of custom filters.
	 * @param filter the filter to be added
	 */
	public void addFilter(Filter filter){
		mCustomFilters.put(filter.getFilterKey(), filter);
		filter.setIcon(eventIcon);
	}

	/**
	 *
	 * @return
	 */
	public List<Filter> getFiltersList() {
		List<Filter> filterList = new ArrayList<>();
		filterList.addAll(mCustomFilters.values());
		filterList.addAll(mConstantFilters);
		return filterList;
	}

	/**
	 * Get the entire map of filters all at once.
	 * @return the map of filter description string key and filter object value pairs
	 */
	public Map<String, Filter> getFilterMap() {
		Map<String, Filter> filterMap = new HashMap<>(mCustomFilters);
		for (Filter f : mConstantFilters){
			filterMap.put(f.getFilterKey(), f);
		}
		return filterMap;
	}

	/**
	 * Searches all of the events and people for anything containing the given search string
	 * @param term the string to be searched
	 * @return a list of Items that contain the given string
	 */
	public List<SearchActivity.Item> searchAll(String term){
		List<SearchActivity.Item> searchedList = new ArrayList<>();
		for (Person p : mPersonMap.values()){
			if (p.contains(term))
				searchedList.add(p);
		}

		for (Event e : mEventMap.values()){
			if (e.contains(term))
				searchedList.add(e);
		}
		return searchedList;
	}

	/**
	 * Get the current event
	 * @return event of the current event
	 */
	public Event getCurrentEvent() {
		return mCurrentEvent;
	}

	/**
	 * Sets the current event
	 *
	 * @param event the event to be set
	 */
	public void setCurrentEvent(Event event) {
		mCurrentEvent = event;
	}

	/**
	 * Processes a logout
	 */
	public void logout() {
		mPersonMap = new HashMap<>();
		mEventMap = new HashMap<>();
		mCurrentEvent = null;
		mCustomFilters = new HashMap<>();
		Login.getInstance().logout();
		mLoggedIn = false;
	}




	/**
	 * Get the male icon
	 * @return IconDrawable male icon
	 */
	public IconDrawable getMaleIcon() {
		return maleIcon;
	}

	/**
	 * Set the male icon
	 * @param maleIcon IconDrawable male icon
	 */
	public void setMaleIcon(IconDrawable maleIcon) {
		this.maleIcon = maleIcon;
	}

	/**
	 * Get the female icon
	 * @return IconDrawable female icon
	 */
	public IconDrawable getFemaleIcon() {
		return femaleIcon;
	}

	/**
	 * Set the female icon
	 * @param femaleIcon IconDrawable female icon
	 */
	public void setFemaleIcon(IconDrawable femaleIcon) {
		this.femaleIcon = femaleIcon;
	}

	/**
	 * Get the android icon
	 * @return IconDrawable androi icon
	 */
	public IconDrawable getAndroidIcon() {
		return androidIcon;
	}

	/**
	 * Set the android icon
	 * @param androidIcon IconDrawable android icon
	 */
	public void setAndroidIcon(IconDrawable androidIcon) {
		this.androidIcon = androidIcon;
	}

	/**
	 * Get the event icon
	 * @return IconDrawable event icon
	 */
	public IconDrawable getEventIcon() {
		return eventIcon;
	}

	/**
	 * Set the event icon
	 * @param eventIcon IconDrawable event icon
	 */
	public void setEventIcon(IconDrawable eventIcon) {
		this.eventIcon = eventIcon;
	}

	/**
	 * Get the search icon
	 * @return IconDrawable search icon
	 */
	public IconDrawable getSearchIcon() {
		return searchIcon;
	}

	/**
	 * Set the search icon
	 * @param searchIcon IconDrawable search icon
	 */
	public void setSearchIcon(IconDrawable searchIcon) {
		this.searchIcon = searchIcon;
	}

	/**
	 * Get the filter icon
	 * @return IconDrawable filter icon
	 */
	public IconDrawable getFilterIcon() {
		return filterIcon;
	}

	/**
	 * Set the filter icon
	 * @param filterIcon IconDrawable filter icon
	 */
	public void setFilterIcon(IconDrawable filterIcon) {
		this.filterIcon = filterIcon;
	}

	/**
	 * Get the gear icon
	 * @return IconDrawable gear icon
	 */
	public IconDrawable getGearIcon() {
		return gearIcon;
	}

	/**
	 * Set the gear icon
	 * @param gearIcon IconDrawable gear icon
	 */
	public void setGearIcon(IconDrawable gearIcon) {
		this.gearIcon = gearIcon;
	}

	/**
	 * Get the double up icon
	 * @return IconDrawable double up icon
	 */
	public IconDrawable getDoubleUpIcon() {
		return doubleUpIcon;
	}

	/**
	 * Set the double up arrow icon
	 * @param doubleUpIcon IconDrawable double up icon
	 */
	public void setDoubleUpIcon(IconDrawable doubleUpIcon) {
		this.doubleUpIcon = doubleUpIcon;
	}
}

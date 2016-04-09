package edu.byu.robpneu.cs240.familymap.model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
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

	private Drawable maleIcon;
	private Drawable femaleIcon;
	private Drawable androidIcon;
	private Drawable eventIcon;
	private Drawable searchIcon;
	private Drawable filterIcon;
	private Drawable gearIcon;
	private Drawable doubleUpIcon;

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

	public static FamilyMap getInstance(){
		if (instance == null){
			instance = new FamilyMap();
		}
		return instance;
	}



	public void addPerson(Person person){
		mPersonMap.put(person.getPersonID(), person);
	}

	public void addPeople(Map<String, Person> people){
		mPersonMap.putAll(people);
		mRootID = Login.getInstance().getPersonID();
		assignFamilySide();
	}

	public Person getPerson(String personID){
		return mPersonMap.get(personID);
	}

	public Map<String, Person> getPersonMap(){
		return mPersonMap;
	}

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

	private void getAncestors(Person p, Map<String, Person> personMap){
		personMap.put(p.getPersonID(), p);
		if (p.getMotherID() != null){
			getAncestors(getPerson(p.getMotherID()), personMap);
		}
		if (p.getFatherID() != null){
			getAncestors(getPerson(p.getFatherID()), personMap);
		}
	}



	public boolean isLoggedIn() {
		return mLoggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		mLoggedIn = loggedIn;
	}



	public void addEvent(Event event) {
		mEventMap.put(event.getEventID(), event);
	}

	public void addEvents(Map<String, Event> events){
		mEventMap.putAll(events);
	}

	public Event getEvent(String eventID){
		return mEventMap.get(eventID);
	}

	public Map<String, Event> getEventMap() {
		return mEventMap;
	}


	public void addFilter(Filter filter){
		mCustomFilters.put(filter.getFilterKey(), filter);
	}

	public List<Filter> getFiltersList() {
		List<Filter> filterList = new ArrayList<>();
		filterList.addAll(mCustomFilters.values());
		filterList.addAll(mConstantFilters);
		return filterList;
	}

	public Map<String, Filter> getFilterMap() {
		Map<String, Filter> filterMap = new HashMap<>(mCustomFilters);
		for (Filter f : mConstantFilters){
			filterMap.put(f.getFilterKey(), f);
		}
		return filterMap;
	}



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



	public Drawable getMaleIcon() {
		return maleIcon;
	}

	public void setMaleIcon(Drawable maleIcon) {
		this.maleIcon = maleIcon;
	}

	public Drawable getFemaleIcon() {
		return femaleIcon;
	}

	public void setFemaleIcon(Drawable femaleIcon) {
		this.femaleIcon = femaleIcon;
	}

	public Drawable getAndroidIcon() {
		return androidIcon;
	}

	public void setAndroidIcon(Drawable androidIcon) {
		this.androidIcon = androidIcon;
	}

	public Drawable getEventIcon() {
		return eventIcon;
	}

	public void setEventIcon(Drawable eventIcon) {
		this.eventIcon = eventIcon;
	}

	public Drawable getSearchIcon() {
		return searchIcon;
	}

	public void setSearchIcon(Drawable searchIcon) {
		this.searchIcon = searchIcon;
	}

	public Drawable getFilterIcon() {
		return filterIcon;
	}

	public void setFilterIcon(Drawable filterIcon) {
		this.filterIcon = filterIcon;
	}

	public Drawable getGearIcon() {
		return gearIcon;
	}

	public void setGearIcon(Drawable gearIcon) {
		this.gearIcon = gearIcon;
	}

	public Drawable getDoubleUpIcon() {
		return doubleUpIcon;
	}

	public void setDoubleUpIcon(Drawable doubleUpIcon) {
		this.doubleUpIcon = doubleUpIcon;
	}



	public void logout(){
		mPersonMap = new HashMap<>();
		mEventMap = new HashMap<>();
		mLoggedIn = false;
	}
}

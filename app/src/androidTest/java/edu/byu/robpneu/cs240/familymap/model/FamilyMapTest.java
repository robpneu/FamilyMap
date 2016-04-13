package edu.byu.robpneu.cs240.familymap.model;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import edu.byu.robpneu.cs240.familymap.dao.HttpClient;
import edu.byu.robpneu.cs240.familymap.dao.Login;

/**
 * @author Robert P Neu
 * @version 1.0 4/12/16
 */
public class FamilyMapTest extends AndroidTestCase {
	FamilyMap mFamilyMap;
	HttpClient mHttpClient;

	@Before
	public void setUp() throws Exception {
		Login login = Login.getInstance();
		mHttpClient = HttpClient.getInstance();

		login.setUserName("r");
		login.setPassword("n");
		login.setServerHost("10.10.10.42");
		login.setServerPort("8081");

		mHttpClient.login();

		mFamilyMap = FamilyMap.getInstance();
		mFamilyMap.addPeople(mHttpClient.getAllPeople());
		mFamilyMap.addEvents(mHttpClient.getAllEvents());
	}

	@Test
	public void testGetEvent() throws Exception {
		Map<String, Event> eventMap = mHttpClient.getAllEvents();
		for (Event e : eventMap.values()) {
			assertEquals(e.equals(mFamilyMap.getEvent(e.getEventID())), true);
		}
	}

	@Test
	public void testGetEventMap() throws Exception {
		assertEquals(mFamilyMap.getEventMap().equals(mHttpClient.getAllEvents()), true);
	}

	@Test
	public void testGetEventsByPerson() throws Exception {
		assertEquals(mFamilyMap.getPersonMap().equals(mHttpClient.getAllPeople()), true);
	}

	@Test
	public void testGetPerson() throws Exception {
		Map<String, Person> personMap = mHttpClient.getAllPeople();
		for (Person p : personMap.values()) {
			assertEquals(p.equals(mFamilyMap.getPerson(p.getPersonID())), true);
		}
	}

	@Test
	public void testGetPersonMap() throws Exception {
		assertEquals(mFamilyMap.getPersonMap().equals(mHttpClient.getAllPeople()), true);
	}

	@Test
	public void testGetFamilyByPerson() throws Exception {
		Map<String, Person> personMap = mHttpClient.getAllPeople();
		for (String personID : personMap.keySet()) {
			Person person = personMap.get(personID);
			List<Object> family = new ArrayList<>();
			if (person.getFatherID() != null) {
				family.add(personMap.get(person.getFatherID()));
			}
			if (person.getMotherID() != null) {
				family.add(personMap.get(person.getMotherID()));
			}
			if (person.getSpouseID() != null) {
				family.add(personMap.get(person.getSpouseID()));
			}
			for (Person p : personMap.values()) {
				if (p.getFatherID() != null) {
					if (p.getFatherID().equals(person.getPersonID()))
						family.add(p);
				} else if (p.getMotherID() != null) {
					if (p.getMotherID().equals(person.getPersonID()))
						family.add(p);
				}
			}

			assertEquals(family.equals(mFamilyMap.getFamilyByPerson(personID)), true);
		}

	}

	@Test
	public void testGetFilterMap() throws Exception {
		Map<String, Filter> filterMap = mFamilyMap.getFilterMap();
		Map<String, Event> eventMap = mFamilyMap.getEventMap();
		Set<String> eventDescriptions = new TreeSet<>();
		for (Event event : eventMap.values()) {
			eventDescriptions.add(event.getDescription().toLowerCase());
		}
		Set<String> filterDescriptions = new TreeSet<>();
		for (Filter filter : filterMap.values()) {
			filterDescriptions.add(filter.getDescription().toLowerCase());
		}
		assertEquals(eventDescriptions.equals(filterDescriptions), true);
	}

	@Test
	public void testGetCurrentEvent() throws Exception {
		Map<String, Event> eventMap = mFamilyMap.getEventMap();
		for (Event event : eventMap.values()) {
			mFamilyMap.setCurrentEvent(null);
			assertEquals(mFamilyMap.getCurrentEvent() == null, true);
			mFamilyMap.setCurrentEvent(event);
			assertEquals(mFamilyMap.getCurrentEvent() == event, true);
		}
	}

	@Test
	public void testLogout() throws Exception {
		assertEquals(mFamilyMap.getCurrentEvent() != null, true);
		assertEquals(mFamilyMap.getPersonMap() != null, true);
		assertEquals(mFamilyMap.getEventMap() != null, true);
		assertEquals(mFamilyMap.isLoggedIn(), true);

		mFamilyMap.logout();

		assertEquals(mFamilyMap.getCurrentEvent() == null, true);
		assertEquals(mFamilyMap.getPersonMap() == null, true);
		assertEquals(mFamilyMap.getEventMap() == null, true);
		assertEquals(mFamilyMap.isLoggedIn(), false);
	}

	@Test
	public void testEventOrder() throws Exception {
		Map<String, Person> personMap = mFamilyMap.getPersonMap();
		Map<String, Event> eventMap = mFamilyMap.getEventMap();
		for (Person person : personMap.values()) {
			List<Event> events = new ArrayList<>();
			for (Event event : eventMap.values()) {
				if (event.getPersonID().equals(person.getPersonID()))
					events.add(event);
			}
			Collections.sort(events);
			assertEquals(events.equals(mFamilyMap.getEventsByPerson(person.getPersonID())), true);
		}
	}

	@Test
	public void testEventFilter() throws Exception {
		Map<String, Event> eventMap = mFamilyMap.getEventMap();
		Map<String, Filter> filterMap = mFamilyMap.getFilterMap();
		for (Filter filter : filterMap.values()) {
			Set<String> eventTypes = new HashSet<>();
			for (Event event : eventMap.values()) {
				if (filter.getDescription().toLowerCase().equals(event.getDescription().toLowerCase()))
					eventTypes.add(event.getDescription().toLowerCase());
			}
			assertEquals(eventTypes.contains(filter.getDescription().toLowerCase()), filter.isShown());

			filter.setShown(true);
			eventTypes = new HashSet<>();
			for (Event event : eventMap.values()) {
				if (filter.getDescription().toLowerCase().equals(event.getDescription().toLowerCase()))
					eventTypes.add(event.getDescription().toLowerCase());
			}
			assertEquals(eventTypes.contains(filter.getDescription().toLowerCase()), true);

			filter.setShown(false);
			eventTypes = new HashSet<>();
			for (Event event : eventMap.values()) {
				if (filter.getDescription().toLowerCase().equals(event.getDescription().toLowerCase()))
					eventTypes.add(event.getDescription().toLowerCase());
			}
			assertEquals(eventTypes.contains(filter.getDescription().toLowerCase()), false);
		}
	}
}
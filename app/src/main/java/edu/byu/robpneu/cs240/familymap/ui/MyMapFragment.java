package edu.byu.robpneu.cs240.familymap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.amazon.geo.mapsv2.model.Polyline;
import com.amazon.geo.mapsv2.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.robpneu.cs240.familymap.R;
import edu.byu.robpneu.cs240.familymap.model.Event;
import edu.byu.robpneu.cs240.familymap.model.FamilyMap;
import edu.byu.robpneu.cs240.familymap.model.Filter;
import edu.byu.robpneu.cs240.familymap.model.Person;
import edu.byu.robpneu.cs240.familymap.model.Settings;

/**
 * @author Robert P Neu
 * @version 1.0 3/26/16
 */
public class MyMapFragment extends android.support.v4.app.Fragment {
	private SupportMapFragment mSupportMapFragment;
	private FamilyMap mFamilyMap;
	private AmazonMap mAmazonMap;
	private Settings mSettings;
	private Map<Integer, Event> mEventHashMap;
	private Person currentPerson;
	private Event currentEvent;
	private List<Polyline> mPolylines;

	private ImageView genderIcon;
	private TextView personName;
	private TextView personDetails;
	private LinearLayout eventDetails;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		mFamilyMap = FamilyMap.getInstance();
		mSettings = Settings.getInstance();
		mEventHashMap = new HashMap<>();
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
		currentEvent = mFamilyMap.getCurrentEvent();
		mFamilyMap.setCurrentEvent(null);
		mPolylines = new ArrayList<>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_map, container, false);

		genderIcon = (ImageView)v.findViewById(R.id.gender_image_view);
		genderIcon.setImageDrawable(mFamilyMap.getAndroidIcon());

		personName = (TextView)v.findViewById(R.id.eventPerson);
		personDetails = (TextView)v.findViewById(R.id.eventDetails);

		eventDetails = (LinearLayout)v.findViewById(R.id.eventPersonInfo);
		eventDetails.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentPerson != null) {
					Intent intent = new Intent(getActivity(), PersonActivity.class);
					intent.putExtra("PERSON_ID", currentPerson.getPersonID());
					startActivity(intent);
				}
			}
		});

		FragmentManager fm = getChildFragmentManager();

		mSupportMapFragment =(com.amazon.geo.mapsv2.SupportMapFragment)getChildFragmentManager()
				.findFragmentById(R.id.map);
		mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(AmazonMap amazonMap) {
				mAmazonMap = amazonMap;
				mAmazonMap.setMyLocationEnabled(false);
				mAmazonMap.setMapType(mSettings.getMapType());
				mAmazonMap.getUiSettings().setMapToolbarEnabled(false);
				putEventPins();
				if (currentEvent != null) {
					updateMarkerDetails(currentEvent);
					mAmazonMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude()), 5.0f));
				}
				mAmazonMap.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
					@Override
					public boolean onMarkerClick(Marker marker) {
						Event event = mEventHashMap.get(marker.hashCode());
						updateMarkerDetails(event);
						drawMapLines();
						mAmazonMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(event.getLatitude(), event.getLongitude()), 4.0f));
						return true;
					}
				});
				mAmazonMap.setOnMapClickListener(new AmazonMap.OnMapClickListener() {
					@Override
					public void onMapClick(LatLng latLng) {
						genderIcon.setImageDrawable(mFamilyMap.getAndroidIcon());
						personName.setText("Click on a marker");
						personDetails.setText("to see event details");
						currentEvent = null;
						currentPerson = null;
					}
				});
			}
		});
		return v;
	}

	/**
	 * Put all of the event pins onto the map
	 */
	private void putEventPins() {
		Map<String, Event> mEvents = mFamilyMap.getEventMap();
		Map<String, Filter> mFilters = mFamilyMap.getFilterMap();
		Iterator it = mEvents.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Event event = (Event) pair.getValue();

			boolean putMarkerOnMap = false;

			String gender = mFamilyMap
					.getPerson(event.getPersonID())
					.getGender();
			if (mFilters.get(gender).isShown()) {
				putMarkerOnMap = true;
			}

			if (putMarkerOnMap == true) {
				Person person = mFamilyMap.getPerson(event.getPersonID());
				String familySide = person.getFamilySide();
				if (familySide == "none") {
					if (!mFilters.get("father").isShown() & !mFilters.get("mother").isShown()) {
						putMarkerOnMap = false;
					}
				} else {
					if (!mFilters.get(familySide).isShown()) {
						putMarkerOnMap = false;
					}
				}
			}

			int markerColor = 0;
			if (putMarkerOnMap == true) {
				Filter filter = mFilters.get(event.getDescription());
				putMarkerOnMap = filter.isShown();
				markerColor = filter.getHSVColor();
			}
			LatLng pnt = new LatLng(event.getLatitude(), event.getLongitude());
			MarkerOptions options = new MarkerOptions().position(pnt)
					.title(event.getCity() + ", " + event.getCountry())
					.snippet(pnt.toString())
					.icon(BitmapDescriptorFactory.defaultMarker(markerColor));
			if (putMarkerOnMap) {
				Marker marker = mAmazonMap.addMarker(options);
				mEventHashMap.put(marker.hashCode(), event);
			}
		}
	}

	/**
	 * Draw all of the map lines onto the map
	 */
	private void drawMapLines() {
		for (Polyline polyline : mPolylines) {
			polyline.remove();
		}
		mPolylines.clear();

		if (currentPerson != null) {
			if (mSettings.showLifeStoryLines()) {
				int lifeStoryLinesColor = mSettings.getLifeStoryLinesColor();
				List<LatLng> pnts = new ArrayList<>();
				List<Object> events = mFamilyMap.getEventsByPerson(currentPerson.getPersonID());
				if (events.size() > 0) {
					pnts.add(currentEvent.getLatLng());
					for (Object e : events) {
						Event event = (Event) e;
						pnts.add(event.getLatLng());
						PolylineOptions polylineOptions = new PolylineOptions().addAll(pnts).color(lifeStoryLinesColor).width(9f);
						mPolylines.add(mAmazonMap.addPolyline(polylineOptions));
					}
				}
			}

			if (mSettings.showFamilyStoryLines()) {
				recursiveFamilyLines(currentPerson, currentEvent, 9f);
			}

			if (mSettings.showSpouseLines()) {
				int spouseLinesColor = mSettings.getSpouseLinesColor();
				List<LatLng> pnts = new ArrayList<>();
				String spouseID = currentPerson.getSpouseID();
				if (spouseID != null) {
					List<Object> events = mFamilyMap.getEventsByPerson(spouseID);
					if (events.size() > 0) {
						Event earliestEvent = (Event) events.get(0);
						pnts.add(currentEvent.getLatLng());
						pnts.add(earliestEvent.getLatLng());
						PolylineOptions polylineOptions = new PolylineOptions().addAll(pnts).color(spouseLinesColor).width(9f);
						mPolylines.add(mAmazonMap.addPolyline(polylineOptions));
					}
				}
			}
		}
	}

	/**
	 * Recursive map lines drawing helper for the family lines
	 * It will draw lines between the incoming person's mother and father's earliest events
	 * and the incoming event, which belongs to the incoming person
	 *
	 * The thickness of each line drawn is smaller as we get father back into the family tree
	 * This is found as the recursion gets deeper
	 *
	 * @param person the current person we're on
	 * @param event the current event that we're drawing from
	 * @param thickness the incoming thickness of the line
	 */
	private void recursiveFamilyLines(Person person, Event event, float thickness) {
		if (person.getFatherID() != null) {
			List<LatLng> pnts = new ArrayList<>();
			List<Object> events = mFamilyMap.getEventsByPerson(person.getFatherID());
			if (events.size() > 0) {
				pnts.add(event.getLatLng());
				Event earliestEvent = (Event) events.get(0);
				pnts.add(earliestEvent.getLatLng());
				PolylineOptions polylineOptions = new PolylineOptions().addAll(pnts).color(mSettings.getFamilyStoryLinesColor()).width(thickness);
				mPolylines.add(mAmazonMap.addPolyline(polylineOptions));
				recursiveFamilyLines(mFamilyMap.getPerson(person.getFatherID()), earliestEvent, thickness - 3f);
			}
		}
		if (person.getMotherID() != null) {
			List<LatLng> pnts = new ArrayList<>();
			List<Object> events = mFamilyMap.getEventsByPerson(person.getMotherID());
			if (events.size() > 0) {
				pnts.add(event.getLatLng());
				Event earliestEvent = (Event) events.get(0);
				pnts.add(earliestEvent.getLatLng());
				PolylineOptions polylineOptions = new PolylineOptions().addAll(pnts).color(mSettings.getFamilyStoryLinesColor()).width(thickness);
				mPolylines.add(mAmazonMap.addPolyline(polylineOptions));
				recursiveFamilyLines(mFamilyMap.getPerson(person.getMotherID()), earliestEvent, thickness - 3f);
			}
		}

	}

	/**
	 * Updates the bar below the map on the bottom of the screen with the selected event's information
	 *
	 * @param event the selected event to be updated.
	 */
	private void updateMarkerDetails(Event event){
		Person person = mFamilyMap.getPerson(event.getPersonID());
		currentEvent = event;
		currentPerson = person;
		switch (person.getGender().toLowerCase()){
			case "f":
				genderIcon.setImageDrawable(mFamilyMap.getFemaleIcon());
				break;
			case "m":
				genderIcon.setImageDrawable(mFamilyMap.getMaleIcon());
				break;
			default:
				genderIcon.setImageDrawable(mFamilyMap.getAndroidIcon());
		}
		personName.setText(person.getFullName());
		personDetails.setText(event.toString());
	}
}



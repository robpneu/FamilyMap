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
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;

import java.util.HashMap;
import java.util.Iterator;
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

	private ImageView genderIcon;
	private TextView personName;
	private TextView personDetails;
	private LinearLayout eventDetails;

//	private static final int RED = 0;
//	private static final int ORANGE = 30;
//	private static final int YELLOW = 60;
//	private static final int GREEN = 120;
//	private static final int BLUE = 240;
//	private static final int PURPLE = 300;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		mFamilyMap = FamilyMap.getInstance();
		mSettings = Settings.getInstance();
		mEventHashMap = new HashMap<>();
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
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
				if (currentPerson != null){
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
				mAmazonMap.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
					@Override
					public boolean onMarkerClick(Marker marker) {
						Event event = mEventHashMap.get(marker.hashCode());
						updateMarkerDetails(event);
						return false;
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

	/**
	 * Put all of the vent pins onto the map
	 */
	private void putEventPins(){
		Map<String, Event> mEvents = mFamilyMap.getEventMap();
		Map<String, Filter> mFilters = mFamilyMap.getFilterMap();
		Iterator it = mEvents.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Event event = (Event)pair.getValue();

			boolean putMarkerOnMap = false;

			String gender = mFamilyMap
					.getPerson(event.getPersonID())
					.getGender();
			if (mFilters.get(gender).isShown()){
				putMarkerOnMap = true;
			}

			if (putMarkerOnMap == true){
				Person person = mFamilyMap.getPerson(event.getPersonID());
				String familySide = person.getFamilySide();
				if (familySide == "none"){
					if (!mFilters.get("father").isShown() & !mFilters.get("mother").isShown()){
						putMarkerOnMap = false;
					}
				}
				else {
					if (!mFilters.get(familySide).isShown()) {
						putMarkerOnMap = false;
					}
				}
			}

			int markerColor = 0;
			if (putMarkerOnMap == true) {
				Filter filter = mFilters.get(event.getDescription());
				putMarkerOnMap = filter.isShown();
				markerColor = filter.getColor();
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
}

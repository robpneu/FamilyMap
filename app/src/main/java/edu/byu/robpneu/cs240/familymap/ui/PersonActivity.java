package edu.byu.robpneu.cs240.familymap.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.robpneu.cs240.familymap.R;
import edu.byu.robpneu.cs240.familymap.model.Event;
import edu.byu.robpneu.cs240.familymap.model.FamilyMap;
import edu.byu.robpneu.cs240.familymap.model.Filter;
import edu.byu.robpneu.cs240.familymap.model.Person;

public class PersonActivity extends AppCompatActivity {
	private Person mPerson;
	private FamilyMap mFamilyMap;
	private TextView mFirstName;
	private TextView mLastName;
	private TextView mGender;
	private List<String> mGroupList;
	private List<Object> mEventsList;
	private List<Object> mFamilyList;
	private Map<String, List<Object>> mExpandableItemsMap;
	private MenuItem mGoToTopMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFamilyMap = FamilyMap.getInstance();
		setContentView(R.layout.activity_person);
		getSupportActionBar().setTitle("FamilyMap: Person Details");
		// TODO set up up button on menu bar
		String personID = getIntent().getStringExtra("PERSON_ID");
		mPerson = mFamilyMap.getPerson(personID);

		mFirstName = (TextView) findViewById(R.id.first_name);
		mLastName = (TextView) findViewById(R.id.last_name);
		mGender = (TextView) findViewById(R.id.gender);

		mFirstName.setText(mPerson.getFirstName());
		mLastName.setText(mPerson.getLastName());
		if (mPerson.getGender().equals("f"))
			mGender.setText("Female");
		else
			mGender.setText("Male");

		mGroupList = new ArrayList<>();
		mGroupList.add("LIFE EVENTS");
		mGroupList.add("FAMILY");

		mEventsList = mFamilyMap.getEventsByPerson(mPerson.getPersonID());
		mFamilyList = mFamilyMap.getFamilyByPerson(mPerson.getPersonID());

		mExpandableItemsMap = new HashMap<>();
		mExpandableItemsMap.put("LIFE EVENTS", mEventsList);
		mExpandableItemsMap.put("FAMILY", mFamilyList);

		ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.person_details);
		final ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(this, mGroupList, mExpandableItemsMap);
		expandableListView.setAdapter(expandableListAdapter);
		expandableListView.expandGroup(0);
		expandableListView.expandGroup(1);

		expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				if (groupPosition == 0) {
					Event event = (Event) expandableListAdapter.getChild(groupPosition, childPosition);
					Intent intent = new Intent(getApplicationContext(), MapActivity.class);
					mFamilyMap.setCurrentEvent(event);
					startActivity(intent);
				} else {
					Person person = (Person) expandableListAdapter.getChild(groupPosition, childPosition);
					Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
					intent.putExtra("PERSON_ID", person.getPersonID());
					startActivity(intent);
				}
				return true;
			}

		});
	}

	/**
	 * Initialize the contents of the Activity's standard options menu.  You
	 * should place your menu items in to <var>menu</var>.
	 * <p/>
	 * <p>This is only called once, the first time the options menu is
	 * displayed.  To update the menu every time it is displayed, see
	 * {@link #onPrepareOptionsMenu}.
	 * <p/>
	 * <p>The default implementation populates the menu with standard system
	 * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
	 * they will be correctly ordered with application-defined menu items.
	 * Deriving classes should always call through to the base implementation.
	 * <p/>
	 * <p>You can safely hold on to <var>menu</var> (and any items created
	 * from it), making modifications to it as desired, until the next
	 * time onCreateOptionsMenu() is called.
	 * <p/>
	 * <p>When you add items to the menu, you can implement the Activity's
	 * {@link #onOptionsItemSelected} method to handle them there.
	 *
	 * @param menu The options menu in which you place your items.
	 * @return You must return true for the menu to be displayed;
	 * if you return false it will not be shown.
	 * @see #onPrepareOptionsMenu
	 * @see #onOptionsItemSelected
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.up_menu, menu);
		mGoToTopMenu = menu.findItem(R.id.menu_item_top);
		mGoToTopMenu.setIcon(mFamilyMap.getDoubleUpIcon());

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * This hook is called whenever an item in your options menu is selected.
	 * The default implementation simply returns false to have the normal
	 * processing happen (calling the item's Runnable or sending a message to
	 * its Handler as appropriate).  You can use this method for any items
	 * for which you would like to do processing without those other
	 * facilities.
	 * <p/>
	 * <p>Derived classes should call through to the base class for it to
	 * perform the default menu handling.</p>
	 *
	 * @param item The menu item that was selected.
	 * @return boolean Return false to allow normal menu processing to
	 * proceed, true to consume it here.
	 * @see #onCreateOptionsMenu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		if (item.getItemId() == R.id.menu_item_top) {
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		} else if (item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		} else
			return super.onOptionsItemSelected(item);

	}

	public class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context context;
		private List<String> titles;
		private Map<String, List<Object>> details;

		public ExpandableListAdapter(Context context, List<String> expandableListTitle, Map<String, List<Object>> expandableListDetail) {
			this.context = context;
			this.titles = expandableListTitle;
			this.details = expandableListDetail;
		}

		/**
		 * Gets the number of groups.
		 *
		 * @return the number of groups
		 */
		@Override
		public int getGroupCount() {
			return titles.size();
		}

		/**
		 * Gets the number of children in a specified group.
		 *
		 * @param groupPosition the position of the group for which the children
		 *                      count should be returned
		 * @return the children count in the specified group
		 */
		@Override
		public int getChildrenCount(int groupPosition) {
			return details.get(titles.get(groupPosition)).size();
		}

		/**
		 * Gets the data associated with the given group.
		 *
		 * @param groupPosition the position of the group
		 * @return the data child for the specified group
		 */
		@Override
		public Object getGroup(int groupPosition) {
			return titles.get(groupPosition);
		}

		/**
		 * Gets the data associated with the given child within the given group.
		 *
		 * @param groupPosition the position of the group that the child resides in
		 * @param childPosition the position of the child with respect to other
		 *                      children in the group
		 * @return the data of the child
		 */
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return details.get(titles.get(groupPosition)).get(childPosition);
		}

		/**
		 * Gets the ID for the group at the given position. This group ID must be
		 * unique across groups. The combined ID (see
		 * {@link #getCombinedGroupId(long)}) must be unique across ALL items
		 * (groups and all children).
		 *
		 * @param groupPosition the position of the group for which the ID is wanted
		 * @return the ID associated with the group
		 */
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		/**
		 * Gets the ID for the given child within the given group. This ID must be
		 * unique across all children within the group. The combined ID (see
		 * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
		 * (groups and all children).
		 *
		 * @param groupPosition the position of the group that contains the child
		 * @param childPosition the position of the child within the group for which
		 *                      the ID is wanted
		 * @return the ID associated with the child
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		/**
		 * Indicates whether the child and group IDs are stable across changes to the
		 * underlying data.
		 *
		 * @return whether or not the same ID always refers to the same object
		 * @see Adapter#hasStableIds()
		 */
		@Override
		public boolean hasStableIds() {
			return true;
		}

		/**
		 * Gets a View that displays the given group. This View is only for the
		 * group--the Views for the group's children will be fetched using
		 * {@link #getChildView(int, int, boolean, View, ViewGroup)}.
		 *
		 * @param groupPosition the position of the group for which the View is
		 *                      returned
		 * @param isExpanded    whether the group is expanded or collapsed
		 * @param convertView   the old view to reuse, if possible. You should check
		 *                      that this view is non-null and of an appropriate type before
		 *                      using. If it is not possible to convert this view to display
		 *                      the correct data, this method can create a new view. It is not
		 *                      guaranteed that the convertView will have been previously
		 *                      created by
		 *                      {@link #getGroupView(int, boolean, View, ViewGroup)}.
		 * @param parent        the parent that this view will eventually be attached to
		 * @return the View corresponding to the group at the specified position
		 */
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			String groupTitle = (String) getGroup(groupPosition);
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.list_group, null);
			}

			TextView listTitle = (TextView) convertView.findViewById(R.id.list_title);
			listTitle.setText(groupTitle);

			return convertView;
		}

		/**
		 * Gets a View that displays the data for the given child within the given
		 * group.
		 *
		 * @param groupPosition the position of the group that contains the child
		 * @param childPosition the position of the child (for which the View is
		 *                      returned) within the group
		 * @param isLastChild   Whether the child is the last child within the group
		 * @param convertView   the old view to reuse, if possible. You should check
		 *                      that this view is non-null and of an appropriate type before
		 *                      using. If it is not possible to convert this view to display
		 *                      the correct data, this method can create a new view. It is not
		 *                      guaranteed that the convertView will have been previously
		 *                      created by
		 *                      {@link #getChildView(int, int, boolean, View, ViewGroup)}.
		 * @param parent        the parent that this view will eventually be attached to
		 * @return the View corresponding to the child at the specified position
		 */
		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_search, null);
			TextView description = (TextView) convertView.findViewById(R.id.search_description);
			TextView subDescription = (TextView) convertView.findViewById(R.id.search_subdescription);
			ImageView imageView = (ImageView) convertView.findViewById(R.id.search_image_view);


			if (groupPosition == 0) { // Event
				Event event = (Event) getChild(groupPosition, childPosition);
				description.setText(event.toString());
				subDescription.setText(mPerson.getFullName());
				Filter filter = mFamilyMap.getFilterMap().get(event.getDescription());
				imageView.setImageDrawable(filter.getIcon().color(Color.BLACK));
			} else { // Person
				Person person = (Person) getChild(groupPosition, childPosition);
				description.setText(person.getFullName());
				if (person.getGender().equals("f")) {
					imageView.setImageDrawable(mFamilyMap.getFemaleIcon());
				} else {
					imageView.setImageDrawable(mFamilyMap.getMaleIcon());
				}

				if (person.getPersonID().equals(mPerson.getMotherID())) {
					subDescription.setText("Mother");
				} else if (person.getPersonID().equals(mPerson.getFatherID())) {
					subDescription.setText("Father");
				} else if (person.getPersonID().equals(mPerson.getSpouseID())) {
					subDescription.setText("Spouse");
				} else if (mPerson.getPersonID().equals(person.getMotherID()) | mPerson.getPersonID().equals(person.getFatherID())) {
					if (person.getGender().equals("f"))
						subDescription.setText("Daughter");
					else
						subDescription.setText("Son");
				} else {
					Log.e("PersonActivityChildView", "person's ID is not spouse, father, or mother. Houston we have a problem.");
				}
			}
			return convertView;
		}

		/**
		 * Whether the child at the specified position is selectable.
		 *
		 * @param groupPosition the position of the group that contains the child
		 * @param childPosition the position of the child within the group
		 * @return whether the child is selectable.
		 */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
}

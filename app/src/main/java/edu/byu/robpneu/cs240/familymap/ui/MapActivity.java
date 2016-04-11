package edu.byu.robpneu.cs240.familymap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import edu.byu.robpneu.cs240.familymap.R;
import edu.byu.robpneu.cs240.familymap.model.Event;
import edu.byu.robpneu.cs240.familymap.model.FamilyMap;

public class MapActivity extends AppCompatActivity {
	Event mEvent;
	Fragment mMapFragment;
	FamilyMap mFamilyMap;
	MenuItem mGoToTopMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFamilyMap = FamilyMap.getInstance();
		getSupportActionBar().setTitle("FamilyMap");

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.map_fragment_container);
		if (fragment == null) {
			mMapFragment = new MyMapFragment();
			fm.beginTransaction()
					.add(R.id.map_fragment_container, mMapFragment)
					.commit();
		}
		setContentView(R.layout.activity_map);
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
}

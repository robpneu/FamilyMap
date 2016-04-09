package edu.byu.robpneu.cs240.familymap.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.net.URL;

import edu.byu.robpneu.cs240.familymap.R;
import edu.byu.robpneu.cs240.familymap.dao.HttpClient;
import edu.byu.robpneu.cs240.familymap.model.FamilyMap;

//import android.support.v4.app.Fragment;

public class MainActivity extends AppCompatActivity {
	Fragment mloginFragment;
	Fragment mMapFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if(savedInstanceState.containsKey("logout")){
			onLogout();
		}
		else if(savedInstanceState.containsKey("resync")){
			onLogin();
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment;

		FamilyMap familyMap = FamilyMap.getInstance();

		familyMap.setMaleIcon(new IconDrawable(getApplicationContext(), Iconify.IconValue.fa_male).color(Color.BLUE).sizeDp(24));
		familyMap.setFemaleIcon(new IconDrawable(getApplicationContext(), Iconify.IconValue.fa_female).color(Color.MAGENTA).sizeDp(24));
		familyMap.setAndroidIcon(new IconDrawable(getApplicationContext(), Iconify.IconValue.fa_android).color(Color.GREEN).sizeDp(24));
		familyMap.setEventIcon(new IconDrawable(getApplicationContext(), Iconify.IconValue.fa_map_marker));
		familyMap.setSearchIcon(new IconDrawable(getApplicationContext(), Iconify.IconValue.fa_search));
		familyMap.setFilterIcon(new IconDrawable(getApplicationContext(), Iconify.IconValue.fa_filter));
		familyMap.setGearIcon(new IconDrawable(getApplicationContext(), Iconify.IconValue.fa_gear));
		familyMap.setDoubleUpIcon(new IconDrawable(getApplicationContext(), Iconify.IconValue.fa_angle_double_up));


		if (!FamilyMap.getInstance().isLoggedIn()){
			mloginFragment = (LoginFragment)fm.findFragmentById(R.id.fragment_container);
			if (mloginFragment == null){
				mloginFragment = new LoginFragment();
				fm.beginTransaction().add(R.id.fragment_container, mloginFragment).commit();
			}
		}
		else {
			fragment = (MyMapFragment)fm.findFragmentById(R.id.fragment_container);
			if(fragment == null){
				mMapFragment = new MyMapFragment();
				fm.beginTransaction()
						.add(R.id.fragment_container, mMapFragment)
						.commit();
			}
		}
		setContentView(R.layout.activity_main);
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
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_menu, menu);

		MenuItem searchMenu = menu.findItem(R.id.menu_item_search);
		MenuItem filterMenu = menu.findItem(R.id.menu_item_filter);
		MenuItem settingsMenu = menu.findItem(R.id.menu_item_settings);

		if(!FamilyMap.getInstance().isLoggedIn()){
			searchMenu.setVisible(false);
			filterMenu.setVisible(false);
			settingsMenu.setVisible(false);
		}

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
	 * perform the default menu handling.
	 *
	 * @param item The menu item that was selected.
	 * @return boolean Return false to allow normal menu processing to
	 * proceed, true to consume it here.
	 * @see #onCreateOptionsMenu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()){
			case R.id.menu_item_search:
				intent = new Intent(getApplicationContext(), SearchActivity.class);
				startActivity(intent);
				return true;
			case R.id.menu_item_filter:
				intent = new Intent(getApplicationContext(), FilterActivity.class);
				startActivity(intent);
				return true;
			case R.id.menu_item_settings:
				intent = new Intent(getApplicationContext(), SettingActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void onLogin(){
		DownloadPeople downloadPeople = new DownloadPeople();
		downloadPeople.execute();
		// Download events is called by download people.
	}

	public class DownloadPeople extends AsyncTask<URL, Integer, String>{
		/**
		 * Override this method to perform a computation on a background thread. The
		 * specified parameters are the parameters passed to {@link #execute}
		 * by the caller of this task.
		 * <p/>
		 * This method can call {@link #publishProgress} to publish updates
		 * on the UI thread.
		 *
		 * @param params The parameters of the task.
		 * @return A result, defined by the subclass of this task.
		 * @see #onPreExecute()
		 * @see #onPostExecute
		 * @see #publishProgress
		 */
		@Override
		protected String doInBackground(URL... params) {
			Log.i("Main download task", "it has begun");
			if (FamilyMap.getInstance().isLoggedIn()){
				FamilyMap.getInstance().addPeople(HttpClient.getInstance().getAllPeople());
				return "Done";
			}
			else {
				return null;
			}
		}

		/**
		 * <p>Runs on the UI thread after {@link #doInBackground}. The
		 * specified result is the value returned by {@link #doInBackground}.</p>
		 * <p/>
		 * <p>This method won't be invoked if the task was cancelled.</p>
		 *
		 * @param s The result of the operation computed by {@link #doInBackground}.
		 * @see #onPreExecute
		 * @see #doInBackground
		 * @see #onCancelled(Object)
		 */
		@Override
		protected void onPostExecute(String s) {
			Log.i("DownloadTask", "onPostExecute");
			if (s == null){
				Log.i("loginToast", "Fail!");
//				Toast.makeText(getApplicationContext(), "Login Failed. Please try again.", Toast.LENGTH_LONG).show();
			}
			else {
				Log.i("loginToast", "People Data Downloaded! ");
//				Toast.makeText(getApplicationContext(), "People Data Downloaded!", Toast.LENGTH_LONG).show();
				DownloadEvents downloadEvents = new DownloadEvents();
				downloadEvents.execute();
			}
		}

	}

	public class DownloadEvents extends AsyncTask<URL, Integer, String> {
		/**
		 * Override this method to perform a computation on a background thread. The
		 * specified parameters are the parameters passed to {@link #execute}
		 * by the caller of this task.
		 * <p/>
		 * This method can call {@link #publishProgress} to publish updates
		 * on the UI thread.
		 *
		 * @param params The parameters of the task.
		 * @return A result, defined by the subclass of this task.
		 * @see #onPreExecute()
		 * @see #onPostExecute
		 * @see #publishProgress
		 */
		@Override
		protected String doInBackground(URL... params) {
			Log.i("Main download task", "it has begun");
			if (FamilyMap.getInstance().isLoggedIn()){
				FamilyMap.getInstance().addEvents(HttpClient.getInstance().getAllEvents());
				return "Done";
			}
			else {
				return null;
			}
		}

		/**
		 * <p>Runs on the UI thread after {@link #doInBackground}. The
		 * specified result is the value returned by {@link #doInBackground}.</p>
		 * <p/>
		 * <p>This method won't be invoked if the task was cancelled.</p>
		 *
		 * @param s The result of the operation computed by {@link #doInBackground}.
		 * @see #onPreExecute
		 * @see #doInBackground
		 * @see #onCancelled(Object)
		 */
		@Override
		protected void onPostExecute(String s) {
			Log.i("DownloadTask", "onPostExecute");
			if (s == null){
				Log.i("loginToast", "Fail!");
//				Toast.makeText(getApplicationContext(), "Login Failed. Please try again.", Toast.LENGTH_LONG).show();
			}
			else {
				Log.i("loginToast", "Events Data Downloaded! ");
//				Toast.makeText(getApplicationContext(), "Events Data Downloaded!", Toast.LENGTH_SHORT).show();
				FragmentManager fm  = getSupportFragmentManager();
				fm.beginTransaction().remove(mloginFragment);
				if(mMapFragment == null){
					mMapFragment = new MyMapFragment();

				}
				fm.beginTransaction()
						.add(R.id.fragment_container, mMapFragment)
						.commit();
			}
		}

	}

	public void onLogout(){
		// TODO make sure I go to top before running this? I think I'll already be at the top...
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().remove(mMapFragment);
		if(mloginFragment == null){
			mloginFragment = new LoginFragment();
		}
		fragmentManager.beginTransaction().add(R.id.fragment_container, mloginFragment).commit();
		FamilyMap.getInstance().logout();
	}
}

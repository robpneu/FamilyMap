package edu.byu.robpneu.cs240.familymap.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.net.URL;

import edu.byu.robpneu.cs240.familymap.R;
import edu.byu.robpneu.cs240.familymap.dao.HttpClient;
import edu.byu.robpneu.cs240.familymap.model.FamilyMap;
import edu.byu.robpneu.cs240.familymap.model.Settings;

public class SettingActivity extends AppCompatActivity {
	private Settings mSettings;
	private Spinner mLifeStoryLinesSpinner;
	private Switch mLifeStoryLinesSwitch;
	private Switch mFamilyTreeLinesSwitch;
	private Spinner mFamilyTreeLinesSpinner;
	private Switch mSpouseLinesSwitch;
	private Spinner mSpouseLinesSpinner;
	private Spinner mMapTypeSpinner;
	private LinearLayout mReSyncData;
	private LinearLayout mLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		mSettings = Settings.getInstance();
		getSupportActionBar().setTitle("FamilyMap: Settings");


		ArrayAdapter<CharSequence> colors = ArrayAdapter.createFromResource(this, R.array.colors_array, android.R.layout.simple_spinner_item);
		colors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mLifeStoryLinesSpinner = (Spinner)findViewById(R.id.lifeStoryLinesColor);
		mLifeStoryLinesSpinner.setAdapter(colors);
		mLifeStoryLinesSpinner.setSelection(mSettings.getLifeStoryLinesSpinnerColor());
		mLifeStoryLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int color = 0;
				switch (position) {
					case 0: // red
						color = Color.RED;
						break;
					case 1: // orange
						color = Color.rgb(255, 165, 0);
						break;
					case 2: // yellow
						color = Color.YELLOW;
						break;
					case 3: // green
						color = Color.GREEN;
						break;
					case 4: // blue
						color = Color.BLUE;
						break;
					case 5: // purple
						color = Color.rgb(128, 0, 128);
						break;
					case 6: // brown
						color = Color.rgb(165, 42, 42);
						break;
					case 7: // black
						color = Color.BLACK;
						break;
					default:// bad!
						Log.e("Item Selected", "The color was not in the list.");
						break;
				}

				mSettings.setShowLifeStoryLinesColor(color);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// No change if nothing was actually selected
			}
		});

		mLifeStoryLinesSwitch = (Switch)findViewById(R.id.lifeStoryLinesSwitch);
		mLifeStoryLinesSwitch.setChecked(mSettings.showLifeStoryLines());
		mLifeStoryLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mSettings.setShowLifeStoryLines(isChecked);
			}
		});

		mFamilyTreeLinesSpinner = (Spinner)findViewById(R.id.familyLinesColor);
		mFamilyTreeLinesSpinner.setAdapter(colors);
		mFamilyTreeLinesSpinner.setSelection(mSettings.getFamilyStoryLinesSpinnerColor());
		mFamilyTreeLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int color = 0;
				switch (position) {
					case 0: // red
						color = Color.RED;
						break;
					case 1: // orange
						color = Color.rgb(255, 165, 0);
						break;
					case 2: // yellow
						color = Color.YELLOW;
						break;
					case 3: // green
						color = Color.GREEN;
						break;
					case 4: // blue
						color = Color.BLUE;
						break;
					case 5: // purple
						color = Color.rgb(128, 0, 128);
						break;
					case 6: // brown
						color = Color.rgb(165, 42, 42);
						break;
					case 7: // black
						color = Color.BLACK;
						break;
					default:// bad!
						Log.e("Item Selected", "The color was not in the list.");
						break;
				}

				mSettings.setShowFamilyStoryLinesColor(color);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		mFamilyTreeLinesSwitch = (Switch)findViewById(R.id.familyTreeLinesSwitch);
		mFamilyTreeLinesSwitch.setChecked(mSettings.showFamilyStoryLines());
		mFamilyTreeLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mSettings.setShowFamilyStoryLines(isChecked);
			}
		});


		mSpouseLinesSpinner = (Spinner)findViewById(R.id.spouseLinesColor);
		mSpouseLinesSpinner.setAdapter(colors);
		mSpouseLinesSpinner.setSelection(mSettings.getSpouseLinesSpinnerColor());
		mSpouseLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int color = 0;
				switch (position) {
					case 0: // red
						color = Color.RED;
						break;
					case 1: // orange
						color = Color.rgb(255, 165, 0);
						break;
					case 2: // yellow
						color = Color.YELLOW;
						break;
					case 3: // green
						color = Color.GREEN;
						break;
					case 4: // blue
						color = Color.BLUE;
						break;
					case 5: // purple
						color = Color.rgb(128, 0, 128);
						break;
					case 6: // brown
						color = Color.rgb(165, 42, 42);
						break;
					case 7: // black
						color = Color.BLACK;
						break;
					default:// bad!
						Log.e("Item Selected", "The color was not in the list.");
						break;
				}

				mSettings.setShowSpouseLinesColor(color);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// No change if nothing was actually selected
			}
		});

		mSpouseLinesSwitch = (Switch)findViewById(R.id.spouseLinesSwitch);
		mSpouseLinesSwitch.setChecked(mSettings.showSpouseLines());
		mSpouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mSettings.setShowSpouseLines(isChecked);
			}
		});

		final ArrayAdapter<CharSequence> mapTypes = ArrayAdapter.createFromResource(this, R.array.map_type_array, android.R.layout.simple_spinner_item);
		mapTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mMapTypeSpinner = (Spinner)findViewById(R.id.mapTypeSpinner);
		mMapTypeSpinner.setAdapter(mapTypes);
		mMapTypeSpinner.setSelection(mSettings.getMapSpinnerType());
		mMapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int mapType = 0;
				switch (position) {
					case 0: // normal map
						mapType = 1;
						break;
					case 1:
						mapType = 4;
						break;
					case 2:
						mapType = 2;
						break;
					case 3:
						mapType = 3;
						break;
					default:
						Log.e("MapType selection", "Got an invalid map type");
						break;
				}
				mSettings.setShowMapType(mapType);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		mReSyncData = (LinearLayout)findViewById(R.id.reSyncDataLayout);
		mReSyncData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DownloadPeople downloadPeople = new DownloadPeople();
				downloadPeople.execute();
			}
		});

		mLogout = (LinearLayout)findViewById(R.id.logoutLayout);
		mLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FamilyMap.getInstance().logout();
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});

	}


	public class DownloadPeople extends AsyncTask<URL, Integer, String> {
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
			if (FamilyMap.getInstance().isLoggedIn()) {
				FamilyMap.getInstance().addPeople(HttpClient.getInstance().getAllPeople());
				return "Done";
			} else {
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
			if (s == null) {
				Log.i("resyncToast", "people download fail!");
				Toast.makeText(getApplicationContext(), "Resync Failed. Please try again.", Toast.LENGTH_LONG).show();
			} else {
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
			if (FamilyMap.getInstance().isLoggedIn()) {
				FamilyMap.getInstance().addEvents(HttpClient.getInstance().getAllEvents());
				return "Done";
			} else {
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
			if (s == null) {
				Log.i("resyncToast", "people download fail!");
				Toast.makeText(getApplicationContext(), "Resync Failed. Please try again.", Toast.LENGTH_LONG).show();
			} else {
				Log.i("loginToast", "Events Data Downloaded! ");
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		}

	}


}

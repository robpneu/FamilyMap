package edu.byu.robpneu.cs240.familymap.ui;

import android.graphics.Color;
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

import edu.byu.robpneu.cs240.familymap.R;
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

			}
		});

		mLogout = (LinearLayout)findViewById(R.id.logoutLayout);
		mLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FamilyMap.getInstance().logout();
			}
		});

	}


}

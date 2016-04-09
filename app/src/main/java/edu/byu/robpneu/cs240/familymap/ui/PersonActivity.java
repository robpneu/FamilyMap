package edu.byu.robpneu.cs240.familymap.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.byu.robpneu.cs240.familymap.R;
import edu.byu.robpneu.cs240.familymap.model.FamilyMap;
import edu.byu.robpneu.cs240.familymap.model.Person;

public class PersonActivity extends AppCompatActivity {
	private Person mPerson;
	private FamilyMap mFamilyMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFamilyMap = FamilyMap.getInstance();
		setContentView(R.layout.activity_person);
		getSupportActionBar().setTitle("FamilyMap: Person Details");
		// TODO set up up button on menu bar
		String personID = getIntent().getStringExtra("PERSON_ID");
		mPerson = mFamilyMap.getPerson(personID);
	}
}

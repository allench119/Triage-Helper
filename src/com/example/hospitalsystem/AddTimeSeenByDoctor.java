package com.example.hospitalsystem;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddTimeSeenByDoctor extends Activity {
	
	public final static String HEALTH_CARD_NUM = "com.example.hospitalsystem.HEALTH_CARD_NUMBER";
	private Calendar currentTime;
	private SimpleDateFormat sdf;
	private EditText timeView;
	private PatientsDbHelper db;
	PatientSystem patientsystem;

	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database,
	 * link front-end views to back-end.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_time_seen_by_doctor);
		timeView = (EditText)findViewById(R.id.doctorVisitInput);
		db = new PatientsDbHelper(this.getApplicationContext());
		patientsystem = new PatientSystem(this.getApplicationContext());
		patientsystem.populateSystemFromDatabase(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
		    actionBar.setHomeButtonEnabled(false); // disable the button
		    actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
		    actionBar.setDisplayShowHomeEnabled(false); // remove the icon
		}
		getMenuInflater().inflate(R.menu.add_time_seen_by_doctor, menu);
		return true;
	}
	
	public void onClickCurrentTime(View view){
		currentTime = Calendar.getInstance();
		sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		timeView.setText(sdf.format(currentTime.getTime()));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Executed when save button is clicked.
	 * Retrieves the health card number entered in previous activity.
	 * Adds current time to the new record.
	 * Finds the patient in the patientSystem and adds the new record to the database.
	 * The activity finishes, and goes back to the previous activity.
	 * @param view Save button
	 */
	public void onClickSave(View view){
		String visitTime = timeView.getText().toString();
		Intent comingIntent = getIntent();
		String healthCard = comingIntent.getStringExtra(HEALTH_CARD_NUM);
		Time newRecord = new Time(visitTime);
		//add the new record, no need to change when modifying to database.
		Patient p = patientsystem.findPatientByHealthCard(healthCard);
		p.addDoctorVisit(newRecord);
		Log.e("hi", "OMFG IT'S SAVING");
		db.addTimes(newRecord, p.getNumRecords(), db.getPatientID(p));
		finish();		
	}
}

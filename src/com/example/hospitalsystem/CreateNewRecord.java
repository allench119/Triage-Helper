package com.example.hospitalsystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CreateNewRecord extends Activity {
	
	public final static String HEALTH_CARD_NUM = "com.example.hospitalsystem.HEALTH_CARD_NUMBER";
	private Calendar currentTime;
	private SimpleDateFormat sdf;
	private EditText timeView;
	private PatientsDbHelper db;

	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database,
	 * link front-end views to back-end.
	 * Get the arrival time entered by the nurse from previous activity.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_record);
		timeView = (EditText)findViewById(R.id.ArrivalTimeInput);
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
		getMenuInflater().inflate(R.menu.create_new_record, menu);
		return true;
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
	
	public void onClickCurrentTime(View view){
		currentTime = Calendar.getInstance();
		sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		timeView.setText(sdf.format(currentTime.getTime()));
	}
	
	/**
	 * Executed when Save button is clicked.
	 * Populates the system from database and looks for the patient in patientSystem by health card number.
	 * Adds a new record to the patient.
	 * Starts a new activity displayig the previous vital signs for the patient.
	 * The activity finishes.
	 * @param view Save button
	 */
	public void onClickSave(View view){
		String arrivalTime = timeView.getText().toString();
		Intent comingIntent = getIntent();
		String healthCard = comingIntent.getStringExtra(HEALTH_CARD_NUM);
		Record newRecord = new Record(new Time(arrivalTime));
		db = new PatientsDbHelper(getApplicationContext());
		PatientSystem patientSystem = new PatientSystem(getApplicationContext());
		patientSystem.populateSystemFromDatabase(this.getApplicationContext());
		Patient p = patientSystem.findPatientByHealthCard(healthCard);
		p.addRecord(newRecord);
		db.addRecord(newRecord, db.getPatientID(p));
		db.updatePatientUrgency(db.getPatientID(p), newRecord.getUrgency());
		Intent nextIntent = new Intent(this, VitalSignsUpdate.class);
		nextIntent.putExtra(HEALTH_CARD_NUM, healthCard);
		startActivity(nextIntent);
		finish();	
	}
}

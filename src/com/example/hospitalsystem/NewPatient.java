package com.example.hospitalsystem;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewPatient extends Activity {
	private PatientsDbHelper db;

	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database,
	 * link front-end views to back-end.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_patient);
		db = new PatientsDbHelper(this.getApplicationContext());
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
		getMenuInflater().inflate(R.menu.new_patient, menu);
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
	
	/**
	 * Executed when save button is clicked. 
	 * Retrieve all the data collected for a new patient, including name, date of birth, and health card number.
	 * Checks the health card number for duplicated patients.
	 * A feedback will be provided if the patient already exists.
	 * @param view Confirm button
	 */
	public void onClick(View view){
		EditText patientName = (EditText) findViewById(R.id.newPatientName);
		EditText patientDOB = (EditText) findViewById(R.id.patientDOB);
		EditText patientHealtheCardNumber = (EditText) findViewById(R.id.newPatientHCN);
		
		String nameInput = patientName.getText().toString();
		String DOBInput = patientDOB.getText().toString();
		String healthCardNumberInput= patientHealtheCardNumber.getText().toString();
		
		
		String[] parts = DOBInput.split("/");
		Integer month = Integer.parseInt(parts[0]);
		Integer day = Integer.parseInt(parts[1]);
		Integer year = Integer.parseInt(parts[2]);
		
		
		Time DOB = new Time(year, month, day);
	
		Patient newPatient = new Patient(nameInput, healthCardNumberInput, DOB);
		
		PatientSystem patientSystem = new PatientSystem(getApplicationContext());
		patientSystem.populateSystemFromDatabase(getApplicationContext());
		if(patientSystem.findPatientByHealthCard(healthCardNumberInput) != null){
			TextView errorMessage = (TextView) findViewById(R.id.errorMessage);
			errorMessage.setText("A patient with that health card number already exists!");
		}
		else {
			patientSystem.addPatient(newPatient);
			db.addPatient(newPatient);
			finish();
		}
		}
}

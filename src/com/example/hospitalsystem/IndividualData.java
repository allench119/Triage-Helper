package com.example.hospitalsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity for viewing individual patient's personally identifying data.
 * Also serves as a hub to allow access to updating vital signs record and viewing previous entries.
 */
public class IndividualData extends Activity {

	public final static String HEALTH_CARD_NUM = "com.example.hospitalsystem.HEALTH_CARD_NUMBER";
	public final static String TRACE_BACK = "com.example.hospitalsystem.TRACE_BACK";
	public final static String PERMISSION = "com.example.hospitalsystem.PERMISSION";
	
	Button update;
	Button viewPrevRecord;
	String healthCardNumber = null;
	TextView errMessage;
	boolean hasRecord;

	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database, 
	 * link front-end views to back-end. Set up UI for either Nurse or Physician, which contains different interface layouts
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_data);

		Intent intent = getIntent();
		String healthCardNumber = intent.getStringExtra(LookUpPatient.HEALTH_CARD_NUM);
		String userLevel = intent.getStringExtra(PERMISSION);
		this.healthCardNumber = healthCardNumber;
		PatientSystem patientSystem = new PatientSystem(getApplicationContext());
		patientSystem.populateSystemFromDatabase(getApplicationContext());

		Button updateRecord = (Button)findViewById(R.id.updateRecord);
		Button addTimeSeen = (Button)findViewById(R.id.addTimeSeenByDoctor);
		Button newPresciption = (Button)findViewById(R.id.individualNewPrescription);
		
		if (userLevel.equals("Physician")){
			updateRecord.setVisibility(View.GONE);
			addTimeSeen.setVisibility(View.GONE);
		}

		if (userLevel.equals("Nurse")){
			newPresciption.setVisibility(View.GONE);
		}

		TextView patientNameLarge= (TextView)findViewById(R.id.individualTextView2);
		patientNameLarge.setText(patientSystem.findPatientByHealthCard(healthCardNumber).getName());

		TextView patientNameSmall= (TextView)findViewById(R.id.name);
		patientNameSmall.setText("Name: " + patientSystem.findPatientByHealthCard(healthCardNumber).getName());

		TextView IndividualDOBView= (TextView)findViewById(R.id.dob);
		IndividualDOBView.setText("Date of Birth: " + patientSystem.findPatientByHealthCard(healthCardNumber).getDob());

		TextView IndividualHCNView= (TextView)findViewById(R.id.healthCardNumber);
		IndividualHCNView.setText("Health Card Number: " + healthCardNumber);

		errMessage = (TextView) findViewById(R.id.errMessage);
		hasRecord = patientSystem.findPatientByHealthCard(healthCardNumber).hasRecord();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.individual_data, menu);
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
	 * Called upon clicking the update patient record button.
	 * Passes on the health card number of the current patient as an extra and start the VitalSignsUpdate activity.
	 * @param view
	 */
	public void clickUpdate(View view) {
		if(hasRecord) {
			Intent Intent = new Intent(this, VitalSignsUpdate.class);
			Intent.putExtra(HEALTH_CARD_NUM,healthCardNumber);
			startActivity(Intent);
		}
		else {
			errMessage.setText("This patient does not have a visit record yet! Please add one.");
		}
	}


	/**
	 * Called upon clicking the update view previous records button.
	 * Passes on the health card number of the current patient as an extra and start the PreviousRecord activity.
	 * @param view
	 */
	public void clickPrevRecord(View view){
		Intent Intent = new Intent(this, PreviousRecord.class);
		Intent.putExtra(HEALTH_CARD_NUM,healthCardNumber);
		startActivity(Intent);
	}

	public void OnClickAddVisit(View view){
		if(hasRecord) {
			Intent Intent = new Intent(this, AddTimeSeenByDoctor.class);
			Intent.putExtra(HEALTH_CARD_NUM,healthCardNumber);
			startActivity(Intent);
		}
		else {
			errMessage.setText("This patient does not have a visit record yet! Please add one.");
		}
	}

	public void onClickPrescription(View view){
		if(hasRecord) {
			Intent intent = new Intent(this, AddPrescription.class);
			intent.putExtra(HEALTH_CARD_NUM, healthCardNumber);
			startActivity(intent);
		}
		else {
			errMessage.setText("This patient does not have a visit record yet! Please add one.");
		}
	}

}

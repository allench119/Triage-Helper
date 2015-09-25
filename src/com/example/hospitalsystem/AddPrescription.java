package com.example.hospitalsystem;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddPrescription extends Activity {
	
	public final static String HEALTH_CARD_NUM = "com.example.hospitalsystem.HEALTH_CARD_NUMBER";

	private PatientSystem patientSystem;
	private Button save;
	private EditText medicationInput;
	private EditText instructionInput;
	private String healthCardNumber;
	private PatientsDbHelper db; 
	
	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database, 
	 * link front-end views to back-end. 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_prescription);
		db = new PatientsDbHelper(this.getApplicationContext());
		patientSystem = new PatientSystem(this.getApplicationContext());
		patientSystem.populateSystemFromDatabase(getApplicationContext());
		save = (Button) findViewById(R.id.prescriptionSave);
		save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onClickSave();
			}
		});
		instructionInput = (EditText) findViewById(R.id.instructionsInput);
		medicationInput = (EditText) findViewById(R.id.medicationInput);
		healthCardNumber = getIntent().getStringExtra(HEALTH_CARD_NUM);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_prescription, menu);
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
	 * Executed when users click the save button. Save the new prescription to the patient that has been looked up,
	 * and pop up a notification indicates that the prescription has been successfully added. 
	 */
	private void onClickSave() {
		Patient p = patientSystem.findPatientByHealthCard(healthCardNumber);
		String medication = medicationInput.getText().toString();
		String instructions = instructionInput.getText().toString();
		
		Prescription prescription = new Prescription(medication, instructions, new Time());
		p.addPrescription(prescription);
		db.addPrescription(prescription, db.getPatientID(p), p.getNumRecords());

		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("Your patient has been prescribed!")
		        .setContentText("Congratulations, you are one step closer to saving and enhancing this person's life." +
		        		" Feel better about yourself.");
		NotificationManager mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, mBuilder.build());
		finish();
	}
}

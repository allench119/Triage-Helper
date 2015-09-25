package com.example.hospitalsystem;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Activity for searching a patient in the database by his/her health card number.
 */
public class LookUpPatient extends Activity {

	public final static String HEALTH_CARD_NUM = "com.example.hospitalsystem.HEALTH_CARD_NUMBER";
	public final static String TRACE_BACK = "com.example.hospitalsystem.TRACE_BACK";
	public final static String PERMISSION = "com.example.hospitalsystem.PERMISSION";
	PatientSystem patientSystem;
	EditText healthCardNumber;
	TextView inputFeedback;
	ListView lookUpList;
	List<Patient> patients;
	Button search;
	
	
	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database, 
	 * link front-end views to back-end.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_look_up_patient);
		inputFeedback = (TextView) findViewById(R.id.LookUpInputFeedback);
		healthCardNumber = (EditText) findViewById(R.id.LookUpEditText1);
		patientSystem = new PatientSystem(getApplicationContext());
		patientSystem.populateSystemFromDatabase(getApplicationContext());

		lookUpList = (ListView) findViewById(R.id.lookUpList);
		lookUpList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onClickItem(position);
			}

		});
		search = (Button) findViewById(R.id.lookUp);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickSearch();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.look_up_patient, menu);
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
	 * executed when click on an entry, which stands for a patient. Jump to corresponding activity after check if a patient's record exists.
	 * @param position the position of the entry clicked.
	 */
	public void onClickItem(int position) {
		String traceBack = getIntent().getStringExtra(TRACE_BACK);
		String userLevel = getIntent().getStringExtra(PERMISSION);

		Patient curPatient = patients.get(position);
		Intent nextIntent = null;

		if(traceBack.equals("Prescription")){
			if(curPatient.hasRecord()) {
				nextIntent = new Intent(this, AddPrescription.class);
			}
			else {
				inputFeedback.setText("Patient " + curPatient.getName() + " does not have a record yet! Add one first.");
				return;
			}
		}
		if(traceBack.equals("LookUpPatient")){
			nextIntent = new Intent(this, IndividualData.class);
		}
		if(traceBack.equals("CreateNewRecord")){
			nextIntent = new Intent(this, CreateNewRecord.class);
		}
		nextIntent.putExtra(HEALTH_CARD_NUM, curPatient.getHealthCardNumber());
		nextIntent.putExtra(PERMISSION, userLevel);
		startActivity(nextIntent);
		inputFeedback.setText("");

	}


	/**
	 * Executed upon clicking the confirm button. Checks the patient database for the specified patient,
	 * pushes it as an extra onto the IndividualData activity, then starts the IndividualData activity if a match is found.
	 * Modifies a textView to display a user friendly error message if patient is not found. 
	 */
	public void onClickSearch() {
		//Get health card number from the text field
		String healthCardNum = healthCardNumber.getText().toString();

		Intent intent = getIntent();
		String traceBack = intent.getStringExtra(TRACE_BACK);
		String userLevel = intent.getStringExtra(PERMISSION);
		Patient curPatient = patientSystem.findPatientByHealthCard(healthCardNum);
	

		if (curPatient != null) {

			Intent nextIntent = null;

			if(traceBack.equals("Prescription")){
				if(curPatient.hasRecord()) {
					nextIntent = new Intent(this, AddPrescription.class);
				}
				else {
					inputFeedback.setText("Patient " + curPatient.getName() + " does not have a record yet! Add one first.");
					return;
				}
			}
			if(traceBack.equals("LookUpPatient")){
				nextIntent = new Intent(this, IndividualData.class);
			}
			if(traceBack.equals("CreateNewRecord")){
				nextIntent = new Intent(this, CreateNewRecord.class);
			}
			nextIntent.putExtra(HEALTH_CARD_NUM, healthCardNum);
			nextIntent.putExtra(PERMISSION, userLevel);
			startActivity(nextIntent);
			inputFeedback.setText("");

		}
		else {
			patients = patientSystem.getPatientListWithHCN(healthCardNum);
			ListAdapter adapter = new ArrayAdapter<Patient> (
					this,
					android.R.layout.simple_list_item_1,
					patients);
			lookUpList.setAdapter(adapter);
			inputFeedback.setText(R.string.hcnNotFound);
		}
	}

}

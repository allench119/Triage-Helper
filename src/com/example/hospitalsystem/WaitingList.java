package com.example.hospitalsystem;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class WaitingList extends Activity implements OnItemClickListener {
	
	PatientSystem patientSystem;
	ListView waitingList;
	List<Patient> patients;
	

	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database, 
	 * link front-end views to back-end. Set up the ListView for patients list. 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting_list);
		PatientSystem patientSystem = new PatientSystem(getApplicationContext());
		patientSystem.populateSystemFromDatabase(getApplicationContext());
		
		patients = patientSystem.getPatientListNotSeenByDoctor();
		waitingList = (ListView) findViewById(R.id.waitingList);
		ListAdapter adapter = new ArrayAdapter<Patient> (
				this,
				android.R.layout.simple_list_item_1,
				patients);
		waitingList.setAdapter(adapter);
		waitingList.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.waiting_list, menu);
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

	@Override
	/**
	 * Executed when one of the entries in the patients list is clicked. Start the activity that 
	 * displays the patient's data.
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
        Intent intent = new Intent(this, IndividualData.class);
        intent.putExtra(LookUpPatient.PERMISSION, "Nurse");
        intent.putExtra(LookUpPatient.HEALTH_CARD_NUM, patients.get(position).getHealthCardNumber());
        startActivity(intent);
	}
}

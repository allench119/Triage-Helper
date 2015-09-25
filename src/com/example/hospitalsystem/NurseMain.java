package com.example.hospitalsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NurseMain extends Activity {
	public final static String TRACE_BACK = "com.example.hospitalsystem.TRACE_BACK";
	public final static String PERMISSION = "com.example.hospitalsystem.PERMISSION";

	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database,
	 * link front-end views to back-end.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nurse_main, menu);
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
	 * Executed when the nurse presses New Patient button.
	 * Starts another activity that allows nurse to create a new patient.
	 * @param view New Patient button
	 */
	public void onClickNewPatient(View view){
		Intent intent = new Intent(this, NewPatient.class);
		String userLevel = "Nurse";
		intent.putExtra(PERMISSION, userLevel);
		startActivity(intent);
	}
	
	/**
	 * Executed when the nurse presses Waiting Patient List button.
	 * Starts another activity that displays all the patients waiting with their urgency points.
	 * @param view Waiting Patient List button
	 */
	public void onClickPaitentList(View view){
		Intent intent = new Intent(this, WaitingList.class);
		String userLevel = "Nurse";
		intent.putExtra(PERMISSION, userLevel);
		startActivity(intent);
	}
	
	/**
	 * Executed when the nurse presses Look Up Patient button.
	 * Starts another activity that allows the nurse to look up patient by health card number.
	 * @param view Look Up Patient button
	 */
	public void onClickLookUpPatient(View view){
		Intent intent = new Intent(this, LookUpPatient.class);
		String userLevel = "Nurse";
		String traceBack = "LookUpPatient";
		intent.putExtra(PERMISSION, userLevel);
		intent.putExtra(TRACE_BACK, traceBack);
		startActivity(intent);
	}
	
	/**
	 * Executed when the nurse presses Create New Record button.
	 * Starts another activity that allows the nurse to create a new record for a patient with arrival time.
	 * @param view Create New Record button
	 */
	public void onClickCreateNewRecord(View view){
		Intent intent = new Intent(this, LookUpPatient.class);
		String userLevel = "Nurse";
		String traceBack = "CreateNewRecord";
		intent.putExtra(PERMISSION, userLevel);
		intent.putExtra(TRACE_BACK, traceBack);
		startActivity(intent);
	}
}

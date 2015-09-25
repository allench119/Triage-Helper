package com.example.hospitalsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PhysicianMain extends Activity {
	
	public final static String TRACE_BACK = "com.example.hospitalsystem.TRACE_BACK";
	public final static String PERMISSION = "com.example.hospitalsystem.PERMISSION";
	@Override
	/**
	 * Executed when this activity first creates. Set up contentView.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.physician_main, menu);
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
	 * Executed if the Look Up Patient button is clicked, start LookUpPatient activity,
	 * with the user level("Physician") and the intent("Look up a patient") as String
	 * extras.
	 */
	public void onClickLookUp(View view){
		Intent intent = new Intent(this, LookUpPatient.class);
		String traceBack = "LookUpPatient";
		String userLevel = "Physician";
		intent.putExtra(TRACE_BACK, traceBack);
		intent.putExtra(PERMISSION, userLevel);
		startActivity(intent);
	}
	
	/**
	 * Executed if the Look Up Patient button is clicked, start LookUpPatient activity,
	 * with the user level("Physician") and the intent("add new prescription") as String
	 * extras.
	 */
	public void onClickPrescription(View view){
		Intent intent = new Intent(this, LookUpPatient.class);
		String traceBack = "Prescription";
		String userLevel = "Physician";
		intent.putExtra(TRACE_BACK, traceBack);
		intent.putExtra(PERMISSION, userLevel);
		startActivity(intent);
	}
}

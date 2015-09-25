package com.example.hospitalsystem;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * An activity for viewing individual entries in detail.
 */
public class VisitRecord extends Activity{
	
	private ViewFlipper viewFlipper;
    private float lastX;
    private Record thisRecord;
	protected String healthCardNumber;
	
    public final static String HEALTH_CARD_NUM = "com.example.hospitalsystem.HEALTH_CARD_NUMBER";
	public final static String TRACE_BACK = "com.example.hospitalsystem.TRACE_BACK";
	public final static String PERMISSION = "com.example.hospitalsystem.PERMISSION";
	public final static String RECORD_POSITION = "com.example.hospitalsystem.PreviousRecord.RECORD_POSITION";
	public final static String ROW_ID = "com.example.hospitalsystem.PreviousRecord.ROW_ID";
	public final static String MESSAGE = "com.example.hospitalsystem.MESSAGE";
	public final static String MESSAGE2 = "com.example.hospitalsystem.MESSAGE2";
	
	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database, 
	 * link front-end views to back-end. Set up three ListViews for the data of vital signs, medical history and 
	 * prescriptions. Set up a ViewFlipper to contain all three lists.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visit_record);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		
		Intent intent = getIntent();
		int position = intent.getIntExtra(PreviousRecord.RECORD_POSITION, 0);
		
		healthCardNumber = intent.getStringExtra(HEALTH_CARD_NUM);
		
		PatientSystem patientSystem = new PatientSystem(getApplicationContext());
		patientSystem.populateSystemFromDatabase(getApplicationContext());
		
		TextView nameDisplay = (TextView)findViewById(R.id.recordsName);
		TextView timeDisplay = (TextView)findViewById(R.id.recordsTime);
		nameDisplay.setText(patientSystem.findPatientByHealthCard(healthCardNumber).getName());
		timeDisplay.setText(patientSystem.findPatientByHealthCard(healthCardNumber).getArrivalTime().toString());
		thisRecord = patientSystem.findPatientByHealthCard(healthCardNumber).getRecord(position);
		
		ArrayList<String> VitalSignsTimeRecords = thisRecord.getVitalSignsRecordsStrings();
		ListView VitalSignsList= (ListView) findViewById(R.id.VitalSignsList);
		ListAdapter adapter1 = new ArrayAdapter<String> (
				this,
				android.R.layout.simple_list_item_1,
				VitalSignsTimeRecords	);
		VitalSignsList.setAdapter(adapter1); 
		
		
		ArrayList<String> SeenTimeRecords = thisRecord.getDoctorVisitsTimesStrings();
		ListView TimeList= (ListView) findViewById(R.id.TimeList);
		ListAdapter adapter2 = new ArrayAdapter<String> (
				this,
				android.R.layout.simple_list_item_1,
				SeenTimeRecords	);
		TimeList.setAdapter(adapter2); 
		
		
		ArrayList<String> PrescriptionsRecords = thisRecord.getPrescriptionsRecordsStrings();
		ListView PrescriptionsList= (ListView) findViewById(R.id.PrescriptionsList);
		ListAdapter adapter3 = new ArrayAdapter<String> (
				this,
				android.R.layout.simple_list_item_1,
				PrescriptionsRecords	);
		PrescriptionsList.setAdapter(adapter3); 
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visit_record, menu);
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
	 * Constantly being executed when the activity is active. Keep track of users' touch activities,
	 * if catch a swipe event, it will make the ViewFlipper flip views to intented dirction. 
	 */
	public boolean onTouchEvent(MotionEvent touchevent) {
		switch (touchevent.getAction()) {
	        
			case MotionEvent.ACTION_DOWN: 
				lastX = touchevent.getX();
				break;
			case MotionEvent.ACTION_UP: 
				float currentX = touchevent.getX();
				// Handling left to right screen swap.
				if (lastX < currentX) {
	            	
					// If there aren't any other children, just break.
					if (viewFlipper.getDisplayedChild() == 0)
						break;
	                
					// Next screen comes in from left.
					viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
					// Current screen goes out from right. 
					viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
	                
					// Display next screen.
					viewFlipper.showNext();
				}
	                                     
				// Handling right to left screen swap.
				if (lastX > currentX) {
	            	 
					// If there is a child (to the left), just break.
					if (viewFlipper.getDisplayedChild() == 1)
						break;
	    
					// Next screen comes in from right.
					viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
					// Current screen goes out from left. 
					viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
	                 
					// Display previous screen.
					viewFlipper.showPrevious();
				}
				break;
			}
			return true;
	    }
	
	public void onItemClick(AdapterView<?> l, View v, int position_inner, long id_inner)  {
        Intent intent = new Intent(VisitRecord.this, PrescriptionDisplay.class);
        String message = thisRecord.getArrivalTime().toString();
        intent.putExtra(MESSAGE, message);
        startActivity(intent);
	}
}

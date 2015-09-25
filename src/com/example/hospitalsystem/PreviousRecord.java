package com.example.hospitalsystem;

import java.util.ArrayList;
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
import android.widget.TextView;

/**
 * This activity is for viewing a list of dates of the previous health record updates.
 * Also serves as a menu for selecting which previous health record update you would like to view in detail.
 */
public class PreviousRecord extends Activity implements OnItemClickListener{
	
	public final static String HEALTH_CARD_NUM = "com.example.hospitalsystem.HEALTH_CARD_NUMBER";
	public final static String TRACE_BACK = "com.example.hospitalsystem.TRACE_BACK";
	public final static String PERMISSION = "com.example.hospitalsystem.PERMISSION";
	public final static String RECORD_POSITION = "com.example.hospitalsystem.PreviousRecord.RECORD_POSITION";
	public final static String ROW_ID = "com.example.hospitalsystem.PreviousRecord.ROW_ID";
	
	String healthCardNumber = null;
	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database, 
	 * link front-end views to back-end. 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previous_record);
		
		Intent intent = getIntent();
		this.healthCardNumber = intent.getStringExtra(HEALTH_CARD_NUM);
		PatientSystem patientSystem = new PatientSystem(getApplicationContext());
		patientSystem.populateSystemFromDatabase(getApplicationContext());
		
		ArrayList<String> timeRecords = patientSystem.findPatientByHealthCard(this.healthCardNumber).getArrivalTimesStrings();
		ListView RecordList= (ListView) findViewById(R.id.RecordList);
		ListAdapter adapter = new ArrayAdapter<String> (
				this,
				android.R.layout.simple_list_item_1,
				timeRecords	);
		RecordList.setAdapter(adapter); 
		
		TextView PreviousRecordtextView2 = (TextView)findViewById(R.id.PreviousRecordtextView2);
		PreviousRecordtextView2.setText(patientSystem.findPatientByHealthCard(this.healthCardNumber).getName());
		
		RecordList.setOnItemClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.previous_record, menu);
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
	 * Executed upon clicking of any date. Pushes the id of the date clicked as well as the 
	 * health card number of the current patient, then starts the VisitRecord activity.
	 */
	public void onItemClick(AdapterView<?> l, View v, int position, long id)  {
        Intent intent = new Intent();
        intent.setClass(this, VisitRecord.class);
        intent.putExtra(RECORD_POSITION, position);
        intent.putExtra(ROW_ID, id);
        intent.putExtra(HEALTH_CARD_NUM,this.healthCardNumber);
        startActivity(intent);
}
}

package com.example.hospitalsystem;


import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The title screen, which contains a button to begin the program and search patient by health card number.
 * Also automatically loads the current patient database and serializes it for later use by other activites.
 */
public class MainActivity extends Activity implements OnClickListener {

	private PatientSystem patientSystem;
	private Users users;
	private File f; 
	private File userData;
	private PatientsDbHelper db; 
	
	@Override
	/**
	 * Executed when this activity first creates. Set up contentView and populate patientSystem from database,
	 * link front-end views to back-end.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		f = new File(this.getApplicationContext().getFilesDir().getAbsolutePath() + "/patient_records.txt");
		userData = new File(this.getApplicationContext().getFilesDir().getAbsolutePath() + "/passwords.txt");
		patientSystem = new PatientSystem(this.getApplicationContext());
		
		boolean db_status = patientSystem.checkDatabase(this.getApplicationContext());
		if (db_status == false) {
			users = new Users(userData);
			db = patientSystem.populateDatabaseFromTxt(f, users);
			System.out.println("Loaded from txt");
			Log.i("BLAH", "Loaded from text file");
		}
		else {
			db = patientSystem.db;
			patientSystem.populateSystemFromDatabase(this.getApplicationContext());
			List <User> u = db.getAllUsers();
			users = new Users(u);
			System.out.println("Loaded from existing database");
			Log.i("BLAH", "Loaded from existing database");
		}
		
	}
	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Executed when clicking the log in button. Check the username and password with the files in the database.
	 * If the user is a nurse, then the corresponding activity for NurseMain will start.
	 * If the user is a physician, the the corresponding activity for PhysicianMain will start.
	 * If username or password does not exist in the file in the system, a feedback will be provided.
	 */
	@Override
	public void onClick(View v) {
		//get the username and password
		EditText usernameView = (EditText)findViewById(R.id.MainUserName);
		String username = usernameView.getText().toString();
		
		EditText passwordView = (EditText)findViewById(R.id.MainPassword);
		String password = passwordView.getText().toString();
		TextView loginFeedback = (TextView)findViewById(R.id.LoginFeedback);
		
		User user = users.login(username, password);
		
		if(user != null){
			if(user.isPhysician()){
				Intent intentPhy = new Intent (this, PhysicianMain.class);
				startActivity(intentPhy);
				loginFeedback.setText("");
			}
			else{
					Intent intentNurse = new Intent (this, NurseMain.class);
					startActivity(intentNurse);
					loginFeedback.setText("");
			}
			usernameView.setText("");
			passwordView.setText("");
		}else{
			loginFeedback.setText(R.string.MainLoginFeedBack);
		}
	}

}

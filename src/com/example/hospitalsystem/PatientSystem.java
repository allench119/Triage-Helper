package com.example.hospitalsystem;

import java.io.*;
import java.util.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class PatientSystem{

	/** The list of Patient Objects in the Patient System. */
	protected List<Patient> patients;
	
	/**
	 * Name of the database file
	 */
	protected final String DB_NAME = "PatientsDatabase";
	
	/**
	 * 
	 */
	public PatientsDbHelper db;
	
	protected SQLiteDatabase database; 

	/** 
	 * Constructs a Patient System with an ArrayList of Patient objects.
	 */
	public PatientSystem() {
		patients = new ArrayList<Patient>();
	}
	
	/**
	 * Make a PatientSystem with a Database
	 */
	public PatientSystem(Context context) {
		patients = new ArrayList<Patient>();
		db = new PatientsDbHelper(context);
	}

	/**
	 * Returns the List of Patients from this Patient System.
	 * @return List of Patients from this Patient System.
	 */
	public List<Patient> getPatientsList() {
		return this.patients;
	}

	/**
	 * The list of patients sorted by descending urgency not yet seen by a doctor.
	 * @return A list of all patients not yet seen by a doctor sorted by decreasing urgency.
	 */
	public List<Patient> getPatientListNotSeenByDoctor() {
		List<Patient> sortedList = new ArrayList<Patient>();
		//Make the list of patients not yet seen by doctor.
		for(Patient p: patients) {
			if (p.notSeenByDoctor()){
				if(sortedList.size() == 0) {
					sortedList.add(p);
				}
				else{
					for (int i = sortedList.size() - 1; i >= 0; i--) {
						if(sortedList.get(i).getUrgency() >= p.getUrgency()) {
							sortedList.add(i+1, p);
							break;
						}
					}
				}
			}
		}
		return sortedList;
	}
	
	/**
	 * Gets a list of patients whose health card numbers begin with s.
	 * @param s The string with the health card number being searched for.
	 * @return A list of patients whose health card number begins with s.
	 */
	public List<Patient> getPatientListWithHCN (String s) {
		List<Patient> returnList = new ArrayList<Patient>();
		for (Patient p: patients) {
			if(p.getHealthCardNumber().startsWith(s)) {
				returnList.add(p);
			}
		}
		return returnList;
	}
	
	
	/***
	 * Reads a file from a specified directory and saves it in a String.
	 * @param dir A file.
	 * @throws FileNotFoundException
	 */
	


	
	/**
	 *  Appends patient p to this patientSystem.
	 * @param p The patient to be added.
	 */
	public void addPatient(Patient p) {
		patients.add(patients.size(), p);
	}
	

	/***
	 * Populates the Patient System with Patient Objects from a file.
	 * @param dir A file.
	 * @throws FileNotFoundException
	 */
	public void populateSystem(File dir) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(dir));
		String[] record;
		while (scanner.hasNextLine()) {
			record = scanner.nextLine().split(",");
			String healthCard = record[0];
			String name = record[1];
			String[] dob = record[2].split("-");
			Time time = new Time(Integer.parseInt(dob[0]), Integer.parseInt(dob[1]), Integer.parseInt(dob[2]));
			Patient patient = new Patient(name, healthCard, time);
			this.patients.add(patient);
		}  
		scanner.close();
	}

	/**
	 * Given a Patient and a VitalSigns object, the Patient system will add 
	 * the VitalSigns object to that Patient's vitalSignsRecord.
	 * @param patient A Patient object.
	 * @param vitals The VitalSigns object to be added to Patient.
	 */
	public void updatePatientVitals(Patient patient, VitalSigns vitals) {
		patient.addVitalSigns(vitals);
	}


	/**
	 * Serializes this Patient System to a file.
	 * @param dir a File.
	 * @throws FileNotFoundException
	 */
	public void serialize(File dir) throws FileNotFoundException {
		FileOutputStream stream = new FileOutputStream(dir + "/patient_system.ser");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(stream);
			oos.writeObject(this);
			oos.close();
		}catch(IOException i)
		{
			i.printStackTrace();
		}

	}


	/**
	 * Deserializes a file with a serialized Patient System and returns that Patient System.
	 * @param dir a file.
	 * @return a Patient System.
	 * @throws FileNotFoundException
	 */
	public static PatientSystem deserialize(File dir) throws FileNotFoundException {
		PatientSystem patientSystem = null;
		FileInputStream fis = new FileInputStream(dir + "/patient_system.ser");
		try {
			ObjectInputStream ois = new ObjectInputStream(fis);
			try {
				patientSystem = (PatientSystem) ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			ois.close();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Patient currentPatient = null;
		for (int i = 0; i < patientSystem.getPatientsList().size(); i++){
			currentPatient = patientSystem.getPatientsList().get(i);
			try{
				currentPatient = patientSystem.getPatientsList().get(i).deserialize(dir);
			}catch (FileNotFoundException e){
				e.printStackTrace();
			}
			patientSystem.patients.set(i,currentPatient);
		}


		return patientSystem;
	}
	/**
	 * Returns a toString representation of this Patient System.
	 * @return a toString representation of this Patient System.
	 */
	public String toString() {
		String returnString = "";
		for (Patient patient: this.patients) {
			returnString += patient.toString() + "\n";
		}
		return returnString;

	}

	/***
	 * Looks up a Patient in this Patient System given a health card number.
	 * @param healthCardNumber a Patient's Health Card Number.
	 * @return patient a Patient object.
	 * @throws FileNotFoundException
	 */
	public Patient findPatientByHealthCard(String healthCardNumber) {
		for (int i = 0; i < this.patients.size(); i++) {
			if (patients.get(i).healthCardNumberEquals(healthCardNumber)) {
				Patient patient = patients.get(i);
				return patient;
			}
		}
		return null;
	}
	
	public boolean checkDatabase(Context context) {
		File dbFile = context.getDatabasePath(DB_NAME);
		if (dbFile.exists()) 
			return true;
		else {
			return false;
		}
	}

	public PatientsDbHelper populateDatabaseFromTxt(File dir, Users users) {
		try {
			this.populateSystem(dir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (Patient patient: this.patients) {
			long patient_id = db.addPatient(patient);
		}
		for (User user: users.getUsers()) {
			db.addUser(user);
		}
		db.closeDB();
		return this.db;
	}
	
	public void populateSystemFromDatabase(Context context) { 
		this.patients = db.getAllPatients();
	}
}

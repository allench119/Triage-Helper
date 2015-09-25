package com.example.hospitalsystem;

import java.io.*;
import java.util.*;

public class PatientSystem implements Serializable {

	/** This Patient System's serialization UID. */
	private static final long serialVersionUID = -8472591340772528718L;
	
	/** The list of Patient Objects in the Patient System. */
	protected List<Patient> patients;
    
	/** 
	 * Constructs a Patient System with an ArrayList of Patient objects.
	 */
	public PatientSystem() {
		patients = new ArrayList<Patient>();
	}

	/**
	 * Returns the List of Patients from this Patient System.
	 * @return List of Patients from this Patient System.
	 */
	public List<Patient> getPatientsList() {
		return this.patients;
	}

	/***
	 * Reads a file from a specified directory and saves it in a String.
	 * @param dir A file.
	 * @throws FileNotFoundException
	 */
	public String readFile(File dir) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(dir));
		String text_file = "";
		while (scanner.hasNextLine()) {
			text_file += scanner.nextLine() + "\n";
		}
		scanner.close();
		return text_file;
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
}

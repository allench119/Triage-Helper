package com.example.hospitalsystem;

import java.util.List;


/**
 * A user class, which contains a username and password as well as 
 * whether the user is a physician or a nurse.
 */
public class User {
	
	protected String password;
	protected String username;
	protected boolean isPhysician;
	
	public User(String username, String password, String permission){
		this.username = username;
		this.password = password;
		this.isPhysician = false;
		if (permission.equals("physician")){
			this.isPhysician = true;
		}
	}
	
	/**
	 * Return if this user has the username and password specified. False otherwise.
	 * @param username The username.
	 * @param password The password.
	 * @return True if both username and password are correct for this user. False otherwise.
	 */
	public boolean validCredentials(String username, String password){
		if (this.username.equals(username) && this.password.equals(password)){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns if this user is a physician.
	 * @return If this user is a physician (can write patients' prescriptions).
	 */
	public boolean isPhysician() {
		return this.isPhysician;
	}
	
	/**
	 * Return "Physician" if this user is a physician, "Nurse" otherwise.
	 * @return "Physician" if this user is a physician, "Nurse" otherwise.
	 */
	public String getStringUserLevel() {
		if(isPhysician){
			return "Physician";
		}
		else {
			return "Nurse";
		}
	}
	
	//The patient object is likely created, and may even be added to the patient system class in an activity rather than here.
	/**
	 * Stores new patient p into PatientSystem system.
	 * @param system The system the new patient will be stored into.
	 * @param p The new patient to be created.
	 */
	public void createPatient(PatientSystem system, Patient p) {
		system.addPatient(p);
	}
	
	//Also likely handled within the patient search activity
	public Patient lookUpPatient(PatientSystem system, String healthCardNumber) {
		return system.findPatientByHealthCard(healthCardNumber);
	}
	
	//This is probably handled by the activities
	public void recordData(PatientSystem system, Patient p, float temp, int heartRate, int systolic, int diastolic, Time time) {
		VitalSigns data = new VitalSigns(temp, systolic, diastolic, heartRate, time);
		system.updatePatientVitals(p, data);
	}
	
	public void recordTimeSeenByDoctor(Patient p, Time time) {
		p.addDoctorVisit(time);
	}
	
	public List <Patient> getPatientListNotSeenByDoctor(PatientSystem system) {
		return system.getPatientListNotSeenByDoctor();
	}
	

	public void recordPrescriptionData(Patient p) {
		if (!isPhysician) {
			return;
		}
	}
	
}

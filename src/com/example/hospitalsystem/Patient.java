package com.example.hospitalsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

public class Patient {


	/** Name of this Patient. */
	private String name;

	/** Health card of this Patient. */
	private String healthCardNumber;

	/** Date of birth of this Patient. */
	private Time dob;

	/** An integer between 0-4 inclusive, representing the urgency level according to this hospital's policy.
	 * A value of 5 indicates a patient with no vital sign measurements recorded.*/
	private int urgency;


	private List<Record> records;

	/**
	 * Constructs a Patient object with name, date of birth, health card number,
	 * and arrival time.
	 * @param name This Patient's name.
	 * @param healthCardNumber This Patient's health card number.
	 * @param dob This Patient's date of birth.
	 * @param arrivalTime This Patient's arrival time.
	 */
	public Patient(String name, String healthCardNumber, Time dob, Time arrivalTime){
		this.name = name;
		this.healthCardNumber = healthCardNumber;
		this.dob = dob;
		records = new ArrayList<Record>();
		this.urgency = 5;
	}

	/**
	 * Constructs a Patient with just name, health card number, and date of birth.
	 * @param name This Patient's name.
	 * @param healthCardNumber This Patient's health card number.
	 * @param dob This patient's date of birth.
	 */
	public Patient(String name, String healthCardNumber, Time dob){
		this.name = name;
		this.healthCardNumber = healthCardNumber;
		this.dob = dob;
		records = new ArrayList<Record>();
		this.urgency = 5;
	}

	/**
	 * Return the latest arrival time of this patient. ArrayIndexOutOfBoundsException will occur should no records exist.
	 * @return The latest arrival time of this patient.
	 */
	public Time getArrivalTime() {
		return records.get(0).getArrivalTime();
	}

	/**
	 * Get a list of strings representing all the arrival times of this patient.
	 * @return A list of strings representing all the arrival times of this patient.
	 */
	public ArrayList<String> getArrivalTimesStrings() {
		ArrayList<String> arrivalTimes = new ArrayList<String>();
		for(Record record: records) {
			arrivalTimes.add(record.getArrivalTime().toString());
		}
		return arrivalTimes;
	}

	/**
	 * Adds a new visit record to this patient and resets this patient's urgency to (vital signs not yet measured).
	 * @param r The new record being added to this patient.
	 */
	public void addRecord(Record r) {
		records.add(0, r);
		this.urgency = r.getUrgency();
	}
	
	/**
	 * Returns a string representation of this patient of format healthcardnumber-name-dob.
	 * @return A string representation of this patient of format healthcardnumber-name-dob.
	 */
	public String saveString() {
		return healthCardNumber + "," + name + "," + dob.formattedString();
	}

	/**
	 * Adds a VitalSigns object v to this Patient's vitalSignsRecord.
	 * @param v A VitalSigns object.
	 */
	public void addVitalSigns(VitalSigns v) {
		records.get(0).addVitalSigns(v);
		urgency = v.getUrgency();
	}

	/** 
	 * Adds a Prescription object p to this Patient's prescriptionsRecord.
	 * @param p A Prescription object.
	 */
	public void addPrescription(Prescription p){
		records.get(0).addPrescription(p);
	}

	/**
	 * Adds this time as a time where the patient has been seen by a doctor, maintaining order from most to least recent.
	 * @param t The time this patient has been seen by a doctor.
	 */
	public void addDoctorVisit(Time t){
		records.get(0).addDoctorVisit(t);
	}

	/**
	 * Gets a list of the times this patient has been seen by a doctor.
	 * @return A list containing every time that a doctor has seen this patient.
	 */
	public List<Time> getDoctorVisits(){
		return records.get(0).getDoctorVisits();
	}

	/**
	 * Returns this Patient's name.
	 * @return this Patient's name.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the number of records this patient has.
	 * @return The number of records this patient has.
	 */
	public int getNumRecords() {
		return records.size();
	}

	/**
	 * Return this patent's urgency level according to hospital policy.
	 * @return The current urgency level of this patient.
	 */
	public int getUrgency(){
		//Note here a new time with no arguments is just the current time.
		if(!new Time().twoYearsPassed(this.dob)){
			return urgency + 1;
		}
		return urgency;
	}

	/**
	 * Returns this Patient's date of birth.
	 * @return this Patient's date of birth.
	 */
	public String getDob() {
		return this.dob.toString();
	}

	/**
	 * Creates a new Prescription object and add it to prescriptionsRecord list.
	 * @param m medication
	 * @param i instructions
	 * @param t time this Prescription is recorded.
	 */
	public void createNewPrescription(String m, String i, Time t){
		Prescription pres = new Prescription(m, i, t);
		records.get(0).addPrescription(pres);
	}

	/**
	 * Return if this Patient's health card number is number.
	 * @param number The health card number being searched.
	 * @return True if this patient has number as its health card number,
	 * false otherwise.
	 */
	public boolean healthCardNumberEquals(String number) {
		return number.equals(this.healthCardNumber);
	}

	/**
	 * Returns this Patient's health card number.
	 * @return this Patient's health card number.
	 */
	public String getHealthCardNumber() {
		return this.healthCardNumber;
	}
    
    protected void setUrgency(int urgency) {
    	this.urgency = urgency;
    }
    
    protected void setRecord(List<Record> records) {
    	this.records = records;
    	if(this.records.size() > 0) {
    		this.urgency = this.records.get(0).getUrgency();
    	}
    }
    
	/**
	 * Returns this Patient's vitalSignsRecord.
	 * @return this Patient's vitalSignsRecord.
	 */
	public List<VitalSigns> getVitalSignsRecord() {
		return records.get(0).getVitalSignsRecord();
	}

	/**
	 * Returns this Patient's prescriptionsRecord.
	 * @return this Patient's prescriptionsRecord.
	 */
	public List<Prescription> getPrescriptionsRecord(){
		return records.get(0).getPrescriptionsRecord();
	}

	/**
	 * Return if this patient has been seen by a doctor.
	 * @return True if this patient has at least one recorded time seen by doctor, false otherwise.
	 */
	public boolean notSeenByDoctor() {
		return !hasRecord() || records.get(0).getDoctorVisits().size() == 0;
	}

	/**
	 * Returns an ArrayList containing all the times that a patient has had his/her 
	 * vital signs record updated.
	 * @return An ArrayList containing all times of all VitalSigns. 
	 */
	public ArrayList<String> getArrayListofRecordsTime(){
		return records.get(0).getArrayListofRecordsTime(); 
	}
	
	/**
	 * Return the visit record of this patient at index index. ArrayIndexOutOfBoundsExceptions are unhandled.
	 * @param index The index of the visit record.
	 * @return The visit record at index index.
	 */
	public Record getRecord(int index) {
		return records.get(index);
	}
	
	/**
	 * Get a list of all the visit records of this patient.
	 * @return A list containing all the visit records of this patient.
	 */
	public List<Record> getRecords(){
		return this.records;
	}
	
	/**
	 * Return whether this patient has at least one visit record.
	 * @return True if this patient has at least one visit record, false otherwise.
	 */
	public boolean hasRecord() {
		return this.records.size() > 0;
	}

	/**
	 * Serializes this Patient object to a given file.
	 * @param dir The file.
	 * @throws FileNotFoundException
	 */
	public void serialize(File dir) throws FileNotFoundException {
		FileOutputStream stream = new FileOutputStream(dir + "/" + healthCardNumber + ".ser");
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
	 * Deserializes a Patient object given a serialized Patient object from a file.
	 * @param dir The file.
	 * @return Null if file is not found.
	 * @throws FileNotFoundException
	 */
	public Patient deserialize(File dir) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(dir + "/" + healthCardNumber + ".ser");
		try {
			ObjectInputStream ois = new ObjectInputStream(fis);
			try {
				Patient patient = (Patient) ois.readObject();
				ois.close();
				return patient;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ois.close();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a String representation of this Patient object.
	 * @return a String representation of this Patient object.
	 */
	public String toString() {
		String returnString = "";
		returnString = "Name: " + name + "\n" + "Health Card Number: "
				+ healthCardNumber + "\n" + "Birth Date: " + dob.dateString()
				+ "\n" ;
		if(!hasRecord()) {
			returnString += "Time of arrival: Unspecified.\n";
		}
		else {
			returnString += "Arrived: " + records.get(0).getArrivalTime() + "\n";
		}
		if(urgency == 5) {
			returnString += "No measurements since arrival. Please assess ASAP";
		}
		else {
			returnString += "Urgency: " + Integer.toString(urgency);
		}
		return returnString;
	}

}

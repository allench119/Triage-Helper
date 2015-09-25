package com.example.hospitalsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Record implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1585011874583035101L;

	/** Arrival time of this Patient. */
	private Time arrivalTime;
	
	/** A list of vital signs for this Patient. */
	private List<VitalSigns> vitalSignsRecord;
	
	/** A list of times representing when this patient has been seen by a doctor. */
	private List<Time> timeSeenByDoctor;
	
	/** An integer between 0-4 inclusive, representing the urgency level according to this hospital's policy. 
	 * An urgency 'level' of 5 means patient has not been checked since arrival time. */
	private int urgency;
	
	private boolean twoYearsPassedChecked =  false; 
	
	/** A list of prescriptions for this Patient. */
	private List<Prescription> prescriptionsRecord;
	
	public Record(Time arrivalTime){
		this.arrivalTime = arrivalTime;
		vitalSignsRecord = new ArrayList<VitalSigns>();
		timeSeenByDoctor = new ArrayList<Time>();
		prescriptionsRecord = new ArrayList<Prescription>();
		urgency = 5;
	}
	
	/**
	 * Return the arrival time for this record.
	 * @return The arrival time for this record.
	 */
	public Time getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * Adds a VitalSigns object v to this Record's vitalSignsRecord.
	 * @param v A VitalSigns object.
	 */
	public void addVitalSigns(VitalSigns v) {
		vitalSignsRecord.add(v.insert(vitalSignsRecord),v);
		urgency = v.getUrgency();
	}
	
	/**
	 * Sets the vitalSignsRecords of this Record to v. 
	 * @param v A list of VitalSigns objects
	 */
	public void setVitalSignsRecord(List<VitalSigns> v) {
		this.vitalSignsRecord = v;
	}
	
	/** 
	 * Adds a Prescription object p to this Record's prescriptionsRecord.
	 * @param p A Prescription object.
	 */
	public void addPrescription(Prescription p){
		prescriptionsRecord.add(0,p);
	}
	
	/**
	 * Adds this time as a time where the patient has been seen by a doctor, maintaining order from most to least recent.
	 * @param t The time this patient has been seen by a doctor.
	 */
	public void addDoctorVisit(Time t){
		timeSeenByDoctor.add(t.getInsertionIndex(timeSeenByDoctor), t);
	}
	
	/**
	 * Gets a list of the times this patient has been seen by a doctor.
	 * @return A list containing every time that a doctor has seen this patient.
	 */
	public List<Time> getDoctorVisits(){
		return timeSeenByDoctor;
	}
	
	public ArrayList<String> getDoctorVisitsTimesStrings() {
		ArrayList<String> visitTimes = new ArrayList<String>();
		for(Time time: timeSeenByDoctor) {
			visitTimes.add(time.toString());
		}
		return visitTimes;
	}
	
	/**
	 * Return this patent's urgency level according to hospital policy.
	 * @return The current urgency level of this patient.
	 */
	public int getUrgency(){
		//Note here a new time with no arguments is just the current time.
		if(vitalSignsRecord.size() > 0) {
			return vitalSignsRecord.get(0).getUrgency();
		}
		return urgency;
	}
	
	public void checkUrgencyAge(Time dob) {
		if(new Time().twoYearsPassed(dob) && this.twoYearsPassedChecked == false){
			this.urgency += 1;
			this.twoYearsPassedChecked = true;
		}
	}
	
	/**
	 * Creates a new Prescription object and add it to prescriptionsRecord list.
	 * @param m medication
	 * @param i instructions
	 * @param t time this Prescription is recorded.
	 */
	public void createNewPrescription(String m, String i, Time t){
		Prescription pres = new Prescription(m, i, t);
		prescriptionsRecord.add(0, pres);
	}
	
	/**
	 * Returns this Patient's vitalSignsRecord.
	 * @return this Patient's vitalSignsRecord.å
	 */
	public List<VitalSigns> getVitalSignsRecord() {
		return this.vitalSignsRecord;
	}
	
	/**
	 * Get an arrayList of strings of the times associated with all the vitalSigns records of this record.
	 * @return ArrayList of strings representing all the times of the vitalSigns.
	 */
	public ArrayList<String> getVitalSignsRecordsStrings() {
		ArrayList<String> vitalSignsTimes = new ArrayList<String>();
		for(VitalSigns VitalSigns: vitalSignsRecord) {
			vitalSignsTimes.add(VitalSigns.toString());
		}
		return vitalSignsTimes;
	}
	
	/**
	 * Returns this Patient's prescriptionsRecord.
	 * @return this Patient's prescriptionsRecord.
	 */
	public List<Prescription> getPrescriptionsRecord(){
		return this.prescriptionsRecord;
	}
	
	/**
	 * Return the prescription at the specified index.
	 * @param index The index of the prescription.
	 * @return The prescription at index index.
	 */
	public Prescription getPrescription(int index){
		return prescriptionsRecord.get(index);
	}
	
	public ArrayList<String> getPrescriptionsRecordsStrings() {
		ArrayList<String> prescriptionsTimes = new ArrayList<String>();
		System.out.println(prescriptionsRecord);
		for(Prescription prescription: prescriptionsRecord) {
			prescriptionsTimes.add(prescription.toString());
		}
		return prescriptionsTimes;
	}
	
	/**
	 * Return whether this patient has no doctor visits for this particular visit record.
	 * @return True if number of doctor visits == 0; false otherwise.
	 */
	public boolean notSeenByDoctor() {
		return this.timeSeenByDoctor.size() == 0;
	}
	
	/**
	 * Returns an ArrayList containing all the times that a patient has had his/her 
	 * vital signs record updated.
	 * @return An ArrayList containing all times of all VitalSigns. 
	 */
	public ArrayList<String> getArrayListofRecordsTime(){
		ArrayList<String> records = new ArrayList<String>();
		for (VitalSigns iterator:this.getVitalSignsRecord()){
			records.add(iterator.getTime().toString());
		}
		return records; 
	}

	protected void setPrescriptions(List<Prescription> p) {
		// TODO Auto-generated method stub
		this.prescriptionsRecord = p;
	}
	
	protected void setTimesSeenByDoctor(List<Time> times) {
		this.timeSeenByDoctor = times;
	}
	
}

package com.example.hospitalsystem;

import java.util.List;

public class VitalSigns extends Data {
	 
	
	/** The temperature of this VitalSigns object. */
	private float temp;
	
	/** The systolic blood pressure of this VitalSigns object. */
	private int systolic;
	
	/** The diastolic blood pressure of this VitalSigns object. */
	private int diastolic;
	
	/** The heart rate of this VitalSigns object. */
	private int heartRate;
	
	/** The time of this VitalSigns object. */
	private Time timeRecorded;
	
	/**
	 * Creates a VitalSigns object with temperature, systolic and diastolic blood pressure.
	 * heart rate, and time that the object was recorded. 
	 * @param temp The temperature.
	 * @param systolic Systolic blood pressure.
	 * @param diastolic Diastolic blood pressure.
	 * @param heartRate Heart rate.
	 * @param timeRecorded Time recorded.
	 */
	public VitalSigns(float temp, int systolic, int diastolic, int heartRate, Time timeRecorded){
		this.timeRecorded = timeRecorded;
		this.temp = temp;
		this.heartRate = heartRate;
		this.systolic = systolic;
		this.diastolic = diastolic;
	}
	
	/**
	 * Returns the Time that this VitalSigns object was recorded.
	 * @return Time that this VitalSigns object was recorded.
	 */
	public Time getTime(){
		return this.timeRecorded;
	}
	
	/**
	 * Return the urgency level of a patient with these vital signs.
	 * @return The urgency level associated with this vital signs record.
	 */
	public int getUrgency() {
		int urgency = 0;
		if (systolic >= 140 || diastolic >= 90) {
			urgency++;
		}
		if(heartRate >= 100 || heartRate < 50) {
			urgency++;
		}
		if(temp > 39.0f) {
			urgency++;
		}
		return urgency;
	}
	
	/**
	 * Returns a String representation of this VitalSigns object.
	 * @return a String representation of this VitalSigns object.
	 */
	public String toString() {
		return "Date recorded: " + this.timeRecorded.toString() + "\n" +
				"Temperature: " + Float.toString(this.temp) + "'C\n" +
				"Heart rate: " + Integer.toString(this.heartRate) + "BPM\n" +
				"Systolic: " + Integer.toString(this.systolic) + "mmHg\n" +
				"Diastolic: " + Integer.toString(this.diastolic) + "mmHg";
	}
	
	public float getTemp() {
		return temp;
	}

	public int getSystolic() {
		return systolic;
	}

	public int getDiastolic() {
		return diastolic;
	}

	public int getHeartRate() {
		return heartRate;
	}

	public Time getTimeRecorded() {
		return timeRecorded;
	}

	/**
	 * Returns in which index d should be inserted into in order to preserve order from most to least recent.
	 * @param d The list of VitalSigns this VitalSigns object is being put into/
	 * @return The index which this VitalSigns object should be put into to preserve order.
	 */
	public int insert(List<VitalSigns> data) {
		for (int i = 0; i < data.size(); i++) {
			if (this.timeRecorded.compareTo(data.get(i).timeRecorded) == 1) {
				return i;
			}
		}
		return data.size();
	}
	
}

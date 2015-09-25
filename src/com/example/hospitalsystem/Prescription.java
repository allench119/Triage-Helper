package com.example.hospitalsystem;

public class Prescription extends Data{
	
	/** The medication for this Prescription object.*/
	private String medication;
	
	/** The instructions for this Prescription object.*/
	private String instructions;
	
	/**
	 * Creates a Prescription object with medication and instructoins, and the time that the
	 * object was recorded.
	 * @param medication The medication.
	 * @param instructions The instructions.
	 * @param timeRecorded Time recorded.
	 */
	public Prescription(String medication, String instructions, Time timeRecorded){
		this.medication = medication;
		this.instructions = instructions;
		this.timeRecorded = timeRecorded;
	}
	
	public String getMedication() {
		return medication;
	}

	public String getInstructions() {
		return instructions;
	}

	/**
	 * Returns the Time that this Prescription object was recorded.
	 * @return Time that this Prescription object was recorded.
	 */
	public Time getTime(){
		return this.timeRecorded;
	}
	
	/**
	 * Returns a String representation of this Prescription object.
	 * @return a String representation of this Prescription object.
	 */
	public String toString(){
		return "Date recorded: " + this.timeRecorded.toString() + "\n" +
				"Medication: " + this.medication + "\n" +
				"Instructions: " + this.instructions + "\n";
	}
}

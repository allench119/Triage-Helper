package com.example.hospitalsystem;

import java.util.Calendar;
import java.util.List;


public class Time implements Comparable<Object>{
	

	/** The year of this Time object. */
	private int year;
	
	/** The month of this Time object. */ 
	private int month;
	
	/** The day of this Time object. */
	private int day;
	
	/** The hour of this Time object. */
	private int hour;
	
	/** The minute of this Time object. */
	private int minute;
	
	/** A Calendar object. */
	Calendar timeRecord = Calendar.getInstance();
	
	/**
	 * Constructs a Time object by parsing a string with format "YYYY/MM/DD HH:MM".
	 * @param time The string being parsed.
	 */
	public Time(String time) {
		String[] timeFull = time.split(" ");
		String[] curDate = timeFull[0].split("/");
		String[] curTime = timeFull[1].split(":");
		this.year = Integer.parseInt(curDate[0]);
		this.month = Integer.parseInt(curDate[1]);
		this.day = Integer.parseInt(curDate[2]);
		this.hour = Integer.parseInt(curTime[0]);
		this.minute = Integer.parseInt(curTime[1]);
	}
	
	/**
	 * Constructs a Time object by parsing a string with format "YYYY/MM/DD (HH:MM)".
	 * @param time The string being parsed.
	 * @param isDate True if this is a date, false if this is a time.
	 */
	public Time(String time, boolean isDate) {
		String[] timeFull = time.split(" ");
		String[] curDate = timeFull[0].split("/");
		this.hour = -1;
		this.minute = -1;
		if(!isDate) {
			String[] curTime = timeFull[1].split(":");
			this.hour = Integer.parseInt(curTime[0]);
			this.minute = Integer.parseInt(curTime[1]);
		}
		this.year = Integer.parseInt(curDate[0]);
		this.month = Integer.parseInt(curDate[1]);
		this.day = Integer.parseInt(curDate[2]);
	}
	
	/**
	 * Constructs a Time object. 
	 * @param year The year.
	 * @param month The month.
	 * @param day The day.
	 * @param hour The hour.
	 * @param minute The minute.
	 */
	public Time(int year, int month, int day, int hour, int minute) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	
	/**
	 * Constructs a Time object where the specific time is irrelevant.
	 * @param year The year.
	 * @param month The month.
	 * @param day The day.
	 */
	public Time(int year, int month, int day){
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = -1;
		this.minute = -1;
	}
	
	/**
	 * Constructs a Time object using the Calendar object.
	 */
	public Time(){
			this.year = timeRecord.get(Calendar.YEAR);
			this.month = timeRecord.get(Calendar.MONTH) + 1;
			this.day = timeRecord.get(Calendar.DAY_OF_MONTH);
			this.hour = timeRecord.get(Calendar.HOUR_OF_DAY);
			this.minute = timeRecord.get(Calendar.MINUTE);
			
		}

	/**
	 * Compares Time objects to see which occurs first.
	 * @param time A time object.
	 * @return 1 if this time is later than the other time,
	 *  -1 if this time is earlier than the other time,
	 *  0 if both times are the same.
	 */
	@Override
	public int compareTo(Object time) {

		int i = compare(((Time)time).year, this.year);
		if (i != 0){
			return i;
		}

		i = compare(((Time)time).month, this.month);
		if (i != 0){
			return i;
		}

		i = compare(((Time)time).day, this.day);
		if (i != 0){
			return i;
		}

		i = compare(((Time)time).hour, this.hour);
		if (i != 0){
			return i;
		}

		i = compare(((Time)time).minute, this.minute);
		if (i != 0){
			return i;
		}

		return 0;
	}

	/**
	 * Returns 1 if  a < b, -1 if a > b, 0 if a == b.
	 * @param a The first int.
	 * @param b The second int.
	 * @return 1 if  a < b, -1 if a > b, 0 if a == b. 
	 */
	private int compare(int a, int b){
		if (a < b){
			return 1;
		}
		else if (b < a){
			return -1;
		}
		else {
			return 0;
		}
	}

	/**
	 * Returns a string representation of this Time.
	 * @return the String representation of this Time, in YYYY/MM/DD hh:mm format.
	 */
	public String toString() {
		
		//Formatting of month so that we get 2014/09/09 05:07 instead of 2014/9/9 5:7
		String monthString = Integer.toString(this.month);
		if(this.month < 10){
			monthString = "0" + monthString;
		}
		String dayString = Integer.toString(this.day);
		if(this.day < 10){
			dayString = "0" + dayString;
		}
		String hourString = Integer.toString(this.hour);
		if(this.hour < 10){
			hourString = "0" + hourString;
		}
		String minuteString = Integer.toString(this.minute);
		if(this.minute < 10){
			minuteString = "0" + minuteString;
		}
		if(hour != -1) {
			return Integer.toString(this.year) + "/" + monthString + "/" + dayString + " "
					+ hourString + ":" + minuteString;
		}
		else {
			return Integer.toString(this.year) + "/" + monthString + "/" + dayString;
		}
	}
	
	/**
	 * Returns a string representation of this Time object in a Date format.
	 * @return a string representation of this Time object in a Date format.
	 */
	public String dateString() {
		String monthString = Integer.toString(this.month);
		if(this.month < 10){
			monthString = "0" + monthString;
		}
		String dayString = Integer.toString(this.day);
		if(this.day < 10){
			dayString = "0" + dayString;
		}
		return Integer.toString(this.year) + "/" + monthString + "/" + dayString;
	}
	
	/**
	 * Returns a string representation of this date of format yyyy-mm-dd.
	 * @return A string representation of this date of format yyyy-mm-dd.
	 */
	public String formattedString() {
		return Integer.toString(this.year) + "-" + 
				Integer.toString(this.month) + "-" +
				Integer.toString(this.day);
	}
	
	/**
	 * Return if this time is at least two years past otherTime.
	 * @param otherTime The time in the past to be compared to this time.
	 * @return
	 */
	public boolean twoYearsPassed(Time otherTime) {
		if (this.year - otherTime.year > 2){
			return true;
		}
		else if (this.year - otherTime.year == 2){
			if(this.month > otherTime.month || (this.month == otherTime.month && this.day >= otherTime.day)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the index that this time should be inserted into to maintain latest to earliest sorting of the list.
	 * @param times A list of times already sorted from latest to earliest.
	 * @return
	 */
	public int getInsertionIndex(List<Time> times) {
		for(int i = 0; i < times.size(); i++) {
			if(compareTo(times.get(i)) == 1) {
				return i;
			}
		}
		return times.size();
	}
}
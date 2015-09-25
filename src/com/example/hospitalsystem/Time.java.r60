package com.example.hospitalsystem;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Time implements Comparable<Object>, Serializable{
	/** Serialization UID for Time Object. */
	private static final long serialVersionUID = -3845438474807457872L;
	
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

	private int compare(int a, int b){
		if (a > b){
			return -1;
		}
		else if (b < a){
			return 1;
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
}
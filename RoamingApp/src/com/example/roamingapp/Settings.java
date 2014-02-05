package com.example.roamingapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.TimePicker;


public class Settings {
	private TimePicker startPicker, endPicker;
	private int frequency, duration;
	private Date startTime, endTime;
	private boolean schedule, roamingNotification, roamingAlarm, sleepwalkingNotification,
	sleepwalkingAlarm;
	
	public Settings(boolean schedule, TimePicker start, TimePicker end, String frequency, String duration,
			boolean roamingNotif, boolean roamingAlarm, boolean swNotif, boolean swAlarm){
		
		this.schedule = schedule;
		this.startPicker = start;
		this.endPicker = end;
		this.frequency = Integer.parseInt(frequency);
		this.duration = Integer.parseInt(duration);
		this.roamingNotification = roamingNotif;
		this.roamingAlarm = roamingAlarm;
		this.sleepwalkingNotification = swNotif;
		this.sleepwalkingAlarm = swAlarm;
		
		setTime(start, end);
	}
	
	public void setTime(TimePicker start, TimePicker end){
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:MM");
		String startString = start.getCurrentHour()+":"+start.getCurrentMinute();
		String endString = end.getCurrentHour()+":"+end.getCurrentMinute();
		try {
			startTime = dateFormat.parse(startString);
			endTime = dateFormat.parse(endString);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
	}

}

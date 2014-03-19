package com.gmail.utexas.rmsystem.roamingapp;

import android.content.Context;

public class Settings {
	public String id;
	public int frequency, duration;
	public String start, end;
	public boolean schedule, roamingNotification, roamingAlarm, sleepwalkingNotification,
	sleepwalkingAlarm;
	
	public Settings(String id, boolean schedule, String start, String end, String frequency, String duration,
			boolean roamingNotif, boolean roamingAlarm, boolean swNotif, boolean swAlarm){
		
		this.id = id;
		this.schedule = schedule;
		this.start = start;
		this.end = end;
		this.frequency = Integer.parseInt(frequency);
		this.duration = Integer.parseInt(duration);
		this.roamingNotification = roamingNotif;
		this.roamingAlarm = roamingAlarm;
		this.sleepwalkingNotification = swNotif;
		this.sleepwalkingAlarm = swAlarm;
		
	}

}

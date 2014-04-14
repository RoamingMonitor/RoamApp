package com.gmail.utexas.rmsystem.roamingapp;

import android.content.Context;

public class Settings {
	public String id;
	public int frequency, duration, snooze;
	public String start, end;
	public boolean schedule, roamingNotification, roamingAlarm, sleepwalkingNotification,
	sleepwalkingAlarm;
	
	public Settings(String id, boolean schedule, String start, String end, String duration, String snooze,
			boolean roamingNotif, boolean roamingAlarm, boolean swNotif, boolean swAlarm){
		
		this.id = id;
		this.schedule = schedule;
		this.start = start;
		this.end = end;
		this.frequency = 60;
		this.duration = Integer.parseInt(duration);
		this.snooze = Integer.parseInt(snooze);
		this.roamingNotification = roamingNotif;
		this.roamingAlarm = roamingAlarm;
		this.sleepwalkingNotification = swNotif;
		this.sleepwalkingAlarm = swAlarm;
		
	}

}

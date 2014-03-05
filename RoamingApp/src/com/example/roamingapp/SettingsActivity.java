package com.example.roamingapp;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

public class SettingsActivity extends Activity{
	
    String SENDER_ID = "645540694740";
	
	GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	public void goHome(View view){
		Intent intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	}
	
	public void save(View view){
		
		Settings currentSettings = createSettings();
		Toast toast;

        if (currentSettings != null){
        	HttpResponse resp = ClientAdapter.postData(currentSettings);
			toast = Toast.makeText(getApplicationContext(), "Settings Saved", Toast.LENGTH_SHORT);
        } else {
			toast =Toast.makeText(getApplicationContext(), "Input Incorrect", Toast.LENGTH_SHORT);
        }

        toast.show();
	}
	
	private void showError(EditText text){
		text.setError("Input required");
    }
	
	//Create Settings object to be passed to gson
	@SuppressLint("NewApi") public Settings createSettings(){
		boolean error = false;
		
		EditText text1 = (EditText)findViewById(R.id.editTextNotifFreq);
		String notifFreq = "";
		if(validEntry(text1)){
			notifFreq = text1.getText().toString();
		} else {
			showError(text1);
			error = true;
		}
       
        EditText text2 = (EditText)findViewById(R.id.editTextMovDuration);
        String moveDuration = "";
		if(validEntry(text2)){
			moveDuration = text2.getText().toString();
		} else {
			showError(text2);
			error = true;
		}
		
		if (error) return null;				//Return null object signifying improper input
		text1.setText("");
		text2.setText("");
		
        TimePicker startTP = (TimePicker) findViewById(R.id.startTimePicker);
        TimePicker stopTP = (TimePicker) findViewById(R.id.stopTimePicker);
        String startTime = startTP.getCurrentHour() + ":" + startTP.getCurrentMinute();
        String stopTime = stopTP.getCurrentHour() + ":" + stopTP.getCurrentMinute();
        System.out.println("Start Time: " + startTime);
        System.out.println("Stop Time: " + stopTime);
        
        Switch switch1 = (Switch)findViewById(R.id.autoTimeSwitch);
        boolean autoTime =  switch1.isChecked();
        Switch switch2 = (Switch)findViewById(R.id.notifRASwitch1);
        boolean notifRA =  switch2.isChecked();
        Switch switch3 = (Switch)findViewById(R.id.alarmRASwitch);
        boolean alarmRA =  switch3.isChecked();
        Switch switch4 = (Switch)findViewById(R.id.notifSWSwitch1);
        boolean notifSW =  switch4.isChecked();
        Switch switch5 = (Switch)findViewById(R.id.alarmSWSwitch);
        boolean alarmSW =  switch5.isChecked();
        
        Settings settings = new Settings(autoTime, startTime, stopTime, notifFreq, moveDuration,
        			notifRA, alarmRA, notifSW, alarmSW);

        return settings;
        
	}
	
	//Check for valid entry
	public boolean validEntry(EditText text){
		String valid = text.getText().toString();
        if(valid.equals("")){
        	return false;
        }
        
        return true;
	}
	
}

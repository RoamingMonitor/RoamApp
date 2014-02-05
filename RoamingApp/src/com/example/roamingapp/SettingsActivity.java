package com.example.roamingapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

public class SettingsActivity extends Activity{

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
		Toast toast;
		boolean validate = false;
		if(validate){
			showIncorrectInput();
			toast = Toast.makeText(getApplicationContext(),"Incorrect Input",Toast.LENGTH_SHORT);
		} else {
			saveSettings();
			toast =Toast.makeText(getApplicationContext(), "Settings Saved", Toast.LENGTH_SHORT);
		}
		toast.show();
	}
	
	private void showIncorrectInput(){
		
	}
	
	@SuppressLint("NewApi")
	private void saveSettings(){
		EditText text = (EditText)findViewById(R.id.editTextNotifFreq);
        String notifFreq = text.getText().toString();
        text = (EditText)findViewById(R.id.editTextMovDuration);
        String moveDuration = text.getText().toString();
        TimePicker startTP = (TimePicker) findViewById(R.id.startTimePicker);
        TimePicker stopTP = (TimePicker) findViewById(R.id.stopTimePicker);
        String startTime = startTP.getCurrentHour() + ":" + startTP.getCurrentMinute();
        String stopTime = stopTP.getCurrentHour() + ":" + stopTP.getCurrentMinute();
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
        //TODO Need to get Settings class from master branch
        //Settings currentSettings = new Settings(autoTime, startTime, stopTime, notifFreq, moveDuration
        //			notifRA, alarmRA, notifSW, alarmSW);
        
        
	}
}

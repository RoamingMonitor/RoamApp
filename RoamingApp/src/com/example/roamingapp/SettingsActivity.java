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
import android.widget.Button;
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
        
		Toast toast = new Toast(null);

        if (currentSettings != null){
        	HttpResponse resp = ClientAdapter.postData(currentSettings);
			toast =Toast.makeText(getApplicationContext(), "Settings Saved", Toast.LENGTH_SHORT);
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
		
		EditText text = (EditText)findViewById(R.id.editTextNotifFreq);
		String notifFreq = "";
		if(validEntry(text)){
			notifFreq = text.getText().toString();
		} else {
			showError(text);
			error = true;
		}
       
        text = (EditText)findViewById(R.id.editTextMovDuration);
        String moveDuration = "";
		if(validEntry(text)){
			moveDuration = text.getText().toString();
		} else {
			showError(text);
			error = true;
		}
		
		if (error) return null;				//Return null object signifying improper input
		
        TimePicker startTP = (TimePicker) findViewById(R.id.startTimePicker);
        TimePicker stopTP = (TimePicker) findViewById(R.id.stopTimePicker);
        //String startTime = startTP.getCurrentHour() + ":" + startTP.getCurrentMinute();
        //String stopTime = stopTP.getCurrentHour() + ":" + stopTP.getCurrentMinute();
        
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

        return new Settings(autoTime, startTP, stopTP, notifFreq, moveDuration,
        			notifRA, alarmRA, notifSW, alarmSW);
        
	}
	
	//Check for valid entry
	public boolean validEntry(EditText text){

        if(text == null){
        	return false;
        }
        
        return true;
	}
	
	// Send an upstream message.
	public void sendUpstreamMessage(){
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    String msg = "";
                    try {
                        Bundle data = new Bundle();
                        data.putString("my_message", "Hello World");
                        data.putString("my_action", "com.google.android.gcm.demo.app.ECHO_NOW");
                        String id = Integer.toString(msgId.incrementAndGet());
                        gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
                        msg = "Sent message";
                    } catch (IOException ex) {
                        msg = "Error :" + ex.getMessage();
                    }
                    Toast.makeText(getApplicationContext(), "Upstream message sent: " + msg,
                     		Toast.LENGTH_SHORT).show();
                    return msg;
                }

                
            }.execute(null, null, null);
        
	}
}

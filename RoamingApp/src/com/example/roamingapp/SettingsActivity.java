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

        Settings currentSettings = new Settings(autoTime, startTP, stopTP, notifFreq, moveDuration,
        			notifRA, alarmRA, notifSW, alarmSW);
        
        HttpResponse resp = ClientAdapter.postData(currentSettings);
        
        //sendUpstreamMessage();
        
        //TODO Add functionality for failed post
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

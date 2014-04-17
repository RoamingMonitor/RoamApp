package com.gmail.utexas.rmsystem.roamingapp;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.roamingapp.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

@SuppressLint("NewApi")
public class SettingsActivity extends Activity{
	
    String SENDER_ID = "645540694740";
	
	GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    
    Context context;
    
    private EditText startTimeField, stopTimeField;
    private int startHour, startMinute, stopHour, stopMinute;
    private TimePicker startTP, stopTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        context = getApplicationContext();
        
        addListenerOnTimeTextFields();
        String jsonSettings = Prefs.getSettings(context);
        //Log.i("jsonSettings", jsonSettings);
        if(!(jsonSettings.equals("{}"))){
        	loadSavedSettings(jsonSettings);
        } 
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);     
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

	public void goHome(View view){
		Intent intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	}
	
	public void addListenerOnTimeTextFields() {
		 
		startTimeField = (EditText) findViewById(R.id.editTextStartTime);
		stopTimeField = (EditText)	findViewById(R.id.editTextStopTime);
 
		startTimeField.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            Calendar mcurrentTime = Calendar.getInstance();
	            if (startHour != 0 && startMinute != 0){
	            	startHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
	            	startMinute = mcurrentTime.get(Calendar.MINUTE);
	            }
	            TimePickerDialog mTimePicker;
	            mTimePicker = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
	                @Override
	                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
	                	String selector = "am";
	                	startHour = selectedHour;
	                	startMinute = selectedMinute;
	                	startTP = timePicker;
	                	if (selectedHour > 12){
	                		selectedHour -= 12;
	                		selector = "pm";
	                	} else if (selectedHour == 12){
	                		selector = "pm";
	                	} else if (selectedHour == 0) {
	                		selectedHour = 12;
	                	}
	                	String newMinute = ""+startMinute;
	                	if (startMinute <= 9){
	                		newMinute = "0"+startMinute;
	                	}
	                    startTimeField.setText( "" + selectedHour + ":" + newMinute + " " + selector);
	                }
	            }, startHour, startMinute, false);
	            mTimePicker.setTitle("Select Start Time");
	            mTimePicker.show();

	        }
	    });
		
		stopTimeField.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            Calendar mcurrentTime = Calendar.getInstance();
	            if (stopHour != 0 && stopMinute != 0){
	            	stopHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
	            	stopMinute = mcurrentTime.get(Calendar.MINUTE);
	            }
	            TimePickerDialog mTimePicker;
	            mTimePicker = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
	                @Override
	                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
	                	String selector = "am";
	                	stopHour = selectedHour;
	                	stopMinute = selectedMinute;
	                	stopTP = timePicker;
	                	if (selectedHour > 12){
	                		selectedHour -= 12;
	                		selector = "pm";
	                	} else if (selectedHour == 12){
	                		selector = "pm";
	                	} else if (selectedHour == 0) {
	                		selectedHour = 12;
	                	}
	                	String newMinute = ""+stopMinute;
	                	if (stopMinute <= 9){
	                		newMinute = "0"+stopMinute;
	                	}
	                    stopTimeField.setText( "" + selectedHour + ":" + newMinute + " " + selector);
	                }
	            }, stopHour, stopMinute, false);
	            mTimePicker.setTitle("Select Stop Time");
	            mTimePicker.show();

	        }
	    });
	}
	
	public void save(View view){
		
		Settings currentSettings = createSettings();
		Toast toast;

        if (currentSettings != null){
    		Gson gson = new Gson();
    		String json = gson.toJson(currentSettings);
    		Prefs.setSettings(context, json);
        	HttpResponse resp = ClientAdapter.postData(json, ClientAdapter.SETTINGS_URL);
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
		 
		EditText text1 = (EditText)findViewById(R.id.editTextSnoozeTime);
		String snoozeTime = "";
		if(validEntry(text1)){
			snoozeTime = text1.getText().toString();
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
		
		EditText text3 = (EditText)findViewById(R.id.editTextStartTime);
        String startTime = "";
		if(validEntry(text3)){
			//startTime = text3.getText().toString();
			startTime = startHour + ":" + startMinute;
		} else {
			showError(text3);
			error = true;
		}
		
		EditText text4 = (EditText)findViewById(R.id.editTextStopTime);
        String stopTime = "";
		if(validEntry(text4)){
			//stopTime = text4.getText().toString();
			stopTime = stopHour + ":" + stopMinute;
		} else {
			showError(text4);
			error = true;
		}
		
		if (error) return null;				//Return null object signifying improper input
		//text1.setText("");
		//text2.setText("");
		
        //TimePicker startTP = (TimePicker) findViewById(R.id.startTimePicker);
        //TimePicker stopTP = (TimePicker) findViewById(R.id.stopTimePicker);
        //String startTime = startTP.getCurrentHour() + ":" + startTP.getCurrentMinute();
        //String stopTime = stopTP.getCurrentHour() + ":" + stopTP.getCurrentMinute();
        System.out.println("Start Time: " + startTime);
        System.out.println("Stop Time: " + stopTime);
        
        Switch switch1 = (Switch)findViewById(R.id.autoTimeSwitch);
        boolean autoTime =  switch1.isChecked();
        Switch switch2 = (Switch)findViewById(R.id.notifRASwitch);
        boolean notifRA =  switch2.isChecked();
        Switch switch3 = (Switch)findViewById(R.id.alarmRASwitch);
        boolean alarmRA =  switch3.isChecked();
        Switch switch4 = (Switch)findViewById(R.id.notifSWSwitch);
        boolean notifSW =  switch4.isChecked();
        Switch switch5 = (Switch)findViewById(R.id.alarmSWSwitch);
        boolean alarmSW =  switch5.isChecked();
        
        Settings settings = new Settings(Prefs.getAppID(context), autoTime, startTime, stopTime, moveDuration,
        			snoozeTime, notifRA, alarmRA, notifSW, alarmSW);

        return settings;
        
	}
	
	@SuppressLint("NewApi") public void loadSavedSettings(String jsonSettings){
		Gson gson = new Gson();
		//JSONObject jsonObject = new JSONObject(Prefs.getSettings(context));
        Settings settings = gson.fromJson(Prefs.getSettings(context), Settings.class);
        
        //autoTimeSwitch
        Switch schedule = (Switch)findViewById(R.id.autoTimeSwitch);
        schedule.setChecked(settings.schedule);
        
        /*//startTimePicker
        TimePicker startTime = (TimePicker)findViewById(R.id.startTimePicker);
        startTime.setCurrentHour(Integer.parseInt(settings.start.split(":")[0]));
        startTime.setCurrentMinute(Integer.parseInt(settings.start.split(":")[1]));*/
        
        /*//stopTimePicker
        TimePicker stopTime = (TimePicker)findViewById(R.id.stopTimePicker);
        stopTime.setCurrentHour(Integer.parseInt(settings.end.split(":")[0]));
        stopTime.setCurrentMinute(Integer.parseInt(settings.end.split(":")[1]));*/
        
        //editTextNotifFreq
        //EditText notifFreq = (EditText)findViewById(R.id.editTextNotifFreq);
        //notifFreq.setText(settings.frequency+"");
        
        //editTextMovDuration
        if (settings.duration >= 0){
        	EditText movDuration = (EditText)findViewById(R.id.editTextMovDuration);
        	movDuration.setText(settings.duration+"");
        }
         
        //editTextSnoozeTime
        if (settings.snooze >= 0){
        	EditText snoozeTime = (EditText)findViewById(R.id.editTextSnoozeTime);
        	snoozeTime.setText(settings.snooze+"");
        }
        
        //editTextStartTime
        Log.i("Settings Activity", "Start Time: " + settings.start);
        if (!settings.start.equals(null)){
        	int savedHour = Integer.parseInt(settings.start.split(":")[0]);
        	int savedMinute = Integer.parseInt(settings.start.split(":")[1]);
        	
        	startHour = savedHour;
        	startMinute = savedMinute;
        	
        	String selector = "am";
        	if (savedHour > 12){
        		selector = "pm";
        		savedHour -= 12;
        	} else if (savedHour == 12){
        		selector = "pm";
        	} else if (savedHour == 0){
        		savedHour = 12;
        	}
        	String newMinute = ""+savedMinute;
        	if (savedMinute <= 9){
        		newMinute = "0" + savedMinute;
        	}
        	
        	String savedTime = "" + savedHour + ":" + newMinute + "" + selector;
        	Log.i("Settings Activity", "Saved Time: " + savedTime);
        	startTimeField.setText(savedTime);
        }
        
        
        //editTextStopTime
        if (!settings.end.equals(null)){
        	int savedHour = Integer.parseInt(settings.end.split(":")[0]);
	        int savedMinute = Integer.parseInt(settings.end.split(":")[1]);
	        String selector = "am";
	        stopHour = savedHour;
        	stopMinute = savedMinute;
	        if (savedHour > 12){
	        	selector = "pm";
	        	savedHour -= 12;
	        } else if (savedHour == 12){
        		selector = "pm";
        	} else if (savedHour == 0){
        		savedHour = 12;
        	}
        	String newMinute = ""+savedMinute;
        	if (savedMinute <= 9){
        		newMinute = "0" + savedMinute;
        	}
	        stopTimeField.setText( "" + savedHour + ":" + newMinute + "" + selector);
        }
        
        
        //notifRASwitch
        Switch notifRA = (Switch)findViewById(R.id.notifRASwitch);
        notifRA.setChecked(settings.roamingNotification);
        
        //alarmRASwitch
        Switch alarmRA = (Switch)findViewById(R.id.alarmRASwitch);
        alarmRA.setChecked(settings.roamingAlarm);
        
        //notifSWSwitch
        Switch notifSW = (Switch)findViewById(R.id.notifSWSwitch);
        notifSW.setChecked(settings.sleepwalkingNotification);
        
        //alarmSWSwitch
        Switch alarmSW = (Switch)findViewById(R.id.alarmSWSwitch);
        alarmSW.setChecked(settings.sleepwalkingAlarm);
        
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

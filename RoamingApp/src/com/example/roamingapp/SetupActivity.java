package com.example.roamingapp;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class SetupActivity extends Activity {
	private String verifCode;
	private int i = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void submitVerification(View view){
    	EditText text = (EditText)findViewById(R.id.verif_entry);
		String verif = "";
		Toast toast;
		
		if(validEntry(text)){
			verif = text.getText().toString();
		} else {
			showError(text);
		}

		verifCode = verif;
		
		if (!verifCode.equals("") && checkCode(verifCode)){
			toast = Toast.makeText(getApplicationContext(), "Device Synchronized", Toast.LENGTH_SHORT);
        } else {
			toast =Toast.makeText(getApplicationContext(), "Input Incorrect", Toast.LENGTH_SHORT);
        }
		
		text.setText("");
		toast.show();
		
		//testListView(i);
		i++;
    }
    
    public boolean checkCode(String code){
    	//TODO Implement call to server to authenticate code
    	return true;
    }
    
    private void testListView(int i){
    	//For testing if the list view updates correctly
    	NotificationLogMessage logMessage = new NotificationLogMessage();
    	logMessage.setMessageTitle("Test Title" + i);
    	logMessage.setMessageBody("This is a test message added to the ArrayAdapter");
    	logMessage.setDateAndTime("3/3/2014 " + i +":00am");
    	NotificationHistoryActivity.updateNotifLogArray(logMessage);
    }
    
    //Check for valid entry
  	public boolean validEntry(EditText text){
  		String valid = text.getText().toString();
          if(valid.equals("")){
          	return false;
          }
          
          return true;
  	}
  	
  	private void showError(EditText text){
		text.setError("Input required");
    }
  	
  	public void goHome(View view){
		Intent intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	}
}

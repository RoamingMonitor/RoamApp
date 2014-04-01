package com.gmail.utexas.rmsystem.roamingapp;

import java.util.ArrayList;

import com.example.roamingapp.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi") public class NotificationHistoryActivity extends ListActivity{
    
	private final String TAG = "NotificationHistoryActivity";
	private ListView list;
	private boolean testFlag = false;
    NotificationAdapter adapter;
    public static Context context;
    public  NotificationHistoryActivity CustomListView = null;
    private static ArrayList<NotificationLogMessage> arrayOfNotifLogs;
    public ArrayList<NotificationLogMessage> testArr = new ArrayList<NotificationLogMessage>();
    
    public NotificationHistoryActivity(){
    	context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif_hist);
        CustomListView = this; 
        Resources res = getResources();
        if(arrayOfNotifLogs == null){
        	arrayOfNotifLogs = new ArrayList<NotificationLogMessage>();
        }
        
        // Construct the data source
        if (!testFlag) {
        	// Create the adapter to convert the array to views
        	adapter = new NotificationAdapter(CustomListView, arrayOfNotifLogs, res, this);
        	// Attach the adapter to a ListView
        	ListView listView = getListView();
        	listView.setAdapter(adapter);
        } else {
            setListData();  
            list = getListView(); 
            adapter=new NotificationAdapter(CustomListView, testArr, res, this);
            list.setAdapter( adapter );
        }
        
        //context = this;
        //testMessageHandler();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);     
    }
    
    /****** Function to set data in ArrayList *************/
    public void setListData() {
         
        for (int i = 0; i < 11; i++) {
             
            final NotificationLogMessage alert = new NotificationLogMessage();
                 
              /******* Firstly take data in model object ******/
               alert.setMessageTitle("Message Title "+i);
               alert.setDateAndTime(i+":00am "+i+"/"+i+"/"+"2014");
               alert.setMessageBody("There are now " + i + " messages");
                
            /******** Take Model Object in ArrayList **********/
            testArr.add( alert );
        }
         
    }
    
    @SuppressLint("NewApi")
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
    	startActivityForResult(myIntent, 0);    
    	
//    	Toast.makeText(this,
//          String.valueOf(getListView().getCheckedItemCount()),
//          Toast.LENGTH_LONG).show();
    	
      return true;
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
      // do something with the data
    	super.onListItemClick(l, v, position, id);
    	Log.i(TAG, "Position: "  + position);
    	/*String item = (String) getListAdapter().getItem(position);
    	new AlertDialog.Builder(this)
    	   .setTitle("Test")
    	   .setMessage(item)
    	   .setPositiveButton("OK",
    	     new DialogInterface.OnClickListener() {
    	      public void onClick(DialogInterface dialog, int which) {}}
    	     )
    	   .show();
    	
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();*/
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
	
    public static void updateNotifLogArray(NotificationLogMessage logMessage){
    	if(arrayOfNotifLogs == null){
    		arrayOfNotifLogs = new ArrayList<NotificationLogMessage>();
    	}
    	arrayOfNotifLogs.add(0, logMessage);
    }
    
    public static void updateNotifLogArray(ArrayList<NotificationLogMessage> multLogMessages){
    	
    	for (int i = 0; i < multLogMessages.size(); i++) {
    		updateNotifLogArray(multLogMessages.get(i));
        }
    	
    }
    
    public NotificationAdapter getNotificationAdapter(){
    	return adapter;
    }
}

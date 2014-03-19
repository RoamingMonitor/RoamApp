package com.gmail.utexas.rmsystem.roamingapp;

import java.util.ArrayList;

import com.example.roamingapp.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class NotificationHistoryActivity extends ListActivity{
    
	private ListView list;
	private boolean testFlag = false;
    NotificationAdapter adapter;
    private static Context context;
    public  NotificationHistoryActivity CustomListView = null;
    private static ArrayList<NotificationLogMessage> arrayOfNotifLogs;
    public ArrayList<NotificationLogMessage> testArr = new ArrayList<NotificationLogMessage>();

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
        
        context = this;
        testMessageHandler();
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
      Toast.makeText(this,
          String.valueOf(getListView().getCheckedItemCount()),
          Toast.LENGTH_LONG).show();
      return true;
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
      // do something with the data
    	super.onListItemClick(l, v, position, id);
    	String item = (String) getListAdapter().getItem(position);
    	new AlertDialog.Builder(this)
    	   .setTitle("Test")
    	   .setMessage(item)
    	   .setPositiveButton("OK",
    	     new DialogInterface.OnClickListener() {
    	      public void onClick(DialogInterface dialog, int which) {}}
    	     )
    	   .show();
    	
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
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
    	arrayOfNotifLogs.add(logMessage);
    	MessageHandler msg = new MessageHandler(NotificationHistoryActivity.context);
    	msg.sendNotification(logMessage);
    }
    
    public static void updateNotifLogArray(ArrayList<NotificationLogMessage> multLogMessages){
    	
    	for (int i = 0; i < multLogMessages.size(); i++) {
    		updateNotifLogArray(multLogMessages.get(i));
        }
    	
    }
    
    private static void testMessageHandler(){
    	NotificationLogMessage msg = new NotificationLogMessage();
    	msg.setMessageTitle("Test Notificaiton");
    	msg.setDateAndTime("3/19/2014 1:35pm");
    	msg.setMessageBody("Let's see where the message body is displayed and how much is visible.");
    	MessageHandler mh = new MessageHandler(context);
    	mh.sendNotification(msg); 	
    }
    
}

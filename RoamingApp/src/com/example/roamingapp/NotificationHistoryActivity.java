package com.example.roamingapp;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class NotificationHistoryActivity extends ListActivity{
    
	private ListView list;
	private boolean test = true;
    NotificationAdapter adapter;
    private static Context context;
    public  NotificationHistoryActivity CustomListView = null;
    private static ArrayList<NotificationLogMessage> arrayOfNotifLogs;
    public  ArrayList<NotificationLogMessage> testArr = new ArrayList<NotificationLogMessage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif_hist);
        
        // Construct the data source
        if (!test) {
        	arrayOfNotifLogs = new ArrayList<NotificationLogMessage>();
        	// Create the adapter to convert the array to views
        	NotificationAdapter adapter = new NotificationAdapter(this, arrayOfNotifLogs);
        	// Attach the adapter to a ListView
        	ListView listView = getListView();
        	listView.setAdapter(adapter);
        } else {
        	CustomListView = this;  
            setListData();  
            Resources res = getResources();
            list = getListView(); 
            adapter=new NotificationAdapter(CustomListView, testArr, res, this);
            list.setAdapter( adapter );
        }
        
        context = this;
    }
    
    /****** Function to set data in ArrayList *************/
    public void setListData()
    {
         
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
    	String item = (String) getListAdapter().getItem(position);
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
    	arrayOfNotifLogs.add(logMessage);
    	MessageHandler msg = new MessageHandler(NotificationHistoryActivity.context);
    	msg.sendNotification(logMessage);
    }
    
    public static void updateNotifLogArray(ArrayList<NotificationLogMessage> multLogMessages){
    	
    	for (int i = 0; i < multLogMessages.size(); i++) {
    		updateNotifLogArray(multLogMessages.get(i));
        }
    	
    }
    
}

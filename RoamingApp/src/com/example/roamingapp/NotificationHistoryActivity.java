package com.example.roamingapp;

import java.util.ArrayList;
import java.util.Collection;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationHistoryActivity extends ListActivity{
	
	ListView list;
    NotificationAdapter adapter;
    public  NotificationHistoryActivity CustomListView = null;
    public  ArrayList<NotificationLogMessage> testArr = new ArrayList<NotificationLogMessage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif_hist);
        
       /* // Construct the data source
        ArrayList<NotificationLogMessage> arrayOfNotifLogs = new ArrayList<NotificationLogMessage>();
        // Create the adapter to convert the array to views
        NotificationAdapter adapter = new NotificationAdapter(this, arrayOfNotifLogs);
        // Attach the adapter to a ListView
        ListView listView = getListView();
        listView.setAdapter(adapter);*/
        
        //Begin code for manual testing
        CustomListView = this;  
        setListData();  
        Resources res = getResources();
        list = getListView(); 
        adapter=new NotificationAdapter(CustomListView, testArr, res, this);
        list.setAdapter( adapter );
        //End code for manual testing
    }
    
    /****** Function to set data in ArrayList *************/
    public void setListData()
    {
         
        for (int i = 0; i < 11; i++) {
             
            final NotificationLogMessage alert = new NotificationLogMessage();
                 
              /******* Firstly take data in model object ******/
               alert.setMessageTitle("Message Title "+i);
               alert.setDateAndTime("0:00"+i+"/"+i+"/"+"2014");
               alert.setMessageBody("http:\\www."+i+".com");
                
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
	
}

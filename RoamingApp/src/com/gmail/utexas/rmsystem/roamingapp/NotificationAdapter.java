package com.gmail.utexas.rmsystem.roamingapp;

import java.util.ArrayList;

import com.example.roamingapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NotificationAdapter extends ArrayAdapter<NotificationLogMessage> {
	private Activity activity;
    private ArrayList<NotificationLogMessage> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    
    public NotificationAdapter(Context context, ArrayList<NotificationLogMessage> logMessages) {
       super(context, R.layout.item_notif_log, logMessages);
    }
    
    //Constructor used for testing
    public NotificationAdapter(Activity a, ArrayList<NotificationLogMessage> d, Resources resLocal, Context context) {         	
    		super(context, R.layout.item_notif_log, d);
            activity = a;
            data=d;
            res = resLocal;
            inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    // View lookup cache
    private static class ViewHolder {
        TextView messageTitle;
        TextView dateAndTime;
        TextView messageBody;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       NotificationLogMessage logMessage = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       ViewHolder viewHolder; // view lookup cache stored in tag
       if (convertView == null) {
          viewHolder = new ViewHolder();
          LayoutInflater inflater = LayoutInflater.from(getContext());
          convertView = inflater.inflate(R.layout.item_notif_log, null);
          viewHolder.messageTitle = (TextView) convertView.findViewById(R.id.messageTitle);
          viewHolder.dateAndTime = (TextView) convertView.findViewById(R.id.dateAndTime);
          viewHolder.messageBody = (TextView) convertView.findViewById(R.id.messageBody);
          convertView.setTag(viewHolder);
       } else {
           viewHolder = (ViewHolder) convertView.getTag();
       }
       // Populate the data into the template view using the data object
       viewHolder.messageTitle.setText(logMessage.getMessageTitle());
       viewHolder.dateAndTime.setText(logMessage.getDateAndTime());
       viewHolder.messageBody.setText(logMessage.getMessageBody());
       // Return the completed view to render on screen
       return convertView;
   }
       
}

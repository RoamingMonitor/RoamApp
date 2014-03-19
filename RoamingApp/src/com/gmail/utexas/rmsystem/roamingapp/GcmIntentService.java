package com.gmail.utexas.rmsystem.roamingapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "GCMIntentService";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent); 

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.i(TAG, "Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.i(TAG, "Deleted messages on server: " + extras.toString());
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
            	persistLogMessage(extras);
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void persistLogMessage(Bundle extras){
    	//TODO use Bundle.get(String key) to get the json object containing log messages
    	//from the server.
    	boolean alertFlag = false;
    	NotificationLogMessage logMessage = null;
    	ArrayList<NotificationLogMessage> multLogMessages = new ArrayList<NotificationLogMessage>();
    	Iterator keyIt = extras.keySet().iterator();
    	
    	try {
    		
    		if (extras.containsKey("logMessage")){
	    		JSONObject jsonObject = new JSONObject(extras.getString("logMessage"));
	    		logMessage = new NotificationLogMessage(jsonObject);
	    		alertFlag = true;
    		} 
    		
    		if (extras.containsKey("multipleLogMessages")){
	    		//TODO add implementation for receiving an array of log messages
	    		JSONArray jsonObjects = new JSONArray(extras.getString("multipleLogMessages"));
	    		multLogMessages = NotificationLogMessage.fromJson(jsonObjects);
	    		alertFlag = true;
    		}
    	
    		if (extras.containsKey("activeStatus")){
    			JSONObject jsonObject = new JSONObject(extras.getString("activeStatus"));
    			boolean activeFlag = jsonObject.getBoolean("activeFlag");
    			/*Time today = new Time(Time.getCurrentTimezone());
    			today.setToNow();*/
    			SimpleDateFormat fmt = new SimpleDateFormat("h:mm a");
    			Date date = new Date();
    			String dateString = fmt.format(date);
    			alertFlag = true;
    			MainActivity.updateDeviceStatusValues(activeFlag, dateString);
    			
    		}
    		
			if (logMessage != null){
				NotificationHistoryActivity.updateNotifLogArray(logMessage);
			} else if (multLogMessages.isEmpty())
				Log.i(TAG, "The logMessage is empty");

			if (!multLogMessages.isEmpty())
				NotificationHistoryActivity
						.updateNotifLogArray(multLogMessages);
			else if (logMessage == null)
				Log.i(TAG, "The multipleLogMessages is empty");
			
    	} catch (JSONException je){    		
    		System.err.println("JSON Exception in GCMIntentService.persistLogMessage()");
    		je.printStackTrace();
    	}

    }
}

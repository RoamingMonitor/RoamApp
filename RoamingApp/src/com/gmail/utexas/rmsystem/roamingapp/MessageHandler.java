package com.gmail.utexas.rmsystem.roamingapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.roamingapp.R;



public class MessageHandler {
	private final String ALERT = "Alert Title";
	private final String MESSAGE = "Test Message";
	public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private Context context;
    private NotificationLogMessage msg;
    private String msgType;
    private final String NOTIF = "notification";
    private final String ALARM = "alarm";
    private final String BOTH = "both";
    private final String FAILURE = "Alert Failure";
    private final String SUCCESS = "Succesful Alert";
    private final String TAG = "MessageHandler.java";
    
	public MessageHandler (Context context, NotificationLogMessage logMessage){
		this.context = context;
		msg = logMessage;
		msgType = msg.getAlertType();
	}
	
	public MessageHandler (Context context){
		this.context = context;
	}
	
	public void sendNotification(NotificationLogMessage logMessage) {
		Log.i(TAG, "Context: " + context);
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, NotificationHistoryActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.transparent_logo)
        .setContentTitle(logMessage.getMessageTitle())
        .setStyle(new NotificationCompat.BigTextStyle())
        .setContentText(logMessage.getMessageBody());
        Log.i(TAG, "Message built and ready to send to phone.");
        
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
	
	public void sendAlarm(NotificationLogMessage logMessage){
		Context thisActivity = NotificationHistoryActivity.context;
		Intent intent = new Intent(thisActivity, AlarmDialogActivity.class);
		intent.putExtra(ALERT, logMessage.getMessageTitle());
		intent.putExtra(MESSAGE, logMessage.getMessageBody());
		thisActivity.startActivity(intent);
	}
	
	public void sendAlert(){
		Log.i(TAG, "Message made it to alert decision. MsgType = " + msgType);
        
        if (msgType.equals(NOTIF))
        	sendNotification(msg);
        else if (msgType.equals(ALARM))
        	sendAlarm(msg);
        else if (msgType.equals(BOTH)){
        	sendNotification(msg);
        	sendAlarm(msg);
        }
        
	}
	
	

}

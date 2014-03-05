package com.example.roamingapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;



public class MessageHandler {
	public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private Context context;
    
	public MessageHandler (Context context){
		this.context = context;
	}
	
	public void sendNotification(NotificationLogMessage logMessage) {
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

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}

package com.gmail.utexas.rmsystem.roamingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;

public class AlarmDialogActivity extends Activity{
	private final String ALERT = "Alert Title";
	private final String MESSAGE = "Test Message";
	private final String TAG = "AlarmDialogActivity.class";
	private String alertTitle;
	private String alertMsg;
	private AlertDialog alert; 
	private MediaPlayer media;
	private Vibrator vibrator;
    private Thread vibrateThread;
    private PowerManager.WakeLock lock;
    private Context context;
	
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        
        Intent intent = getIntent();
        alertTitle = intent.getStringExtra(ALERT);
        alertMsg = intent.getStringExtra(MESSAGE);
        
        PowerManager power = (PowerManager)getSystemService(Context.POWER_SERVICE);
        lock = power.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK |
            PowerManager.ACQUIRE_CAUSES_WAKEUP, "Alarm Received");
        
        AlertDialog.Builder builder = new AlertDialog.Builder(AlarmDialogActivity.this);
        builder.setTitle(alertTitle)
        .setMessage(alertMsg)
        .setCancelable(false)
        .setNegativeButton("Dismiss",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	media.stop();
	            //vibrateThread.interrupt();
            	if (lock.isHeld())
            		lock.release();
                dialog.cancel();
                Intent i = new Intent(context, NotificationHistoryActivity.class);
                startActivity(i);
            }
        });
        
        alert = builder.create();
        
        vibrator = (Vibrator)this.context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrateThread = new VibrateThread();
        
        alert.show();
        startAlarm(this);
        //vibrateThread.start();

    }
	
	private void startAlarm(final Context context) {
       
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(uri == null) {
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if(uri == null)
                uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        }

        media = new MediaPlayer();
        try{
            media.setDataSource(context, uri);
            final AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                if(audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                    media.setAudioStreamType(AudioManager.STREAM_ALARM);
                    media.setLooping(true);
                    media.prepare();
                    media.start();
                }
        } catch(Exception e){}
               
    }
	
	class VibrateThread extends Thread {
        
		public VibrateThread() {
            super();
        }
        public void run() {               
            try {
            	Log.i(TAG, "Vibrator is ON!");
                long[] vibPattern = new long[] {0L,100L,250L,1000L,250L,500L};
                //vibrator.vibrate(vibPattern, 1);
                vibrator.vibrate(10000L);
            }// Ends try
            catch (Exception e) {}
        }// Ends run method
    }
}

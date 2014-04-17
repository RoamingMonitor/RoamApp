package com.gmail.utexas.rmsystem.roamingapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roamingapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

@SuppressLint("NewApi") public class MainActivity extends Activity {
	private static Switch deviceStatusSwitch;
	
	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static boolean deviceStatusFlag = false;
    private static String updateTime = "n/a";
    private static String updateDependentStatus;
    private static boolean initial = true;
    
    String SENDER_ID = "645540694740";    
    

    //Tag used on log messages.
    static final String TAG = "GCM Registration: MainActivity";
    
    //Used for GCM registration
    String regid, deviceID;
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    
    Context context;
    TextView mDisplay;
    static boolean deviceStatus;
    static String lastDependentStatus = "";
    static String lastUpdated = "";
    static Date lastUpdatedDate;     
    
    private static NotificationHistoryActivity nha;
    static Context nhaContext;
    
    //Used for recent notifications list
    NotificationAdapter adapter;
    public  MainActivity CustomListView = null;
    private static ArrayList<NotificationLogMessage> arrayOfNotifLogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        context = getApplicationContext();
        deviceStatusSwitch = (Switch) findViewById(R.id.on_off_switch);
        
        updateDeviceStatus();
        updateTimeText();
        updateDependentStatus();
        
        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices()) {
        	Log.i(TAG, "Google Play Services is installed.");
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);
            //regid = "";
        	//Prefs.getPrefs(context, MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
            Log.i(TAG, "RegId is: "  + regid);
            
            if (regid.isEmpty()) {
            	Log.i(TAG, "About to register in background");
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
        
        if(initial){
        	enableWIFI();
        	initial = false;
        }        
        displayDeviceStatusOnClick();
        //setUpRecentNotif();
    }
    
    /*public void setUpRecentNotif(){
    	
        Resources res = getResources();
        if(arrayOfNotifLogs == null){
        	arrayOfNotifLogs = new ArrayList<NotificationLogMessage>();
        }
        
        // Construct the data source
    	// Create the adapter to convert the array to views
    	adapter = new NotificationAdapter(CustomListView, arrayOfNotifLogs, res, this);
    	// Attach the adapter to a ListView
    	ListView listView = (ListView)findViewById(R.id.listView1);
    	listView.setAdapter(adapter);
        	
    }*/
    
    public static void updateDeviceStatusValues(boolean deviceString, String dateString, 
    		String dependent){
    	deviceStatus = deviceString;
    	lastUpdated = dateString;
    	lastDependentStatus = dependent;
    }
    
    public void updateDeviceStatus(){    	    	
    	deviceStatusSwitch.setChecked(deviceStatus);
    	if (!lastUpdated.equals("")){
    		updateTime = lastUpdated;
    	}
    	if (!lastDependentStatus.equals("")){
    		updateDependentStatus = lastDependentStatus;
    	}
    }
    
    private void updateTimeText(){
    	TextView textView = (TextView) findViewById(R.id.updateTimeText);
    	textView.setText("Last updated: " + updateTime);
    }
    
    private void updateDependentStatus(){
    	String name = "";
    	if (SetupActivity.context != null){
        	Prefs.getPrefs(SetupActivity.context, SetupActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    		name = Prefs.getDependentName(SetupActivity.context);
    	}
    		
    	TextView textview = (TextView) findViewById(R.id.dependentStatusText);
    	String status;
    	Log.i("Main Activity", "Saved Name: "+name);
    	Log.i("Main Activity", "DependentStatus: "+updateDependentStatus);
    	int startBold = 0;
    	if (updateDependentStatus == null){
    		status = "Device Inactive";
    		startBold = status.length();
    	} else {
    		status = name + " is " + updateDependentStatus+".";
    		startBold = name.length() + 4;
    	}
    	SpannableString spanString = new SpannableString(status);
    	
    	spanString.setSpan(new StyleSpan(Typeface.BOLD), startBold, spanString.length(), 0);
    	textview.setText(spanString);
    }
    
    //Toast message displays device status after change of state
    public void displayDeviceStatusOnClick(){
    	deviceStatusSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    

    			if (isChecked) {
    				deviceStatusFlag = true;
    				Toast.makeText(getApplicationContext(), "Device is ON",
    						Toast.LENGTH_SHORT).show();
    			} else {
    				deviceStatusFlag = false;
    				Toast.makeText(getApplicationContext(),
    						"Device is OFF", Toast.LENGTH_SHORT).show();
    			}
    			//Prefs.getPrefs(context, MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    	        deviceID = Prefs.getDeviceID(context);
    			toggleInBackground();
    		}
    	});

    }
    
    public void switchToSettings(View view){
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    }
    
    public void switchToNotifHistory(View view){
    	Intent intent = new Intent(this, NotificationHistoryActivity.class);
    	startActivity(intent);
    }
    
    public void switchToSetup(View view){
    	Intent intent = new Intent(this, SetupActivity.class);
    	startActivity(intent);
    }
    
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    
    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        Prefs.setAppID(context, regId);
        Prefs.setAppVersion(context, appVersion);
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
    	Prefs.getPrefs(context, MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = Prefs.getAppID(context);
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = Prefs.getAppVersion(context);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        Log.i(TAG, "About to send to backend. RegId is: " + registrationId);
        sendRegistrationIdToBackend(registrationId);
        return registrationId;
    }
    
    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


    
    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;
                    Log.i(TAG, "RegID from Gcm is: " + regid);
                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend(regid);

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }


        }.execute(null, null, null);
    }
    
    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend(String regid) {
		String message = "{\"appID\": \""+regid+"\"}";
    	HttpResponse resp = ClientAdapter.postData(message, ClientAdapter.REGISTER_URL);
    }
    
    /**
     * Prompt user to enable WIFI if not already on.
     */
    public void enableWIFI()
    {
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if(!(wifiManager.isWifiEnabled())){
        	AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    this);

            // Setting Dialog Title
            alertDialog.setTitle("Confirm...");

            // Setting Dialog Message
            alertDialog.setMessage("WiFi is disabled. Do you want to go to WiFi settings?");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.ic_launcher);

            // Setting Positive "Yes" Button
            alertDialog.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Activity transfer to wifi settings
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });

            // Setting Negative "NO" Button
            alertDialog.setNeutralButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();
        }
        
    }

    private void toggleInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                boolean toggledStatus = deviceStatusSwitch.isChecked();
        		String message = deviceID+","+toggledStatus;
            	HttpResponse resp = ClientAdapter.postData(message, ClientAdapter.MANUAL_TOGGLE);            	
            	return null;
            }
        }.execute(null, null, null);
    }
    
    /*public void saveDepenedentName(String name){
    	Prefs.setDependentName(context, name);
    }*/
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
      
}

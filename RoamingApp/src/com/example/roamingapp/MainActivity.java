package com.example.roamingapp;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.Toast;

@SuppressLint("NewApi") public class MainActivity extends Activity {
	private Switch mySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        enableWIFI();
        
        mySwitch = (Switch) findViewById(R.id.on_off_switch);      
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            	
            	if (isChecked) {

            		Toast.makeText(getApplicationContext(), "Device is ON",
            		Toast.LENGTH_SHORT).show();

            		} else {

            		Toast.makeText(getApplicationContext(),
            		"Device is OFF", Toast.LENGTH_SHORT).show();
            		}
            }
        });
    }
    
    
    public void switchToSettings(View view){
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    }
    
    public void enableWIFI()
    {
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if(!(wifiManager.isWifiEnabled())){
        	AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    this);

            // Setting Dialog Title
            alertDialog.setTitle("Confirm...");

            // Setting Dialog Message
            alertDialog.setMessage("Do you want to go to wifi settings?");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.ic_launcher);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Activity transfer to wifi settings
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("no",
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
        
}

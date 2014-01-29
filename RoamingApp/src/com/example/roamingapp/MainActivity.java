package com.example.roamingapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
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
        

        mySwitch = (Switch) findViewById(R.id.autoTimeSwitch);
        
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            	
            	if (isChecked) {

            		Toast.makeText(getApplicationContext(), "The switch is ON",
            		Toast.LENGTH_SHORT).show();

            		} else {

            		Toast.makeText(getApplicationContext(),
            		"The switch is OFF", Toast.LENGTH_SHORT).show();
            		}
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
   /* @SuppressLint("NewApi") public void onSwitchClicked(View view) {
        // Is the toggle on?
        boolean on = ((Switch) view).isChecked();
        
        if (on) {
            // Enable vibrate
        } else {
            // Disable vibrate
        }
    }
*/
    
}

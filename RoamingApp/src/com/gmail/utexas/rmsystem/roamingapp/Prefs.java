package com.gmail.utexas.rmsystem.roamingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressLint("NewApi") public class Prefs {
    private static String device_id = "device id";
    private static String app_id = "app id";
    private static String app_version = "app version";
    private static String saved_settings = "saved settings";
    private static String dependent_name = "dependent name";
    private static String verif_code = "verif code";
    private static String pref_name = "pref name";
    private static int pref_privacy = 0;

    public static SharedPreferences getPrefs(Context context, String name, int privacy) {
    	pref_name = name;
    	pref_privacy = privacy;
        return context.getSharedPreferences(pref_name, pref_privacy);
    }
    
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(pref_name, pref_privacy);
    }
    
    public static String getAppID(Context context) {
        return getPrefs(context).getString(app_id, "phone not registered with gcm");
    }

    public static String getDeviceID(Context context) {
        return getPrefs(context).getString(device_id, "device not registered");
    }

    public static int getAppVersion(Context context) {
        return getPrefs(context).getInt(app_version, 0);
    }
    
    public static String getSettings(Context context) {
        return getPrefs(context).getString(saved_settings, "{}");
    }
    
    public static String getDependentName(Context context) {
    	return getPrefs(context).getString(dependent_name, "Dependent");
    }
    
    public static String getVerifCode(Context context) {
    	return getPrefs(context).getString(verif_code, "");
    }

    public static void setDeviceID(Context context, String value) {
        getPrefs(context).edit().putString(device_id, value).apply();
    }
    
    public static void setAppID(Context context, String value) {
        getPrefs(context).edit().putString(app_id, value).apply();
    }

    public static void setAppVersion(Context context, int value) {
        getPrefs(context).edit().putInt(app_version, value).apply();
    }
    
    public static void setDependentName(Context context, String value){
    	getPrefs(context).edit().putString(dependent_name, value).apply();
    }
    
    public static void setVerifCode(Context context, String value){
    	getPrefs(context).edit().putString(verif_code, value).apply();
    }
    
    //Saves json representation of Settings object to SharedPreferences
    public static void setSettings(Context context, String settings) {
        getPrefs(context).edit().putString(saved_settings, settings).apply();
    }
}

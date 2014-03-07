package com.example.roamingapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

public class ClientAdapter {
	//TODO fill out URL with server info
	private static Settings settingsToSend;
	private static String regIdToSend;
	final static String settingsURL = "http://rmsystem2014.appspot.com/settings";
	final static String registerURL = "http://rmsystem2014.appspot.com/register";
	
	public static HttpResponse postData(final Settings settings) {
	    // Create a new HttpClient and Post Header
	    settingsToSend = settings;	    

	    new Connection().execute();
	    
	    return null;
	} 
	
	public static HttpResponse postData(String regId){
		regIdToSend = regId;
		
		new Connection().execute();
	    
	    return null;
	}
	
	private static class Connection extends AsyncTask {
		 
        @Override
        protected HttpResponse doInBackground(Object... arg0) {
            HttpResponse response = connect();
            return response;
        }
 
    }
 
    private static HttpResponse connect() {
    	
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpPost httppost; httppost = new HttpPost(settingsURL);
    	
        try {
        	// Add your data
        	StringEntity entity;
        	if (settingsToSend != null) {
        		httppost = new HttpPost(settingsURL);
        		Gson gson = new Gson();
        		String json = gson.toJson(settingsToSend);
        		entity = new StringEntity(json);
        	} else {
        		httppost = new HttpPost(registerURL);
        		String sender = "{\"appID\": \""+regIdToSend+"\"}";
        		entity = new StringEntity(sender);
        	}
	    	
	        httppost.setEntity(entity);
        	
    	    HttpResponse response = httpclient.execute(httppost);
    	    
    	    return response;
        } catch (ClientProtocolException e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
            return null;
        }
    }
    
    /**
     * Whenever doInBackground finished, this method is
     * called with the doInBackground return value.
     */
    protected void onPostExecute(HttpResponse response) {
        /**
         * You need to check if any exceptions where
         * set in the background process and handle 
         * them.
         *
         * Check this.exception
         */
 
        // Do something with your value
    }
}

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
	final static String URL = "http://rmsystem2014.appspot.com/settings";
	
	public static HttpResponse postData(final Settings settings) {
	    // Create a new HttpClient and Post Header
	    settingsToSend = settings;	    

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
    	HttpPost httppost = new HttpPost(URL);
    	
        try {
        	// Add your data
	    	Gson gson = new Gson();
	    	String json = gson.toJson(settingsToSend);
	    	StringEntity entity = new StringEntity(json);
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

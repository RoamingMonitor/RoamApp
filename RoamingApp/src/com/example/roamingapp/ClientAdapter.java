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

import android.os.Looper;

public class ClientAdapter {
	//TODO fill out URL with server info
	final static String URL = "";
	
	public static HttpResponse postData(final Settings settings) {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    
	    HttpPost httppost = new HttpPost(URL);
	    

	    try {
	        // Add your data
	    	Gson gson = new Gson();
	    	String json = gson.toJson(settings);
	    	StringEntity entity = new StringEntity(json);
	        httppost.setEntity(entity);

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        return response;
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	    return null;
	} 
			
}

package com.example.roamingapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationLogMessage {
		public String messageTitle;
		public String dateAndTime;
		public String messageBody;
		
		public NotificationLogMessage(){} //Empty Constructor used for testing
		
		public NotificationLogMessage(JSONObject object){
	        try {
	            this.messageTitle = object.getString("messageTitle");
	            this.dateAndTime = object.getString("dateAndTime");
	            this.messageBody = object.getString("messageBody");
	       } catch (JSONException e) {
	            e.printStackTrace();
	       }
	    }

	    // User.fromJson(jsonArray);
	    public static ArrayList<NotificationLogMessage> fromJson(JSONArray jsonObjects) {
	           ArrayList<NotificationLogMessage> logMessages = new ArrayList<NotificationLogMessage>();
	           for (int i = 0; i < jsonObjects.length(); i++) {
	               try {
	                  logMessages.add(new NotificationLogMessage(jsonObjects.getJSONObject(i)));
	               } catch (JSONException e) {
	                  e.printStackTrace();
	               }
	          }
	          return logMessages;
	    }
	    
	    /*********** Set Methods ******************/
        
        public void setMessageTitle(String messageTitle)
        {
            this.messageTitle = messageTitle;
        }
         
        public void setDateAndTime(String dateAndTime)
        {
            this.dateAndTime = dateAndTime;
        }
         
        public void setMessageBody(String messageBody)
        {
            this.messageBody = messageBody;
        }
         
        /*********** Get Methods ****************/
         
        public String getMessageTitle()
        {
            return this.messageTitle;
        }
         
        public String getDateAndTime()
        {
            return this.dateAndTime;
        }
         
        public String getMessageBody()
        {
            return this.messageBody;
        }
}

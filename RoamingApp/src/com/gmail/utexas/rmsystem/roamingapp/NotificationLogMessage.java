package com.gmail.utexas.rmsystem.roamingapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationLogMessage {
		private String messageTitle;
		private String dateAndTime;
		private String messageBody;
		private String alertType;
		
		public NotificationLogMessage(){} //Empty Constructor used for testing
		
		public NotificationLogMessage(JSONObject object){
	        try {
	        	this.alertType = object.getString("alertType");
	            this.messageTitle = object.getString("messageTitle");
	            this.messageBody = object.getString("messageBody");
	            this.dateAndTime = object.getString("dateAndTime");
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
	    
	    //Getter and Setter methods used for testing
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
        
        public void setAlertType(String type)
        {
        	this.alertType = type;
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
        
        public String getAlertType()
        {
        	return this.alertType;
        }
}

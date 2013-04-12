package com.example.smartimageview;
import org.json.*;

import android.util.Log;

import com.loopj.android.http.*;

class TwitterRestClientUsage {
	
	private static final String TweetTAG = TwitterRestClientUsage.class.getName();
	
    public void getPublicTimeline() {
    	
    	Log.d(TweetTAG, "Twitter Time Line");
    	
        TwitterRestClient.get("statuses/user_timeline.json?include_entities=true&include_rts=true&screen_name=twitterapi&count=2", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray timeline) {
                try {
                	
                	Log.d(TweetTAG, "Twitter Success");
                	
                    JSONObject firstEvent = (JSONObject)timeline.get(0);
                    String tweetText = firstEvent.getString("text");

                    // Do something with the response
                    System.out.println(tweetText);
                } catch(JSONException e) {
                	Log.d(TweetTAG, "Twitter Exception");
                    e.printStackTrace();
                }
            }
        });
    }
}
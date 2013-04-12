package com.example.smartimageview;

import com.example.smartimageview.*;

import com.loopj.android.image.SmartImageView;
import com.loopj.android.http.*;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import org.json.*;


@SuppressWarnings("unused")
public class MainActivity extends Activity {
	
	
	private static final String TAG = MainActivity.class.getName();
	
	
//	Log.v("bopzy_debug", "Testing HTTP Connectivity");
//    System.out.println("123");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d(TAG, "Show tag writer");
		
		SmartImageView profileImage = (SmartImageView) findViewById(R.id.profileImage); 
	    profileImage.setImageUrl("http://2.bp.blogspot.com/-vFHPrffYx18/TWG2v0k9uZI/AAAAAAAAA8A/NGhnFtu-0Uw/s1600/mallard_duck.jpg");
	    
	    AsyncHttpClient client = new AsyncHttpClient();
	    client.get("http://www.google.com", new AsyncHttpResponseHandler() {
	        @Override
	        public void onSuccess(String response) {
	            System.out.println(response);
	            Log.d(TAG, "HTTP Request Success!!");
	        }
	    });
	    
	    TwitterRestClientUsage tweettime = new TwitterRestClientUsage();
	    tweettime.getPublicTimeline();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

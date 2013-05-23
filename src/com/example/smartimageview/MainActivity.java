package com.example.smartimageview;

import java.util.Hashtable;

import com.example.smartimageview.*;

import com.loopj.android.image.SmartImageView;
import com.loopj.android.http.*;
import com.pubnub.api.Pubnub;
import com.pubnub.api.Callback;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import org.json.*;


@SuppressWarnings("unused")
public class MainActivity extends Activity {
	
	Pubnub pubnub = new Pubnub("demo", "demo", "", false);
	
	private static final String TAG = MainActivity.class.getName();
	
	private void notifyUser(Object message) {
		try {
			if (message instanceof JSONObject) {
				final JSONObject obj = (JSONObject) message;
				this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), obj.toString(),
								Toast.LENGTH_LONG).show();

						Log.i("Received msg : ", String.valueOf(obj));
					}
				});

			} else if (message instanceof String) {
				final String obj = (String) message;
				this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), obj,
								Toast.LENGTH_LONG).show();
						Log.i("Received msg : ", obj.toString());
					}
				});

			} else if (message instanceof JSONArray) {
				final JSONArray obj = (JSONArray) message;
				this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), obj.toString(),
								Toast.LENGTH_LONG).show();
						Log.i("Received msg : ", obj.toString());
					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void subscribe() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Subscribe");
		builder.setMessage("Enter channel name");
		final EditText input = new EditText(this);
		builder.setView(input);
		builder.setPositiveButton("Subscribe",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Hashtable<String, String> args = new Hashtable<String, String>(1);

						String message = input.getText().toString();
						args.put("channel", message);

						try {
							pubnub.subscribe(args, new Callback() {
								public void connectCallback(String channel,
										Object message) {
									notifyUser("SUBSCRIBE : CONNECT on channel:"
											+ channel
											+ " : "
											+ message.getClass()
											+ " : "
											+ message.toString());
								}

								public void disconnectCallback(String channel,
										Object message) {
									notifyUser("SUBSCRIBE : DISCONNECT on channel:"
											+ channel
											+ " : "
											+ message.getClass()
											+ " : "
											+ message.toString());
								}

								@Override
								public void reconnectCallback(String channel,
										Object message) {
									notifyUser("SUBSCRIBE : RECONNECT on channel:"
											+ channel
											+ " : "
											+ message.getClass()
											+ " : "
											+ message.toString());
								}

								public void successCallback(String channel,
										Object message) {
									notifyUser("SUBSCRIBE : " + channel + " : "
											+ message.getClass() + " : "
											+ message.toString());
								}

								public void errorCallback(String channel,
										Object message) {
									notifyUser("SUBSCRIBE : ERROR on channel "
											+ channel + " : "
											+ message.getClass() + " : "
											+ message.toString());
								}
							});

						} catch (Exception e) {

						}
					}

				});
		AlertDialog alert = builder.create();
		alert.show();

	}
	
//	Log.v("bopzy_debug", "Testing HTTP Connectivity");
//    System.out.println("123");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent intent) {
				pubnub.disconnectAndResubscribe();

			}

		}, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		
		Log.d(TAG, "Show tag writer");
		
		SmartImageView profileImage = (SmartImageView) findViewById(R.id.profileImage); 
	    profileImage.setImageUrl("http://img.thesun.co.uk/multimedia/archive/01654/Panda_1_1654644a.jpg");
	    
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
	    
	    Log.d(TAG, "Subscribe");
	    subscribe();
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

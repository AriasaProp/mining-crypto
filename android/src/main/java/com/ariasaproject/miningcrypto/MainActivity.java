package com.ariasaproject.miningcrypto;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends Activity {
  static final String PREF_URI = "Uri";
  static final String PREF_USERNAME = "Username";
  static final String PREF_PASSWORD = "Password";
  static final String PREF_LOGS = "Logs";
  static final String PREF_LOGS_STATE = "Logs_State";

  EditText uri_value, username_value, password_value;
  Button mining_switch;
  
  ConsoleMessage co;
  ArrayList<String> console_logs;
  int[] console_logs_state = new int[20];
  
  @Override
  protected void onCreate(Bundle b) {
    setContentView(R.layout.main);
    super.onCreate(b);
    mining_switch = (Button) findViewById(R.id.mining_switch);
    uri_value = (EditText) findViewById(R.id.uri_value);
    username_value = (EditText) findViewById(R.id.username_value);
    password_value = (EditText) findViewById(R.id.password_value);
  	final ViewGroup ctr = (ViewGroup) findViewById(R.id.log_container);
    co = new ConsoleMessage() {
	  	final DateFormat logDateFormat = new SimpleDateFormat("HH:mm:ss|");
	  	@Override
	  	public void sendLog(ConsoleMessage.Message lvl, String msg) {
			TextView vt;
	  		for (int i = 19; i > 0; i--) {
				console_logs.set(i, console_logs.get(i-1));
				console_logs_state[i] = console_logs_state[i-1];
				vt = (TextView)ctr.getChildAt(i);
				vt.setText(console_logs.get(i));
				vt.setTextColor(console_logs_state[i]);
			}
			console_logs.set(0, logDateFormat.format(new Date()) + msg);
			switch (lvl) {
				default:
				case DEBUG:
					console_logs_state[0] = 0xffa3a3a3;
					break;
				case INFO:
					console_logs_state[0] = 0xffffffff;
					break;
				case SUCCESS:
					console_logs_state[0] = 0xff00ff00;
					break;
				case WARNING:
					console_logs_state[0] = 0xffffff00;
					break;
				case ERROR:
					console_logs_state[0] = 0xffff0000;
					break;
			}
			vt = (TextView)ctr.getChildAt(0);
			vt.setText(console_logs.get(0));
			vt.setTextColor(console_logs_state[0]);
		}
	  };
	if ((b != null) && b.containsKey(PREF_URI)) {
		uri_value.setText(b.getString(PREF_URI));
		username_value.setText(b.getString(PREF_USERNAME));
		password_value.setText(b.getString(PREF_PASSWORD));
		console_logs = b.getStringArrayList(PREF_LOGS);
		console_logs_state = b.getIntArray(PREF_LOGS_STATE);
	} else {
		console_logs = new ArrayList<String>(20);
		for (int i = 0; i < 20; i++) {
			console_logs.add("None");
			console_logs_state[i] = 0xffa3a3a3;
		}
	}
	co.sendLog(ConsoleMessage.Message.DEBUG, "Login App!");
  }
  
  @Override
  protected void onSaveInstanceState(Bundle b) {
  	super.onSaveInstanceState(b);
  	b.putString(PREF_URI, uri_value.getText().toString());
  	b.putString(PREF_USERNAME, username_value.getText().toString());
  	b.putString(PREF_PASSWORD, password_value.getText().toString());
  	b.putStringArrayList(PREF_LOGS, console_logs);
  	b.putIntArray(PREF_LOGS_STATE, console_logs_state);
  }
  Thread m_mining_thread = null;
  URL m_url;
  public void startstopMining(View v) {
  	if (m_mining_thread == null) {
  		final Button mine = (Button)v;
		mine.setEnabled(false);
		mine.setClickable(false);
		mine.setText("Starting ...");
	  	m_mining_thread = new Thread(new Runnable() {
	  		@Override
	  		public void run(){
					try {
				  	String tmp_uri = uri_value.getText().toString();
				  	String tmp_auth = String.format("%s:%s",
				  		username_value.getText().toString(),
				  		password_value.getText().toString());
						m_url = new URI(tmp_uri).toURL();
					} catch (Exception e) {
						co.sendLog(ConsoleMessage.Message.ERROR, e.getMessage());
						mine.setText("Mining Start");
						mine.setEnabled(true);
						mine.setClickable(true);
						Thread.currentThread().interrupt();
					}
					co.sendLog(ConsoleMessage.Message.DEBUG, "Begin!");
	  			try {
		  			int ct = 0;
		  			while (ct < 10) {
		  				Thread.sleep(1000);
		  				co.sendLog(ConsoleMessage.Message.DEBUG, "Work data "+ ct);
		  				ct++;
		  			}
	  			} catch (InterruptedException e) {
	  				co.sendLog(ConsoleMessage.Message.DEBUG, "Interrupted!");
	  			}
					co.sendLog(ConsoleMessage.Message.DEBUG, "Ended!");
					//thread Done!
					MainActivity.this.runOnUiThread(new Runnable(){
						@Override
						public void run(){
							mine.setText("Mining Start");
			  			mine.setEnabled(true);
			  			mine.setClickable(true);
			  			m_mining_thread = null;
						}
					});
	  		}
	  	});
	  	m_mining_thread.start();
		mine.setEnabled(true);
		mine.setClickable(true);
		mine.setText("Mining Stop");
  	} else {
  		m_mining_thread.interrupt();
  	}
  }
}

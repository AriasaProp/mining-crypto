package com.ariasaproject.miningcrypto;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends Activity {
  static final String PREF_URI = "Uri";
  static final String PREF_USERNAME = "Username";
  static final String PREF_PASSWORD = "Password";

  EditText uri_value, username_value, password_value;
  Button mining_switch;

  @Override
  protected void onCreate(Bundle b) {
    setContentView(R.layout.main);
    super.onCreate(b);
    mining_switch = (Button) findViewById(R.id.mining_switch);
    uri_value = (EditText) findViewById(R.id.uri_value);
    username_value = (EditText) findViewById(R.id.username_value);
    password_value = (EditText) findViewById(R.id.password_value);
  }
  
  private static final DateFormat logDateFormat = new SimpleDateFormat("HH:mm:ss =>");
  private int[] colorsLogLvl = new int[] {
  	0xffa3a3a3, //gray low
  	0xffffffff, //white info
  	0xff00ff00, //green succes
  	0xffffff00, //yellow warning
  	0xffff0000 //red error
  };

  public void log_A(int logLvl, String t) {
  	if (logLvl < 0)
  		logLvl = 0;
  	else if (logLvl > 4)
  		logLvl = 4;
  	ViewGroup ctr = (ViewGroup) findViewById(R.id.log_container);
  	int i = 0;
  	final int j = ctr.getChildCount();
  	TextView vt = (TextView)ctr.getChildAt(i);
  	String t1 = vt.getText().toString(), t2;
  	int c1 = vt.getCurrentTextColor(), c2;
  	vt.setText(logDateFormat.format(new Date()) + t);
  	vt.setTextColor(colorsLogLvl[logLvl]);
  	while(++i < j) {
		  vt = (TextView)ctr.getChildAt(i);
  		t2 = vt.getText().toString();
  		c2 = vt.getCurrentTextColor();
  		vt.setText(t1);
		  vt.setTextColor(c1);
		  t1 = t2;
		  c1 = c2;
		}
  }

  URI curURI;
  Random rm = new Random();
  public void startMining(View v) {
    String address = uri_value.getText().toString();
    String username = username_value.getText().toString();
    String password = password_value.getText().toString();
    /*try {
      curURI = new URI(address);
      log_A(rm.nextInt(5), String.format("uri=%s auth=%s:%s", curURI.toString(), username, password));
    } catch (URISyntaxException e) {
      Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
      Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }*/

    String url = address;
    String auth = String.format("%s:%s",username,password);
    int nThread = 1;
    double throttle = 1.0d;
    long scanTime = 500L;
    long retryPause = 500L;

    try {
    	new Miner(this, url, auth, scanTime, retryPause, nThread, throttle);
    } catch (Exception e) {
    	log_A(4, e.getMessage());
    }
    
  }
}

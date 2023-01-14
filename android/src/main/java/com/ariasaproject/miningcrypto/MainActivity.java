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
  
  private static final DateFormat logDateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ");
  private int[] colorsLogLvl = new int[] {
  	0xffa3a3a3, //gray low
  	0xffffffff, //white info
  	0xff4bb543, //green succes
  	0xffeed202, //yellow warning
  	0xffff9494 //red error
  };

  public void log_A(int logLvl, String t) {
  	if (logLvl < 0)
  		logLvl = 0;
  	else if (logLvl > 4)
  		logLvl = 4;
  	ViewGroup ctr = (ViewGroup) findViewById(R.id.log_container);
  	int i = 0;
  	final int j = ctr.getChildCount()-1;
  	TextView prev,next;
  	while(i < j) {
		  prev = (TextView)ctr.getChildAt(i);
		  next = (TextView)ctr.getChildAt(++i);
		  prev.setText(next.getText());
		  prev.setTextColor(next.getCurrentTextColor());
		}
	  next.setText(logDateFormat.format(new Date()) + t);
	  next.setTextColor(colorsLogLvl[logLvl]);
  }

  URI curURI;
  Random rm = new Random();
  public void startMining(View v) {
    String address = uri_value.getText().toString();
    String username = username_value.getText().toString();
    String password = password_value.getText().toString();
    try {
      curURI = new URI(address);
      Toast.makeText(
              v.getContext(),
              String.format("uri=%s auth=%s:%s", curURI.toString(), username, password),
              Toast.LENGTH_SHORT)
          .show();
      
      log_A(rm.nextInt(5), String.format("uri=%s auth=%s:%s", curURI.toString(), username, password));
    } catch (URISyntaxException e) {
      Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
      Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    /*
    String url = DEFAULT_URL;
    String auth = DEFAULT_AUTH;
    int nThread = Runtime.getRuntime().availableProcessors();
    double throttle = 1.0;
    long scanTime = DEFAULT_SCAN_TIME;
    long retryPause = DEFAULT_RETRY_PAUSE;

    if (args.length > 0 && args[0].equals("--help")) {
    	System.out.println("Usage:  java Miner [URL] [USERNAME:PASSWORD] [THREADS] [THROTTLE] [SCANTIME] [RETRYPAUSE]");
    	return;
    }

    if (args.length > 0) url = args[0];
    if (args.length > 1) auth = args[1];
    if (args.length > 2) nThread = Integer.parseInt(args[2]);
    if (args.length > 3) throttle = Double.parseDouble(args[3]);
    if (args.length > 4) scanTime = Integer.parseInt(args[4]) * 1000L;
    if (args.length > 5) retryPause = Integer.parseInt(args[5]) * 1000L;

    try {
    	new Miner(url, auth, scanTime, retryPause, nThread, throttle);
    } catch (Exception e) {
    	System.err.println(e.getMessage());
    }
    */
  }
}

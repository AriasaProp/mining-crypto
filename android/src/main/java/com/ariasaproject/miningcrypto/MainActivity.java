package com.ariasaproject.miningcrypto;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
  public void log_A(String t) {
  	ViewGroup ctr = (ViewGroup) findViewById(R.id.log_container);
  	int i = 0;
  	final int j = ctr.getChildCount()-1;
  	while(i < j) {
		  final TextView prev = (TextView)ctr.getChildAt(i);
		  final TextView next = (TextView)ctr.getChildAt(++i);
		  prev.setText(next.getText());
		}
	  final TextView cur = (TextView)ctr.getChildAt(i);
	  cur.setText(t);
  }

  URI curURI;

  public void startMining(View v) {
    String address = uri_value.getText().toString();
    String username = username_value.getText().toString();
    String password = password_value.getText().toString();
    try {
      curURI = new URI(address);
      Toast.makeText(
              v.getContext(),
              String.format("%s %s:%s", curURI.toString(), username, password),
              Toast.LENGTH_SHORT)
          .show();
      log_A(String.format("%s %s:%s", curURI.toString(), username, password));
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

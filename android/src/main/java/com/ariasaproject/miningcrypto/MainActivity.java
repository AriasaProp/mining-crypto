package com.ariasaproject.miningcrypto;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends Activity implements Runnable{
  static final String PREF_URI = "Uri";
  static final String PREF_USERNAME = "Username";
  static final String PREF_PASSWORD = "Password";

  EditText uri_value, username_value, password_value;
  Button mining_switch;
  
  ConsoleMessage co;
  @Override
  protected void onCreate(Bundle b) {
    setContentView(R.layout.main);
    super.onCreate(b);
    mining_switch = (Button) findViewById(R.id.mining_switch);
    uri_value = (EditText) findViewById(R.id.uri_value);
    username_value = (EditText) findViewById(R.id.username_value);
    password_value = (EditText) findViewById(R.id.password_value);
  	final ViewGroup ctr = (ViewGroup) findViewById(R.id.log_container);
  	final int j = ctr.getChildCount();
    co = new ConsoleMessage() {
		  private static final DateFormat logDateFormat = new SimpleDateFormat("HH:mm:ss =>");
		  private final int[] colorsLogLvl = new int[] {
		  	0xffa3a3a3, //gray debug
		  	0xffffffff, //white info
		  	0xff00ff00, //green succes
		  	0xffffff00, //yellow warning
		  	0xffff0000 //red error
		  };
	  	@Override
	  	public void sendLog(int lvl, String msg) {
	  		if (logLvl < 0)
		  		logLvl = 0;
		  	else if (logLvl > 4)
		  		logLvl = 4;
		  	int i = 0;
		  	TextView vt = (TextView)ctr.getChildAt(i);
		  	CharSequence t1 = vt.getText(), t2;
		  	int c1 = vt.getCurrentTextColor(), c2;
		  	vt.setText(logDateFormat.format(new Date()) + msg);
		  	vt.setTextColor(colorsLogLvl[logLvl]);
		  	while(++i < j) {
				  vt = (TextView)ctr.getChildAt(i);
		  		t2 = vt.getText();
		  		c2 = vt.getCurrentTextColor();
		  		vt.setText(t1);
				  vt.setTextColor(c1);
				  t1 = t2;
				  c1 = c2;
				}
	  	}
	  });
  }
  
  //URI curURI;
  public void startMining(View v) {
  	Thread t = new Thread(this);
  }
  @Override
  public void run () {
  	URI m_uri;
  	try {
  		co.sendLog(0, "begin");
  		//get and check data
  		co.sendLog(0, "get and check data");
  		String uri, auth;
  		synchronized (this) {
		    uri = uri_value.getText().toString();
		  	auth = username_value.getText().toString()+":"+password_value.getText().toString();
  		}
  		try {
  			m_uri = new URI(uri);
  		} catch (Exception e) {
  			co.sendLog(4, e.getMessage());
  			Thread.currentThread().interrupt()
  		}
  		co.sendLog(0, "check data");
	  	//check data
	  	int ct = 5;
  		while ((ct--) != 0) {
  			co.sendLog(0, "work data" +ct);
  			Thread.sleep(2000);
  		}
  	} catch (InterruptException e) {
  		//interupted by system to stop thread
  		co.sendLog(4, "ended");
  	}
  }
  
}

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

public class MainActivity extends Activity {
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
	  final DateFormat logDateFormat = new SimpleDateFormat("HH:mm:ss =>");
    co = new ConsoleMessage() {
	  	@Override
	  	public void sendLog(ConsoleMessage.Message lvl, String msg) {
	  		synchronized (MainActivity.this) {
			  	int i = 0;
			  	TextView vt = (TextView)ctr.getChildAt(i);
			  	CharSequence t1 = vt.getText(), t2;
			  	int c1 = vt.getCurrentTextColor(), c2;
			  	vt.setText(logDateFormat.format(new Date()) + msg);
		  		switch (lvl) {
		  			default:
		  			case DEBUG:
			  			vt.setTextColor(0xffa3a3a3);
		  				break;
		  			case INFO:
			  			vt.setTextColor(0xffffffff);
		  				break;
		  			case SUCCESS:
			  			vt.setTextColor(0xff00ff00);
		  				break;
		  			case WARNING:
			  			vt.setTextColor(0xffffff00);
		  				break;
		  			case ERROR:
			  			vt.setTextColor(0xffff0000);
		  				break;
		  		}
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
	  	}
	  };
  }
  Thread m_mining_thread = null;
  public void startstopMining(final View v) {
  	m_mining_thread = new Thread(new Runnable(){
  		@Override
  		public void run(){
  			mining_switch.setEnabled(false);
				co.sendLog(ConsoleMessage.Message.DEBUG, "Begin!");
  			try {
	  			int ct = 0;
	  			while (ct < 100) {
	  				co.sendLog(ConsoleMessage.Message.DEBUG, "Work data "+ ct);
	  				ct++;
	  				Thread.sleep(1000);
	  			}
  			} catch (InterruptedException e) {
  				//ignored
  				co.sendLog(ConsoleMessage.Message.DEBUG, "Interrupted!");
  			}
				co.sendLog(ConsoleMessage.Message.DEBUG, "Ended!");
  			mining_switch.setEnabled(true);
  		}
  	});
  }
}

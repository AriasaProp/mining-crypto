package com.ariasaproject.miningcrypto;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends Activity {
		final static String PREF_HOST = "Host";
		final static String PREF_PORT = "Port";
		final static String PREF_USERNAME = "Username";
		final static String PREF_PASSWORD = "Password";
		
		EditText host_value, port_value, username_value, password_value;
		Button mining_switch;
		
		@Override
		protected void onCreate(Bundle b) {
				setContentView(R.layout.main);
				super.onCreate(b);
				mining_switch = (Button) findViewById(R.id.mining_switch);
				host_value = (EditText) findViewById(R.id.host_value);
				port_value = (EditText) findViewById(R.id.port_value);
				username_value = (EditText) findViewById(R.id.username_value);
				password_value = (EditText) findViewById(R.id.password_value);
				/*
				if (b.containsKey(PREF_HOST)) {
						host_value.setText(b.getString(PREF_HOST));
						port_value.setText(String.valueOf(b.getShort(PREF_PORT)));
						username_value.setText(b.getString(PREF_USERNAME));
						password_value.setText(b.getString(PREF_PASSWORD));
						mining_switch.setEnabled(true);
						checkAllSettings();
				}
				*/
		}
		URI curURI;
		public void startMining(View v) {
				String host = host_value.getText().toString();
				try {
						InetAddress.getByName(host);
				} catch (UnknownHostException e) {
						host = e.getMessage();
				} catch (Exception e) {
						return;
				}
				short port = 8080;// Short.valueOf(host_value.getText().toString());
				String username = username_value.getText().toString();
				String password = password_value.getText().toString();
				try {
						curURI = new URI(String.format("%s:%d",host,port));
						Toast.makeText(v.getContext(), String.format("%s u:%s p:%s", curURI.toString(), username, password),Toast.LENGTH_SHORT).show();
				} catch (URISyntaxException e) {
						Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
				}
		}
		
}
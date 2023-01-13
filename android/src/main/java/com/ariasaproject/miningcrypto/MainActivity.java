package com.ariasaproject.miningcrypto;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

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
				username_value = (EditText) findViewById(R.id.user_value);
				password_value = (EditText) findViewById(R.id.password_value);
				host_value.setOnTextChangedListener(new TextWatcher {
						@Override
						public void afterTextChanged(Editable s) {
								//check text after input
								checkAllSettings();
						}
						@Override    
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
						@Override    
						public void onTextChanged(CharSequence s, int start, int before, int count) {}
				});
				port_value.setOnTextChangedListener(new TextWatcher {
						@Override
						public void afterTextChanged(Editable s) {
								//check text after input
								checkAllSettings();
						}
						@Override    
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
						@Override    
						public void onTextChanged(CharSequence s, int start, int before, int count) {}
				});
				username_value.setOnTextChangedListener(new TextWatcher {
						@Override
						public void afterTextChanged(Editable s) {
								//check text after input
								checkAllSettings();
						}
						@Override    
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
						@Override    
						public void onTextChanged(CharSequence s, int start, int before, int count) {}
				});
				password_value.setOnTextChangedListener(new TextWatcher {
						@Override
						public void afterTextChanged(Editable s) {
								//check text after input
								checkAllSettings();
						}
						@Override    
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
						@Override    
						public void onTextChanged(CharSequence s, int start, int before, int count) {}
				});
				if (b.containsKey(PREF_HOST)) {
						host_value.setText(b.getString(PREF_HOST));
						port_value.setText(String.valueOf(b.getShort(PREF_PORT)));
						username_value.setText(b.getString(PREF_USERNAME));
						password_value.setText(b.getString(PREF_PASSWORD));
						mining_switch.setEnabled(true);
						checkAllSettings();
				}
		}
		
		private void checkAllSettings() {
				String host = host_value.getText();
				String port = Short.parseShort(port_value.getText());
				String username = username_value.getText();
				String password = password_value.getText();
				Toast.makeText(getContext(), host+" "+port+" "+username+" "+password, Toast.LENGTH_SHORT);
		}
}
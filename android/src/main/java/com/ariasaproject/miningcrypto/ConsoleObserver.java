package com.ariasaproject.miningcrypto;

public class ConsoleObserver {
	final ConsoleListener reciever;
	
	public ConsoleObserver(ConsoleListener r) {
		receiver = r;
	}
	
	public void sendLog(int lvl, String msg) {
		reciever.receiveLog(lvl,msg);
	}
	
	public static interface ConsoleListener {
		public void receiveLog(int lvl, String msg);
	}
}
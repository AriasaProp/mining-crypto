package com.ariasaproject.miningcrypto;

public class ConsoleObserver {
	final ConsoleListener getter;
	
	public ConsoleObserver(ConsoleListener r) {
		getter = r;
	}
	
	public void sendLog(int lvl, String msg) {
		getter.receiveLog(lvl,msg);
	}
	
	public static interface ConsoleListener {
		public void receiveLog(int lvl, String msg);
	}
}
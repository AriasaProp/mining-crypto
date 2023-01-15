package com.ariasaproject.miningcrypto;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class Miner implements Observer {

  private Worker worker;
  private long lastWorkTime;
  private long lastWorkHashes;
  MainActivity act;
  final Thread mT;

  public Miner( MainActivity act, String url, String auth, long scanTime, long retryPause, int nThread, double throttle) {
    this.act = act;
    if (nThread < 1) throw new IllegalArgumentException("Invalid number of threads: " + nThread);
    if (throttle <= 0.0 || throttle > 1.0)
      throw new IllegalArgumentException("Invalid throttle: " + throttle);
    if (scanTime < 1L) throw new IllegalArgumentException("Invalid scan time: " + scanTime);
    if (retryPause < 0L) throw new IllegalArgumentException("Invalid retry pause: " + retryPause);
    try {
      worker = new Worker(new URL(url), auth, scanTime, retryPause, nThread, throttle);
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("Invalid URL: " + url);
    }
    worker.addObserver(this);
    mT = new Thread(worker);
    mT.setPriority(Thread.MIN_PRIORITY);
    mT.start();
    log(nThread + " miner threads started");
  }

  private static final DateFormat logDateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ");

  public void log(String str) {
  	act.log_A(0, logDateFormat.format(new Date()) + str);
  }

  public void update(Observable o, Object arg) {
    switch ((Worker.Notification) arg) {
    	case SYSTEM_ERROR:
	      act.log_A(4, "System error");
	      mT.interrupt();
    		break;
    	case PERMISSION_ERROR:
	      act.log_A(4, "Permission error");
	      mT.interrupt();
    		break;
    	case AUTHENTICATION_ERROR:
	      act.log_A(4, "Invalid worker username or password");
	      mT.interrupt();
    		break;
    	case CONNECTION_ERROR:
      	act.log_A(4, "Connection error, retrying in " + worker.getRetryPause() / 1000L + " seconds");
    		break;
    	case COMMUNICATION_ERROR:
      	act.log_A(4, "Communication error");
    		break;
    	case LONG_POLLING_FAILED:
      	act.log_A(3, "Long polling failed");
    		break;
    	case LONG_POLLING_ENABLED:
      	act.log_A(2, "Long polling activated");
    		break;
    	case NEW_BLOCK_DETECTED:
      	act.log_A(1, "LONGPOLL detected new block");
    		break;
    	case POW_TRUE:
      	act.log_A(1, "PROOF OF WORK RESULT: true (yay!!!)");
    		break;
    	case POW_FALSE:
      	act.log_A(1, "PROOF OF WORK RESULT: false (booooo)");
    		break;
    	case NEW_WORK:
    		if (lastWorkTime > 0L) {
	        long hashes = worker.getHashes() - lastWorkHashes;
	        float speed = (float) hashes / Math.max(1, System.currentTimeMillis() - lastWorkTime);
	        log(String.format("%d hashes, %.2f khash/s", hashes, speed));
	      }
	      lastWorkTime = System.currentTimeMillis();
	      lastWorkHashes = worker.getHashes();
    		break;
  		default:
    		break;
    }
  }
}

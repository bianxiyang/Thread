package com.thread;

import java.util.concurrent.TimeUnit;
/**
 * volatile关键字
 * @author bian_xy
 *
 */
public class ThreadVolatile {

	 volatile boolean running = true;
	
	

	void m() {
		System.out.println("m start");
		while (running) {

//			try {
//				TimeUnit.SECONDS.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

		}
		System.out.println("m end");
	}

	public static void main(String[] args) {

		ThreadVolatile tv = new ThreadVolatile();

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				tv.m();
			}
		}, "t1");
		thread.start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		tv.running = false;

	}

}

package com.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 门栓，倒数
 * 
 * 
 * 可以从中间拦截，如果是join只能等线程结束才可以加入
 * 
 * 
 * 使用countDownLatch可以随时加入
 * @author bian_xy
 *
 */
public class CountDownLatchTest {
	
	public static void main(String[] args) {
		usingCountDownLatch();
		
		usingJoin();
		
	}
	
	private static void usingCountDownLatch() {
		Thread[] threads   = new Thread[100];
		
		CountDownLatch latch = new CountDownLatch(threads.length);
		
		for(int i=0;i<threads.length;i++) {
			threads[i] = new Thread() {
				@Override
				public void run() {
					int result = 0;
					for(int j=0;j<10000;j++) {
						result +=j;
					}
					latch.countDown();
				}
				
			};
		}
		for(int i=0;i<threads.length;i++) {
			threads[i].start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end latch");
	}
	
	
	private static void usingJoin() {
		Thread[] threads = new Thread[100];
		
		for(int i=0;i<threads.length;i++) {
			threads[i] = new Thread() {
				@Override
				public void run() {
					int result = 0;
					for(int j=0;j<10000;j++) {
						result +=j;
					}
				}
				
			};
		}
		for(int i=0;i<threads.length;i++) {
			threads[i].start();
		}
		for(int i=0;i<threads.length;i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("end join");
		
	}
	
	
	
}

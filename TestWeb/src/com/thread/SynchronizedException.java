package com.thread;

import java.util.concurrent.TimeUnit;

/**
 * 	异常会释放锁
 * @author bian_xy
 * 
 *
 */
public class SynchronizedException {
	
	int count = 0;
	
	synchronized void m() {
		System.out.println("START");
		
		while(true) {
			count++;
			System.out.println(Thread.currentThread().getName()+" count="+count);
			
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(count==5) {
				int i = 1/0;
				System.out.println(i);
			}
		}
		
	};
	
	
	public static void main(String[] args) {
		
		SynchronizedException e =new SynchronizedException();
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				e.m();
				
			}
		};
		new Thread(r,"t1").start();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		new Thread(r,"t2").start();
		
		
	}
	
	
	
}

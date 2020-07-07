package com.thread;

import java.util.concurrent.TimeUnit;

/**
 * 	可重入锁
 * 
 * 
 * 	m1持有这把锁，由于synchronized是可重入锁，所以锁的都是当前的这个对象，可以重复的使用这把锁，
 * 	如果不可重入，则m2不会执行，
 * 
 * 	如果父类synchronized，子类也有synchronized。如果不可重入，没法调用了，父子如果锁定的是this，就是同一把锁
 * 
 * @author bian_xy
 *
 */
public class ReentrantLock1 {
	synchronized void m1() {
		for(int i=0;i<10;i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(i==2) {
				m2();
			}
			
		}
		
	}
	
	
	synchronized void m2() {
		System.out.println("m2......");
	}
	
	
	public static void main(String[] args) {
		ReentrantLock1 rl = new ReentrantLock1();
		new Thread() {
			@Override
			public void run() {
				rl.m1();
			}
		}.start();
		
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
//		new Thread() {
//			@Override
//			public void run() {
//				rl.m2();
//			}
//		}.start();
		
		
	}
}

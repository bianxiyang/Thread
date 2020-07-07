package com.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock
 * 	可以锁定，但是注意一定要解锁
 * 
 * 
 * @author bian_xy
 *
 */
public class ReentrantLock2 {
	Lock lock = new ReentrantLock();
	
	void m1() {
			try {
				lock.lock();
				for(int i=0;i<10;i++) {
					TimeUnit.SECONDS.sleep(1);
					System.out.println(i);
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		
	}
	
	
	void m2() {
		try {
			lock.lock();
			System.out.println("m2.....");
		} finally {
			lock.unlock();
		}
		
	}
	public static void main(String[] args) {
		ReentrantLock2 rl = new ReentrantLock2();
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
		
		new Thread() {
			@Override
			public void run() {
				rl.m2();
			}
		}.start();
		
		
		
	}
	
}	

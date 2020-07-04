package com.thread;

import java.util.concurrent.TimeUnit;
/**
 * synchronized是一种可重入锁，两个synchronized的方法，其中m1调用m2，会发现当前m1获得锁，调用m2的时候发现，锁还是同一把锁，可重入,否则会死锁
 * @author bian_xy
 *
 */
public class SynchronizedTest2 {
	
	synchronized void m1() {
		System.out.println("m1 start");
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		m2();
		System.out.println("m1 end");
		
	}
	
	synchronized void m2() {
		System.out.println("m2 start");
		
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("m2 end");
	}
	
	
	public static void main(String[] args) {
		
		new SynchronizedTest2().m1();
	}
}

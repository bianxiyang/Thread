package com.thread;
/**
 * CAS的无锁优化，使用JUC包中的自旋锁
 * @author Administrator
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
	AtomicInteger count = new AtomicInteger();
	
	void m() {
		for(int i=0;i<10000;i++) {
			count.incrementAndGet();//相当于count++，但是线程不安全
		}
	}
	
	public static void main(String[] args) {
		
		AtomicTest vt = new AtomicTest();
		
		
		List<Thread> threads = new ArrayList<Thread>();
		
		for(int j=0;j<10;j++) {
			Thread thread = new Thread() {
				public void run() {
					vt.m();
				};
			};
			threads.add(thread);
		}
		
		threads.forEach((th)->th.start());
		
		threads.forEach((th)->{
			try {
				th.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println(vt.count);
		
		
	}
	
}

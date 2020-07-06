package com.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile只能保证可见性，不能保证一致性
 * @author Administrator
 *
 */
public class VolatileTest {
	/* volatile */ int count = 0;
	
	synchronized void  m() {
		for(int i=0;i<10000;i++) {
			
				count++;
			
		}
	}
	
	
	public static void main(String[] args) {
		VolatileTest vt = new VolatileTest();
		
		
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

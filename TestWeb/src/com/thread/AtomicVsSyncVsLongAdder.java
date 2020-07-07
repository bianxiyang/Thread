package com.thread;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * atomic 对比sync 对比longAdder
 * @author bian_xy
 * 1000个线程，十万次操作
 *	
 *	AtomicLong:100000000 time 1490
	sync:100000000 time 7388
	LongAdder100000000 time 1104(有一个分段锁的概念)
 *
 *
 */
public class AtomicVsSyncVsLongAdder {
	static AtomicLong count1 = new AtomicLong();
	static long count2 = 0L;
	static LongAdder count3  = new LongAdder();
	
	
	
	public static void main(String[] args) throws Exception{
		
		
		//Atomic
		Thread[] threads = new Thread[1000];
		for(int i=0;i<threads.length;i++) {
			threads[i] = new Thread() {
				@Override
				public void run() {
					for(int k=0;k<100000;k++) {
						count1.incrementAndGet();
					}
				}
			};
		}
		long start = System.currentTimeMillis();
		for(Thread t:threads) {
			t.start();
		}
		for(Thread t:threads) {
			t.join();//保证线程有序进行
		}
		
		long end = System.currentTimeMillis();
		System.out.println("AtomicLong:"+count1.get()+" time "+(end-start));
		
		//普通count++
		Object lock = new Object();
		for(int i=0;i<threads.length;i++) {
			threads[i] = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(int k=0;k<100000;k++) {
						synchronized (lock) {
							count2++;
						}
						
					}
				}
			});
		}
		start = System.currentTimeMillis();
		for(Thread t:threads) {
			t.start();
		}
		for(Thread t:threads) {
			t.join();//保证线程有序进行
		}
		
		end = System.currentTimeMillis();
		System.out.println("sync:"+count2+" time "+(end-start));
		//LongAdder
		for(int i=0;i<threads.length;i++) {
			threads[i] = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(int k=0;k<100000;k++) {
							count3.increment();
					}
				}
			});
		}
		start = System.currentTimeMillis();
		for(Thread t:threads) {
			t.start();
		}
		for(Thread t:threads) {
			t.join();//保证线程有序进行
		}
		
		end = System.currentTimeMillis();
		System.out.println("LongAdder"+count3+" time "+(end-start));
		
		
		
		
	}
	
	
}

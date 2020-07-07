package com.thread;

/**
 * 
 * 
 * 线程常用方法 Thread.sleep(5000):线程循环调用CPU执行，当前线程睡眠5000毫秒，让给其他线程来调用CPU
 * Thread.yield():暂时让出一下CPU，进入到等待队列里
 * join:加入到当前线程中，常用于等待一个线程的结束，面试题（保证三个线程按顺序执行，t1执行后，joint2,t2执行完执行t3,或者t1.join()--->t2.join()--->t3.join()）
 *
 *
 */

public class ThreadMethod {
	
	
		Thread t1 = new Thread() {
			@Override
			public void run() {
				for(int i=0;i<10;i++) {
					
					System.out.println("t1");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		
		
		
		
		Thread t2 = new Thread() {
			public void run() {
				for(int j=0;j<10;j++) {
					System.out.println("t222222");
					if(j==5) {
						try {
							t1.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
		};
		
		
		
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		ThreadMethod m = new ThreadMethod();
//		m.t1.start();
		m.t2.start();
	}
	
	
	
}

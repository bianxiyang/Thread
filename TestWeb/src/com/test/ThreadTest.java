package com.test;

public class ThreadTest {
	public static void main(String[] args) {
		
		//第一种创建线程的方法
		Thread thread = new Thread(){
			@Override
			public void run() {
				
				while(true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("thread1");
				}
				
			}
		};
		thread.start();
		
		
		
		//第二种创建线程
		
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while(true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("thread2");
				}
			}
		}) {
			
		};
		
		thread2.start();
		
		
	}
	
	
	
	
}

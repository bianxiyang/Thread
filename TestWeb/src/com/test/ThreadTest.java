package com.test;

public class ThreadTest {
	public static void main(String[] args) {
		
		Thread thread = new Thread(){
			@Override
			public void run() {
				
				while(true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("1213");
				}
				
			}
		};
		thread.start();
	}
}

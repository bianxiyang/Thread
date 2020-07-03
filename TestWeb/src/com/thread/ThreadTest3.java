package com.thread;
/**
 *
 *	实现runnable接口
 *
 */
public class ThreadTest3 implements Runnable{

	@Override
	public void run() {
		
		for(int i=0;i<10;i++) {
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i);
		}
	}
	
	public static void main(String[] args) {
		
		new ThreadTest3().run();
		
		
		
	}
	
	
	
}

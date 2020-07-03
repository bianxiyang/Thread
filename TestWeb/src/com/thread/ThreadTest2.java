package com.thread;
/**
 *	继承Thread类，两种调用方式
 *
 */
public class ThreadTest2 extends Thread{
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
		//第一种启动Thread的子类中重写的run();
//		new ThreadTest2().run();
		
		//第二种启动ThreadTest2线程
		new ThreadTest2().start();
		

		
	}
}

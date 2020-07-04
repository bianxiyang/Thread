package com.thread;
/**
 * 
 * 	锁，循环对count--,--的同时要锁定当前操作，完成后才可以让其他线程进行操作
 * 
 * 
 * @author bian_xy
 *
 */
public class SynchronizedTest implements Runnable{
	
	private int count = 10;
	
	
	@Override
	public  synchronized void run() {
		count--;
		System.out.println(Thread.currentThread().getName()+" count="+ count);
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		
		SynchronizedTest test = new SynchronizedTest();
		
		for(int i=0;i<10;i++) {

			new Thread(test).start();
		}
		
		
	}
	
}

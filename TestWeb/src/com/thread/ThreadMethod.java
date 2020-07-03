package com.thread;
/**
 * 
 * 
 *	线程常用方法
 *	Thread.sleep(5000):线程循环调用CPU执行，当前线程睡眠5000毫秒，让给其他线程来调用CPU
 *	Thread.yield():暂时让出一下CPU，进入到等待队列里
 *	join:加入到当前线程中，常用于等待一个线程的结束，面试题（保证三个线程按顺序执行，t1执行后，joint2,t2执行完执行t3,或者t1.join()--->t2.join()--->t3.join()）
 *
 *
 */

public class ThreadMethod extends Thread{
	@Override
	public void run() {
		while(true) {
			try {
				//线程的休眠方法
				Thread.sleep(5000);
				System.out.println(ThreadMethod.class.getName());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		
		ThreadMethod method = new ThreadMethod();
		method.start();
		
	}
	
}

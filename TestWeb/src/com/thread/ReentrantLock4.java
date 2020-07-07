package com.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  ReentrantLock可以对打断状态做出响应
 * @author bian_xy
 *
 */
public class ReentrantLock4 {
	public static void main(String[] args) {
		
		Lock lock = new ReentrantLock();
		
		
		Thread thread1 = new Thread() {
			@Override
			public void run() {
				
				try {
					lock.lock();
					System.out.println("t1 start");
					TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
					System.out.println("t1 end");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					lock.unlock();
				}
				
			}
		};
		
		thread1.start();
		
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				try {
					lock.lockInterruptibly();//可以对interrupt（）方法做出相应
					System.out.println("t2 start");
					TimeUnit.SECONDS.sleep(5);
					System.out.println("t2 end");
				} catch (InterruptedException e) {
					System.out.println("interrupted");
					e.printStackTrace();
				}finally {
					lock.unlock();
				}
				
			}
		};
		
		thread2.start();
		
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		thread2.interrupt();//打断线程2 的等待
		
		
	}
}

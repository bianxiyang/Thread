package com.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * new ReentrantLock(true);
 *	其中可以传一个参数true ,为公平锁
 * 	公平锁，如果当前很多线程都在等待队列里等待锁的获取，此时有一个新的线程来参与，
 *	 如果是非公平锁，不会检查等待队列中是否有线程正在等待，会有几率获得锁；
 *	如果是公平锁，会检查等待队列中是否有线程正在等待，如果有，排队等待
 * 
 * @author bian_xy
 *
 */
public class ReentrantLock5 {
	private static ReentrantLock lock =new ReentrantLock(true);
//	private static ReentrantLock lock =new ReentrantLock(false);
	
	public void run() {
		for(int i=0;i<100;i++) {
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName()+" 获得锁");
			} finally {
				lock.unlock();
			}
		}
	}
	
	public static void main(String[] args) {
		ReentrantLock5 rl5 = new ReentrantLock5();
		Thread thread1 = new Thread() {
			@Override
			public void run() {
				rl5.run();
			}
		};
		thread1.start();
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				rl5.run();
			}
		};
		thread2.start();
	}
	
}

package com.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock的优点和synchronized相比是可以使用try lock ,尝试锁定，可以设置一个时间。在时间
 * 	范围内是否可以拿到锁，m1方法执行时间要十秒，所以m2在五秒的时候肯定是拿不到锁的
 * @author Administrator
 *
 */
public class ReentrantLock3 {
	Lock lock = new ReentrantLock();

	void m1() {
		try {
			lock.lock();
			for (int i = 0; i < 10; i++) {
				TimeUnit.SECONDS.sleep(1);
				System.out.println(i);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}

	void m2() {
		
		boolean locked = false;
		try {
			locked = lock.tryLock(5, TimeUnit.SECONDS);
			System.out.println("m2...."+locked);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(locked) {
				lock.unlock();
			}
		}
		
		
	}

	public static void main(String[] args) {
		ReentrantLock3 rl = new ReentrantLock3();
		new Thread() {
			@Override
			public void run() {
				rl.m1();
			}
		}.start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Thread() {
			@Override
			public void run() {
				rl.m2();
			}
		}.start();

	}
}

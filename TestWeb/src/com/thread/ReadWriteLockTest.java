package com.thread;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * @author bian_xy
 *	比如部门组织结构，读取的场景非常多，但是很少更改,
 *
 *	如果是读取的时候，下一个线程也是读取的线程，允许
 *	如果是写的线程，不允许
 *
 *
 *	共享锁（读锁），排他锁（写锁）
 *
 *
 *
 *
 */
public class ReadWriteLockTest {
	static Lock lock = new ReentrantLock();
	
	private static  int value;
	
	
	static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	static Lock readLock = readWriteLock.readLock();
	static Lock writeLock = readWriteLock.writeLock();
	
	public static void read(Lock lock) {
		lock.lock();
		try {
			Thread.sleep(1000);
			System.out.println("read over");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	public static void write(Lock lock,int v) {
		lock.lock();
		try {
			Thread.sleep(1000);
			value = v;
			
			System.out.println("write over");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		
		Runnable readR = new Runnable() {
			
			@Override
			public void run() {
//				read(lock);
				read(readLock);
				
				
			}
		};
		
		
		Runnable writeR = new Runnable() {
			
			@Override
			public void run() {
//				write(lock,new Random().nextInt());
				
				write(writeLock,new Random().nextInt());
				
				
			}
		};
		
		
		
		for(int i=0;i<18;i++) {
			new Thread(readR) {
			}.start();
		}
		
		for(int j=0;j<2;j++) {
			new Thread(writeR) {
			}.start();
		}
		
	}
	
	
	
	
}

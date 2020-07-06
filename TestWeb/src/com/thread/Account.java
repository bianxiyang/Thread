package com.thread;

import java.util.concurrent.TimeUnit;

/**
 * 	模拟一个账户加锁，对写方法和读方法加锁
 * 
 * 	dirty read(脏读)
 * 
 * 	给张三设置100余额，在1秒后就getBalance，此时还没有给余额setBalance,如果不加锁，会读出来0.0，
 * 	然后第二秒后，setBalance成功，此刻，余额才是正确的
 * 
 * @author bian_xy
 *
 */
public class Account {
	
	
	String name;
	
	double balance;

	public synchronized void setBalance(String name,double balance) {
		this.name  = name;
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.balance = balance;
	}

	public /* synchronized */  double getBalance(String name) {
		return this.balance;
	}

	
	
	
	public static void main(String[] args) {
		
		Account a = new Account();
		new Thread(()->a.setBalance("张三", 100.0)).start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(a.getBalance("张三"));
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(a.getBalance("张三"));
		
		
	}
	
	
	
}

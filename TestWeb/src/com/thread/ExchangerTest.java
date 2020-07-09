package com.thread;

import java.util.concurrent.Exchanger;

/**
 * 交换器，线程中间交换数据使用的
 * @author bian_xy
 *
 */
public class ExchangerTest {
	static Exchanger<String> exchanger = new Exchanger<>();
	
	public static void main(String[] args) {
		
		new Thread() {
			String s = "T1";
			@Override
			public void run() {
				try {
					s = exchanger.exchange(s);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("T1线程"+" "+ s);
				
			}
		}.start();
		
		
		new Thread() {
			String s = "T2";
			@Override
			public void run() {
				try {
					s = exchanger.exchange(s);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("T2线程"+" "+ s);
				
			}
		}.start();
		
		
	}
	
	
	
	
}

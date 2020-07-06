package com.thread;

import java.io.ObjectInputStream.GetField;

/**
 * 	懒汉式的单例模式，什么时候获取实例的时候，再new
 * @author Administrator
 *
 */
public class Singleton_LH {
	
	
	private static Singleton_LH instance;
	
	
	//构造方法私有化
	private Singleton_LH() {
		
	}
	
	
	public static /* synchronized */ Singleton_LH getInstance() {
		
		if(instance==null) {
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			instance = new Singleton_LH();
		}
		return instance;
	}
	
	public static void main(String[] args) {
		
		for(int i=0;i<10;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println(Singleton_LH.getInstance().hashCode());
					
				}
			}).start();
		}
		
		
	}
	
}

package com.thread;

/**
 * 	懒汉式的单例模式，什么时候获取实例的时候，再new，
 * 
 * 
 * 	更细化的懒汉式加锁，在判断实例为空的时候加锁，双重检查
 * @author Administrator
 *
 */
public class Singleton_LH2 {
	
	
	private static volatile  Singleton_LH2 instance;
	
	
	//构造方法私有化
	private Singleton_LH2() {
		
	}
	
	
	public static  Singleton_LH2 getInstance() {
		
		if(instance==null) {
			
			synchronized (Singleton_LH2.class) {
				//双重检查锁
				if(instance==null) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					instance = new Singleton_LH2();
				}
				
			}
			
			
		}
		return instance;
	}
	
	public static void main(String[] args) {
		
		for(int i=0;i<10;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println(Singleton_LH2.getInstance().hashCode());
					
				}
			}).start();
		}
		
		
	}
	
}

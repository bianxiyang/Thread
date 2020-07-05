package com.thread;
/**
 * 	饿汉模式单例
 * 	构造方法私有化，只有我自己的类可以new当前的对象，其他类不可以new,生成的只有一个实例，
 * 	
 * 
 * 	获得当前实例的方法，getInstance,来获得我自己new出来的这个对象
 * 
 * 	先初始化，然后在调用
 * 
 * 	
 * @author bian_xy
 *
 */
public class Singleton_EH {
	
	
	private static final Singleton_EH instance = new Singleton_EH();
	
	
	//无参构造私有化
	private  Singleton_EH() {
		
	}
	
	public static Singleton_EH getInstance() {
		return instance;
	}
	
	
	public static void main(String[] args) {
		
		Singleton_EH s1 = Singleton_EH.getInstance();
		Singleton_EH s2 = Singleton_EH.getInstance();
		
		System.out.println(s1==s2);
	}
	
	
}

package com.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏：人满了，栅栏放下
 * 
 * 20个数字栅栏放下一次,一共100 ，分成五批
 * 
 *  具体的应用实例
 *  
 *  有很多操作，读取数据库，读取网络，读取文件并发执行
 *  
 *  可以使用CyclicBarrier，多个线程，都执行结束后，统一做后续的操作
 *  
 * 
 * 
 * 
 * @author bian_xy
 *
 */
public class CyclicBarrierTest {
	public static void main(String[] args) {
		
		
		
		/* CyclicBarrier barrier = new CyclicBarrier(20); */
		
		
		
		CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
			
			@Override
			public void run() {
				System.out.println("满人 发车");
			}
		});
		
		
		for(int i=0;i<100;i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						barrier.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		}
		
		
		
		
		
	}
}	

package com.thread;

import java.util.concurrent.Semaphore;

/**
 * 信号灯,亮着的时候可以执行，灭的时候不能执行
 * 
 * acquire()阻塞方法，获得，取得这把锁
 * 
 * release()释放
 * 
 * @author bian_xy
 *
 */
public class SemaphoreTest {
	public static void main(String[] args) {
		
		Semaphore s = new Semaphore(1);
		
		
		
		new Thread(){
			@Override
			public void run() {
				try {
					s.acquire();
					System.out.println("T1 running");
					Thread.sleep(200);
					System.out.println("T1 running");
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					s.release();
				}
			}
		}.start();
		
		
		new Thread(){
			@Override
			public void run() {
				try {
					s.acquire();
					System.out.println("T2 running");
					Thread.sleep(200);
					System.out.println("T2 running");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					s.release();
				}
			}
		}.start();
		
		
		
	}
}

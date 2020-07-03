package com.thread;
/**
 * 
 *	线程的几种状态
 *
 *	new Thread():new状态	
 *	start():线程调度器来执行，runnable状态（其中还包含着ready和running）
 *			ready:放到等待队列中，等待CPU来运行
 *			running:cpu执行
 *
 *			注：调用Thread.yield(),会把线程从running状态变成ready状态
 *				被调度器选中执行的时候，会从ready--->running
 *				从running--->ready线程被挂起
 *
 *				
 *	teminated:结束状态，到了此状态后不可重新调用start(),重新周而复始	
 *	
 *	在runnable状态中，还有三种变种状态
 *	1.timedWaiting(时间等待)：从ready状态调用了sleep(time),wait(time),LockSupport.parkNanos(),LockSupport.parkUntil()
 *	2.waiting(等待状态)：wait(),join(),LockSupport.park()
 *	3.blocked(阻塞):ready等待进入同步代码块的锁会阻塞，获得锁之后会运行
 *
 *
 *
 *
 *
 */
public class ThreadStatus {
	
	Thread t1 = new Thread() {
		@Override
		public void run() {
			for(int i=0;i<10;i++) {
				
				System.out.println("t1");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	
	
	
	
	Thread t2 = new Thread() {
		public void run() {
			for(int j=0;j<10;j++) {
				System.out.println("t222222");
				if(j==4) {
					try {
						t1.join();
						System.out.println(t2.getState());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	};
	
	
	







public static void main(String[] args) {
	
	ThreadMethod m = new ThreadMethod();
	
	System.out.println(m.t1.getState());
	
	m.t1.start();
	m.t2.start();
	
	System.out.println(m.t2.getState());
	
}
	
	
	
}

# 多线程笔记


一.多线程创建方式

1.继承thread类

	详见com.thread.ThreadTest.java

2.实现runnable接口

	详见com.thread.ThreadTest.java

3.继承callable接口

	详见com.thread.CallableTest.java

4.线程常用方法
	
	详见com.thread.ThreadMethod.java
	
5.线程常用状态
	
	详见com.thread.ThreadStatus.java

6.线程运行轨迹





new------------->NEW状态------start()------->RUNNABLE状态------------->TEMINATED状态

										<--线程被挂起
							ready------线程被调度器选中执行------->Running
										<--Thread.yield()
										
										
							|								|
							|Thread.sleep(time)				|
							|wait(time)						|
							|join(time)						|
							|LockSupport parkNanos()			|
							|LockSupport.parkUntil()			|
							|------------>TimeWaiting----------->
										
										
										
							|								|
							|wait()							|
							|join()							|
							|LockSupport park()				|
							|------------>Waiting----------->			
										
										
										
							|								|
							|								|
							|								|
							|								|
							|------------>Blocked----------->				
						等待进入同步代码块的锁					获得锁
7.锁的机制和synchronized关键字									
	
	
	在多个线程同时操作同一个资源的时候，需要进行锁定 ，锁定的不是资源，是来判断锁的归属到底属于谁，当前的锁属于A ，A 可以进行操作
	
	锁的是一个对象的前两位，来判断是否锁定（00，01,10,11），可以new Object(),但是正常情况都会synchronized(this),来锁定当前对象					
										
										
										
										
										
										
										
										
										
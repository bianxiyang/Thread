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
										
	
										
										
										
										
										
										
										
										
										
										
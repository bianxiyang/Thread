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


new------->NEW状态------start()-------------->RUNNABLE状态----------------------------------------->TEMINATED状态

							|			<--线程被挂起			|	
							ready------线程被调度器选中执行------->Running
							|			<--Thread.yield()		|
							|								|
							|								|
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
	
	锁的是一个对象的前两位，来判断是否锁定（00，01,10,11），可以new Object(),但是正常情况都会synchronized(this),来锁定当前对象,如果是static类，锁定的是xxx.class
	
	synchronized(Object):对象不能使用String常量,Integer,Long等基础数据类型
						
	简单的锁机制详见com.thread.SynchronizedTest.java
	
	用户模拟账户详见com.thread.Account.java							
										
	synchronized是可重入锁，两个synchronized的方法，m1调m2,获得的是同一把锁，允许，详见com.thread.SynchronizedTest2							
										
	如果程序中出现异常，锁会被释放详见com.thread.SynchronizedException									

8.synchronized具体的底层的实现

	早起的synchronized是非常重量级的，需要找操作系统要资源，效率非常的慢jdk1.5
	后来的改造有一个锁升级的概念
							
	sync(object)	
	markword,第一个获得锁的对象，不会记录锁的状态，只会记录一下线程的id(偏向锁)，而且不会有其他的线程来争夺锁的所有权
	
	如果有线程争用，升级为自旋锁，自动旋10次之后，如果锁还没被释放，升级为重量级锁（去操作系统申请资源加锁）
				
	执行时间（加锁代码）短，线程数量少用自旋锁(atomic,lock)；
	执行时间（加锁代码）长，线程数量比较多用重量级锁(synchronized)；			

9.volatile的概念
	
	可变的，易变的 
	
	关键字有两个作用：
	
	A：保证线程可见性
	
		加了个volatile关键字，保证一个线程发生改变，另一个线程就能看到
		
		MESI:缓存一致性协议
	
	B：禁止指令重排序(CPU)
		DCL单例
		Double check lock
			例子：com.thread.Singleton_LH2  单例模式中的双重检查锁，虽然不加volatile也不会影响执行结果，但是会有指令重排序的问题
		
		int a = 8会分成三步
		
		1.给a申请内存,并且赋默认值a=0;
		2.给a赋值 a=8;
		3.把a的栈中的实例指向a=8.
		
		如果发生指令重排序，则123步顺序会变更

	volatile只能保证可见性，不能保证一致性详见实例com.thread.VolatileTest
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
						
										
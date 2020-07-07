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

8.synchronized具体的底层的实现（悲观锁）

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

10.锁的优化机制synchronized

	a.把锁的粒度变细：当某个方法前后都有处理逻辑，中间需要加锁，那么锁的位置要加在中间，而不是整个方法
	b.当锁的争用十分频繁，有很多细小颗粒的锁，那么优化成一个整个的锁，争用就没有那么频繁，锁的粗化
	
11.CAS(无锁优化，自旋)compareAndSet，其实跟乐观锁的概念是一样的

	由于很多业务模块，需要频繁的加锁，所以java提供了一些类来加自旋锁，而不是重量级锁	
	
	java.util.concurrent.atomic.*
	
	原理
	举例：AtomicInteger
	
	cas(V,Expected,NewValue)
	
	
	v:目前的值
	Expected:期望的值
	NewValue：新值
	
	
	目前是3 ，期望是3 ，新值是4 ，正常操作
	目前是3 ，期望是4，就再试一次otherwise try again or fail
		目前是4 ，期望是4，正常操作
		
	问题：如果在V和Expected比较的时候，其他线程进入修改怎么办？？
	A：CAS是CPU原语上的支持，无法干预
	
12.ABA问题
	
	CAS想把1变成2
	
	在执行CAS之前，有其他线程把1变成2 ，又从2变成了1，想解决ABA问题，要加一个版本号 使用AtomicStampedReference
	
	ABA问题，如果是基础类型，无所谓
	如果是引用类型，就会出问题

13.高并发问题(1000个线程，每个线程10万次操作)详见com.thread.AtomicVsSyncVsLongAdder
	
	atomic(无锁操作)
	synchronized（锁操作）
	longAdder（线程数多的时候，有优势）：内部是一个分段锁的概念，类似于一个数组，比如四个数组，每个数组放250个线程，
	各自合计，最后sum
	
14.基于CAS的新类型的锁
	
	synchronized：详见com.thread.ReentrantLock1
	
	lock:详见com.thread.ReentrantLock2
	
	lock的优点：详见com.thread.ReentrantLock3；详见com.thread.ReentrantLock4
	
	公平锁：详见com.thread.ReentrantLock5
						
										
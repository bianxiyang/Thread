##GC垃圾回收


1.什么是垃圾

	自动内存回收，编程上简单，系统不容易出错，手动释放内存，容易出现两种类型的问题
	1.忘记回收（内存泄漏）
	2.多次回收
	
	没有任何引用指向的一个对象或者多个对象（循环引用）
2.如何定位垃圾
	
	1.reference count(引用计数)：到底有多少个引用指向了他
	
	这种寻找的机制，如果遇到循环引用，会找不到，计数都是1 ，外部没有引用，就会发生一堆垃圾无法回收，造成内存泄漏
	
	
	所以在jvm寻找垃圾的机制是root searching 根搜索算法
	
	GC roots 根对象：线程栈变量，静态变量，常量池，JNI指针
	
	通过根搜索算法，通过根对象找不到的，全是垃圾，根对象有引用的不是垃圾
3.常见的垃圾回收算法

	1.mark-sweep（标记清除）：找出垃圾，把他标记成非垃圾，告知可以使用了
		弊端：位置不连续，产生碎片
	2.Copying(拷贝)：将非垃圾的部分，拷贝到另一半的区域，然后将现有部分全部清除
		弊端：没有碎片，但是浪费空间
	3.Mark-compact(标记压缩)：一边标记垃圾，一边整理位置
		弊端：没有碎片，效率偏低
	
4.JVM内存分代模型
	
	主要是把内存区域分成两代
	
	new -young(新生代):装的是刚刚new出来的对象,把刚new出来的对象，有用的对象放到survivor，然后清空eden(Copying算法)
	
		eden
		survivor
		survivor 
	
	
	
	
	
	
	old(老年代)：如果经历过eden，和两个survivor都没有被回收，放到老年代，使用的垃圾回收算法一般是标记清除，或者是标记压缩
	
	
	
	永久代（1.7）/元数据区（1.8）metaspace:都是装class对象
		永久代必须指定大小限制，元数据区可以无上限
		
5.10种垃圾回收器
	
	
	parNew（new）			Serial（new）			ParallelScavenge（new）
	
	
	CMS(old)				SerialOld(old)		ParallelOld(old)
	
	
	G1		ZGC			Shenandoah
	
	
	Epsilon
	
		
	1.最早的serial（单线程，承受几十兆）
	
	单线程执行，垃圾满了之后，停止一切线程，清理完继续使用，serialOld就是在老年代执行单线程操作		
	
	2.ParallelScavenge（多线程，承受几个G）
	
	
	3.Parnew和CMS
	
		CMS:concurrent mark sweep---并发的标记清除算法
		全称：a mostly concurrent,low-pause collector
		四个阶段
		A initial mark(初始标记)
		B concurrent mark（并发标记）
		C remark（重新标记）
		D concurrent sweep（并发清理）
		
		 
		初始标记出来垃圾，
		并发标记的同时，会出现一种情况，原来被标记成垃圾的对象，重新被使用，会出现新的使用对象呗清除掉，所以才会有重新标记
		
	
		最重要的是重新标记的这一步骤，怎样找到错误标记或者遗漏标记的，使用的是三色标记算法
	4.G1（逻辑分代，物理不分代）
		地区化，可以是eden,也可以是old,也可以是survivor
		
6.JVM初始参数

	java -XX:+PrintCommandLineFlags -version（windows）
	java -XX:+PrintFlagsFinal -version(linux)
	
	
	-Xms 最小堆
	-Xmx 最大堆
	
	jps:查询java进程的进程号
	jinfo 进程号：打印相关信息
	jstat -gc 进程号  （毫秒数）：整个堆内存的信息
	
	jstack 进程号：把进程里所有的线程都列出来，一般用来发现有没有死锁
	
7.什么是调优
	
	1.根据需求进行JVM规划和预调优
	
	2.优化运行JVM运行环境（慢，卡顿）
	
	3.解决JVM运行过程中出现的各种问题（OOM）
	

		
	
#synchronized底层

1.CAS（compareAndSwap比较并交换）----无锁，自旋锁

一个对象的构成
	T{
		int m = 8;
	}

	markwork  8个字节
	class pointer
	m 四个字节
	padding(填充位置)
	

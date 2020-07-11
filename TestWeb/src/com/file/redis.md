##REDIS


1.互联网的3V和三高

	海量（Volume）,多样（Variety）,实时（Velocity）
	高并发，高可扩，高性能
	
2.关系型数据库和非关系数据库的对比
	
	传统的关系型数据库最重要的就是ACID ，四个条件都要满足
		A:Atomicity原子性
		C：Consistency一致性
		I：Isolation独立性
		D：Durability持久性
	非关系型数据库重要的是CAP	，三个条件，满足两个
		C:consistency强一致性
		A：Availability(可用性)
		P：Partition tolerance(分区容错性)
		
3.BASE就是为了解决关系数据库强一致性引起的问题而引起的可用性降低而提出的解决方案


	基本可用（Basically Available）
	软引用（SoftState）
	最终一致（Eventually consistent）
	
	
4.redis


	Remote Dictionary	Server(远程字典服务器)
	
	
	redis之所以替代了Memcached
	a:支持数据的持久化
	b:提供了丰富的数据结构，string（字符串），hash（哈希），list（列表），set（集合）及zset(sorted set：有序集合)。
	c:redis支持数据的备份


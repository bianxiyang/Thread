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

	
	windows版本的安装
	https://github.com/dmajkic/redis/downloads
	
	
	redis是个单进程
	set K V
	GET K V
	默认16个数据库：select 7 切换数据库，0-15，一共16个数据库
	Dbsize查看当前数据库的key的数量 keys * 列出来所有的key,支持&占位符操作，查询k1,k2,k3..... 可以使用keys k?
	flushdb:清空当前库	flushall清空所有16个数据库
	统一密码管理：16个库都是同样的密码，要么都OK 要么一个也连不上
	redis索引从0开始
	默认端口是6379
	
5.五大数据类型，基本操作和配置
	
	
	String：字符串
		是redis最基本的数据类型，类型是二进制安全的，可以包含任何数据，比如jpg或者序列化的对象，
		一个String的value最多可以是512M
	Hash：哈希
		是一个键值对集合，是一个String类型的field 和value的映射表，hash特别适合存储对象
		类似于Map<String,Object>
	List：列表
		是最简单的字符串列表，按照插入顺序排序，可以添加一个元素到列表的头部，或者尾部，底层是一个链表（LinkedList）
	Set：集合
		是String类型的无序集合，是通过HashTable实现
	Zset(sorted set:有序集合)
		zset和set一样也是String类型元素的集合，且不允许重复的成员
		不同的是每个元素都会关联一个double类型的分数
		是通过分数来为集合中的成员进行从小到大的排序，成员是唯一的，但是分数可以重复，（例如100分以上的有10万人，50分以上的有5万人）
	



	常用的数据类型的操作命令：http://redisdoc.com/
	
	
	（key）:exists测试是否存在
	
	
	


6.持久化和复制，RDB/AOF



7.事务的控制




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
		是最简单的字符串列表，按照插入顺序排序，可以添加一个元素到列表的头部，或者尾部，底层是一个链表（LinkedList）  LPUSH mylist 2345      LRANGE mylist 0 -1
	Set：集合
		是String类型的无序集合，是通过HashTable实现
	Zset(sorted set:有序集合)
		zset和set一样也是String类型元素的集合，且不允许重复的成员
		不同的是每个元素都会关联一个double类型的分数
		是通过分数来为集合中的成员进行从小到大的排序，成员是唯一的，但是分数可以重复，（例如100分以上的有10万人，50分以上的有5万人）
	



	常用的数据类型的操作命令：http://redisdoc.com/
	
	
	（key）:
	exists测试是否存在
	move：（剪切） movie k3 2（把k3移到三号库）
	expire key 秒数
	ttl key 查看还有多少秒过期，-1表示永不过期，-2表示已经过期
	type key 查看key是什么类型
	 
	
	（String）:
	incr/decr/incrby/decrby  加减（数字）
	getrange:类似于between and
	setrange:范围内覆盖
	setex(set with expire)过期时间
	setnx（set if not exist）如果不存在才覆盖
	mset/mget批量操作，多个操作
	msetnx:如果不存在，则set ,（如果存在+不存在，结果为都失败）
	
	
	（list）
	LPUSH:正序进，反着出
	RPUSH:怎么进怎么出
	LRANGE:LRANGE mylist 0 -1
	lpop:栈顶的移除
	rpop:栈底的移除
	lindex:索引
	llen:长度
	lrem key 删N 个value
	ltrim key 开始index 结束index :截取制定范围的值在赋值给key+
	ropolpush :list1 6,5,4   list 0  执行后 list 1 6,5   list2 4,0
	lset key index value :将指定索引的地方，覆盖某个值
	linsert key befor/after 值1 值2
	
	总结：是一个字符串链表，left ,right都可以插入添加
	键不存在，创建新的链表
	键已存在，新增内容
	值全移除，对应的键也消失
	链表无论头和为效率都极高，中间操作，效率就低了	
	
	
	（Set不允许重复的，无序）
	单值多个value
	
	sadd set01 1 1 2 2 3 3 (只会存进去三个)
	smembers set01列出所有的值
	sismember set01 1(查询当前元素是否在set里)
	scard:获取集合里的元素个数
	srem key value：移除某个值
	srandmember key：随机出数
	spop key :随机出栈
	smove key1 key2 在key1里某个值，将key1的某个值赋给key2
	数学集合类：sdiff差集
			sinter交集
			sunion并集
	
	
	
	(Hash)
	KV模式不变，但V是一个键值对
	
	hset/hget/hmset/hmget/hgetall/hdel
	
	
	（单个赋值）
	hset user id 11
	hset user name tom
	
	(多个赋值)
	hmset
	hmset employeeinfo id 01 name lala age 27
	hmget employeeinfo id name age
	hgetall employeeinfo：全部取出来 
	hlen employeeinfo
	hexists key :在key里面的某个值的key
	hkeys/hvals:遍历key 
	hincrby/hincrbyfloat：增长（整数，小数）
	hsetnx：如果不存在，set
	
	
	
	(Zset)
	在set基础上，加一个score值
	set是 k1 v1 v2 v3
	Zset 是k1 score1 v1 score2 v2
	zadd zset01 60 v1 70 v2 80 v3 90 v4 100 v5
	zrange zset01 0 -1：查询出来所有
	zrange zset01 0 -1 withscores：带分数
	zrangebyscore zset01 60 80：分数范围
	zrangebyscore zset01 60 (80：大于等于60，小于80
	zrangebyscore zset01 60 100 limit 2 2
	zrem zset01 v5
	zcard zset01  4



6.解析配置文件redis.conf

	####INCLUDES####
	可以包含其他配置文件
	
	tcp-backlog:一个连接队列  ，backlog的队列总和 = 未完成三次握手队列+已经完成三次握手队列
	高并发的情况下你需要一个高backlog的值来避免慢客户端的连接问题，注意linux内核会将这个值减小到/procsys/net/core/somaxconn的值
	所以要增大somaxconn和tcp_max_syn_backlog两个值
	
	
	timeout:超时时间
	tcp-keepalive:单位为秒，如果设置为0，则不会进行keepalive的检测，建议设置成60
	log-level:日志级别，debug<verbose<notice<warning
	logfile:日志文件
	syslog-enabled no：是否把日志输出到syslog中
	
	
	SNAPSHOTTING快照
		save：自动化的保存rdb文件，立刻就保存
		
		bgsave:后台异步进行快照操作
		
		stop-writes-on-bgsave-error:后台保存出错了，停止写入，默认是yes
			如果配置改成no,表示你不在乎数据一致性或者有其他手段发现和控制
		rdbcompression：对于存储到磁盘中的快照，可以设置是否进行压缩存储
		rdbchecksum:存储快照以后，还可以让redis使用CRC64算法进行数据校验，但是这样做会增加大约10%的性能消耗
			如果希望获取到最大的性能提升，可以关闭此功能
	
	security(安全)：config get requirepass 查询密码
				config set requirepass "123456"  设置密码
				auth 123456密码验证
	
	
	limits限制：
		maxclients:
		maxmemory
		maxmemory-policy:缓存过期清除策略（noeviction永不过期，一般不会这样配置）
			1.volatile-lru:使用LRU算法移除key，只对设置了过期时间的键
			2.allkeys-lru:使用LRU算法移除key
			3.volatile-random：在过期集合中移除随机的key,只对设置了过期时间的键
			4.allkeys-random：移除随机的key
			5.volatile-ttl:移除那些ttl值最小的key,即那些最近要过期的key
			6.noeviction不进行移除，针对写操作，只是返回错误信息
		maxmemory-samples
			设置样本数量，是一个估算值。
	
	
	
	


6.持久化和复制，RDB/AOF


	RDB:redis database
	
		在指定的时间间隔内将内存中的数据集快照写入磁盘
		也就是snapshot快照，它恢复时是将快照文件直接读到内存里
		
		如果需要大规模的数据恢复，且对于数据恢复的完整性不是非常敏感，RDB方式比AOF更加的高效，缺点是最后一次
		持久化的数据可能丢失
		
		fork:复制一个与当前线程一样的线程，新线程的所有数据（变量，环境变量，程序计数器等）数值都和原进程一样，但是是一个
		全新的进程，并作为原进程的子进程
		要注意的是：如果当前系统很大，fork一份就更慢了
		
		Rdb保存的是dump.rdb文件
		配置文件的位置：查询是否有启动，ps -ef|grep redis
		
		
		rdb文件恢复策略，在清空并关机后立刻生成最新的空的rdb文件，如果想备份，再恢复，数据才不会受影响
		
	
	AOF:append only file
		以日志的形式来记录每个写操作，将Redis执行过得所有写指令记录下来（读操作不记录）
		
		只追加文件但不可以改写文件，redis启动之初会读取该文件重新构建结构，redis重启就根据日志文件内容
		将写指令从前到后执行一次以完成数据恢复。
		
		配置：
			该功能默认是关闭的需要更改appendonly no --->yes
			appendfsync:always:同步持久化，每次发生数据变更会立即记录到磁盘，性能较差但是数据完整性好
						everysec:默认设置，异步操作，每秒记录
						no
		
   		
		RDB和AOF可以共存，先找aof，如果aof被损坏，可以使用redis-check-aof进行修复：redis-check-aof --fix appendonly.aof
		
		
		Rewrite:appendonly.aof越来越大，新增了重写机制，当aof的文件大小达到了所设定的阈值，Redis会启动
			AOF文件的压缩，只保留可以恢复数据的最小指令集，可以使用命令bgrewriteaof
			
			会fork出一条新的进程来重写，遍历新进程的内存中数据，每条记录有一条的set语句，重写没有读老的aof文件，而是将
			整个内存中的数据内容重写了一个新的aof文件，类似于快照
		触发机制:redis会记录上一次重写的aof的大小，默认配置是当aof文件大小是上次rewrite后大小的一倍且文件大于64M时触发
				auto-aof-rewrite-min-size
		
		AOF的优势：每秒同步，每修改同步，不同步
		AOF的劣势：aof文件很大，恢复速度慢
				运行效率慢，每秒同步策略效率好，不同步效率和rdb相同
		


7.事务的控制

	redis的事务：可以一次执行多个命令，本质是一组命令的集合，一个事务中的所有命令都会序列化（按顺序的串行的执行）
	
	在一个队列中，一次性，顺序性，排他性的执行一系列命令
	
	常用命令：
	
	discard:取消事务，放弃执行事务块内的所有命令
	exec:执行所有事务块内的命令
	multi:标记一个事务块的开始
	unwatch:取消watc命令对所有key的监视
	watch key[key......]:监控一个或者多个key,如果在事务执行之前这个key被其他命令所改动，那么事务将被打断
	
	
		case1:正常执行：multi开启事务，exec执行事务
		case2:放弃事务：multi开启事务，discard取消事务
		case3:全体连坐：multi开启事务，执行错一个，全体挂掉
		case4：冤头债主：谁出了问题找谁
			case3和case4的区别：连坐是在执行的时候，都无法通过，case4更像是运行时异常，命令没有错，执行过程中出现问题
		
		case5:watch监控：
		
			悲观锁/乐观锁/CAS:
			初始化信用卡可用余额和欠款
			A:无加塞篡改，先监控在开启multi,保证两笔金额变动在同一个事物内
				set balance 100----可用余额
				set debt 0	----欠款
				watch balance
				multi（开启事务）
				decrby balance 20
				incrby debt 20
				exec
			
			B：有加塞篡改
				在消费的同时，余额被篡改了，事务无法提交成功，
				要重新获取余额的值，UNwatch，直到成功为止
				unwatch:
				
			一旦执行了exec之前加的监控锁都会被取消
			
			
			watch的指令类似于乐观锁，如果事务提交时，值已经被修改了，整个事务不会被执行
			
			通过watch命令在事务执行之前监控了多个keys,倘若在watch之后有任何的key发生了变化
			exec命令执行的事务都将被放弃，同时返回NullMulti-bulk应答以通知调用者事务执行失败
	
	
	
	事务的总结：开启：以multi开始一个事务
			入队：讲多个命令入队到事务中，接到这些命令并不会立即执行，而是放到等待执行事务的队列里面
			执行：有exec命令触发事务
	
	特性：单独的隔离操作：事务中的所有命令都会序列化，按顺序的执行。事务在执行的过程中，不会被其他客户端发来的命令锁打断
		没有隔离级别的概念，队列中的命令没有提交之前都不会实际的被执行，因为事务提交之前任何指令都不会实际执行
		不保证原子性：redis同一事务中如果有一个命令执行失败，其后的命仍然会被执行，没有回滚
	

8.Redis的发布订阅

	进程间的一种消息通信模式，发送者（PUB）发送消息，订阅者（SUB）接收信息
	
	
	subscribe c1 c2 c3订阅了c1 c2 c3
	publish c2 hello-redis发布了c2
	
	
	订阅多个，通配符*，psubscribe new*
	
	
	
	
9.Redis的主从复制（Master/Stave）





10.Jedis



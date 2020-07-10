#MQ消息中间件

1.发送和接受是异步的
2.消息服务器存放在若干的队列或者主题中

	队列就是queue，一对一
	主题就是朋友圈公众号
	
	发布Pub/订阅Sub
	
	发送者发布了一个消息，只能给订阅了的接受者发送，一个消息就有多个接受的人
3.安装步骤

	官网下载
	/opt目录下面
	解压缩
	在根目录 mkdir /myactivemq
	cp -r apache-activemq-5.15.13 /myactivemq/
	
	启动：./activemq start
	activemq默认端口号是61616
	查询进程：ps -ef|grep activemq
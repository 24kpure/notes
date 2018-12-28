之前是用kafka，不过新项目尝试新东西，消息队列打算使用rocketmq。

一.简单比较下

1.吞吐 在数据量小的时候，其实效率没什么差，不过kafka的吞吐是真的暴力，rocketmq虽然吞吐略弱。

2.时延  rocketmq时延较短，我自己试的时候是2-3ms。

3.rocketmq不需要依靠于zookeeper，主从根据设置决定，不会有角色变换。

4.其它层次尚未实践，比如丢消息（kafka虽然非事务，不过确实没见过丢几次消息）

二.安装

[官网传送门 ](http://rocketmq.apache.org/docs/quick-start/)

runbroker.sh

runserver.sh

两个文件虚拟机参数记得调小，默认4g过大

三.监控

[git地址](https://github.com/apache/rocketmq-externals/tree/master/rocketmq-console)   需要更改配置文件中 namesrc信息，系统中直接改不生效。

语言支持中文很赞~

四.java代码

1.producer长启动，不能即用即启关。

2.consumer可以根据tags过滤很赞。

五.注意点

1.多网卡的时候 配置文件需要配置主机ip

以下文件示例  cat conf/2m-2s-async/broker-a.properties

brokerClusterName=post
brokerName=broker-a
brokerId=0
namesrcAddr=119.3.29.17:9876
brokerIP1=119.3.29.17 //这里标识
listenPort=10911
deleteWhen=04
fileReservedTime=48
brokerRole=ASYNC_MASTER
flushDiskType=ASYNC_FLUSH
autoCreateTopicEnable=true

启动脚本

nohup bin/mqbroker -c conf/2m-2s-async/broker-a.properties >/dev/null 2>&1  
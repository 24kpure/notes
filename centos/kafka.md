##### 从别人手上接手了一个消息队列为核心的项目，使用的kafka中间很多不顺利，不过现在都福大命大了

安装：

基于zookeeper那么集群最少三台，之前是用三台服务器配置的，不过建议使用docker了现在

官网默认安装是普通安装 参考：http://kafka.apache.org/intro

##### 逻辑梳理：

服务基于broker，消息以topic独立，支持多个group消费，不同group的offset独立。异常服务宕机，或者kafka服务宕机会有几率导致offset重置

##### 常用命令：

1.新建topic
./kafka-topics.sh --create --zookeeper 集群任意ip:消费端口 --replication-factor 2 --partitions 1 --topic名
2.创建一个生产者
./kafka-console-producer.sh --broker-list 集群任意ip:生产端口 --topic topic名
3.创建消费者
./kafka-console-consumer.sh --zookeeper 集群任意ip:消费端口 --topic topic名 --from-beginning
4.查看topic
./kafka-topics.sh --list --zookeeper localhost:消费端口
5.查看topic状态
./kafka-topics.sh --describe --zookeeper localhost:12181 --topic topic名
6.查看topic group组下offset偏移
./kafka-run-class.sh kafka.tools.ConsumerOffsetChecker --zookeeper localhost:2181 --group  wsccc-v6  --topic wsccc-v6
/opt/kafka_2.9.1-0.8.2.1/bin/kafka-run-class.sh kafka.tools.ConsumerOffsetChecker --zookeeper localhost:2181 --group  wsccc-v6  --topic wsccc-v6
7.更改offset
 bin/kafka-run-class.sh kafka.tools.UpdateOffsetsInZK  latest config/consumer.properties wsccc-v6 
注意config中groupid配置






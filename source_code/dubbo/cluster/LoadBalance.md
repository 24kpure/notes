### 1.AbstractLoadBalance
#### 概述：所有实现类的父类，入口select方法，最终调用子类doSelect
#### 核心方法:
- getWeight
> 获取配置权重
> 启动前权重降低 公式 weight= uptime/warmTime * configWeight
> warmTime(预热时间) =（默认,下同）10min 
#### 2.ConsistentHashLoadBalance 
#### 概述：一致性hash，根据invokers.hashCode缓存,前argumentIndex参数一致必定负载到同一个invoker
#### 核心方法:
- ConsistentHashSelector
>每个invokeUrl根据地址散列各个分区，实际调用根据参数值匹配最近散列分区(treeMap.ceilingEntry)
>replicaNumber(分区数)=160
>argumentIndex(前x参与hash计算参数)=0
#### 3.LeastActiveLoadBalance
#### 概述: 最少优先
#### 核心方法 doSelect
```java
RpcStatus.getStatus(invoker.getUrl(), invocation.getMethodName()).getActive()
```
> 最小调用数invoker(数量相同比对weight)
#### 4.RandomLoadBalance
#### 概述:根据权重加权随机调用
#### 核心方法 doSelect

#### 5.RoundRobinLoadBalance
#### 概述: 加权随机 窗口期60秒
#### 核心方法 doSelect
> 各invoker根据权重自增,值最高的invoker扣除总权重。
#### 6.ShortestResponseLoadBalance
#### 概述：最小平均返回时间invoker优先
#### 核心方法 doSelect
```java
RpcStatus rpcStatus = RpcStatus.getStatus(invoker.getUrl(), invocation.getMethodName());
long succeededAverageElapsed = rpcStatus.getSucceededAverageElapsed();
```
> 获取invoker成功请求的总时间/成功总次数 最小的优先

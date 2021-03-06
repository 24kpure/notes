## 说说那些高大上！

之前面试，或者和别人闲聊，总会不可避免提到几个词，分布式，集群，负载均衡。那时候一知半解（可能连半解也算牵强），总觉得这些东西很高大上，今天整理了下概念，就说那些高大上！

### 一.集群要分成三大类( 高可用集群， 负载均衡集群，科学计算集群)

```
1.高可用集群，我的理解是一种主备形式，在主机宕机时候，备用机可以接替主机的任务，保证高可用性，稳定性。
```

举个例子吧，就是一个店里其实有两个员工，我们称为员工a，员工b。一般都是a上班，可以维持店的正常经营，然后b也经常过来逛逛（数据同步）熟悉业务，当a生病请假的时候，这时候b就来上班，由于b对店铺状况和a一样了解，店铺的经营活动还可以保持不变，我们称为高可用性。

```
2.负载均衡集群，我觉得也可以做个比喻。
```

一家商店，有多个导购员，然而这些导购员都不能主动接待用户，只有老板去指派你去接待某个客户，导购员才会屁颠屁颠跑去接待客户。老板就是负载均衡器，他的心情就是调度规则（开玩笑~），导购员就是集群中的主机，指派去接待客户就是调度，客户可以理解成请求。（还有一种特殊的情况，就是老板其实也算一个导购员，就是负载均衡器所在的那台主机，也会被分发一些业务，只不过老板做事情，肯定做的少了）

```
3.科学计算集群
```

乡下来的 ，不懂是啥，跳过。
​​​​​

### 二.负载均衡系统： 负载均衡又有DNS负载均衡（比较常用）、IP负载均衡、反向代理负载均衡等。

```
1.DNS负载均衡系统。
```

最近做的应该就算DNS负载均衡。用户访问的web系统其实是一个域名，我们称之为 www.a.com,但是这个域名解析的主机（服务器？我不管，我就要叫主机）主要是用来做负载均衡调度，在分发任务的时候，再将请求分发到其它不同ip的主机上。总体来说，就是一个域名（ip）映射多个ip主机。具体分发场景可以参考上面负载均衡集群举的例子。

```
2.ip负载均衡

没做过，说了也是扯淡。。。。


3.反向代理负载均衡
```

反向代理，有点类似spring的IOC，简单来说就是帮你做了一些你不擅长的事情。

这个博客网站其实就是运用了反向代理技术，本来所有业务请求都是交给uwsgi（类似java的tomcat）来处理，uwsgi处理python部分得心应手，可是在处理静态资源的时候（js脚本，图片，文档等），他就有点带不动唉（又黑我大泽哥），当然要搞懂一件事，就是不是它不能做，只是它的相对处理效率非常低下，这时候就需要web服务器nginx（不喜欢apached，也没啥实践经历）来帮忙。在客户发出请求的时候，一并由nginx接收，在静态资源部分，nginx自己先处理完，剩余的部分交给uwsgi来处理（至于nginx和uwsgi通信部分， 可以通过端口，但是一般是使用sock通信），从而提高了整个流程的处理速度。

举例子的话，还是用商店的例子吧，有a,b两个导购，a对商店的东西比较熟悉，可以最快的找到商品，可是却对推销有心无力。而b恰恰相反，可以很容易让用户动心付款，却找商品需要找半天。所以老板决定，用户进门的时候a去带领找商品，找到后换b去推销，大大提升业绩~

```
4.总结负载均衡
负载均衡重点在均衡，如果一台服务器宕机，最坏的结果只是负载不够均衡，但是还是可以完全响应请求的，每个都是独立的个体。
```

### 三.分布式是指将不同的业务分布在不同的地方。

而集群指的是将几台服务器集中在一起，实现同一业务。分布式中的每一个节点，都可以做集群。 而集群并不一定就是分布式的。 分布式的每一个节点，都完成不同的业务，一个节点垮了，哪这个业务就不可访问了。
还是举商店的例子，有导购员a,b,c。a负责日用品，b负责食品区，c是家居区，由于各司其职效率很高，有一天a生病了，b也想帮a，可是他对日用品根本一无所知，结果日用品那块就停滞了，但是食品区和家居区还是夜夜笙歌，正常的不行。 

### 四.全文浓缩一波

高大上的概念其实也要一步一步去实现，所以也没有什么神秘莫测的，只能活到老，学到老。很多都是个人想法，不切主题，理解如果有偏差，欢迎斧正~

参考：[这位老哥有点帅](http://itsoul.iteye.com/blog/777212)
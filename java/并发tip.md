并发tip

#### 一.线程状态

1.新建

2.执行（也可能等待时间片）

3.阻塞  （互斥操作）

4.等待（限时等待和无限等待）

5.死亡



#### 二.Executor

- CachedThreadPool：一个任务创建一个线程；
- FixedThreadPool：所有任务只能使用固定大小的线程；
- SingleThreadExecutor：相当于大小为 1 的 FixedThreadPool。

```
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 5; i++) {
        executorService.execute(new MyRunnable());
    }
    //执行者关闭
    executorService.shutdown();
}
```



#### 三.AQS

##### 1.CountDownLatch

维护了一个计数器 cnt，每次调用 countDown() 方法会让计数器的值减 1，减到 0 的时候，那些因为调用 await() 方法而在等待的线程就会被唤醒。

##### 2.CyclicBarrier

用来控制多个线程互相等待，只有当多个线程都到达时，这些线程才会继续执行

##### 3.ForkJoin

主要用于并行计算中，和 MapReduce 原理类似，都是把大的计算任务拆分成多个小任务并行计算。

##### 4.Semaphore

控制同时访问数量

##### 5.FutureTask

用于异步获取执行结果或取消执行任务的场景

##### 6.BlockingQueue

提供了阻塞的 take() 和 put() 方法：如果队列为空 take() 将阻塞，直到队列中有内容；如果队列为满 put() 将阻塞，直到队列有空闲位置。



#### 四.偏向锁

偏向锁的思想是偏向于让第一个获取锁对象的线程，这个线程在之后获取该锁就不再需要进行同步操作，甚至连 CAS 操作也不再需要。


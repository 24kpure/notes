从这篇开始仔仔细细连注释一并翻译，提升下相关词汇量。

### 一.前言

```
A hash table supporting full concurrency of retrievals and
* high expected concurrency for updates. This class obeys the
* same functional specification as {@link java.util.Hashtable}, and
* includes versions of methods corresponding to each method of
* {@code Hashtable}. However, even though all operations are
* thread-safe, retrieval operations do <em>not</em> entail locking,
* and there is <em>not</em> any support for locking the entire table
* in a way that prevents all access.  This class is fully
* interoperable with {@code Hashtable} in programs that rely on its
* thread safety but not on its synchronization details.
```

##### 此段生词：

##### retrievals 检索；

##### specification 说明；

##### entai 需要；

##### interoperable 可共同使用；

##### corresponding 相应的，与xx一致；

一个支持完全并发检索和高并发更新的散列表。这个类遵守了与HashTable相同的函数说明，并且包括了各种版本与方法与HashTable一致。然而，即便所有的操作都是线程安全的，检索操作不需要上锁，而且在内部并没有提供任何锁整个表的方法【支持，做法】达到阻止所有可能获取table内信息的目的。在依赖于线程安全但是不关注同步的实现细节的情况下，这个类在项目中是完全可替代hashTable。



```
* <p>Retrieval operations (including {@code get}) generally do not
* block, so may overlap with update operations (including {@code put}
* and {@code remove}). Retrievals reflect the results of the most
* recently <em>completed</em> update operations holding upon their
* onset. (More formally, an update operation for a given key bears a
* <em>happens-before</em> relation with any (non-null) retrieval for
* that key reporting the updated value.)  For aggregate operations
* such as {@code putAll} and {@code clear}, concurrent retrievals may
* reflect insertion or removal of only some entries.  Similarly,
* Iterators, Spliterators and Enumerations return elements reflecting the
* state of the hash table at some point at or since the creation of the
* iterator/enumeration.  They do <em>not</em> throw {@link
* java.util.ConcurrentModificationException ConcurrentModificationException}.
* However, iterators are designed to be used by only one thread at a time.
* Bear in mind that the results of aggregate status methods including
* {@code size}, {@code isEmpty}, and {@code containsValue} are typically
* useful only when a map is not undergoing concurrent updates in other threads.
* Otherwise the results of these methods reflect transient states
* that may be adequate for monitoring or estimation purposes, but not
* for program control.
```

##### 此段生词：

##### overlap 重叠；

##### onset 出发，开始；

##### aggregate 合计，聚合；

##### Bear in mind 牢记；

##### undergoing 经历；

##### transient 瞬间；

##### adequate 充足；

检索操作（包含get）通常并不会阻塞，所以可能会与更新操作同时进行（包含put和remove）。检索操作反馈的结果是多数近期已完成的更新操作之前的数据【你get的结果的时间点保持在 update有操作之前】（正式的来说，一个给定key的更新操作支持 happens-before规则，前提情况是key非空的检索或者更新）【非空的检索与更新对于更新操作都满足happen-before原则】。对于批量的操作，例如putAll和clear，并发的检索可能会反馈的结果，可能是在只有数次子操作完成后读取后的【批量操作时，可以进行读取，不过并不保证操作完全执行完】。相似度的，迭代器，分割器和枚举返回的结果可以是从其创建后的任意时间点的结果。它们并不会抛出ConcurrentModificationException异常，然而，迭代器被设计为在一个时间点只在一个线程使用。需要牢记的是，只有在map在其它线程中不在执行更新操作的情况下，统计方法返回的结果是典型值。否则，这个结果是瞬时结果，这对于监控或者预估为目的情况是充足的，但是对于程序控制而言并不够。



```
* <p>The table is dynamically expanded when there are too many
* collisions (i.e., keys that have distinct hash codes but fall into
* the same slot modulo the table size), with the expected average
* effect of maintaining roughly two bins per mapping (corresponding
* to a 0.75 load factor threshold for resizing). There may be much
* variance around this average as mappings are added and removed, but
* overall, this maintains a commonly accepted time/space tradeoff for
* hash tables.  However, resizing this or any other kind of hash
* table may be a relatively slow operation. When possible, it is a
* good idea to provide a size estimate as an optional {@code
* initialCapacity} constructor argument. An additional optional
* {@code loadFactor} constructor argument provides a further means of
* customizing initial table capacity by specifying the table density
* to be used in calculating the amount of space to allocate for the
* given number of elements.  Also, for compatibility with previous
* versions of this class, constructors may optionally specify an
* expected {@code concurrencyLevel} as an additional hint for
* internal sizing.  Note that using many keys with exactly the same
* {@code hashCode()} is a sure way to slow down performance of any
* hash table. To ameliorate impact, when keys are {@link Comparable},
* this class may use comparison order among keys to help break ties.
```

##### 此段生词

collisions  冲突；maintaining 保卫，保养，坚持；slot 位置，狭缝；

roughly 大体的；threshold 门槛；variance 变化；

tradeoff 折中；relatively 相对的；customizing 定制；specify 指定；

density 密度；compatibility 兼容性；hint 暗示；

ameliorate 改进；ties 关系；modulo 以xx为模；

这个表当散列碰撞过过时会动态拓展（不同的散列值的key与表的取模归入同一分区），预期平均值每个映射大体保持向两个分区（0.75平衡因子作为门槛用以变更表）【指每个平均每个hash值都会指向两个区块，碰撞率高】。在这个映射添加或者移除时可能会有很多变动，但是总体而言，对于散列表，这个保持着一个承受时间空间的折中。然而，变更表大小或者其它形式的散列表可能变为一个相对慢操作。当可能时，提供一个可选的预计初始容量构造器参数是个好做法。一个额外的选择负载因子构造器提供了一个定制的初始化表密度用于计算申请空间通过所给的数字与元素。同样的，因为兼容这个类之前的版本，构造器可能会有可选的指定参数同步等级作为一个额外的暗示用于变更内部大小。记下这点那些用了很多同样key值确认会影响散列表的性能。为了改进这些影响，这个类的键值会可能会在键值中进行对比用于破除这些关系（高碰撞的关系）。



```
* <p>A {@link Set} projection of a ConcurrentHashMap may be created
* (using {@link #newKeySet()} or {@link #newKeySet(int)}), or viewed
* (using {@link #keySet(Object)} when only keys are of interest, and the
* mapped values are (perhaps transiently) not used or all take the
* same mapping value.
```

##### 此段生词

projection 投影，规划，设计；

一个ConcurrentHashMap的投影可能会被创建，在调用newKeySet或者newKeySet或者查看keySet当这些键值被关注时，并且映射值可能是断站的不被使用或者取相同的映射值。



```
* <p>A ConcurrentHashMap can be used as scalable frequency map (a
* form of histogram or multiset) by using {@link
* java.util.concurrent.atomic.LongAdder} values and initializing via
* {@link #computeIfAbsent computeIfAbsent}. For example, to add a count
* to a {@code ConcurrentHashMap<String,LongAdder> freqs}, you can use
* {@code freqs.computeIfAbsent(k -> new LongAdder()).increment();}

 * <p>This class and its views and iterators implement all of the
 * <em>optional</em> methods of the {@link Map} and {@link Iterator}
 * interfaces.
```



##### 此段生词

scalable 可缩放；frequency频繁；histogram柱状图；

multiset多集；implement 实施，执行，工具；

一个ConcurrentHashMap可以被用于频繁伸缩的map（一种柱状图和多集的形式）通过使用java.util.concurrent.atomic.LongAdder的值 并且通过computeIfAbsent初始化。举个例子，想要添加一个计数，你可以通过freqs.computeIfAbsent(k -> new LongAdder()).increment()实现。

这个类的所有视图和迭代器等可选方法都是实现自map和Iterator的接口



```
<p>ConcurrentHashMaps support a set of sequential and parallel bulk
* operations that, unlike most {@link Stream} methods, are designed
* to be safely, and often sensibly, applied even with maps that are
* being concurrently updated by other threads; for example, when
* computing a snapshot summary of the values in a shared registry.
* There are three kinds of operation, each with four forms, accepting
* functions with Keys, Values, Entries, and (Key, Value) arguments
* and/or return values. Because the elements of a ConcurrentHashMap
* are not ordered in any particular way, and may be processed in
* different orders in different parallel executions, the correctness
* of supplied functions should not depend on any ordering, or on any
* other objects or values that may transiently change while
* computation is in progress; and except for forEach actions, should
* ideally be side-effect-free. Bulk operations on {@link java.util.Map.Entry}
* objects do not support method {@code setValue}.
```

##### 此段生词

parallel 平行；bulk 大量；sensibly 明智的；snapshot 急速，快照相片；

be processed in 被加工；

ConcurrentHashMaps支持有序并且大量平行操作的set，不同于大多数（比如staream）方法，此类被设计的安全并且大多数时候明智，甚至应用于在其它线程高并发更新时的map。举个例子，当计算共享注册表值的快速的大纲。有3中形式的操作，每种都有四个形式，通过键，值，实体，接收函数和键值参数或者返回值。因为这些集合元素并不是按照特定的排序，并且可能被在不同的顺序处理在不同的平行处理中，这些函数的正确性应该依赖于任意的排序，或者当在计算是其它可能临时变更的对象值。并且期望的遍历举动，应该理想化无副作用。大量的操作在map对象不应该支持这个方法。



```
* <p>These bulk operations accept a {@code parallelismThreshold}
* argument. Methods proceed sequentially if the current map size is
* estimated to be less than the given threshold. Using a value of
* {@code Long.MAX_VALUE} suppresses all parallelism.  Using a value
* of {@code 1} results in maximal parallelism by partitioning into
* enough subtasks to fully utilize the {@link
* ForkJoinPool#commonPool()} that is used for all parallel
* computations. Normally, you would initially choose one of these
* extreme values, and then measure performance of using in-between
* values that trade off overhead versus throughput.
```

##### 此段生词

suppresses  压制，抑制；partitioning 分割法；maximal 最大的；

utilize 利用；trade off 权衡； overhead 开销；

versus 相对的； throughput 吞吐量；

这段看不大懂，目前理解的是 有一个给定数字，限定平行数量，拆分子任务，更好的完成一些操作。



```
* <p>The concurrency properties of bulk operations follow
* from those of ConcurrentHashMap: Any non-null result returned
* from {@code get(key)} and related access methods bears a
* happens-before relation with the associated insertion or
* update.  The result of any bulk operation reflects the
* composition of these per-element relations (but is not
* necessarily atomic with respect to the map as a whole unless it
* is somehow known to be quiescent).  Conversely, because keys
* and values in the map are never null, null serves as a reliable
* atomic indicator of the current lack of any result.  To
* maintain this property, null serves as an implicit basis for
* all non-scalar reduction operations. For the double, long, and
* int versions, the basis should be one that, when combined with
* any other value, returns that other value (more formally, it
* should be the identity element for the reduction). Most common
* reductions have these properties; for example, computing a sum
* with basis 0 or a minimum with basis MAX_VALUE.
p>Search and transformation functions provided as arguments
 * should similarly return null to indicate the lack of any result
 * (in which case it is not used). In the case of mapped
 * reductions, this also enables transformations to serve as
 * filters, returning null (or, in the case of primitive
 * specializations, the identity basis) if the element should not
 * be combined. You can create compound transformations and
 * filterings by composing them yourself under this "null means
 * there is nothing there now" rule before using them in search or
 * reduce operations.
 *
 * <p>Methods accepting and/or returning Entry arguments maintain
 * key-value associations. They may be useful for example when
 * finding the key for the greatest value. Note that "plain" Entry
 * arguments can be supplied using {@code new
 * AbstractMap.SimpleEntry(k,v)}.
 *
 * <p>Bulk operations may complete abruptly, throwing an
 * exception encountered in the application of a supplied
 * function. Bear in mind when handling such exceptions that other
 * concurrently executing functions could also have thrown
 * exceptions, or would have done so if the first exception had
 * not occurred.
 *
 * <p>Speedups for parallel compared to sequential forms are common
 * but not guaranteed.  Parallel operations involving brief functions
 * on small maps may execute more slowly than sequential forms if the
 * underlying work to parallelize the computation is more expensive
 * than the computation itself.  Similarly, parallelization may not
 * lead to much actual parallelism if all processors are busy
```



挖的坑，日后补先看函数；



### 二.类中常量

1.MAXIMUM_CAPACITY =1 <<30 最大表容量

2.DEFAULT_CAPACITY =16 初始容量

3.MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8 数组上限

4.DEFAULT_CONCURRENCY_LEVEL=16 同步等级 没啥用，为了兼容之前的版本

5.LOAD_FACTOR=0.75 平衡因子 0.75

6.TREEIFY_THRESHOLD =8 超过这个值，链表转树。 当达到8时，红黑树找寻次数为3，而链表的期望的4.

7.UNTREEIFY_THRESHOLD=6 链表转树。减少到6时，树寻址2-3，链表为3。

8.MIN_TREEIFY_CAPACITY=64 要转化成树的最小容量  ，必须为32的倍数

9.MIN_TRANSFER_STRIDE=16 bin允许参与大小，最小也要等于默认值

10.RESIZE_STAMP_BITS=16 变更带下标志，当是32bit数组时 至少大于6

11.MAX_RESIZERS 扩容时，线程最大参与数

12.RESIZE_STAMP_SHIFT 不懂

13.节点hash种类

```
static final int MOVED     = -1; // hash for forwarding nodes
static final int TREEBIN   = -2; // hash for roots of trees
static final int RESERVED  = -3; // hash for transient reservations
static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
```

### 三.方法



#### 四.其它词汇

1.paranoically   [pærə'dɒksɪklɪ]  偏执

2.conservative   [kənˈsɜ:rvətɪv]  保守的
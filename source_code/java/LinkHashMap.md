### 新的征程 LinkHashMap

#### 一.前言概述

```
/**
 * <p>Hash table and linked list implementation of the <tt>Map</tt> interface,
 * with predictable iteration order.  This implementation differs from
 * <tt>HashMap</tt> in that it maintains a doubly-linked list running through
 * all of its entries.  This linked list defines the iteration ordering,
 * which is normally the order in which keys were inserted into the map
 * (<i>insertion-order</i>).  Note that insertion order is not affected
 * if a key is <i>re-inserted</i> into the map.  (A key <tt>k</tt> is
 * reinserted into a map <tt>m</tt> if <tt>m.put(k, v)</tt> is invoked when
 * <tt>m.containsKey(k)</tt> would return <tt>true</tt> immediately prior to
 * the invocation.)
 *
 
 散列表和连接列实现map接口，带有可预测的迭代顺序。由于其中实体是双向连接，这个实现并不同于hashmap。当一个元素正常的插入这个map，这个链接列定义了其迭代顺序。记录下插入顺序并不影响这个键是否是重新插入这个map。一个键从插入一个map 当map包含这个键，在调用之前将会立即返回真（然而现在并没有在父类中找到符合描述的）。
 
 
 
 
 * <p>This implementation spares its clients from the unspecified, generally
 * chaotic ordering provided by {@link HashMap} (and {@link Hashtable}),
 * without incurring the increased cost associated with {@link TreeMap}.  It
 * can be used to produce a copy of a map that has the same order as the
 * original, regardless of the original map's implementation:
 * <pre>
 *     void foo(Map m) {
 *         Map copy = new LinkedHashMap(m);
 *         ...
 *     }
 * </pre>
 * This technique is particularly useful if a module takes a map on input,
 * copies it, and later returns results whose order is determined by that of
 * the copy.  (Clients generally appreciate having things returned in the same
 * order they were presented.)
 
 这上半部分没看懂，下半部分提到个用法。LinkedHashMap 可以用来copy保留之前map所存在的顺序。
 
 
 *
 * <p>A special {@link #LinkedHashMap(int,float,boolean) constructor} is
 * provided to create a linked hash map whose order of iteration is the order
 * in which its entries were last accessed, from least-recently accessed to
 * most-recently (<i>access-order</i>).  This kind of map is well-suited to
 * building LRU caches.  Invoking the {@code put}, {@code putIfAbsent},
 * {@code get}, {@code getOrDefault}, {@code compute}, {@code computeIfAbsent},
 * {@code computeIfPresent}, or {@code merge} methods results
 * in an access to the corresponding entry (assuming it exists after the
 * invocation completes). The {@code replace} methods only result in an access
 * of the entry if the value is replaced.  The {@code putAll} method generates one
 * entry access for each mapping in the specified map, in the order that
 * key-value mappings are provided by the specified map's entry set iterator.
 * <i>No other methods generate entry accesses.</i>  In particular, operations
 * on collection-views do <i>not</i> affect the order of iteration of the
 * backing map.
 
   一个特殊的linkHashMap构造器被提供用于创建一个map，该map最近插入的值将会是最慢访问到的。这种类型的map对于创建lru缓存（最少缓存使用算法）是最适合的。
   特别的，集合的视图操作并不会影响该map的迭代的顺序。
 
 
 *
 * <p>The {@link #removeEldestEntry(Map.Entry)} method may be overridden to
 * impose a policy for removing stale mappings automatically when new mappings
 * are added to the map.
 *
 * <p>This class provides all of the optional <tt>Map</tt> operations, and
 * permits null elements.  Like <tt>HashMap</tt>, it provides constant-time
 * performance for the basic operations (<tt>add</tt>, <tt>contains</tt> and
 * <tt>remove</tt>), assuming the hash function disperses elements
 * properly among the buckets.  Performance is likely to be just slightly
 * below that of <tt>HashMap</tt>, due to the added expense of maintaining the
 * linked list, with one exception: Iteration over the collection-views
 * of a <tt>LinkedHashMap</tt> requires time proportional to the <i>size</i>
 * of the map, regardless of its capacity.  Iteration over a <tt>HashMap</tt>
 * is likely to be more expensive, requiring time proportional to its
 * <i>capacity</i>.
 *

    这个类提供了所有map的操作方法，并且允许空元素。与HashMap一样，对于基本的操作（例如加，包含，移除操作）其提供了常量执行时间的性能，声明了散列方法分散元素到各个元素桶中。其性能可能只是轻度低于HashMap，因为添加的成本用于爆出连接链表，一个情况例外：迭代完成后集合视图取决于map的size而不是他的容量。迭代完一个HashMap可能更加耗时，因为它迭代取决于其容量。
 
 * <p>A linked hash map has two parameters that affect its performance:
 * <i>initial capacity</i> and <i>load factor</i>.  They are defined precisely
 * as for <tt>HashMap</tt>.  Note, however, that the penalty for choosing an
 * excessively high value for initial capacity is less severe for this class
 * than for <tt>HashMap</tt>, as iteration times for this class are unaffected
 * by capacity.
 
 一个散列map有两个参数影响着它的表现，初始容量和平衡因子（这不是和hashmap一样吗？），但是初始值设置过大造成的负面影响要小于hashMap，因为这个类迭代取不取决于容量（承接上文）
 
 
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a linked hash map concurrently, and at least
 * one of the threads modifies the map structurally, it <em>must</em> be
 * synchronized externally.  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the map.
 *
 * If no such object exists, the map should be "wrapped" using the
 * {@link Collections#synchronizedMap Collections.synchronizedMap}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the map:<pre>
 *   Map m = Collections.synchronizedMap(new LinkedHashMap(...));</pre>
 *
 * A structural modification is any operation that adds or deletes one or more
 * mappings or, in the case of access-ordered linked hash maps, affects
 * iteration order.  In insertion-ordered linked hash maps, merely changing
 * the value associated with a key that is already contained in the map is not
 * a structural modification.  <strong>In access-ordered linked hash maps,
 * merely querying the map with <tt>get</tt> is a structural modification.
 * </strong>)
 *
     记录下这个实现并不是同步的。如果多个线程可对这个map并行操作，并且至少一个线程更改了这个map的结构，那么必须额外声明同步。这是很显然通过同步封装这个map来实现的。
     一个结构的变化包含一切add,delete等任意可达map的操作，影响着迭代顺序。添加操作，如果原来就有这个key，那么不被认为更改结构的操作。
 
 * <p>The iterators returned by the <tt>iterator</tt> method of the collections
 * returned by all of this class's collection view methods are
 * <em>fail-fast</em>: if the map is structurally modified at any time after
 * the iterator is created, in any way except through the iterator's own
 * <tt>remove</tt> method, the iterator will throw a {@link
 * ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the future.
 *
 这个迭代器返回的集合视图方法都并不快：如果迭代器创建后，这个map发生结构变化，如何方法除了除了迭代器自己的remove方法，这个迭代器会抛出ConcurrentModificationException同步异常。因此，面对并发变更，相较于未来不确定的行为，这个迭代器对于速率和便利程度并不成功。
 
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness:   <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 
 记录下这个迭代并不快的表现并不能确认这样，大体来说，不可能在不同步的情况下进行并行更改可靠的。因此，由于其准确性根据这个例外编写程序是不准确的，迭代并不快的表现只能被用来检查缺陷。
 
 
 * <p>The spliterators returned by the spliterator method of the collections
 * returned by all of this class's collection view methods are
 * <em><a href="Spliterator.html#binding">late-binding</a></em>,
 * <em>fail-fast</em>, and additionally report {@link Spliterator#ORDERED}.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @implNote
 * The spliterators returned by the spliterator method of the collections
 * returned by all of this class's collection view methods are created from
 * the iterators of the corresponding collections.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @see     Object#hashCode()
 * @see     Collection
 * @see     Map
 * @see     HashMap
 * @see     TreeMap
 * @see     Hashtable
 * @since   1.4
 */
```

#### 二.方法描述

1.类定义   继承HashMap实现Map接口

```
public class LinkedHashMap<K,V>
    extends HashMap<K,V>
    implements Map<K,V>
```

2.继承HashMap的Node作为Entity

```
/*
 * Implementation note.  A previous version of this class was
 * internally structured a little differently. Because superclass
 * HashMap now uses trees for some of its nodes, class
 * LinkedHashMap.Entry is now treated as intermediary node class
 * that can also be converted to tree form. The name of this
 * class, LinkedHashMap.Entry, is confusing in several ways in its
 * current context, but cannot be changed.  Otherwise, even though
 * it is not exported outside this package, some existing source
 * code is known to have relied on a symbol resolution corner case
 * rule in calls to removeEldestEntry that suppressed compilation
 * errors due to ambiguous usages. So, we keep the name to
 * preserve unmodified compilability.
 *
 * The changes in node classes also require using two fields
 * (head, tail) rather than a pointer to a header node to maintain
 * the doubly-linked before/after list. This class also
 * previously used a different style of callback methods upon
 * access, insertion, and removal.
 */
 实现比较。一个这个类之前的版本内部结构有一小小区别。因为父类HashMap现在一些节点用的是树。
 类LinkedHashMap.Entry现在作为中间节点类用于转换树形式。后面过于复杂····
/**
 * HashMap.Node subclass for normal LinkedHashMap entries.
 */
static class Entry<K,V> extends HashMap.Node<K,V> {
    Entry<K,V> before, after;
    Entry(int hash, K key, V value, Node<K,V> next) {
        super(hash, key, value, next);
    }
}
```

3.get方法

```
/**
 * Returns the value to which the specified key is mapped,
 * or {@code null} if this map contains no mapping for the key.
 *
 * <p>More formally, if this map contains a mapping from a key
 * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
 * key.equals(k))}, then this method returns {@code v}; otherwise
 * it returns {@code null}.  (There can be at most one such mapping.)
 *
 * <p>A return value of {@code null} does not <i>necessarily</i>
 * indicate that the map contains no mapping for the key; it's also
 * possible that the map explicitly maps the key to {@code null}.
 * The {@link #containsKey containsKey} operation may be used to
 * distinguish these two cases.
 */
public V get(Object key) {
    Node<K,V> e;
    if ((e = getNode(hash(key), key)) == null)
        return null;
    if (accessOrder)
        afterNodeAccess(e);
    return e.value;
}
//承接总述 linkHashMap的get方法比HashMap额外添加了维护双向联系的成本
```

3.accessOrder 用于描述排序顺序，默认为false。为真时，排序为访问顺序，为假时为插入顺序。下面方法描述get后，accessOrder 为真，根据访问顺序设置顺序。

```
void afterNodeAccess(Node<K,V> e) { // move node to last
    LinkedHashMap.Entry<K,V> last;
    if (accessOrder && (last = tail) != e) {
        LinkedHashMap.Entry<K,V> p =
            (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
        p.after = null;
        //p下一个节点设为空，为当做尾节点做尊卑
        if (b == null)
            //b为空说明p是首节点，首节点设为原首节点的子节点a
            head = a;
        else
            //原p的子节点变为父节点的子节点，去除p
            b.after = a;
        if (a != null)
           //原p的子节点变为父节点的子节点，去除p
            a.before = b;
        else
           //a为空说明p是尾节点，尾节点设为原尾节点的子节点b
            last = b;
        if (last == null)
           //p原来为尾节点的情况下，扔是   
            head = p;
        else {
            //非尾节点的情况下，还需要设置父节点为原尾节点，原为尾节点子节点为p
            p.before = last;
            last.after = p;
        }
        tail = p;
        ++modCount;
    }
}
```

4.afterNodeInsertion 原HashMap中putVal中添加完成后有调用的方法，是为LinkHashMap准备的

```
void afterNodeInsertion(boolean evict) { // possibly remove eldest
    LinkedHashMap.Entry<K,V> first;
    // removeEldestEntry这个恒为真 没get意思
    if (evict && (first = head) != null && removeEldestEntry(first)) {
        K key = first.key;
        removeNode(hash(key), key, null, false, true);
    }
}
```

5.forEach方法  与上文对应，实现与HashMap不一致，迭代顺序根据指针迭代，不根据size，此处比hashMap更快。

```
public void forEach(BiConsumer<? super K, ? super V> action) {
    if (action == null)
        throw new NullPointerException();
    int mc = modCount;
    for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
        //迭代取决于指针
        action.accept(e.key, e.value);
    if (modCount != mc)
        throw new ConcurrentModificationException();
}
```
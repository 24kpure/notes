TreeMap 源码

#### 1.综述

```

/**
 * A Red-Black tree based {@link NavigableMap} implementation.
 * The map is sorted according to the {@linkplain Comparable natural
 * ordering} of its keys, or by a {@link Comparator} provided at map
 * creation time, depending on which constructor is used.
 *
 * <p>This implementation provides guaranteed log(n) time cost for the
 * {@code containsKey}, {@code get}, {@code put} and {@code remove}
 * operations.  Algorithms are adaptations of those in Cormen, Leiserson, and
 * Rivest's <em>Introduction to Algorithms</em>.
 *
   一个以红黑树为基础的实现。这个map的是依据Comparable key排序的，或者在创建时在构造器提供一个比对规则。这个实现提供了log（n）时间内执行包含key操作。
 
 * <p>Note that the ordering maintained by a tree map, like any sorted map, and
 * whether or not an explicit（明确） comparator is provided, must be <em>consistent
 * with {@code equals}</em> if this sorted map is to correctly implement the
 * {@code Map} interface.  (See {@code Comparable} or {@code Comparator} for a
 * precise definition of <em>consistent with equals</em>.)  This is so because
 * the {@code Map} interface is defined in terms of the {@code equals}
 * operation, but a sorted map performs all key comparisons using its {@code
 * compareTo} (or {@code compare}) method, so two keys that are deemed equal by
 * this method are, from the standpoint of the sorted map, equal.  The behavior
 * of a sorted map <em>is</em> well-defined even if its ordering is
 * inconsistent with {@code equals}; it just fails to obey the general contract
 * of the {@code Map} interface.
    记录下treeMap的顺序保持，像sortMap，而且不管是否明确提供了比较器，必须与sortMap保持一致实现了Map接口。
  
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a map concurrently, and at least one of the
 * threads modifies the map structurally, it <em>must</em> be synchronized
 * externally.  (A structural modification is any operation that adds or
 * deletes one or more mappings; merely changing the value associated
 * with an existing key is not a structural modification.)  This is
 * typically accomplished by synchronizing on some object that naturally
 * encapsulates the map.
 * If no such object exists, the map should be "wrapped" using the
 * {@link Collections#synchronizedSortedMap Collections.synchronizedSortedMap}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the map: <pre>
 *   SortedMap m = Collections.synchronizedSortedMap(new TreeMap(...));</pre>
 *
 多线程可能影响结构变化时，线程不安全。
   
 
 * <p>The iterators returned by the {@code iterator} method of the collections
 * returned by all of this class's "collection view methods" are
 * <em>fail-fast</em>: if the map is structurally modified at any time after
 * the iterator is created, in any way except through the iterator's own
 * {@code remove} method, the iterator will throw a {@link
 * ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness:   <em>the fail-fast behavior of iterators
 * should be used only to detect bugs.</em>
 *
 * <p>All {@code Map.Entry} pairs returned by methods in this class
 * and its views represent snapshots of mappings at the time they were
 * produced. They do <strong>not</strong> support the {@code Entry.setValue}
 * method. (Note however that it is possible to change mappings in the
 * associated map using {@code put}.)
   这个迭代器返回的迭代是快速失败的。如果这个map结构在创建迭代器后任何时间发生变化，迭代器会抛出异常。
   因此，面对并发变更，迭代器快速干脆的失败而不是任意冒险。
   
 
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch and Doug Lea
 * @see Map
 * @see HashMap
 * @see Hashtable
 * @see Comparable
 * @see Comparator
 * @see Collection
 * @since 1.2
 */

```

#### 2.方法

1.类声明  继承AbstractMap

```
public class TreeMap<K,V>
    extends AbstractMap<K,V>
    implements NavigableMap<K,V>, Cloneable, java.io.Serializable
```

2.常量

```
/**
 * The comparator used to maintain order in this tree map, or
 * null if it uses the natural ordering of its keys.
 *
 * @serial
 */
 //比较器
private final Comparator<? super K> comparator;

//根节点
private transient Entry<K,V> root;

/**
 * The number of entries in the tree
 */
private transient int size = 0;

/**
 * The number of structural modifications to the tree.
 */
private transient int modCount = 0;

```

3.典型构造器

```
/**
 * Constructs a new, empty tree map, ordered according to the given
 * comparator.  All keys inserted into the map must be <em>mutually
 * comparable</em> by the given comparator: {@code comparator.compare(k1,
 * k2)} must not throw a {@code ClassCastException} for any keys
 * {@code k1} and {@code k2} in the map.  If the user attempts to put
 * a key into the map that violates this constraint, the {@code put(Object
 * key, Object value)} call will throw a
 * {@code ClassCastException}.
 *
 * @param comparator the comparator that will be used to order this map.
 *        If {@code null}, the {@linkplain Comparable natural
 *        ordering} of the keys will be used.
 */
 //建造一个新的，空的树Map，排序根据所给的比较器。
public TreeMap(Comparator<? super K> comparator) {
    this.comparator = comparator;
}
```

4.

业务系统->发送消息  

​                  

消息系统1  消息系统2  消息系统3
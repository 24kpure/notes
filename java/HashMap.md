虽然现在很菜，但是想着以后还会菜着几十年，为什么不好好过好自己，菜的有意义？

### 一.注释综述

```
* Hash table based implementation of the <tt>Map</tt> interface.  This
* implementation provides all of the optional map operations, and permits
* <tt>null</tt> values and the <tt>null</tt> key.  (The <tt>HashMap</tt>
* class is roughly equivalent to <tt>Hashtable</tt>, except that it is
* unsynchronized and permits nulls.)  This class makes no guarantees as to
* the order of the map; in particular, it does not guarantee that the order
* will remain constant over time.
```

总体介绍：hashtable是基于Map的接口，这个接口提供了所有map方法的实现，并且允许空的value值和空的key值，hashmap大体和hashtable一致，但是不保证同步。

```
* <p>This implementation provides constant-time performance for the basic
* operations (<tt>get</tt> and <tt>put</tt>), assuming the hash function
* disperses the elements properly among the buckets.  Iteration over
* collection views requires time proportional to the "capacity" of the
* <tt>HashMap</tt> instance (the number of buckets) plus its size (the number
* of key-value mappings).  Thus, it's very important not to set the initial
* capacity too high (or the load factor too low) if iteration performance is
* important.
```

方法效率介绍：大部分方法都是即时完成，迭代速度受容量和size之和影响，提示不要把容量设的太大。

```
* <p>An instance of <tt>HashMap</tt> has two parameters that affect its
* performance: <i>initial capacity</i> and <i>load factor</i>.  The
* <i>capacity</i> is the number of buckets in the hash table, and the initial
* capacity is simply the capacity at the time the hash table is created.  The
* <i>load factor</i> is a measure of how full the hash table is allowed to
* get before its capacity is automatically increased.  When the number of
* entries in the hash table exceeds the product of the load factor and the
* current capacity, the hash table is <i>rehashed</i> (that is, internal data
* structures are rebuilt) so that the hash table has approximately twice the
* number of buckets.
```

性能影响因素：一个是容量，一个是载入因子。载入因子是形容空间满的程度，数据量达到载入因子*容量时，容量增加两倍。

### 二.构造方法

**1.定义容量和载入因子。**

```
public HashMap(int initialCapacity, float loadFactor) {
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal initial capacity: " +
                                           initialCapacity);
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    if (loadFactor <= 0 || Float.isNaN(loadFactor))
        throw new IllegalArgumentException("Illegal load factor: " +
                                           loadFactor);
    this.loadFactor = loadFactor;
    this.threshold = tableSizeFor(initialCapacity);
}
```

**2.只定义容量，载入因子0.75**

```
public HashMap(int initialCapacity) {
    this(initialCapacity, DEFAULT_LOAD_FACTOR);
}
```

**3.最常用的构造方法，容量是1<<4**

```
public HashMap() {
    this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
}
```

**4. 没看懂**

```
public HashMap(Map<? extends K, ? extends V> m) {
    this.loadFactor = DEFAULT_LOAD_FACTOR;
    putMapEntries(m, false);
}
final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
    int s = m.size();
    if (s > 0) {
        if (table == null) { // pre-size
            float ft = ((float)s / loadFactor) + 1.0F;
            int t = ((ft < (float)MAXIMUM_CAPACITY) ?
                     (int)ft : MAXIMUM_CAPACITY);
            if (t > threshold)
                threshold = tableSizeFor(t);
        }
        else if (s > threshold)
            resize();
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            K key = e.getKey();
            V value = e.getValue();
            putVal(hash(key), key, value, false, evict);
        }
    }
}
```

### 三.其它方法

**1.get方法。判断e节点是否为空，不为空输出e.value值，否者输出null。**

```
public V get(Object key) {
    Node<K,V> e;
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}
```

**2.是否含有key。**

```
public boolean containsKey(Object key) {
    return getNode(hash(key), key) != null;
}
```

3.java6好文htt[p://www.cnblogs.com/ITtangtang/p/3948406.html](http://www.cnblogs.com/ITtangtang/p/3948406.html)

4.java8好文<http://blog.csdn.net/u010498696/article/details/45888613>

### 四.总结

个人原因，看的很吃力，很且没有领会到源码的深层含义（其实连使用二叉树都没搞懂没发觉，更何况红黑树），目前水平low，还需要积累，日后会反刍这篇文章。
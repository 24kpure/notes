AbstractMap 源码

#### 1.综述

skeletal(骨骼的)

atop（在。。顶上）

Sole（纯粹的）

 Adher（坚持）

```


/**
 * This class provides a skeletal(骨骼的) implementation of the <tt>Map</tt>
 * interface, to minimize the effort required to implement this interface.
 *
 * <p>To implement an unmodifiable map, the programmer needs only to extend this
 * class and provide an implementation for the <tt>entrySet</tt> method, which
 * returns a set-view of the map's mappings.  Typically, the returned set
 * will, in turn, be implemented atop（在。。顶上） <tt>AbstractSet</tt>.  This set should
 * not support the <tt>add</tt> or <tt>remove</tt> methods, and its iterator
 * should not support the <tt>remove</tt> method.
 *
  这个类提供骨架实现Map接口为了最小化实现这个接口的工作量。
  为了实现这个不可变的map，程序需要继承这个类并且提供一个entrySet方法的实现，它可以发挥一个map的set视图映射。显然而言，返回的set会依次被AbstractSet在顶实现。这个Set应该不支持添加或者移除方法，并且它的迭代器不应该支持remove方法。
  
 
 * <p>To implement a modifiable map, the programmer must additionally override
 * this class's <tt>put</tt> method (which otherwise throws an
 * <tt>UnsupportedOperationException</tt>), and the iterator returned by
 * <tt>entrySet().iterator()</tt> must additionally implement its
 * <tt>remove</tt> method.
 *
 * <p>The programmer should generally provide a void (no argument) and map
 * constructor, as per the recommendation in the <tt>Map</tt> interface
 * specification.
 *
 * <p>The documentation for each non-abstract method in this class describes its
 * implementation in detail.  Each of these methods may be overridden if the
 * map being implemented admits a more efficient implementation.
 *
   为了实现这个可变的map，程序必须额外的复写这个类的put方法，而且这个迭代器返回通过entrySet.iterator迭代器必须额外支持remove方法
 
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see Map
 * @see Collection
 * @since 1.2
 */


```

#### 2.方法

1.类声明

```
public abstract class AbstractMap<K,V> implements Map<K,V>
```

2.构造器

```
/**
 * Sole（存粹） constructor.  (For invocation by subclass constructors, typically
 * implicit（隐蔽）.)
 */
protected AbstractMap() {
}
```

3.size函数（不重要函数）isEmpty

```
/**
 * {@inheritDoc}
 *
 * @implSpec
 * This implementation returns <tt>entrySet().size()</tt>.
 */
public int size() {
    return entrySet().size();
}

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation returns <tt>size() == 0</tt>.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

```

4.containsValue函数，containsKey函数，get函数

```
/**
 * {@inheritDoc}
 *
 * @implSpec
 * This implementation iterates over <tt>entrySet()</tt> searching
 * for an entry with the specified value.  If such an entry is found,
 * <tt>true</tt> is returned.  If the iteration terminates without
 * finding such an entry, <tt>false</tt> is returned.  Note that this
 * implementation requires linear time in the size of the map.
 *
    接口在entrySet上迭代查询是否含有特定值。如果这个元素找到了，返回true。如果这个迭代到最后没有发现这个元素，返回false。这个实现要求线性时间，遍历这个map。
 
 * @throws ClassCastException   {@inheritDoc}
 * @throws NullPointerException {@inheritDoc}
 */
public boolean containsValue(Object value) {
    Iterator<Entry<K,V>> i = entrySet().iterator();
    if (value==null) {
        while (i.hasNext()) {
            Entry<K,V> e = i.next();
            if (e.getValue()==null)
                return true;
        }
    } else {
        while (i.hasNext()) {
            Entry<K,V> e = i.next();
            if (value.equals(e.getValue()))
                return true;
        }
    }
    return false;
}
 public boolean containsKey(Object key) {
        Iterator<Map.Entry<K,V>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    return true;
            }
        }
        return false;
    }
   public V get(Object key) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    return e.getValue();
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    return e.getValue();
            }
        }
        return null;
    }
```

5.view临时值

```
/**
 * Each of these fields are initialized to contain an instance of the
 * appropriate view the first time this view is requested.  The views are
 * stateless, so there's no reason to create more than one of each.
 *
 * <p>Since there is no synchronization performed while accessing these fields,
 * it is expected that java.util.Map view classes using these fields have
 * no non-final fields (or any fields at all except for outer-this). Adhering（支持）
 * to this rule would make the races（种类） on these fields benign（温和，良性）.
 *
   几乎所有这些属性被初始化含有一个实例可以第一时间查看到这个view。视图是无状态的，因此没有理由创建多个视图。
   因为没有同步性能当可得这些属性，希望Map 视图类中没有最终类型的属性。支持这个规则会使整个类变得良性。
     
 * <p>It is also imperative that implementations read the field only once,
 * as in:
 *
 * <pre> {@code
 * public Set<K> keySet() {
 *   Set<K> ks = keySet;  // single racy read
 *   if (ks == null) {
 *     ks = new KeySet();
 *     keySet = ks;
 *   }
 *   return ks;
 * }
 *}</pre>
 */
transient Set<K>        keySet;
transient Collection<V> values;
```

6.迭代器替代品 keySet  实际测试，同一个实例类只会加载一次

```
/**
 * {@inheritDoc}
 *
 * @implSpec
 * This implementation returns a set that subclasses {@link AbstractSet}.
 * The subclass's iterator method returns a "wrapper object" over this
 * map's <tt>entrySet()</tt> iterator.  The <tt>size</tt> method
 * delegates to this map's <tt>size</tt> method and the
 * <tt>contains</tt> method delegates to this map's
 * <tt>containsKey</tt> method.
 *
 * <p>The set is created the first time this method is called,
 * and returned in response to all subsequent calls.  No synchronization
 * is performed, so there is a slight chance that multiple calls to this
 * method will not all return the same set.
   这个实现者返回一个子类set，这个子类的迭代方法返回了一个装饰对象通过map的迭代器。这个size方法代表了这个map的size方法背弃这个包含方法代表了这个map的抱哈能否发。
   这个set首次创建是在这个方法被调用并且返回用于返回后续的调用。没有同步性能，所以可能会有轻微的不同多个调用这个方法，并不是所有都返回相同的set
  
    
 */
public Set<K> keySet() {
    Set<K> ks = keySet;
    if (ks == null) {
      //初次加载
        ks = new AbstractSet<K>() {
            public Iterator<K> iterator() {
                return new Iterator<K>() {
                    private Iterator<Entry<K,V>> i = entrySet().iterator();

                    public boolean hasNext() {
                        return i.hasNext();
                    }

                    public K next() {
                        return i.next().getKey();
                    }

                    public void remove() {
                        i.remove();
                    }
                };
            }

            public int size() {
                return AbstractMap.this.size();
            }

            public boolean isEmpty() {
                return AbstractMap.this.isEmpty();
            }

            public void clear() {
                AbstractMap.this.clear();
            }

            public boolean contains(Object k) {
                return AbstractMap.this.containsKey(k);
            }
        };
        keySet = ks;
    }
    return ks;
}

/**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation returns a collection that subclasses {@link
     * AbstractCollection}.  The subclass's iterator method returns a
     * "wrapper object" over this map's <tt>entrySet()</tt> iterator.
     * The <tt>size</tt> method delegates to this map's <tt>size</tt>
     * method and the <tt>contains</tt> method delegates to this map's
     * <tt>containsValue</tt> method.
     *
     * <p>The collection is created the first time this method is called, and
     * returned in response to all subsequent calls.  No synchronization is
     * performed, so there is a slight chance that multiple calls to this
     * method will not all return the same collection.
     */
    public Collection<V> values() {
        Collection<V> vals = values;
        if (vals == null) {
            vals = new AbstractCollection<V>() {
                public Iterator<V> iterator() {
                    return new Iterator<V>() {
                        private Iterator<Entry<K,V>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public V next() {
                            return i.next().getValue();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                public void clear() {
                    AbstractMap.this.clear();
                }

                public boolean contains(Object v) {
                    return AbstractMap.this.containsValue(v);
                }
            };
            values = vals;
        }
        return vals;
    }
```

7.hashCode

```
/**
 * Returns the hash code value for this map.  The hash code of a map is
 * defined to be the sum of the hash codes of each entry in the map's
 * <tt>entrySet()</tt> view.  This ensures that <tt>m1.equals(m2)</tt>
 * implies that <tt>m1.hashCode()==m2.hashCode()</tt> for any two maps
 * <tt>m1</tt> and <tt>m2</tt>, as required by the general contract of
 * {@link Object#hashCode}.
 * 
   返回map的散列值。这个map的散列码是所有元素的散列码之和。这确保了m1等于m2时，散列码也会相等。
    
 * @implSpec
 * This implementation iterates over <tt>entrySet()</tt>, calling
 * {@link Map.Entry#hashCode hashCode()} on each element (entry) in the
 * set, and adding up the results.
 *
 * @return the hash code value for this map
 * @see Map.Entry#hashCode()
 * @see Object#equals(Object)
 * @see Set#equals(Object)
 */
public int hashCode() {
    int h = 0;
    Iterator<Entry<K,V>> i = entrySet().iterator();
    while (i.hasNext())
        h += i.next().hashCode();
    return h;
}
```

8.clone

```
protected Object clone() throws CloneNotSupportedException {
    AbstractMap<?,?> result = (AbstractMap<?,?>)super.clone();
    //需要把ketset和value置空，这样下次拷贝类迭代式可以初始化
    result.keySet = null;
    result.values = null;
    return result;
}
```

9.剩余两个合并 

SimpleEntry 只含有一个key和value的map

SimpleImmutableEntry 不可修改的单个map
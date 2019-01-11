我惶恐的是我自己的无知，我害怕的是自己的浮躁，我向往的是更好的自己。

加油！在路上。

### 一.作者综述

```
This class implements the <tt>Set</tt> interface
this class permits the <tt>null</tt>element
Iterating over this set requires time proportional to the sum of the <tt>HashSet</tt> instance's size (the number of elements) plus the
* "capacity" of the backing <tt>HashMap</tt> instance (the number of buckets).
Note that this implementation is not synchronized
```

这是一个set的接口，允许空值，在迭代过程中，花费的时间是根据hashset的size+容量capacity的和得到，因此如果注重迭代的性能，那么就不要把容量设置的太大。

hashset并不是线程同步（可以通过Set s = Collections.synchronizedSet(new HashSet(...));实现同步）

### 二.个人看法

看下去发现都是用hashmap实现大部分方法，不应该看接口的·····
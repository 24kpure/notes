静下心来，加油！

## 一.作者综述

本节主要介绍，ArrayList源码开始部分，作者对arrylist的介绍。

1.**This class is roughly equivalent to <tt>Vector</tt>, except that it is unsynchronized**.

除了ArrayList是非同步的，ArrayList和Vector大致相等，所以也有一种说法是ArrayList是Vector的裸奔版，虽然非线程安全，但是速度要更快。

 

**2**

```
1> <p>The <tt>size</tt>, <tt>isEmpty</tt>, <tt>get</tt>, <tt>set</tt>, * <tt>iterator</tt>, and <tt>listIterator</tt> operations run in constant * time.

2> The <tt>add</tt> operation runs in <i>amortized constant time</i>, * that is, adding n elements requires O(n) time.

3> All of the other operations * run in linear time (roughly speaking).
```

对方法执行时间的一个概述，1>中的方法是最快的，耗时O(0)，然后是2>中的add的时间的是均摊，添加n个元素耗时O(n),最后3>中说其它方法大致都是线性时间，也就是控制在o(n)以内，那么和2>差不多咯？

 

**3**

```
<p>Each <tt>ArrayList</tt> instance has a <i>capacity</i>.
It is always * at least as large as the list size. As elements are added to an ArrayList, * its capacity grows automatically.
An application can increase the capacity of an <tt>ArrayList</tt> instance * before adding a large number of elements using the <tt>ensureCapacity</tt> *operation
```

每个ArrayList都有容量，容量的大小至少比ArrayList的size一样大，当添加元素到ArrayList同时，它的容量会自动增加。当一个应用添加大量元素时，你可以通过ensureCapacity来限定增加的容量，使得ArrayList增加要小于预期。（自增比例我在vector中有提及，每次变成size的1.5倍）

###  

4**List list = Collections.synchronizedList(new ArrayList(...))**

可以通过这种方式实现线程安全？等等可以写个实例看看。

 

### 二.ArrayList构成

public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable

**1主体构成**

继承自AbstractList，这个没看，先放着。

实现的的接口（与vector一致）

1>list接口，其实就是一些list实现一些自带方法，问题不大；

2>至于RandomAccess接口，根据文档的说明，实现这个接口主要是在日后遍历等情况的时候，使用for比iterator迭代来的要快，而Sequencelist则是迭代比较快。

3>Cloneable接口，vector有仔细说，这边不提。

4>序列化接口。

 

**2其它构成**

1>private static final int DEFAULT_CAPACITY = 10;

默认容量10，所以我们直接声明ArrayList他的capacity是10，继续添加超过10会扩容到15，以此类推。 

2>private static final Object[] EMPTY_ELEMENTDATA = {}; /*Shared empty array instance used for empty instances.*/

使用空的实例来展示空的数组实例？菜菜的我并没有发现有什么鸟用。---看到后面发现还是有用的，用来定义没有指定容量的list

 

### 三.构造器

**1.设置容量的构造器。**

```
public ArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
        this.elementData = new Object[initialCapacity];
    } else if (initialCapacity == 0) {
        this.elementData = EMPTY_ELEMENTDATA;
    } else {
        throw new IllegalArgumentException("Illegal Capacity: "+
                                           initialCapacity);
    }
}
```

**2.无定义容量，最经常用的构造器。**

```
public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}
```

**3.这个方法在vector中有提及，不过有不同的部分。**

```
public ArrayList(Collection<? extends E> c) {
    elementData = c.toArray();
    if ((size = elementData.length) != 0) {               //vector并没有这部分，为毛size区分开来，为0要使用类似2部分的定义，返回空数组，是因为vector没有定义空的部分吧。
        // c.toArray might (incorrectly) not return Object[] (see 6260652)         
        if (elementData.getClass() != Object[].class)    //当初没发现的，为什么class要不同才copy？莫非要对待我看array部分才明白？
            elementData = Arrays.copyOf(elementData, size, Object[].class);
    } else {
        // replace with empty array.
        this.elementData = EMPTY_ELEMENTDATA;
    }
```

 

### 三.其他方法

```
1.这个方法正常情况下，包内都不会调用，是给用户更改elementdata数据以后，将elementdata数规范到size数，没碰到过这种情况，思考ing
public void trimToSize() {
    modCount++;
    if (size < elementData.length) {
        elementData = (size == 0)
          ? EMPTY_ELEMENTDATA
          : Arrays.copyOf(elementData, size);
    }
}
```

**2设置容量数**

```
public void ensureCapacity(int minCapacity) {
    int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
        // any size if not default element table
        ? 0
        // larger than default for default empty table. It's already
        // supposed to be at default size.
        : DEFAULT_CAPACITY;

    if (minCapacity > minExpand) {
        ensureExplicitCapacity(minCapacity);
    }
}
```

**3传入的mincapacity并没有什么软用，意思是和size接近，就无视啦····**

```
private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

**4.在调用contain方法时，是调用indexof，而indexof是遍历的，所以说contain方法是o(n)**

```
public boolean contains(Object o) {
    return indexOf(o) >= 0;
}
public int indexOf(Object o) {
    if (o == null) {  //先判断是否为空，很实用，我经常犯浑忘记
        for (int i = 0; i < size; i++)
            if (elementData[i]==null)
                return i;
    } else {
        for (int i = 0; i < size; i++)
            if (o.equals(elementData[i]))
                return i;
    }
    return -1;
}
```

**5.这个方法设置值的时候还可以返回修改前的值，很实用。**

```
public E set(int index, E element) {
    rangeCheck(index);

    E oldValue = elementData(index);
    elementData[index] = element;
    return oldValue;
}
```

**6.指定位置add，是把指定位置之后的元素全部后移一位实现的。**

```
public void add(int index, E element) {
    rangeCheckForAdd(index);

    ensureCapacityInternal(size + 1);  // Increments modCount!!
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);
    elementData[index] = element;
    size++;
}
```

**7.gc回收实例，将size减小，并设为空。**

```
private void fastRemove(int index) {
    modCount++;
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; // clear to let GC do its work
}
public void clear() {
    modCount++;

    // clear to let GC do its work
    for (int i = 0; i < size; i++)
        elementData[i] = null;

    size = 0;
}
```

**8这个方法是从fromindex到toindex位置的数据删除，各位可以想象下你们怎么实现的，在看看源码实现方式，对思维也有帮助。**

```
protected void removeRange(int fromIndex, int toIndex) {
    modCount++;
    int numMoved = size - toIndex;
    System.arraycopy(elementData, toIndex, elementData, fromIndex,
                     numMoved);

    // clear to let GC do its work
    int newSize = size - (toIndex-fromIndex);
    for (int i = newSize; i < size; i++) {
        elementData[i] = null;
    }
    size = newSize;
}
```

**9这个方法第一遍没看懂，主要是两个方法调用，一个是retain保持，一个是remove，complement分别为true和flase，我下面主要以false即remove情况说**

```
private boolean batchRemove(Collection<?> c, boolean complement) {
    final Object[] elementData = this.elementData;
    int r = 0, w = 0;
    boolean modified = false;
    try {
        for (; r < size; r++)
            if (c.contains(elementData[r]) == complement)     //当集合c不包含list的某个元素时，将该元素添加到list中，由于w一定小于等于r，所以可以采用这种方式。
                elementData[w++] = elementData[r];
    } finally {
        // Preserve behavioral compatibility with AbstractCollection,
        // even if c.contains() throws.
        if (r != size) {                   //当抛出异常时，r！=size，会将没有的判断的部分copy到处理结果后面
            System.arraycopy(elementData, r,
                             elementData, w,
                             size - r);
            w += size - r;
        }
        if (w != size) {
            // clear to let GC do its work
            for (int i = w; i < size; i++)  //w之前的数据都是处理过的数据，之后的都是原数据，将这清理回收
                elementData[i] = null;
            modCount += size - w;
            size = w;
            modified = true;
        }
    }
    return modified;
}
```

### 四.总结

古人云：量力而行，因为无知所以学习，后面一些部分看着难以理解，我打算日后反刍，走好自己的路吧。
一步一个脚印，每天做好自己。

### 一.vector的构成

```
public class Vector<E>    extends AbstractList<E>    implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

首先vector继承自AbstarceList类，这个类先不管，日后再说。

 

实现的的接口

1.list接口，其实就是一些list实现一些自带方法，问题不大；

2.至于RandomAccess接口，根据文档的说明，实现这个接口主要是在日后遍历等情况的时候，使用for比iterator迭代来的要快，而上篇文章实现的Sequencelist则是迭代比较快（文档说明太长不贴，大家可以自己看）。

3.Cloneable接口，这个接口看完我也是饱含热泪。这个接口主要是用户克隆，即你想完全拷贝一个类，却不想在堆中使用同一块地址，那么就使用clone。但是clone接口，都是文档，实现方法都没有，辣么我们只能去找object类了，最后找到了protected native Object clone() throws CloneNotSupportedException;看来只是要重写这个方法，那事情简单多了。不过我们如果只是简单的重写（比如直接返回super.clone）还会涉及到浅克隆和深度克隆的问题,可以参考这边--->[浅克隆？深克隆？](http://kentkwan.iteye.com/blog/739514)

4.序列化接口，问题不大，pass。

 

其它组成

protected Object[] elementData; 数据类型就是object数组，理所应当的吧

protected int elementCount;   一个统计数据个数的变量

private static final long serialVersionUID = -2767605614048989439L; 序列化标识id

protected int capacityIncrement; 这个是增量大小，由于这个和arraylist一样，与根据说明的空间预先分配空间，当不断插入数据超过预先分配之后，vector会根据这个值进行增加，如果为0，就和arraylist一致了。至于arraylist我看了1.7版本上是在原有基础上扩充一点五倍。

 

 

### 二.构造器

##### 1.这个构造器就有增量大小的选项，还有就是初始vector的大小。

```
 public Vector(int initialCapacity, int capacityIncrement) {        super();        if (initialCapacity < 0)            throw new IllegalArgumentException("Illegal Capacity: "+                                               initialCapacity);        this.elementData = new Object[initialCapacity];        this.capacityIncrement = capacityIncrement;    }
```

 

##### 2.第二个就是调用第一个，就是默认增量置为0.

```
 public Vector(int initialCapacity) {        this(initialCapacity, 0);    }
```

 

##### 3.在vector为空的时候，预先分配10的空间。

```
 public Vector() {        this(10);    }
```

 

##### 4.可以直接放入集合，不过问号嘛意思？extends E嘛意思？还有什么叫做toArray方式可能出错，要我去看6260652？[bug说明传送门](http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6260652)

```
 public Vector(Collection<? extends E> c) {        elementData = c.toArray();        elementCount = elementData.length;        // c.toArray might (incorrectly) not return Object[] (see 6260652)        if (elementData.getClass() != Object[].class)            elementData = Arrays.copyOf(elementData, elementCount, Object[].class);    }
```

一个一个的说文号表示任意类，结合extends E表示任意继承自E的类，想看具体-->[传送门](http://blog.csdn.net/wuxinliulei/article/details/38474367)。我也简单说说，Facher是父类，Son是子类，son的一个实例叫son（小写的喔）如下图

```
Vector<Father> myList = new Vector<Fathers>(son);
凡是Father或者Father的子类的Collection均可以构造成Vector<Father>的样式（我自己都有点晕）
```

 

### 三.其它方法

##### 1.copy到数组方法，vector是线程安全的，使用同步字限定

```
public synchronized void copyInto(Object[] anArray) {        System.arraycopy(elementData, 0, anArray, 0, elementCount);    }
```

 

##### 2.修剪vector的容量，当所含数据数量小于这个容量的时候，可以将容量变为当前大小。不过这个modCount是哪来的，找半天找不到在哪，下文也多次出现。

解答：modCount是用来记录更改该List大小次数的  

参考：The number of times this list has been *structurally modified*. Structural modifications are those that change the size of the list

```
public synchronized void trimToSize() {        modCount++;        int oldCapacity = elementData.length;        if (elementCount < oldCapacity) {            elementData = Arrays.copyOf(elementData, elementCount);        }    }
```

 

##### 3.上文提及的扩充容量，当capacityIncrement不为0时，都以它为增量。

```
private void grow(int minCapacity) {        // overflow-conscious code        int oldCapacity = elementData.length;        int newCapacity = oldCapacity + ((capacityIncrement > 0) ?                                         capacityIncrement : oldCapacity);        if (newCapacity - minCapacity < 0)            newCapacity = minCapacity;        if (newCapacity - MAX_ARRAY_SIZE > 0)            newCapacity = hugeCapacity(minCapacity);        elementData = Arrays.copyOf(elementData, newCapacity);    }
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
//数组MAX_ARRAY_SIZE为什么要比MAX_VALUE小8？为什么要在这两个数区间再选择？直接等于MAX_VALUE 就好，大小也就差8而已，为什么？
 private static int hugeCapacity(int minCapacity) {        if (minCapacity < 0) // overflow            throw new OutOfMemoryError();        return (minCapacity > MAX_ARRAY_SIZE) ?            Integer.MAX_VALUE :            MAX_ARRAY_SIZE;    }
```

 

##### 4.枚举三兄弟，vector调用此函数可以返回一个枚举列，大家可以自己写实例就知道意思了（我自己写的太low就不贴了）。

```
public Enumeration<E> elements() {        return new Enumeration<E>() {            int count = 0;
            public boolean hasMoreElements() {                return count < elementCount;            }
            public E nextElement() {                synchronized (Vector.this) {                    if (count < elementCount) {                        return elementData(count++);                    }                }                throw new NoSuchElementException("Vector Enumeration");            }        };    }
```

##### 5.vector的克隆，看下文应该可以看出是深度克隆，所以请大胆使用！

 ```public synchronized Object clone() {        try {            @SuppressWarnings("unchecked")                Vector<E> v = (Vector<E>) super.clone();            v.elementData = Arrays.copyOf(elementData, elementCount);            v.modCount = 0;            return v;        } catch (CloneNotSupportedException e) {            // this shouldn't happen, since we are Cloneable            throw new InternalError();        }    }```

##### 6.数据操作部分的add，remove等就不看了，因为我懒（菜）的不行。直接看迭代器...

 ```private class Itr implements Iterator<E> {        int cursor;       // index of next element to return        int lastRet = -1; // index of last element returned; -1 if no such        int expectedModCount = modCount;//注解自see`

```java
        public boolean hasNext() {            // Racy but within spec, since modifications are checked            // within or after synchronization in next/previous            return cursor != elementCount;        }
        public E next() {            synchronized (Vector.this) {                checkForComodification();//这？好吧找到了在下面，补贴上来，是一个验证                int i = cursor;                if (i >= elementCount)                    throw new NoSuchElementException();                cursor = i + 1;                return elementData(lastRet = i);            }        }
        public void remove() {            if (lastRet == -1)                throw new IllegalStateException();            synchronized (Vector.this) {                checkForComodification();                Vector.this.remove(lastRet);                expectedModCount = modCount;            }            cursor = lastRet;            lastRet = -1;        }
        final void checkForComodification() {            if (modCount != expectedModCount)                throw new ConcurrentModificationException();        }    }
 final void checkForComodification() {            if (modCount != expectedModCount)                throw new ConcurrentModificationException();        }    }
```
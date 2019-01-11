### 聚会吃饭，让我更浮躁，慌唉····

### 一.注释综述

```
All of the operations perform as could be expected for a doubly-linkedlist.
Note that this implementation is not synchronized 
List list = Collections.synchronizedList(new LinkedList(...));
```

此方法的所有操作都可以被看作一个双向的链表。这个接口实现是非同步，如果要同步可以使用List list = Collections.synchronizedList(new LinkedList(...))。

```
The iterators returned by this class's {@code iterator} and methods are <i>fail-fast</i>
```

迭代器可以快速报错

 

### 二.LinkedList构成

```
public class LinkedList<E>
extends AbstractSequentialList<E>
implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

**1主体构成**

继承抽象队列链表实现了List接口，Deque队列，克隆接口，序列化接口。

Deque接口继承Query，实现的是双向队列（double-Query），因此linkedlist也具有双向队列的性质。

 

```
transient int size = 0;
transient Node<E> first;
transient Node<E> last;
```

**2.数据结构**

```
private static class Node<E> {
    E item;   //数据
    Node<E> next; //前节点
    Node<E> prev; //后节点

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

**3其它构成**

```
transient int size = 0;
transient Node<E> first;
transient Node<E> last;
```

size表示list中数据的数量，first为头结点，last为尾节点。

 

### 三.构造器

**1.基础构造器，不过为什么什么都没有？不过既然作为链表，没有头结点什么的，确实内存中也不需要空间。**

```
public LinkedList() {
}
```

**2.调用1构造器，然后添加集合元素。**

```
public LinkedList(Collection<? extends E> c) {
    this();
    addAll(c);
}
```

···好像没什么内容····

 

### 四.其它方法

**1.设置首节点**

```
private void linkFirst(E e) {
    final Node<E> f = first;
    final Node<E> newNode = new Node<>(null, e, f); //下一节点设置为首节点
    first = newNode;   //首届点变为当前节点
    if (f == null)
        last = newNode;//只有一个节点时，当前节点也是尾节点
    else
        f.prev = newNode;//原头结点前节点指向当前节点
    size++;
    modCount++;
}
```

**2.设置尾节点，add方法实际调用的就是本方法，将元素插入尾节点。**

```
void linkLast(E e) {  //与1类似
    final Node<E> l = last;
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;
    else
        l.next = newNode;
    size++;
    modCount++;
}
```

**3.将数据查到节点succ之前**

```
void linkBefore(E e, Node<E> succ) {
    // assert succ != null;
    final Node<E> pred = succ.prev;
    final Node<E> newNode = new Node<>(pred, e, succ);
    succ.prev = newNode;
    if (pred == null)
        first = newNode;
    else
        pred.next = newNode;
    size++;
    modCount++;
}
```

**4.根据下标取节点**

```
Node<E> node(int index) {
    // assert isElementIndex(index);

    if (index < (size >> 1)) {   //根据下标是否小于size右移一位，如果小于就更靠近头结点，否者靠近尾节点，比起从头开始硬迭代，提高效率。
        Node<E> x = first;
        for (int i = 0; i < index; i++)
            x = x.next;
        return x;
    } else {
        Node<E> x = last;
        for (int i = size - 1; i > index; i--)
            x = x.prev;
        return x;
    }
}
```

### 五.总结

果然什么东西和hashmap比都简单一点，可以浮躁的心，但是事情不能浮躁。
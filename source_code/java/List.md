### List

bidirectional（双向的）

arbitrary（任意的）

#### 1.综述

```
/**
 * An ordered collection (also known as a <i>sequence</i>).  The user of this
 * interface has precise control over where in the list each element is
 * inserted.  The user can access elements by their integer index (position in
 * the list), and search for elements in the list.<p>
 *
 
  一个顺序集合（也经常被认为是队列）。这些接口的用户可以精确的操作当这些元素被插入。用户可以获取元素通过该数字索引，在list中查询元素。
 
 * Unlike sets, lists typically allow duplicate elements.  More formally,
 * lists typically allow pairs of elements <tt>e1</tt> and <tt>e2</tt>
 * such that <tt>e1.equals(e2)</tt>, and they typically allow multiple
 * null elements if they allow null elements at all.  It is not inconceivable（不可思议）
 * that someone might wish to implement a list that prohibits duplicates, by
 * throwing runtime exceptions when the user attempts to insert them, but we
 * expect this usage to be rare.<p>
 
    不像set，list允许不同的元素，允许多个空值。
 
 *
 * The <tt>List</tt> interface places additional stipulations, beyond those
 * specified in the <tt>Collection</tt> interface, on the contracts of the
 * <tt>iterator</tt>, <tt>add</tt>, <tt>remove</tt>, <tt>equals</tt>, and
 * <tt>hashCode</tt> methods.  Declarations for other inherited methods are
 * also included here for convenience.<p>
 *
 * The <tt>List</tt> interface provides four methods for positional (indexed)
 * access to list elements.  Lists (like Java arrays) are zero based.  Note
 * that these operations may execute in time proportional to the index value
 * for some implementations (the <tt>LinkedList</tt> class, for
 * example). Thus, iterating over the elements in a list is typically
 * preferable to indexing through it if the caller does not know the
 * implementation.<p>
     List的接口添加了额外的规定，超过了集合的接口，在迭代器，添加，移除，相等等操作方法。
     声明是为了其它继承方法变得更加便利。
   
 
 * The <tt>List</tt> interface provides a special iterator, called a
 * <tt>ListIterator</tt>, that allows element insertion and replacement, and
 * bidirectional（双向的） access in addition to the normal operations that the
 * <tt>Iterator</tt> interface provides.  A method is provided to obtain a
 * list iterator that starts at a specified position in the list.<p>
 *
 * The <tt>List</tt> interface provides two methods to search for a specified
 * object.  From a performance standpoint, these methods should be used with
 * caution.  In many implementations they will perform costly linear
 * searches.<p>
 *
 * The <tt>List</tt> interface provides two methods to efficiently insert and
 * remove multiple elements at an arbitrary（任意的） point in the list.<p>
 *
 * Note: While it is permissible for lists to contain themselves as elements,
 * extreme caution is advised: the <tt>equals</tt> and <tt>hashCode</tt>
 * methods are no longer well defined on such a list.
 *
    list提供了一个特殊的迭代器，称为list迭代器，它支持元素的插入与替代，并且相较于普通迭代器双向可达。这个方法尊重list迭代器可以在指定位置开始迭代。
    list接口提供了两个方法检索特定检索。从单点性能而言，这些方法需要小心使用。在很多实现类中表现都是线性搜索（全部遍历）。
    list接口提供了两个方法有效的插入和移除多个元素在任意的节点。
    记录点：当一个list允许含有自己本身时，尤其要注意这个建议，equal和hashCode方法都不再清晰。
    
    
    
 
 * <p>Some list implementations have restrictions on the elements that
 * they may contain.  For example, some implementations prohibit null elements,
 * and some have restrictions on the types of their elements.  Attempting to
 * add an ineligible element throws an unchecked exception, typically
 * <tt>NullPointerException</tt> or <tt>ClassCastException</tt>.  Attempting
 * to query the presence of an ineligible element may throw an exception,
 * or it may simply return false; some implementations will exhibit the former
 * behavior and some will exhibit the latter.  More generally, attempting an
 * operation on an ineligible element whose completion would not result in
 * the insertion of an ineligible element into the list may throw an
 * exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 *
   一些实现类有对含有元素进行约束。比如说，一些不允许空值。。。。和set基本强调的事情一样
 
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <E> the type of elements in this list
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see Collection
 * @see Set
 * @see ArrayList
 * @see LinkedList
 * @see Vector
 * @see Arrays#asList(Object[])
 * @see Collections#nCopies(int, Object)
 * @see Collections#EMPTY_LIST
 * @see AbstractList
 * @see AbstractSequentialList
 * @since 1.2
 */
```

#### 2.方法（父类重复不记录）

1.类声明 继承自集合

```
public interface List<E> extends Collection<E>
```

2.sort方法 （排序是稳定的 logN）

```
default void sort(Comparator<? super E> c) {
    Object[] a = this.toArray();
    Arrays.sort(a, (Comparator) c);
    //转化为数组 数组排序
    ListIterator<E> i = this.listIterator();
    for (Object e : a) {
        //根据排序挨个设置
        i.next();
        i.set((E) e);
    }
}
```

3.set指定元素添加到指定位置

```
/**
 * Replaces the element at the specified position in this list with the
 * specified element (optional operation).
 *
 * @param index index of the element to replace
 * @param element element to be stored at the specified position
 * @return the element previously at the specified position
 * @throws UnsupportedOperationException if the <tt>set</tt> operation
 *         is not supported by this list
 * @throws ClassCastException if the class of the specified element
 *         prevents it from being added to this list
 * @throws NullPointerException if the specified element is null and
 *         this list does not permit null elements
 * @throws IllegalArgumentException if some property of the specified
 *         element prevents it from being added to this list
 * @throws IndexOutOfBoundsException if the index is out of range
 *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
 */
E set(int index, E element);
```

4.add 与上面基本一致

```
/**
 * Inserts the specified element at the specified position in this list
 * (optional operation).  Shifts the element currently at that position
 * (if any) and any subsequent elements to the right (adds one to their
 * indices).
 *
 * @param index index at which the specified element is to be inserted
 * @param element element to be inserted
 * @throws UnsupportedOperationException if the <tt>add</tt> operation
 *         is not supported by this list
 * @throws ClassCastException if the class of the specified element
 *         prevents it from being added to this list
 * @throws NullPointerException if the specified element is null and
 *         this list does not permit null elements
 * @throws IllegalArgumentException if some property of the specified
 *         element prevents it from being added to this list
 * @throws IndexOutOfBoundsException if the index is out of range
 *         (<tt>index &lt; 0 || index &gt; size()</tt>)
 */
void add(int index, E element);
```

5.listIterator方法

```
/**
 * Returns a list iterator over the elements in this list (in proper
 * sequence).
 *
 * @return a list iterator over the elements in this list (in proper
 *         sequence)
 */
ListIterator<E> listIterator();
```

从指定位置开始的迭代

```
/**
 * Returns a list iterator over the elements in this list (in proper
 * sequence), starting at the specified position in the list.
 * The specified index indicates the first element that would be
 * returned by an initial call to {@link ListIterator#next next}.
 * An initial call to {@link ListIterator#previous previous} would
 * return the element with the specified index minus one.
 *
 * @param index index of the first element to be returned from the
 *        list iterator (by a call to {@link ListIterator#next next})
 * @return a list iterator over the elements in this list (in proper
 *         sequence), starting at the specified position in the list
 * @throws IndexOutOfBoundsException if the index is out of range
 *         ({@code index < 0 || index > size()})
 */
ListIterator<E> listIterator(int index);
```

6.subList  获取起始节点   

```
/**
 * Returns a view of the portion of this list between the specified
 * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive.  (If
 * <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned list is
 * empty.)  The returned list is backed by this list, so non-structural
 * changes in the returned list are reflected in this list, and vice-versa.
 * The returned list supports all of the optional list operations supported
 * by this list.<p>
 *
 * This method eliminates the need for explicit range operations (of
 * the sort that commonly exist for arrays).  Any operation that expects
 * a list can be used as a range operation by passing a subList view
 * instead of a whole list.  For example, the following idiom
 * removes a range of elements from a list:
 * <pre>{@code
 *      list.subList(from, to).clear();
 * }</pre>
 * Similar idioms may be constructed for <tt>indexOf</tt> and
 * <tt>lastIndexOf</tt>, and all of the algorithms in the
 * <tt>Collections</tt> class can be applied to a subList.<p>
 *
 * The semantics of the list returned by this method become undefined if
 * the backing list (i.e., this list) is <i>structurally modified</i> in
 * any way other than via the returned list.  (Structural modifications are
 * those that change the size of this list, or otherwise perturb it in such
 * a fashion that iterations in progress may yield incorrect results.)
 *
 * @param fromIndex low endpoint (inclusive) of the subList
 * @param toIndex high endpoint (exclusive) of the subList
 * @return a view of the specified range within this list
 * @throws IndexOutOfBoundsException for an illegal endpoint index value
 *         (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
 *         fromIndex &gt; toIndex</tt>)
 */
List<E> subList(int fromIndex, int toIndex);
```
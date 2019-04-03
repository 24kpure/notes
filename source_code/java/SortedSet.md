SortedSet sourceCode ,yeah

violate（违反，暴力）

 offending（不愉快）

#### 1.综述

```
/**
 * A {@link Set} that further provides a <i>total ordering</i> on its elements.
 * The elements are ordered using their {@linkplain Comparable natural
 * ordering}, or by a {@link Comparator} typically provided at sorted
 * set creation time.  The set's iterator will traverse the set in
 * ascending element order. Several additional operations are provided
 * to take advantage of the ordering.  (This interface is the set
 * analogue of {@link SortedMap}.)
 
   一个set进一步提供了对其元素总排序，这些元素排序是用默认的Compareble排序，通过创建时间。
   这个set的迭代器会升序遍历set。几个额外的操作会提供提现了排序的优点。
 *
 * <p>All elements inserted into a sorted set must implement the <tt>Comparable</tt>
 * interface (or be accepted by the specified comparator).  Furthermore, all
 * such elements must be <i>mutually comparable</i>: <tt>e1.compareTo(e2)</tt>
 * (or <tt>comparator.compare(e1, e2)</tt>) must not throw a
 * <tt>ClassCastException</tt> for any elements <tt>e1</tt> and <tt>e2</tt> in
 * the sorted set.  Attempts to violate（违反，暴力） this restriction will cause the
 * offending（不愉快） method or constructor invocation to throw a
 * <tt>ClassCastException</tt>.
 *
    所有的元素插入有序set时必须实现Comparable接口。更进一步，所有元素必须是可比较的，不能抛出类型异常。想要去违反这个约束会造成方法或者构造器抛出异常。
 
 * <p>Note that the ordering maintained by a sorted set (whether or not an
 * explicit comparator is provided) must be <i>consistent with equals</i> if
 * the sorted set is to correctly implement the <tt>Set</tt> interface.  (See
 * the <tt>Comparable</tt> interface or <tt>Comparator</tt> interface for a
 * precise definition of <i>consistent with equals</i>.)  This is so because
 * the <tt>Set</tt> interface is defined in terms of the <tt>equals</tt>
 * operation, but a sorted set performs all element comparisons using its
 * <tt>compareTo</tt> (or <tt>compare</tt>) method, so two elements that are
 * deemed equal by this method are, from the standpoint of the sorted set,
 * equal.  The behavior of a sorted set <i>is</i> well-defined even if its
 * ordering is inconsistent with equals; it just fails to obey the general
 * contract of the <tt>Set</tt> interface.
 
    记录下，排序的保持必须使用equals相等（无论comparator是否相等）。可能用equal或者compare实现。
 *
 * <p>All general-purpose sorted set implementation classes should
 * provide four "standard" constructors: 1) A void (no arguments)
 * constructor, which creates an empty sorted set sorted according to
 * the natural ordering of its elements.  2) A constructor with a
 * single argument of type <tt>Comparator</tt>, which creates an empty
 * sorted set sorted according to the specified comparator.  3) A
 * constructor with a single argument of type <tt>Collection</tt>,
 * which creates a new sorted set with the same elements as its
 * argument, sorted according to the natural ordering of the elements.
 * 4) A constructor with a single argument of type <tt>SortedSet</tt>,
 * which creates a new sorted set with the same elements and the same
 * ordering as the input sorted set.  There is no way to enforce this
 * recommendation, as interfaces cannot contain constructors.
 *
    所有通常目的的有序set实现类应该提供4个标准的构造器。
    1.无参
    2.单参数 Comparator
    3.参数为集合
    4.另外一个有序set
      
 * <p>Note: several methods return subsets with restricted ranges.
 * Such ranges are <i>half-open</i>, that is, they include their low
 * endpoint but not their high endpoint (where applicable).
 * If you need a <i>closed range</i> (which includes both endpoints), and
 * the element type allows for calculation of the successor of a given
 * value, merely request the subrange from <tt>lowEndpoint</tt> to
 * <tt>successor(highEndpoint)</tt>.  For example, suppose that <tt>s</tt>
 * is a sorted set of strings.  The following idiom obtains a view
 * containing all of the strings in <tt>s</tt> from <tt>low</tt> to
 * <tt>high</tt>, inclusive:<pre>
 *   SortedSet&lt;String&gt; sub = s.subSet(low, high+"\0");</pre>
 *
    
    涉及子列，后续碰到具体函数说
    
 * A similar technique can be used to generate an <i>open range</i> (which
 * contains neither endpoint).  The following idiom obtains a view
 * containing all of the Strings in <tt>s</tt> from <tt>low</tt> to
 * <tt>high</tt>, exclusive:<pre>
 *   SortedSet&lt;String&gt; sub = s.subSet(low+"\0", high);</pre>
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <E> the type of elements maintained by this set
 *
 * @author  Josh Bloch
 * @see Set
 * @see TreeSet
 * @see SortedMap
 * @see Collection
 * @see Comparable
 * @see Comparator
 * @see ClassCastException
 * @since 1.2
 */
```

#### 2.方法

1.方法声明 继承自set

```
public interface SortedSet<E> extends Set<E> 
```

2.比对器

```
/**
 * Returns the comparator used to order the elements in this set,
 * or <tt>null</tt> if this set uses the {@linkplain Comparable
 * natural ordering} of its elements.
 *
 * @return the comparator used to order the elements in this set,
 *         or <tt>null</tt> if this set uses the natural ordering
 *         of its elements
 */
 返回此set的默认构造器
Comparator<? super E> comparator();
```

3.子列

```
/**
 * Returns a view of the portion of this set whose elements range
 * from <tt>fromElement</tt>, inclusive, to <tt>toElement</tt>,
 * exclusive.  (If <tt>fromElement</tt> and <tt>toElement</tt> are
 * equal, the returned set is empty.)  The returned set is backed
 * by this set, so changes in the returned set are reflected in
 * this set, and vice-versa.  The returned set supports all
 * optional set operations that this set supports.
 *
    返回部分处于指定索引的set元素，返回的set支持所有原set的方法。
 
 * <p>The returned set will throw an <tt>IllegalArgumentException</tt>
 * on an attempt to insert an element outside its range.
 *
 * @param fromElement low endpoint (inclusive) of the returned set
 * @param toElement high endpoint (exclusive) of the returned set
 * @return a view of the portion of this set whose elements range from
 *         <tt>fromElement</tt>, inclusive, to <tt>toElement</tt>, exclusive
 * @throws ClassCastException if <tt>fromElement</tt> and
 *         <tt>toElement</tt> cannot be compared to one another using this
 *         set's comparator (or, if the set has no comparator, using
 *         natural ordering).  Implementations may, but are not required
 *         to, throw this exception if <tt>fromElement</tt> or
 *         <tt>toElement</tt> cannot be compared to elements currently in
 *         the set.
 * @throws NullPointerException if <tt>fromElement</tt> or
 *         <tt>toElement</tt> is null and this set does not permit null
 *         elements
 * @throws IllegalArgumentException if <tt>fromElement</tt> is
 *         greater than <tt>toElement</tt>; or if this set itself
 *         has a restricted range, and <tt>fromElement</tt> or
 *         <tt>toElement</tt> lies outside the bounds of the range
 */
SortedSet<E> subSet(E fromElement, E toElement);
```

4.headSet

```
/**
 * Returns a view of the portion of this set whose elements are
 * strictly less than <tt>toElement</tt>.  The returned set is
 * backed by this set, so changes in the returned set are
 * reflected in this set, and vice-versa.  The returned set
 * supports all optional set operations that this set supports.
 *
    返回在toElement元素之前部分set，返回的set支持所有原set的方法。
 
 * <p>The returned set will throw an <tt>IllegalArgumentException</tt>
 * on an attempt to insert an element outside its range.
 *
 * @param toElement high endpoint (exclusive) of the returned set
 * @return a view of the portion of this set whose elements are strictly
 *         less than <tt>toElement</tt>
 * @throws ClassCastException if <tt>toElement</tt> is not compatible
 *         with this set's comparator (or, if the set has no comparator,
 *         if <tt>toElement</tt> does not implement {@link Comparable}).
 *         Implementations may, but are not required to, throw this
 *         exception if <tt>toElement</tt> cannot be compared to elements
 *         currently in the set.
 * @throws NullPointerException if <tt>toElement</tt> is null and
 *         this set does not permit null elements
 * @throws IllegalArgumentException if this set itself has a
 *         restricted range, and <tt>toElement</tt> lies outside the
 *         bounds of the range
 */
SortedSet<E> headSet(E toElement);
```
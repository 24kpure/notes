### Collection源码

#### 1.综述

hierarchy（分层）

manipulate（操作）

enforce（加强，执行）

restrictions（管制约束）

prohibit（禁止）

ineligible（不合格）

exhibit（表现，陈列）

mutate（变化）

recursive（递归）

impose（征收，欺骗，施加影响）

invariant（不变的）

equivalently（相等的）

stipulations （规定）

symmetric（对称）

imply（暗示）

```
/**
 * The root interface in the <i>collection hierarchy（分层）</i>.  A collection
 * represents a group of objects, known as its <i>elements</i>.  Some
 * collections allow duplicate elements and others do not.  Some are ordered
 * and others unordered.  The JDK does not provide any <i>direct</i>
 * implementations of this interface: it provides implementations of more
 * specific subinterfaces like <tt>Set</tt> and <tt>List</tt>.  This interface
 * is typically used to pass collections around and manipulate（操作） them where
 * maximum generality is desired.
    集合分层中的根接口。一个集合代表了一组对象，被认为是它的元素。一些集合允许独一无二的元素一些并没有。一些是顺序的一些是无序的。这个JDK并不直接提供实现方法实现这个接口：他提供了更多指定的子接口实现，比如set和list。这个接口是在大部分希望的场景下，显而易见是用于于传递集合和操作他们。
 *
 * <p><i>Bags</i> or <i>multisets</i> (unordered collections that may contain
 * duplicate elements) should implement this interface directly.
 *
 * <p>All general-purpose <tt>Collection</tt> implementation classes (which
 * typically implement <tt>Collection</tt> indirectly through one of its
 * subinterfaces) should provide two "standard" constructors: a void (no
 * arguments) constructor, which creates an empty collection, and a
 * constructor with a single argument of type <tt>Collection</tt>, which
 * creates a new collection with the same elements as its argument.  In
 * effect, the latter constructor allows the user to copy any collection,
 * producing an equivalent collection of the desired implementation type.
 * There is no way to enforce（加强，执行） this convention (as interfaces cannot contain
 * constructors) but all of the general-purpose <tt>Collection</tt>
 * implementations in the Java platform libraries comply.
   非顺序集合可能会包含唯一元素，应该直接实现这个接口。所有大体的目标集合实现类（不是直接继承而是通过子接口集成）应该提供两个标准的构造器，一个无参构造器，用于创建一个空的结合，并且一个单参数类型为集合构造器，用于创建一个相同元素的集合。事实上，后面这种构造器允许用户拷贝集合，生产一个相同的结合与实现类集合期望相同。没有办法加强协议（就像接口无法包含构造器），但是所有的Java平台的包中集合的实现类都会遵从的。
 
 
 * <p>The "destructive" methods contained in this interface, that is, the
 * methods that modify the collection on which they operate, are specified to
 * throw <tt>UnsupportedOperationException</tt> if this collection does not
 * support the operation.  If this is the case, these methods may, but are not
 * required to, throw an <tt>UnsupportedOperationException</tt> if the
 * invocation would have no effect on the collection.  For example, invoking
 * the {@link #addAll(Collection)} method on an unmodifiable collection may,
 * but is not required to, throw the exception if the collection to be added
 * is empty.
   接口包含这些破坏性的方法，也就是说，这些变更集合的方法是被指定抛出UnsupportedOperationException异常，如果这个集合不支持这个操作。在这种情况下，这些方法可能，单不是必要的会抛出UnsupportedOperationException当调用对集合没有影响时。比如说调用addAll方法在一个不可能变更的结合，可能但不是必要的会抛出，如果结合被添加后为空。
 
 *
 * <p><a name="optional-restrictions">
 * Some collection implementations have restrictions（管制约束） on the elements that
 * they may contain.</a>  For example, some implementations prohibit（禁止） null elements,
 * and some have restrictions on the types of their elements.  Attempting to
 * add an ineligible element throws an unchecked exception, typically
 * <tt>NullPointerException</tt> or <tt>ClassCastException</tt>.  Attempting
 * to query the presence of an ineligible（不合格） element may throw an exception,
 * or it may simply return false; some implementations will exhibit（表现，陈列） the former
 * behavior and some will exhibit the latter.  More generally, attempting an
 * operation on an ineligible element whose completion would not result in
 * the insertion of an ineligible element into the collection may throw an
 * exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 *
 一个集合的实现有在它所有的元素上有一些约束。举个例子，一些实现禁止空值和一些元素在类型上有所约束。比如空指针，类型转化错误。尝试去执行不合格元素可能会抛出异常，或者一些可能返回false。一些实现表现一个前者行为一些表现为后者的行为（多态）。更通俗来说。。。。。恩 算了
 
 * <p>It is up to each collection to determine its own synchronization
 * policy.  In the absence of a stronger guarantee by the
 * implementation, undefined behavior may result from the invocation
 * of any method on a collection that is being mutated by another
 * thread; this includes direct invocations, passing the collection to
 * a method that might perform invocations, and using an existing
 * iterator to examine the collection.
 *
 每个集合的同步策略取决于自己。接口缺少一个更强的保证，不明确的表现可能会导致执行集合任意方法，但是集合被另外的线程更改。
 
 
 * <p>Many methods in Collections Framework interfaces are defined in
 * terms of the {@link Object#equals(Object) equals} method.  For example,
 * the specification for the {@link #contains(Object) contains(Object o)}
 * method says: "returns <tt>true</tt> if and only if this collection
 * contains at least one element <tt>e</tt> such that
 * <tt>(o==null ? e==null : o.equals(e))</tt>."  This specification should
 * <i>not</i> be construed to imply that invoking <tt>Collection.contains</tt>
 * with a non-null argument <tt>o</tt> will cause <tt>o.equals(e)</tt> to be
 * invoked for any element <tt>e</tt>.  Implementations are free to implement
 * optimizations whereby the <tt>equals</tt> invocation is avoided, for
 * example, by first comparing the hash codes of the two elements.  (The
 * {@link Object#hashCode()} specification guarantees that two objects with
 * unequal hash codes cannot be equal.)  More generally, implementations of
 * the various Collections Framework interfaces are free to take advantage of
 * the specified behavior of underlying {@link Object} methods wherever the
 * implementor deems it appropriate.
 
    很多方法在集合框架中的接口是在Object中声明的。比如说，contain方法。
 *
 * <p>Some collection operations which perform recursive traversal of the
 * collection may fail with an exception for self-referential instances where
 * the collection directly or indirectly contains itself. This includes the
 * {@code clone()}, {@code equals()}, {@code hashCode()} and {@code toString()}
 * methods. Implementations may optionally handle the self-referential scenario,
 * however most current implementations do not do so.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @implSpec
 * The default method implementations (inherited or otherwise) do not apply any
 * synchronization protocol.  If a {@code Collection} implementation has a
 * specific synchronization protocol, then it must override default
 * implementations to apply that protocol.
 *
 * @param <E> the type of elements in this collection
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Set
 * @see     List
 * @see     Map
 * @see     SortedSet
 * @see     SortedMap
 * @see     HashSet
 * @see     TreeSet
 * @see     ArrayList
 * @see     LinkedList
 * @see     Vector
 * @see     Collections
 * @see     Arrays
 * @see     AbstractCollection
 * @since 1.2
 */
```



#### 2.方法描述

1.类声明 实现了迭代接口

```
public interface Collection<E> extends Iterable<E>
```

2.size方法

```
/**
 * Returns the number of elements in this collection.  If this collection
 * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
 * <tt>Integer.MAX_VALUE</tt>.
 *
 * @return the number of elements in this collection
 */
 //返回集合元素的数量。如果集合元素超过最大整数，返回最大整数
int size();


```

3.isEmpty方法

```
/**
 * Returns <tt>true</tt> if this collection contains no elements.
 *
 * @return <tt>true</tt> if this collection contains no elements
 */
 //判断是否无元素
boolean isEmpty();
```

4.contains方法

```
/**
 * Returns <tt>true</tt> if this collection contains the specified element.
 * More formally, returns <tt>true</tt> if and only if this collection
 * contains at least one element <tt>e</tt> such that
 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
 *
 * @param o element whose presence in this collection is to be tested
 * @return <tt>true</tt> if this collection contains the specified
 *         element
 * @throws ClassCastException if the type of the specified element
 *         is incompatible with this collection
 *         (<a href="#optional-restrictions">optional</a>)
 * @throws NullPointerException if the specified element is null and this
 *         collection does not permit null elements
 *         (<a href="#optional-restrictions">optional</a>)
 */
 //当至少集合含有一个指定元素，返回true
boolean contains(Object o);
```

5.iterator  继承自iterator接口

```
/**
 * Returns an iterator over the elements in this collection.  There are no
 * guarantees concerning the order in which the elements are returned
 * (unless this collection is an instance of some class that provides a
 * guarantee).
 *
 * @return an <tt>Iterator</tt> over the elements in this collection
 */
 //大部分情况都不保证顺序，除非一些集合实例提供保证
Iterator<E> iterator();
```

6.toArray方法

```
/**
 * Returns an array containing all of the elements in this collection.
 * If this collection makes any guarantees as to what order its elements
 * are returned by its iterator, this method must return the elements in
 * the same order.
 *
 * <p>The returned array will be "safe" in that no references to it are
 * maintained by this collection.  (In other words, this method must
 * allocate a new array even if this collection is backed by an array).
 * The caller is thus free to modify the returned array.
 *
 * <p>This method acts as bridge between array-based and collection-based
 * APIs.
 *
 * @return an array containing all of the elements in this collection
 */
 //返回数组包含所有集合内的元素，如果集合保证顺序，那么数组也保证顺序。数组和原集合是分开的，数组是新申请的空间，可以自由的变更数组不影响元素。
Object[] toArray();
```

7.add方法

```
/**
 * Ensures that this collection contains the specified element (optional
 * operation).  Returns <tt>true</tt> if this collection changed as a
 * result of the call.  (Returns <tt>false</tt> if this collection does
 * not permit duplicates and already contains the specified element.)<p>
 *
 * Collections that support this operation may place limitations on what
 * elements may be added to this collection.  In particular, some
 * collections will refuse to add <tt>null</tt> elements, and others will
 * impose（强加） restrictions on the type of elements that may be added.
 * Collection classes should clearly specify in their documentation any
 * restrictions on what elements may be added.<p>
 *
 * If a collection refuses to add a particular element for any reason
 * other than that it already contains the element, it <i>must</i> throw
 * an exception (rather than returning <tt>false</tt>).  This preserves
 * the invariant that a collection always contains the specified element
 * after this call returns.
 *
 * @param e element whose presence in this collection is to be ensured
 * @return <tt>true</tt> if this collection changed as a result of the
 *         call
 * @throws UnsupportedOperationException if the <tt>add</tt> operation
 *         is not supported by this collection
 * @throws ClassCastException if the class of the specified element
 *         prevents it from being added to this collection
 * @throws NullPointerException if the specified element is null and this
 *         collection does not permit null elements
 * @throws IllegalArgumentException if some property of the element
 *         prevents it from being added to this collection
 * @throws IllegalStateException if the element cannot be added at this
 *         time due to insertion restrictions
 */
 /***  确认集合是否包含指定元素（可选）。成功执行返回true，如果集合有位移限定切已经包含元素会返回       **false。集合提供这个操作可能设置了限制某些元素添加进集合。额外的，一些集合会拒绝添加null元素，一些**其它集合会强加约束在元素的类型上。当添加元素时，集合类应该在文档中清晰的指定约束。如果集合因为一些原因*拒绝添加特定元素除了它已经包含此元素，它会抛出异常（而不是返回false）这保证了集合时钟包含指定元素在返**回后保持不变。
 **
 **/
boolean add(E e);
```

8.remove方法

```
/**
 * Removes a single instance of the specified element from this
 * collection, if it is present (optional operation).  More formally,
 * removes an element <tt>e</tt> such that
 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
 * this collection contains one or more such elements.  Returns
 * <tt>true</tt> if this collection contained the specified element (or
 * equivalently, if this collection changed as a result of the call).
 *
 * @param o element to be removed from this collection, if present
 * @return <tt>true</tt> if an element was removed as a result of this call
 * @throws ClassCastException if the type of the specified element
 *         is incompatible with this collection
 *         (<a href="#optional-restrictions">optional</a>)
 * @throws NullPointerException if the specified element is null and this
 *         collection does not permit null elements
 *         (<a href="#optional-restrictions">optional</a>)
 * @throws UnsupportedOperationException if the <tt>remove</tt> operation
 *         is not supported by this collection
 */
 
 //如果集合存在指定元素，移除他（可选）。如果这个结合包含多个指定元素，移除它们。当集合包含时，返回true。
boolean remove(Object o);
```

9.containsAll

```
/**
 * Returns <tt>true</tt> if this collection contains all of the elements
 * in the specified collection.
 *
 * @param  c collection to be checked for containment in this collection
 * @return <tt>true</tt> if this collection contains all of the elements
 *         in the specified collection
 * @throws ClassCastException if the types of one or more elements
 *         in the specified collection are incompatible with this
 *         collection
 *         (<a href="#optional-restrictions">optional</a>)
 * @throws NullPointerException if the specified collection contains one
 *         or more null elements and this collection does not permit null
 *         elements
 *         (<a href="#optional-restrictions">optional</a>),
 *         or if the specified collection is null.
 * @see    #contains(Object)
 */
 //如果集合包含所有自定元素，返回真
boolean containsAll(Collection<?> c);
```

10.addAll方法

```
/**
 * Adds all of the elements in the specified collection to this collection
 * (optional operation).  The behavior of this operation is undefined if
 * the specified collection is modified while the operation is in progress.
 * (This implies that the behavior of this call is undefined if the
 * specified collection is this collection, and this collection is
 * nonempty.)
 *
 * @param c collection containing elements to be added to this collection
 * @return <tt>true</tt> if this collection changed as a result of the call
 * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
 *         is not supported by this collection
 * @throws ClassCastException if the class of an element of the specified
 *         collection prevents it from being added to this collection
 * @throws NullPointerException if the specified collection contains a
 *         null element and this collection does not permit null elements,
 *         or if the specified collection is null
 * @throws IllegalArgumentException if some property of an element of the
 *         specified collection prevents it from being added to this
 *         collection
 * @throws IllegalStateException if not all the elements can be added at
 *         this time due to insertion restrictions
 * @see #add(Object)
 */
 //添加这些元素到自定集合。这个操作的表现不明确，如果集合发生变更（因为迭代器的原因？）
boolean addAll(Collection<? extends E> c);
```

11.retain方法

```
/**
 * Retains only the elements in this collection that are contained in the
 * specified collection (optional operation).  In other words, removes from
 * this collection all of its elements that are not contained in the
 * specified collection.
 *
 * @param c collection containing elements to be retained in this collection
 * @return <tt>true</tt> if this collection changed as a result of the call
 * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
 *         is not supported by this collection
 * @throws ClassCastException if the types of one or more elements
 *         in this collection are incompatible with the specified
 *         collection
 *         (<a href="#optional-restrictions">optional</a>)
 * @throws NullPointerException if this collection contains one or more
 *         null elements and the specified collection does not permit null
 *         elements
 *         (<a href="#optional-restrictions">optional</a>),
 *         or if the specified collection is null
 * @see #remove(Object)
 * @see #contains(Object)
 */
 //保留指定集合中含有元素；换言之，移除所有不在指定集合中的元素
boolean retainAll(Collection<?> c);
```

12.clear方法 移除所有元素

```
**
 * Removes all of the elements from this collection (optional operation).
 * The collection will be empty after this method returns.
 *
 * @throws UnsupportedOperationException if the <tt>clear</tt> operation
 *         is not supported by this collection
 */
void clear();
```

13.equal方法

```
/**
 * Compares the specified object with this collection for equality. <p>
 *
   // 比较集合与指定对象的相等性
 * While the <tt>Collection</tt> interface adds no stipulations to the
 * general contract for the <tt>Object.equals</tt>, programmers who
 * implement the <tt>Collection</tt> interface "directly" (in other words,
 * create a class that is a <tt>Collection</tt> but is not a <tt>Set</tt>
 * or a <tt>List</tt>) must exercise care if they choose to override the
 * <tt>Object.equals</tt>.  It is not necessary to do so, and the simplest
 * course of action is to rely on <tt>Object</tt>'s implementation, but
 * the implementor may wish to implement a "value comparison" in place of
 * the default "reference comparison."  (The <tt>List</tt> and
 * <tt>Set</tt> interfaces mandate such value comparisons.)<p>
 *
   // 重写时需注意，因为是非必须的。
 * The general contract for the <tt>Object.equals</tt> method states that
 * equals must be symmetric (in other words, <tt>a.equals(b)</tt> if and
 * only if <tt>b.equals(a)</tt>).  The contracts for <tt>List.equals</tt>
 * and <tt>Set.equals</tt> state that lists are only equal to other lists,
 * and sets to other sets.  Thus, a custom <tt>equals</tt> method for a
 * collection class that implements neither the <tt>List</tt> nor
 * <tt>Set</tt> interface must return <tt>false</tt> when this collection
 * is compared to any list or set.  (By the same logic, it is not possible
 * to write a class that correctly implements both the <tt>Set</tt> and
 * <tt>List</tt> interfaces.)
 *
 * @param o object to be compared for equality with this collection
 * @return <tt>true</tt> if the specified object is equal to this
 * collection
 *
 * @see Object#equals(Object)
 * @see Set#equals(Object)
 * @see List#equals(Object)
 */
boolean equals(Object o);
```

14.hashCode

```
/**
 * Returns the hash code value for this collection.  While the
 * <tt>Collection</tt> interface adds no stipulations to the general
 * contract for the <tt>Object.hashCode</tt> method, programmers should
 * take note that any class that overrides the <tt>Object.equals</tt>
 * method must also override the <tt>Object.hashCode</tt> method in order
 * to satisfy the general contract for the <tt>Object.hashCode</tt> method.
 * In particular, <tt>c1.equals(c2)</tt> implies that
 * <tt>c1.hashCode()==c2.hashCode()</tt>.
 *
 * @return the hash code value for this collection
 *
 * @see Object#hashCode()
 * @see Object#equals(Object)
 */
 返回集合的散列值。当集合的接口为了此方法添加没有约定大体的规则，开发者应该记录笔记，任意一个类覆盖了Object的equal方法那么也一定覆盖了hashCode方法为了确保统一。
int hashCode();
```

15.stream方法 我爱stream表达式

```
/**
 * Returns a sequential {@code Stream} with this collection as its source.
 *
 * <p>This method should be overridden when the {@link #spliterator()}
 * method cannot return a spliterator that is {@code IMMUTABLE},
 * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
 * for details.)
 *
 * @implSpec
 * The default implementation creates a sequential {@code Stream} from the
 * collection's {@code Spliterator}.
 *
 * @return a sequential {@code Stream} over the elements in this collection
 * @since 1.8
 */
 //根据集合本身返回一个线性的Stream
 // 这个方法必须要重写当spliterator是不可得时
default Stream<E> stream() {
    return StreamSupport.stream(spliterator(), false);
}
```

16.parallelStream 和上面调用类似，需要支持。

```
/**
 * Returns a possibly parallel {@code Stream} with this collection as its
 * source.  It is allowable for this method to return a sequential stream.
 *
 * <p>This method should be overridden when the {@link #spliterator()}
 * method cannot return a spliterator that is {@code IMMUTABLE},
 * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
 * for details.)
 *
 * @implSpec
 * The default implementation creates a parallel {@code Stream} from the
 * collection's {@code Spliterator}.
 *
 * @return a possibly parallel {@code Stream} over the elements in this
 * collection
 * @since 1.8
 */
 //根据集合本身返回一个支持并行的Stream，可以允许这个方法会一个线性stream。
default Stream<E> parallelStream() {
    return StreamSupport.stream(spliterator(), true);
}
```

17.上面两个函数调用 额外的stream支持

```
/**
 * Creates a new sequential or parallel {@code Stream} from a
 * {@code Spliterator}.
 *
 * <p>The spliterator is only traversed, split, or queried for estimated
 * size after the terminal operation of the stream pipeline commences.
 *
 * <p>It is strongly recommended the spliterator report a characteristic of
 * {@code IMMUTABLE} or {@code CONCURRENT}, or be
 * <a href="../Spliterator.html#binding">late-binding</a>.  Otherwise,
 * {@link #stream(java.util.function.Supplier, int, boolean)} should be used
 * to reduce the scope of potential interference with the source.  See
 * <a href="package-summary.html#NonInterference">Non-Interference</a> for
 * more details.
 *
 * @param <T> the type of stream elements
 * @param spliterator a {@code Spliterator} describing the stream elements
 * @param parallel if {@code true} then the returned stream is a parallel
 *        stream; if {@code false} the returned stream is a sequential
 *        stream.
 * @return a new sequential or parallel {@code Stream}
 */
public static <T> Stream<T> stream(Spliterator<T> spliterator, boolean parallel) {
    Objects.requireNonNull(spliterator);
    return new ReferencePipeline.Head<>(spliterator,
                                        StreamOpFlag.fromCharacteristics(spliterator),
                                        parallel);
}
```
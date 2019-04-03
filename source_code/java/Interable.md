### Iterable源码

#### 1.综述

```
Implementing this interface allows an object to be the target of the "for-each loop" statement. 
实现这个接口允许一个对象指向for-each环表述
```

#### 2.方法

1.iterator方法

```
/**
 * Returns an iterator over elements of type {@code T}.
 *
 * @return an Iterator.
 */
 返回一个迭代器元素类型取决于泛型类型
Iterator<T> iterator();
```

2.forEach

```
/**
 * Performs the given action for each element of the {@code Iterable}
 * until all elements have been processed or the action throws an
 * exception.  Unless otherwise specified by the implementing class,
 * actions are performed in the order of iteration (if an iteration order
 * is specified).  Exceptions thrown by the action are relayed to the
 * caller.
 *
   对所有元素执行所给操作知道所有元素都执行了或者操作抛出异常。除非实现类另行约定，操作将会按照迭代顺序执行（如果迭代顺序是指定的）。操作的异常抛出取决于发起者。
 
 * @implSpec
 * <p>The default implementation behaves as if:
 * <pre>{@code
 *     for (T t : this)
 *         action.accept(t);
 * }</pre>
 *
 * @param action The action to be performed for each element
 * @throws NullPointerException if the specified action is null
 * @since 1.8
 */
default void forEach(Consumer<? super T> action) {
    Objects.requireNonNull(action);
    for (T t : this) {
        action.accept(t);
    }
}
```

3.spliterator

```
/**
 * Creates a {@link Spliterator} over the elements described by this
 * {@code Iterable}.
 
   创建一个Spliterator按照被iterable元素描述。
 *
 * @implSpec
 * The default implementation creates an
 * <em><a href="Spliterator.html#binding">early-binding</a></em>
 * spliterator from the iterable's {@code Iterator}.  The spliterator
 * inherits the <em>fail-fast</em> properties of the iterable's iterator.
 *
    默认的实现从Iterator创建了一个spliterator。这个分隔迭代器继承了迭代器的快速失败性质。
 
 * @implNote
 * The default implementation should usually be overridden.  The
 * spliterator returned by the default implementation has poor splitting
 * capabilities, is unsized, and does not report any spliterator
 * characteristics. Implementing classes can nearly always provide a
 * better implementation.
    这个默认的实现通常应该被重写。这个分隔迭代器通过默认的实现返回能力有限，没有size属性，并且没有提货人分迭代器的性质。实现类几乎可以提供一个更好的实现。
    
 *
 * @return a {@code Spliterator} over the elements described by this
 * {@code Iterable}.
 * @since 1.8
 */
default Spliterator<T> spliterator() {
    return Spliterators.spliteratorUnknownSize(iterator(), 0);
}
```

4.插一个Spliterators.spliteratorUnknownSize方法

```
/**
 * Creates a {@code Spliterator} using a given {@code Iterator}
 * as the source of elements, with no initial size estimate.
 *
   通过给予迭代器创建一个分隔迭代器用做元素的源，在没有初始化大小预计。
   
 * <p>The spliterator is not
 * <em><a href="Spliterator.html#binding">late-binding</a></em>, inherits
 * the <em>fail-fast</em> properties of the iterator, and implements
 * {@code trySplit} to permit limited parallelism.
 *
  集成快速失败的属性，并且实现类允许限制平行性。
 
 * <p>Traversal of elements should be accomplished through the spliterator.
 * The behaviour of splitting and traversal is undefined if the iterator is
 * operated on after the spliterator is returned.
 *
   遍历元素应该通过分隔迭代器申请。如果迭代器在分割迭代器返回之后被操作 分割迭代遍历的表现就不明朗了。
 
 * @param <T> Type of elements
 * @param iterator The iterator for the source
 * @param characteristics Characteristics of this spliterator's source
 *        or elements ({@code SIZED} and {@code SUBSIZED}, if supplied, are
 *        ignored and are not reported.)
 * @return A spliterator from an iterator
 * @throws NullPointerException if the given iterator is {@code null}
 */
public static <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator,
                                                        int characteristics) {
    return new IteratorSpliterator<>(Objects.requireNonNull(iterator), characteristics);
}
```
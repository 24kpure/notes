ListableBeanFactory

#### 一.前言

```
/**
 * Extension of the {@link BeanFactory} interface to be implemented by bean factories
 * that can enumerate all their bean instances, rather than attempting bean lookup
 * by name one by one as requested by clients. BeanFactory implementations that
 * preload all their bean definitions (such as XML-based factories) may implement
 * this interface.
 *
 * <p>If this is a {@link HierarchicalBeanFactory}, the return values will <i>not</i>
 * take any BeanFactory hierarchy into account, but will relate only to the beans
 * defined in the current factory. Use the {@link BeanFactoryUtils} helper class
 * to consider beans in ancestor factories too.
 *
  BeanFactory的延展接口将会被可以枚举所有bean实例的工厂所实现，而不是尝试通过客户机所给名字在请求时逐个查找bean。BeanFactory的实现类中，预加载所有bean定义可以实现这个接口。
   如果这是一个分类的beanFactory，返回值不会考虑任何BeanFactory层次结构。但是只会关联在当前工厂定义的bean。使用BeanFactoryUtils帮助类来考虑祖先工厂的bean。
 
 * <p>The methods in this interface will just respect bean definitions of this factory.
 * They will ignore any singleton beans that have been registered by other means like
 * {@link org.springframework.beans.factory.config.ConfigurableBeanFactory}'s
 * {@code registerSingleton} method, with the exception of
 * {@code getBeanNamesOfType} and {@code getBeansOfType} which will check
 * such manually registered singletons too. Of course, BeanFactory's {@code getBean}
 * does allow transparent access to such special beans as well. However, in typical
 * scenarios, all beans will be defined by external bean definitions anyway, so most
 * applications don't need to worry about this differentiation.
 *	
 * <p><b>NOTE:</b> With the exception of {@code getBeanDefinitionCount}
 * and {@code containsBeanDefinition}, the methods in this interface
 * are not designed for frequent invocation. Implementations may be slow.
 *
   这个接口的方法只会关心自己bean的定义。他会忽略任何已经被其它注册的单例的bean方法。也要按照单例处理。当然，BeanFactory的@code getbean也允许透明访问这些特殊的bean。然而，在典型的场景中，所有bean都将由外部bean定义来定义，因此大多数应用程序不需要担心这种差异。
 
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 16 April 2001
 * @see HierarchicalBeanFactory
 * @see BeanFactoryUtils
 */
```

#### 二.类方法

##### 1.类声明  继承BeanFactory

```
public interface ListableBeanFactory extends BeanFactory
```

2.containsBeanDefinition

```
/**
 * Check if this bean factory contains a bean definition with the given name.
 * <p>Does not consider any hierarchy this factory may participate in,
 * and ignores any singleton beans that have been registered by
 * other means than bean definitions.
 * @param beanName the name of the bean to look for
 * @return if this bean factory contains a bean definition with the given name
 * @see #containsBean
 */
 检查这个bean工厂包含所给名的bean定义。不要考虑任何分层工厂的参与，并且忽略任何在其它bean中注册的单例bean。
boolean containsBeanDefinition(String beanName);
```

2.getBeanDefinitionCount

```
/**
 * Return the number of beans defined in the factory.
 * <p>Does not consider any hierarchy this factory may participate in,
 * and ignores any singleton beans that have been registered by
 * other means than bean definitions.
 * @return the number of beans defined in the factory
 */
 返回这个工厂内的bean定义数。同样也不考虑分层和其它工厂注册的bean。
int getBeanDefinitionCount();
```

3.getBeanDefinitionNames

```
/**
 * Return the names of all beans defined in this factory.
 * <p>Does not consider any hierarchy this factory may participate in,
 * and ignores any singleton beans that have been registered by
 * other means than bean definitions.
 * @return the names of all beans defined in this factory,
 * or an empty array if none defined
 */
 返回工厂内声明的bean名字。同样也不考虑分层和其它工厂注册的bean。
String[] getBeanDefinitionNames();
```

4.getBeanNamesForType 下面有多种根据参数获取类

```
/**
 * Return the names of beans matching the given type (including subclasses),
 * judging from either bean definitions or the value of {@code getObjectType}
 * in the case of FactoryBeans.
 * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
 * check nested beans which might match the specified type as well.
 * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
 * will get initialized. If the object created by the FactoryBean doesn't match,
 * the raw FactoryBean itself will be matched against the type.
 * <p>Does not consider any hierarchy this factory may participate in.
 * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
 * to include beans in ancestor factories too.
 * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
 * by other means than bean definitions.
 * <p>This version of {@code getBeanNamesForType} matches all kinds of beans,
 * be it singletons, prototypes, or FactoryBeans. In most implementations, the
 * result will be the same as for {@code getBeanNamesForType(type, true, true)}.
 * <p>Bean names returned by this method should always return bean names <i>in the
 * order of definition</i> in the backend configuration, as far as possible.
 * @param type the generically typed class or interface to match
 * @return the names of beans (or objects created by FactoryBeans) matching
 * the given object type (including subclasses), or an empty array if none
 * @since 4.2
 * @see #isTypeMatch(String, ResolvableType)
 * @see FactoryBean#getObjectType
 * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, ResolvableType)
 */
 此方法只对顶级bean进行内省。并不会检查可能与指定类型匹配的嵌套bean。是否考虑由FactoryBeans创建的对象，这意味着FactoryBeans将被初始化。如果FactoryBean创建的对象不匹配，原始FactoryBean本身将与类型匹配。不考虑此工厂可能参与的任何层次结构。使用beanfactoryutils把bean也包括在祖先的工厂里。注意：是否忽略已注册的单例bean通过bean定义以外的其他方法。此版本的 getbeannamesfortype匹配所有种类的bean，不管是单件，原型，还是工厂中的bean。在大多数实现中，结果将与@code getBeannamesForType（type，true，true）相同。此方法返回的bean名称应始终返回尽可能按照后端配置中的定义顺序进行操作。@param键入要匹配的一般类型的类或接口@返回匹配的bean（或由FactoryBeans创建的对象）的名称给定的对象类型（包括子类）或空数组（如果没有）
 
String[] getBeanNamesForType(ResolvableType type);
```
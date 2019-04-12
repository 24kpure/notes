BeanFactory

#### 一.类介绍

```
/**
 * The root interface for accessing a Spring bean container.
 * This is the basic client view of a bean container;
 * further interfaces such as {@link ListableBeanFactory} and
 * {@link org.springframework.beans.factory.config.ConfigurableBeanFactory}
 * are available for specific purposes.
 *
 * <p>This interface is implemented by objects that hold a number of bean definitions,
 * each uniquely identified by a String name. Depending on the bean definition,
 * the factory will return either an independent instance of a contained object
 * (the Prototype design pattern), or a single shared instance (a superior
 * alternative to the Singleton design pattern, in which the instance is a
 * singleton in the scope of the factory). Which type of instance will be returned
 * depends on the bean factory configuration: the API is the same. Since Spring
 * 2.0, further scopes are available depending on the concrete application
 * context (e.g. "request" and "session" scopes in a web environment).
 *
   这个底层接口为了获取Spring bean容器。这是bean容器的基础客户视图。更进一步的接口比如ListableBeanFactory和ConfigurableBeanFactory对于特定的目的更适合。
   这个接口将会被包含bean定义的对象所实现，每个bean都根据字串名而不同。根据bean的定义，这些工厂会返回要么是其包含独立的实例（原型模式），要么是单个共享实例（一个可替代的单列设计，其在工厂的作用域中是单例的）。返回何种类型取决于工厂的配置：API是一致的。从 2.0以来，更多的作用域在会话中是可得。
 
 * <p>The point of this approach is that the BeanFactory is a central registry
 * of application components, and centralizes configuration of application
 * components (no more do individual objects need to read properties files,
 * for example). See chapters 4 and 11 of "Expert One-on-One J2EE Design and
 * Development" for a discussion of the benefits of this approach.
 *
 * <p>Note that it is generally better to rely on Dependency Injection
 * ("push" configuration) to configure application objects through setters
 * or constructors, rather than use any form of "pull" configuration like a
 * BeanFactory lookup. Spring's Dependency Injection functionality is
 * implemented using this BeanFactory interface and its subinterfaces.
 *
  更进一步的点是BeanFactory是应用组成中的中央注册中心，而且应用组件的集中化配置（举例，单个对象不需要读取配置文件）
  请注意，通常依靠依赖注入来配置应用对象通过setter或者构造器会更好，而不是使用整个拉取配置文件像BeanFactory的遍历。Spring的依赖注入功能将会被使用BeanFactory接口与其子接口所实现。
 
 * <p>Normally a BeanFactory will load bean definitions stored in a configuration
 * source (such as an XML document), and use the {@code org.springframework.beans}
 * package to configure the beans. However, an implementation could simply return
 * Java objects it creates as necessary directly in Java code. There are no
 * constraints on how the definitions could be stored: LDAP, RDBMS, XML,
 * properties file, etc. Implementations are encouraged to support references
 * amongst beans (Dependency Injection).
 *
 * <p>In contrast to the methods in {@link ListableBeanFactory}, all of the
 * operations in this interface will also check parent factories if this is a
 * {@link HierarchicalBeanFactory}. If a bean is not found in this factory instance,
 * the immediate parent factory will be asked. Beans in this factory instance
 * are supposed to override beans of the same name in any parent factory.
 *
   正常而言，BeanFactory将会加载配置（比如xml）中存储的bean定义，并且bean的包来配置bean。但是，一个接口实现跨越简单返回Java对象其直接在java代码中创建。定义在不同的存储是没有差异的（lDAP，关系型数据库，xml，配置文件等）接口实现被鼓励去支持bean之间相互引用（注入依赖）
   与ListableBeanFactory中的方法对比，所有这个接口中的操作将会检查其父工厂如果这是HierarchicalBeanFactory。如果一个bean在当前实例没有找到，那么其父工厂将会被立即调用。工厂中的Bean实例需要覆盖父类中名字相同的bean。
   
 * <p>Bean factory implementations should support the standard bean lifecycle interfaces
 * as far as possible. The full set of initialization methods and their standard order is:
 * <ol>
 * <li>BeanNameAware's {@code setBeanName}
 * <li>BeanClassLoaderAware's {@code setBeanClassLoader}
 * <li>BeanFactoryAware's {@code setBeanFactory}
 * <li>EnvironmentAware's {@code setEnvironment}
 * <li>EmbeddedValueResolverAware's {@code setEmbeddedValueResolver}
 * <li>ResourceLoaderAware's {@code setResourceLoader}
 * (only applicable when running in an application context)
 * <li>ApplicationEventPublisherAware's {@code setApplicationEventPublisher}
 * (only applicable when running in an application context)
 * <li>MessageSourceAware's {@code setMessageSource}
 * (only applicable when running in an application context)
 * <li>ApplicationContextAware's {@code setApplicationContext}
 * (only applicable when running in an application context)
 * <li>ServletContextAware's {@code setServletContext}
 * (only applicable when running in a web application context)
 * <li>{@code postProcessBeforeInitialization} methods of BeanPostProcessors
 * <li>InitializingBean's {@code afterPropertiesSet}
 * <li>a custom init-method definition
 * <li>{@code postProcessAfterInitialization} methods of BeanPostProcessors
 * </ol>
 *
 * <p>On shutdown of a bean factory, the following lifecycle methods apply:
 * <ol>
 * <li>{@code postProcessBeforeDestruction} methods of DestructionAwareBeanPostProcessors
 * <li>DisposableBean's {@code destroy}
 * <li>a custom destroy-method definition
 * </ol>
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 13 April 2001
 * @see BeanNameAware#setBeanName
 * @see BeanClassLoaderAware#setBeanClassLoader
 * @see BeanFactoryAware#setBeanFactory
 * @see org.springframework.context.ResourceLoaderAware#setResourceLoader
 * @see org.springframework.context.ApplicationEventPublisherAware#setApplicationEventPublisher
 * @see org.springframework.context.MessageSourceAware#setMessageSource
 * @see org.springframework.context.ApplicationContextAware#setApplicationContext
 * @see org.springframework.web.context.ServletContextAware#setServletContext
 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization
 * @see InitializingBean#afterPropertiesSet
 * @see org.springframework.beans.factory.support.RootBeanDefinition#getInitMethodName
 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization
 * @see DisposableBean#destroy
 * @see org.springframework.beans.factory.support.RootBeanDefinition#getDestroyMethodName
 */
```

#### 2.方法介绍

##### 1.bean前缀

```
/**
 * Used to dereference a {@link FactoryBean} instance and distinguish it from
 * beans <i>created</i> by the FactoryBean. For example, if the bean named
 * {@code myJndiObject} is a FactoryBean, getting {@code &myJndiObject}
 * will return the factory, not the instance returned by the factory.
 */
 用于区分FactoryBean中的实例和从bean中区分被FactoryBean创建的实例。比如，如果这个bean叫做myJndiObject是一个工厂bean，获取myJndiObject将会返回工厂而不是返回工厂的实例。
String FACTORY_BEAN_PREFIX = "&";
```

##### 2.getBean

```
/**
 * Return an instance, which may be shared or independent, of the specified bean.
 * <p>This method allows a Spring BeanFactory to be used as a replacement for the
 * Singleton or Prototype design pattern. Callers may retain references to
 * returned objects in the case of Singleton beans.
 * <p>Translates aliases back to the corresponding canonical bean name.
 * Will ask the parent factory if the bean cannot be found in this factory instance.
 * @param name the name of the bean to retrieve
 * @return an instance of the bean
 * @throws NoSuchBeanDefinitionException if there is no bean definition
 * with the specified name
 * @throws BeansException if the bean could not be obtained
 */
Object getBean(String name) throws BeansException;

/**
 * Return an instance, which may be shared or independent, of the specified bean.
 * <p>Behaves the same as {@link #getBean(String)}, but provides a measure of type
 * safety by throwing a BeanNotOfRequiredTypeException if the bean is not of the
 * required type. This means that ClassCastException can't be thrown on casting
 * the result correctly, as can happen with {@link #getBean(String)}.
 * <p>Translates aliases back to the corresponding canonical bean name.
 * Will ask the parent factory if the bean cannot be found in this factory instance.
 * @param name the name of the bean to retrieve
 * @param requiredType type the bean must match. Can be an interface or superclass
 * of the actual class, or {@code null} for any match. For example, if the value
 * is {@code Object.class}, this method will succeed whatever the class of the
 * returned instance.
 * @return an instance of the bean
 * @throws NoSuchBeanDefinitionException if there is no such bean definition
 * @throws BeanNotOfRequiredTypeException if the bean is not of the required type
 * @throws BeansException if the bean could not be created
 */
<T> T getBean(String name, @Nullable Class<T> requiredType) throws BeansException;

/**
 * Return an instance, which may be shared or independent, of the specified bean.
 * <p>Allows for specifying explicit constructor arguments / factory method arguments,
 * overriding the specified default arguments (if any) in the bean definition.
 * @param name the name of the bean to retrieve
 * @param args arguments to use when creating a bean instance using explicit arguments
 * (only applied when creating a new instance as opposed to retrieving an existing one)
 * @return an instance of the bean
 * @throws NoSuchBeanDefinitionException if there is no such bean definition
 * @throws BeanDefinitionStoreException if arguments have been given but
 * the affected bean isn't a prototype
 * @throws BeansException if the bean could not be created
 * @since 2.5
 */
Object getBean(String name, Object... args) throws BeansException;

/**
 * Return the bean instance that uniquely matches the given object type, if any.
 * <p>This method goes into {@link ListableBeanFactory} by-type lookup territory
 * but may also be translated into a conventional by-name lookup based on the name
 * of the given type. For more extensive retrieval operations across sets of beans,
 * use {@link ListableBeanFactory} and/or {@link BeanFactoryUtils}.
 * @param requiredType type the bean must match; can be an interface or superclass.
 * {@code null} is disallowed.
 * @return an instance of the single bean matching the required type
 * @throws NoSuchBeanDefinitionException if no bean of the given type was found
 * @throws NoUniqueBeanDefinitionException if more than one bean of the given type was found
 * @throws BeansException if the bean could not be created
 * @since 3.0
 * @see ListableBeanFactory
 */
<T> T getBean(Class<T> requiredType) throws BeansException;

/**
 * Return an instance, which may be shared or independent, of the specified bean.
 * <p>Allows for specifying explicit constructor arguments / factory method arguments,
 * overriding the specified default arguments (if any) in the bean definition.
 * <p>This method goes into {@link ListableBeanFactory} by-type lookup territory
 * but may also be translated into a conventional by-name lookup based on the name
 * of the given type. For more extensive retrieval operations across sets of beans,
 * use {@link ListableBeanFactory} and/or {@link BeanFactoryUtils}.
 * @param requiredType type the bean must match; can be an interface or superclass.
 * {@code null} is disallowed.
 * @param args arguments to use when creating a bean instance using explicit arguments
 * (only applied when creating a new instance as opposed to retrieving an existing one)
 * @return an instance of the bean
 * @throws NoSuchBeanDefinitionException if there is no such bean definition
 * @throws BeanDefinitionStoreException if arguments have been given but
 * the affected bean isn't a prototype
 * @throws BeansException if the bean could not be created
 * @since 4.1
 */
<T> T getBean(Class<T> requiredType, Object... args) throws BeansException;
```

##### 2.containsBean

```
/**
 * Does this bean factory contain a bean definition or externally registered singleton
 * instance with the given name?
 * <p>If the given name is an alias, it will be translated back to the corresponding
 * canonical bean name.
 * <p>If this factory is hierarchical, will ask any parent factory if the bean cannot
 * be found in this factory instance.
 * <p>If a bean definition or singleton instance matching the given name is found,
 * this method will return {@code true} whether the named bean definition is concrete
 * or abstract, lazy or eager, in scope or not. Therefore, note that a {@code true}
 * return value from this method does not necessarily indicate that {@link #getBean}
 * will be able to obtain an instance for the same name.
 * @param name the name of the bean to query
 * @return whether a bean with the given name is present
 */
 bean工厂包含一个bean定义或者额外注册的单例是通过所给字串名吗？
 如果所给名为别名，它会转换回相关的正式bean名。如果这个工厂是分级的，将会访问所有的父级工厂如果在这个工厂实例中无法获取。
boolean containsBean(String name);
```

##### 3.判断实例是单例还是原型

```
/**
 * Is this bean a shared singleton? That is, will {@link #getBean} always
 * return the same instance?
 * <p>Note: This method returning {@code false} does not clearly indicate
 * independent instances. It indicates non-singleton instances, which may correspond
 * to a scoped bean as well. Use the {@link #isPrototype} operation to explicitly
 * check for independent instances.
 * <p>Translates aliases back to the corresponding canonical bean name.
 * Will ask the parent factory if the bean cannot be found in this factory instance.
 * @param name the name of the bean to query
 * @return whether this bean corresponds to a singleton instance
 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
 * @see #getBean
 * @see #isPrototype
 */
 如果bean是单例，那是不是说将会一直返回同一实例？
 注意：这个方法返回false并不能清晰的表示是独立的实例。它表示非单例实例，其也可能是作用域的bean。使用isPrototype操作来明确的检查是否独立的实例。
boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

/**
 * Is this bean a prototype? That is, will {@link #getBean} always return
 * independent instances?
 * <p>Note: This method returning {@code false} does not clearly indicate
 * a singleton object. It indicates non-independent instances, which may correspond
 * to a scoped bean as well. Use the {@link #isSingleton} operation to explicitly
 * check for a shared singleton instance.
 * <p>Translates aliases back to the corresponding canonical bean name.
 * Will ask the parent factory if the bean cannot be found in this factory instance.
 * @param name the name of the bean to query
 * @return whether this bean will always deliver independent instances
 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
 * @since 2.0.3
 * @see #getBean
 * @see #isSingleton
 */
 这个bean是原型吗？如果是，将会一直返回独立不同的的实例吗？
boolean isPrototype(String name) throws NoSuchBeanDefinitionException;
```

##### 4.getType

```
/**
 * Determine the type of the bean with the given name. More specifically,
 * determine the type of object that {@link #getBean} would return for the given name.
 * <p>For a {@link FactoryBean}, return the type of object that the FactoryBean creates,
 * as exposed by {@link FactoryBean#getObjectType()}.
 * <p>Translates aliases back to the corresponding canonical bean name.
 * Will ask the parent factory if the bean cannot be found in this factory instance.
 * @param name the name of the bean to query
 * @return the type of the bean, or {@code null} if not determinable
 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
 * @since 1.1.2
 * @see #getBean
 * @see #isTypeMatch
 */ 
在确定类型的给定名称的bean。更特别的是，确定的类型，会根据所给名返回对象。
@Nullable
Class<?> getType(String name) throws NoSuchBeanDefinitionException;
```

##### 5.getAliases 获取别名

```
/**
 * Return the aliases for the given bean name, if any.
 * All of those aliases point to the same bean when used in a {@link #getBean} call.
 * <p>If the given name is an alias, the corresponding original bean name
 * and other aliases (if any) will be returned, with the original bean name
 * being the first element in the array.
 * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
 * @param name the bean name to check for aliases
 * @return the aliases, or an empty array if none
 * @see #getBean
 */
 根据所给bean名，如果存在，返回别名。所有别名指向同一个bean当被调用时。如果所给名是别名，那么相关原始bean名和其它别名将会返回，而原始bean名将会在数组的第一个元素。
String[] getAliases(String name);
```


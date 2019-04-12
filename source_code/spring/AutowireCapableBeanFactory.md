AutowireCapableBeanFactory

#### 一.前言

```
/**
 * Extension of the {@link org.springframework.beans.factory.BeanFactory}
 * interface to be implemented by bean factories that are capable of
 * autowiring, provided that they want to expose this functionality for
 * existing bean instances.
 *
 * <p>This subinterface of BeanFactory is not meant to be used in normal
 * application code: stick to {@link org.springframework.beans.factory.BeanFactory}
 * or {@link org.springframework.beans.factory.ListableBeanFactory} for
 * typical use cases.
 *
 扩展Factory接口，由能够自动连接的bean工厂实现，前提是它们希望为现有bean实例公开此功能。
 这个BeanFactory子接口不意味着会在应用代码中使用。
  
 * <p>Integration code for other frameworks can leverage this interface to
 * wire and populate existing bean instances that Spring does not control
 * the lifecycle of. This is particularly useful for WebWork Actions and
 * Tapestry Page objects, for example.
 *
 * <p>Note that this interface is not implemented by
 * {@link org.springframework.context.ApplicationContext} facades,
 * as it is hardly ever used by application code. That said, it is available
 * from an application context too, accessible through ApplicationContext's
 * {@link org.springframework.context.ApplicationContext#getAutowireCapableBeanFactory()}
 * method.
 *
  为其它框架整合代码可以轻量化接口连接和填充存在的bean实例，这些实例Spring都不控制其生命周期。举例，中医，这个接口并不实现ApplicationContext的假象  ，正如其很难被引用代码所使用。那就是说，它也可以从应用程序上下文中的方法获得。
 
 * <p>You may also implement the {@link org.springframework.beans.factory.BeanFactoryAware}
 * interface, which exposes the internal BeanFactory even when running in an
 * ApplicationContext, to get access to an AutowireCapableBeanFactory:
 * simply cast the passed-in BeanFactory to AutowireCapableBeanFactory.
 *
 * @author Juergen Hoeller
 * @since 04.12.2003
 * @see org.springframework.beans.factory.BeanFactoryAware
 * @see org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 * @see org.springframework.context.ApplicationContext#getAutowireCapableBeanFactory()
 */
```



#### 二.方法

##### 1.常量

```
/**
 * Constant that indicates no externally defined autowiring. Note that
 * BeanFactoryAware etc and annotation-driven injection will still be applied.
 * @see #createBean
 * @see #autowire
 * @see #autowireBeanProperties
 */
 标识不需要外部定义自动装载的常量。注意BeanFactoryAware等和注释驱动注入仍旧引用。
int AUTOWIRE_NO = 0;

/**
 * Constant that indicates autowiring bean properties by name
 * (applying to all bean property setters).
 * @see #createBean
 * @see #autowire
 * @see #autowireBeanProperties
 */
  通过bean名注入的常量
int AUTOWIRE_BY_NAME = 1;

/**
 * Constant that indicates autowiring bean properties by type
 * (applying to all bean property setters).
 * @see #createBean
 * @see #autowire
 * @see #autowireBeanProperties
 */
 通过bean类型注入的常量
int AUTOWIRE_BY_TYPE = 2;

/**
 * Constant that indicates autowiring the greediest constructor that
 * can be satisfied (involves resolving the appropriate constructor).
 * @see #createBean
 * @see #autowire
 */
 通过bean构造器贪心注入的常量
int AUTOWIRE_CONSTRUCTOR = 3;

/**
 * Constant that indicates determining an appropriate autowire strategy
 * through introspection of the bean class.
 * @see #createBean
 * @see #autowire
 * @deprecated as of Spring 3.0: If you are using mixed autowiring strategies,
 * prefer annotation-based autowiring for clearer demarcation of autowiring needs.
 */
 通过反射bean类来确定适当的自动装载策略常量
@Deprecated
int AUTOWIRE_AUTODETECT = 4;
```

##### 2.createBean 创建bean

```
/**
 * Fully create a new bean instance of the given class.
 * <p>Performs full initialization of the bean, including all applicable
 * {@link BeanPostProcessor BeanPostProcessors}.
 * <p>Note: This is intended for creating a fresh instance, populating（居住，填充） annotated
 * fields and methods as well as applying all standard bean initialization callbacks.
 * It does <i>not</> imply traditional by-name or by-type autowiring of properties;
 * use {@link #createBean(Class, int, boolean)} for those purposes.
 * @param beanClass the class of the bean to create
 * @return the new bean instance
 * @throws BeansException if instantiation or wiring failed
 */
 根据所给的类完整的创建一个全新的bean实例。对bean执行一个完整的初始化，包括所有可应用的。注意，这是为了创建一个新的实例，填充带注释的字段和方法，以及应用所有标准bean初始化回调
<T> T createBean(Class<T> beanClass) throws BeansException;
```

##### 3.装载bean 没大懂

```
/**
 * Populate the given bean instance through applying after-instantiation callbacks
 * and bean property post-processing (e.g. for annotation-driven injection).
 * <p>Note: This is essentially intended for (re-)populating annotated fields and
 * methods, either for new instances or for deserialized instances. It does
 * <i>not</i> imply traditional by-name or by-type autowiring of properties;
 * use {@link #autowireBeanProperties} for those purposes.
 * @param existingBean the existing bean instance
 * @throws BeansException if wiring failed
 */
 通过实例化后回调和bean属性后处理填充所给bean实例（例子，注解驱动注入）。注意，填充注解属性和方法的基本趋势，既不是新实例也不是反序列化实例。并不意味着传统通过名字通过类型的自动注入和属性。
void autowireBean(Object existingBean) throws BeansException;
```

##### 4.configureBean

```
/**
 * Configure the given raw bean: autowiring bean properties, applying
 * bean property values, applying factory callbacks such as {@code setBeanName}
 * and {@code setBeanFactory}, and also applying all bean post processors
 * (including ones which might wrap the given raw bean).
 * <p>This is effectively a superset of what {@link #initializeBean} provides,
 * fully applying the configuration specified by the corresponding bean definition.
 * <b>Note: This method requires a bean definition for the given name!</b>
 * @param existingBean the existing bean instance
 * @param beanName the name of the bean, to be passed to it if necessary
 * (a bean definition of that name has to be available)
 * @return the bean instance to use, either the original or a wrapped one
 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
 * if there is no bean definition with the given name
 * @throws BeansException if the initialization failed
 * @see #initializeBean
 */
 配置所给原生bean：自动注入bean属性，应用bean属性值，应用工厂回调比如setBeanName，setBeanFactory，而且也有用所有bean的后置处理器。这实际上是initializebean提供的超集，完全应用相应bean定义指定的配置。注意，这个方法需要所给名称的bean定义。
Object configureBean(Object existingBean, String beanName) throws BeansException;
```

##### 5.autowire 百度翻译你懂得

```
/**
 * Instantiate a new bean instance of the given class with the specified autowire
 * strategy（策略）. All constants defined in this interface are supported here.
 * Can also be invoked with {@code AUTOWIRE_NO} in order to just apply
 * before-instantiation callbacks (e.g. for annotation-driven injection).
 * <p>Does <i>not</i> apply standard {@link BeanPostProcessor BeanPostProcessors}
 * callbacks or perform any further initialization of the bean. This interface
 * offers distinct, fine-grained operations for those purposes, for example
 * {@link #initializeBean}. However, {@link InstantiationAwareBeanPostProcessor}
 * callbacks are applied, if applicable to the construction of the instance.
 * @param beanClass the class of the bean to instantiate
 * @param autowireMode by name or type, using the constants in this interface
 * @param dependencyCheck whether to perform a dependency check for object
 * references in the bean instance (not applicable to autowiring a constructor,
 * thus ignored there)
 * @return the new bean instance
 * @throws BeansException if instantiation or wiring failed
 * @see #AUTOWIRE_NO
 * @see #AUTOWIRE_BY_NAME
 * @see #AUTOWIRE_BY_TYPE
 * @see #AUTOWIRE_CONSTRUCTOR
 * @see #AUTOWIRE_AUTODETECT
 * @see #initializeBean
 * @see #applyBeanPostProcessorsBeforeInitialization
 * @see #applyBeanPostProcessorsAfterInitialization
 */
 用指定的autowire策略（策略）实例化给定类的新bean实例。此处支持此接口中定义的所有常量。也可以用@code autowire_no调用，以便在实例化回调之前应用（例如，用于注释驱动的注入）。<p>是否执行bean的任何进一步初始化。这个接口为这些目的提供了独特的、细粒度的操作，例如@link initializebean。但是，@link instantiationawarebeanpostprocessor回调应用于实例的构造（如果适用）。@param bean class使用此接口中的常量@param dependency check是否对bean实例中的对象引用执行依赖性检查（不适用于自动连接构造函数，因此在那里被忽略）按名称或类型实例化@param autowiremode的bean类。
Object autowire(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException;
```

##### 6.autowireBeanProperties

```
/**
 * Autowire the bean properties of the given bean instance by name or type.
 * Can also be invoked with {@code AUTOWIRE_NO} in order to just apply
 * after-instantiation callbacks (e.g. for annotation-driven injection).
 * <p>Does <i>not</i> apply standard {@link BeanPostProcessor BeanPostProcessors}
 * callbacks or perform any further initialization of the bean. This interface
 * offers distinct, fine-grained operations for those purposes, for example
 * {@link #initializeBean}. However, {@link InstantiationAwareBeanPostProcessor}
 * callbacks are applied, if applicable to the configuration of the instance.
 * @param existingBean the existing bean instance
 * @param autowireMode by name or type, using the constants in this interface
 * @param dependencyCheck whether to perform a dependency check for object
 * references in the bean instance
 * @throws BeansException if wiring failed
 * @see #AUTOWIRE_BY_NAME
 * @see #AUTOWIRE_BY_TYPE
 * @see #AUTOWIRE_NO
 */
void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck)
      throws BeansException;
```

##### 7.applyBeanPropertyValues

```
/**
 * Apply the property values of the bean definition with the given name to
 * the given bean instance. The bean definition can either define a fully
 * self-contained bean, reusing its property values, or just property values
 * meant to be used for existing bean instances.
 * <p>This method does <i>not</i> autowire bean properties; it just applies
 * explicitly defined property values. Use the {@link #autowireBeanProperties}
 * method to autowire an existing bean instance.
 * <b>Note: This method requires a bean definition for the given name!</b>
 * <p>Does <i>not</i> apply standard {@link BeanPostProcessor BeanPostProcessors}
 * callbacks or perform any further initialization of the bean. This interface
 * offers distinct, fine-grained operations for those purposes, for example
 * {@link #initializeBean}. However, {@link InstantiationAwareBeanPostProcessor}
 * callbacks are applied, if applicable to the configuration of the instance.
 * @param existingBean the existing bean instance
 * @param beanName the name of the bean definition in the bean factory
 * (a bean definition of that name has to be available)
 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
 * if there is no bean definition with the given name
 * @throws BeansException if applying the property values failed
 * @see #autowireBeanProperties
 */
void applyBeanPropertyValues(Object existingBean, String beanName) throws BeansException;
```

##### 8.initializeBean 初始化bean

```
/**
 * Initialize the given raw bean, applying factory callbacks
 * such as {@code setBeanName} and {@code setBeanFactory},
 * also applying all bean post processors (including ones which
 * might wrap the given raw bean).
 * <p>Note that no bean definition of the given name has to exist
 * in the bean factory. The passed-in bean name will simply be used
 * for callbacks but not checked against the registered bean definitions.
 * @param existingBean the existing bean instance
 * @param beanName the name of the bean, to be passed to it if necessary
 * (only passed to {@link BeanPostProcessor BeanPostProcessors})
 * @return the bean instance to use, either the original or a wrapped one
 * @throws BeansException if the initialization failed
 */
 初始化所给原生bean，应用工厂回调。
Object initializeBean(Object existingBean, String beanName) throws BeansException;
```

##### 9.destroyBean 销毁bean

```
/**
 * Destroy the given bean instance (typically coming from {@link #createBean}),
 * applying the {@link org.springframework.beans.factory.DisposableBean} contract as well as
 * registered {@link DestructionAwareBeanPostProcessor DestructionAwareBeanPostProcessors}.
 * <p>Any exception that arises during destruction should be caught
 * and logged instead of propagated to the caller of this method.
 * @param existingBean the bean instance to destroy
 */
 销毁所给bean实例（一般来源于创建bean）
void destroyBean(Object existingBean);
```
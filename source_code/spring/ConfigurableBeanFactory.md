##### ConfigurableBeanFactory 配置bean工厂

#### 一.类介绍

##### 1.前言

```
/**
 * Configuration interface to be implemented by most bean factories. Provides
 * facilities to configure a bean factory, in addition to the bean factory
 * client methods in the {@link org.springframework.beans.factory.BeanFactory}
 * interface.
 *
 * <p>This bean factory interface is not meant to be used in normal application
 * code: Stick to {@link org.springframework.beans.factory.BeanFactory} or
 * {@link org.springframework.beans.factory.ListableBeanFactory} for typical
 * needs. This extended interface is just meant to allow for framework-internal
 * plug'n'play and for special access to bean factory configuration methods.
 *
 * @author Juergen Hoeller
 * @since 03.11.2003
 * @see org.springframework.beans.factory.BeanFactory
 * @see org.springframework.beans.factory.ListableBeanFactory
 * @see ConfigurableListableBeanFactory
 */
 配置接口被最多bean工厂实现。提供了工具用于配置bean工厂，额外的客户方法在BeanFactory中。
 这个bean工厂接口不意味着在正常应用代码中被使用，绑定了BeanFactory和ListableBeanFactory使用。继承这个接口意味着允许内部框架插件运行和对于bean工厂配置方法的特殊访问
```

##### 2.声明

```
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry
```

 继承HierarchicalBeanFactory和SingletonBeanRegistry

#### 二.方法介绍

##### 1.常量 作用域中的单例和原型

```
/**
 * Scope identifier for the standard singleton scope: "singleton".
 * Custom scopes can be added via {@code registerScope}.
 * @see #registerScope
 */
String SCOPE_SINGLETON = "singleton";

/**
 * Scope identifier for the standard prototype scope: "prototype".
 * Custom scopes can be added via {@code registerScope}.
 * @see #registerScope
 */
String SCOPE_PROTOTYPE = "prototype";
```

##### 2.setParentBeanFactory

```
/**
 * Set the parent of this bean factory.
 * <p>Note that the parent cannot be changed: It should only be set outside
 * a constructor if it isn't available at the time of factory instantiation.
 * @param parentBeanFactory the parent BeanFactory
 * @throws IllegalStateException if this factory is already associated with
 * a parent BeanFactory
 * @see #getParentBeanFactory()
 */
 设置这个类的父工厂，注意父类是无法变更的：如果在实例化时不可得的话，父类只需要变更被设置在构造器之外
void setParentBeanFactory(BeanFactory parentBeanFactory) throws IllegalStateException;
```

##### 3.classLoader相关

```
/**
 * Set the class loader to use for loading bean classes.
 * Default is the thread context class loader.
 * <p>Note that this class loader will only apply to bean definitions
 * that do not carry a resolved bean class yet. This is the case as of
 * Spring 2.0 by default: Bean definitions only carry bean class names,
 * to be resolved once the factory processes the bean definition.
 * @param beanClassLoader the class loader to use,
 * or {@code null} to suggest the default class loader
 */
 设置bean的clasloader用于加载bean class。默认的是现会话的classLoader。
 注意，这个类加载器只会应用于类定义，也不包含已解析的class。这是2.0默认形式：bean的定义只具有类名，在类定义后马上解决。
 
void setBeanClassLoader(@Nullable ClassLoader beanClassLoader);

/**
 * Return this factory's class loader for loading bean classes
 * (only {@code null} if even the system ClassLoader isn't accessible).
 * @see org.springframework.util.ClassUtils#forName(String, ClassLoader)
 */
@Nullable
ClassLoader getBeanClassLoader();
```

##### 4.tempClassLoader 临时加载器，用于懒加载

```
/**
 * Specify a temporary ClassLoader to use for type matching purposes.
 * Default is none, simply using the standard bean ClassLoader.
 * <p>A temporary ClassLoader is usually just specified if
 * <i>load-time weaving</i> is involved, to make sure that actual bean
 * classes are loaded as lazily as possible. The temporary loader is
 * then removed once the BeanFactory completes its bootstrap phase.
 * @since 2.5
 */
 指定临时的类加载器用于类型匹配。默认是没有的，简单的使用标准的bean加载器。如果类加载时涉及，临时的类加载器只经常用于确保实际的类class被尽可能的懒加载。这个临时的加载器在factory完成初始化引导后，就会被移除。
void setTempClassLoader(@Nullable ClassLoader tempClassLoader);

/**
 * Return the temporary ClassLoader to use for type matching purposes,
 * if any.
 * @since 2.5
 */
@Nullable
ClassLoader getTempClassLoader();
```

##### 5.cacheBeanMetadata 缓存类定义和关联类信息的开关，关闭后每次都会重新加载获取最新的实例。

```
/**
 * Set whether to cache bean metadata such as given bean definitions
 * (in merged fashion) and resolved bean classes. Default is on.
 * <p>Turn this flag off to enable hot-refreshing of bean definition objects
 * and in particular bean classes. If this flag is off, any creation of a bean
 * instance will re-query the bean class loader for newly resolved classes.
 */
 设置是否缓存bean的元数据比如所给bean定义（以合并的方式）和关联beanclass。默认是开启的。把这个标识关闭确保热更新bean的定义对象在特殊的bean类中。如果这个标识是关闭的，如何创建bean的实例将会重新执行bean加载器以获取新的关联class。
 
void setCacheBeanMetadata(boolean cacheBeanMetadata);

/**
 * Return whether to cache bean metadata such as given bean definitions
 * (in merged fashion) and resolved bean classes.
 */
boolean isCacheBeanMetadata();
```

##### 6.BeanExpressionResolver 表达式分解器

```
/**
 * Specify the resolution strategy for expressions in bean definition values.
 * <p>There is no expression support active in a BeanFactory by default.
 * An ApplicationContext will typically set a standard expression strategy
 * here, supporting "#{...}" expressions in a Unified EL compatible style.
 * @since 3.0
 */
 为bean定义值中的表达式指定解析策略。默认的beanFactory中没有表达式的支持。一个应用的上下文通常会再次设置标准的表达式策略，支持 "#{}”表达式在一个统一兼容的格式。
 
void setBeanExpressionResolver(@Nullable BeanExpressionResolver resolver);

/**
 * Return the resolution strategy for expressions in bean definition values.
 * @since 3.0
 */
@Nullable
BeanExpressionResolver getBeanExpressionResolver();
```

##### 7.conversion 转换属性服务

```
/**
 * Specify a Spring 3.0 ConversionService to use for converting
 * property values, as an alternative to JavaBeans PropertyEditors.
 * @since 3.0
 */
void setConversionService(@Nullable ConversionService conversionService);

/**
 * Return the associated ConversionService, if any.
 * @since 3.0
 */
@Nullable
ConversionService getConversionService();
```

##### 8.propertyEditorRegistrar

```
/**
 * Add a PropertyEditorRegistrar to be applied to all bean creation processes.
 * <p>Such a registrar creates new PropertyEditor instances and registers them
 * on the given registry, fresh for each bean creation attempt. This avoids
 * the need for synchronization on custom editors; hence, it is generally
 * preferable to use this method instead of {@link #registerCustomEditor}.
 * @param registrar the PropertyEditorRegistrar to register
 */
 添加一个属性编辑注册者被应用于所有的bean创建流程。这样一个注册者创建一个新的属性编辑这实例并且在所给的注册中心注册他们。
 IO:当您需要在几种不同的情况下使用同一组属性编辑器时，这一点特别有用：编写相应的注册器并在每种情况下重用它。
 
void addPropertyEditorRegistrar(PropertyEditorRegistrar registrar);

/**
 * Register the given custom property editor for all properties of the
 * given type. To be invoked during factory configuration.
 * <p>Note that this method will register a shared custom editor instance;
 * access to that instance will be synchronized for thread-safety. It is
 * generally preferable to use {@link #addPropertyEditorRegistrar} instead
 * of this method, to avoid for the need for synchronization on custom editors.
 * @param requiredType type of the property
 * @param propertyEditorClass the {@link PropertyEditor} class to register
 */
void registerCustomEditor(Class<?> requiredType, Class<? extends PropertyEditor> propertyEditorClass);
```

##### 9.registerCustomEditor

```
/**
 * Register the given custom property editor for all properties of the
 * given type. To be invoked during factory configuration.
 * <p>Note that this method will register a shared custom editor instance;
 * access to that instance will be synchronized for thread-safety. It is
 * generally preferable to use {@link #addPropertyEditorRegistrar} instead
 * of this method, to avoid for the need for synchronization on custom editors.
 * @param requiredType type of the property
 * @param propertyEditorClass the {@link PropertyEditor} class to register
 */
 为给定类型的所有属性注册给定的自定义属性编辑器。在工厂配置期间调用。 请注意，此方法将注册共享自定义编辑器实例;为了线程安全，将同步对该实例的访问。通常最好使用{@link #addPropertyEditorRegistrar}而不是此方法，以避免在自定义编辑器上进行同步。 @param requiredType属性的类型@param propertyEditorClass要注册的{@link PropertyEditor}类
void registerCustomEditor(Class<?> requiredType, Class<? extends PropertyEditor> propertyEditorClass);
```

10.太多了 待续
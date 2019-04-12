#### DefaultListableBeanFactory

#### 二.前言概述

```
/**
 * Spring's default implementation of the {@link ConfigurableListableBeanFactory}
 * and {@link BeanDefinitionRegistry} interfaces: a full-fledged bean factory
 * based on bean definition metadata, extensible through post-processors.
 *
    ConfigurableListableBeanFactory和BeanDefinitionRegistry接口的默认实现方法：一个基于bean定义元数据的完整bean工厂，可通过后处理器扩展。
   
 * <p>Typical usage is registering all bean definitions first (possibly read
 * from a bean definition file), before accessing beans. Bean lookup by name
 * is therefore an inexpensive operation in a local bean definition table,
 * operating on pre-resolved bean definition metadata objects.
 *
   在获取bean之前，通常使用是首先注册所有的bean定义（可能从定义文件读取bean）。因此，操作预联系的bean定义原对象，在本地bean定义表中，通过名字的Bean查询是一个廉价的操作。
 
 * <p>Note that readers for specific bean definition formats are typically
 * implemented separately rather than as bean factory subclasses:
 * see for example {@link PropertiesBeanDefinitionReader} and
 * {@link org.springframework.beans.factory.xml.XmlBeanDefinitionReader}.
 *
   注意，特定bean定义格式的读取通常是单独实现的，而不是作为bean工厂子类实现的：请参见示例
 PropertiesBeanDefinitionReader
 
 * <p>For an alternative implementation of the
 * {@link org.springframework.beans.factory.ListableBeanFactory} interface,
 * have a look at {@link StaticListableBeanFactory}, which manages existing
 * bean instances rather than creating new ones based on bean definitions.
 *
   为了可替代的实现ListableBeanFactory的接口，查看StaticListableBeanFactory，其在定义上操作存在的bean实例而不是创建新的实例。
 
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Costin Leau
 * @author Chris Beams
 * @author Phillip Webb
 * @author Stephane Nicoll
 * @since 16 April 2001
 * @see #registerBeanDefinition
 * @see #addBeanPostProcessor
 * @see #getBean
 * @see #resolveDependency
 */
```

#### 二.方法罗列

##### 1.类声明

```
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
      implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable
```

继承AbstractAutowireCapableBeanFactory，实现ConfigurableListableBeanFactory，BeanDefinitionRegistry，Serializable接口。

2.




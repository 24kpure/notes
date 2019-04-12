#### HierarchicalBeanFactory  

内容简单，不分段

```
/**
 * Sub-interface implemented by bean factories that can be part
 * of a hierarchy.
 *  
 * <p>The corresponding {@code setParentBeanFactory} method for bean
 * factories that allow setting the parent in a configurable
 * fashion can be found in the ConfigurableBeanFactory interface.
 *
   子接口实现beanFactory可以成为分段的一部分。bean对应的setParentBeanFactory方法
允许在配置方法中的父类可以在ConfigurableBeanFactory被找到。
 
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 07.07.2003
 * @see org.springframework.beans.factory.config.ConfigurableBeanFactory#setParentBeanFactory
 */
public interface HierarchicalBeanFactory extends BeanFactory {

   /**
    * Return the parent bean factory, or {@code null} if there is none.
    */
   @Nullable
   BeanFactory getParentBeanFactory();

   /**
    * Return whether the local bean factory contains a bean of the given name,
    * ignoring beans defined in ancestor（祖先） contexts.
    * <p>This is an alternative to {@code containsBean}, ignoring a bean
    * of the given name from an ancestor bean factory.
    * @param name the name of the bean to query
    * @return whether a bean with the given name is defined in the local factory
    * @see BeanFactory#containsBean
    */
    返回本地工厂是否包含所给名称的bean结果，不关心在其祖先会话的定义。
    简而言之，containsBean方法找不到会去父类找，containsLocalBean只会在本类中找。
   boolean containsLocalBean(String name);

}
```


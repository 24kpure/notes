### 1.Router
#### 路由interface
#### 核心方法:
- route
```java
    <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException;
```
> 根据当前规则路由
### 2.TagRouter
#### 概述  有当前调用标签->[匹配标签>空标签]
#### 核心方法
##### route
- 无治理规则
> 当前标签不为空，调用顺序  匹配标签>无标签
> 当前标签为空 只能调用到空标签的服务
- 有治理规则
> 当前标签不为空 根据治理规则匹配标签|地址 过滤,无结果返回返回空标签服务
> 当前标签为空，过滤所有治理规则匹配标签与地址
#### notify
- 获取配置invoker变化（主要是配置中心），替换application与对应listener
### 3.ScriptRouter
#### 概述：从url获取script规则明细，编译规则。

### 4.MockInvokersSelector
#### 概述：mock使用

### 5.FileRouterFactory
#### 概述:用于加载script文件

### 6.ConditionRouter
####概述：解析=>规则，分为 whenRule,thenRule

## AbstractDirectory
### 1.实现Directory接口
dubbo文件目录

    /**
     * get service type.
     *
     * @return service type.
     */
    Class<T> getInterface();

    /**
     * list invokers.
     *
     * @return invokers
     */
    List<Invoker<T>> list(Invocation invocation) throws RpcException;

    List<Invoker<T>> getAllInvokers();

##### Directory 继承Node接口

    /**
     * get url.
     *
     * @return url.
     */
    URL getUrl();

    /**
     * is available.
     *
     * @return available.
     */
    boolean isAvailable();

    /**
     * destroy.
     */
    void destroy();

### 二.内部方法
##### 1.构造器
```
public AbstractDirectory(URL url, URL consumerUrl, RouterChain<T> routerChain) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }

        if (UrlUtils.isRegistry(url)) {
            Map<String, String> queryMap = StringUtils.parseQueryString(url.getParameterAndDecoded(REFER_KEY));
            //存疑 移除MONITOR_KEY
            this.url = url.addParameters(queryMap).removeParameter(MONITOR_KEY);
        } else {
            this.url = url;
        }

        this.consumerUrl = consumerUrl;
        setRouterChain(routerChain);
    }
}
```
##### 2.查询invokeList
    @Override
    public List<Invoker<T>> list(Invocation invocation) throws RpcException {
        if (destroyed) {
            throw new RpcException("Directory already destroyed .url: " + getUrl());
        }

        //子类实现
        return doList(invocation);
    }
### 三.子类 StaticDirectory
根据url与调用确定invoker

    @Override
    protected List<Invoker<T>> doList(Invocation invocation) throws RpcException {
        List<Invoker<T>> finalInvokers = invokers;
        if (routerChain != null) {
            try {
                finalInvokers = routerChain.route(getConsumerUrl(), invocation);
            } catch (Throwable t) {
                logger.error("Failed to execute router: " + getUrl() + ", cause: " + t.getMessage(), t);
            }
        }
        return finalInvokers == null ? Collections.emptyList() : finalInvokers;
    }
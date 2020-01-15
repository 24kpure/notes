## GovernanceRuleRepository
### 一.接口明细
##### 1.添加listener
    /**
     * Register a configuration listener for a specified key
     * The listener only works for service governance purpose, so the target group would always be the value user
     * specifies at startup or 'dubbo' by default. This method will only register listener, which means it will not
     * trigger a notification that contains the current value.
     *
     * @param key      the key to represent a configuration
     * @param group    the group where the key belongs to
     * @param listener configuration listener
     */
    void addListener(String key, String group, ConfigurationListener listener);
##### 2.移除listener
    /**
     * Stops one listener from listening to value changes in the specified key.
     *
     * @param key      the key to represent a configuration
     * @param group    the group where the key belongs to
     * @param listener configuration listener
     */
    void removeListener(String key, String group, ConfigurationListener listener);
##### 3.获取治理规则
     /**
     * Get the governance rule mapped to the given key and the given group. If the
     * rule fails to return after timeout exceeds, IllegalStateException will be thrown.
     *
     * @param key     the key to represent a configuration
     * @param group   the group where the key belongs to
     * @param timeout timeout value for fetching the target config
     * @return target configuration mapped to the given key and the given group, IllegalStateException will be thrown
     * if timeout exceeds.
     */
    String getRule(String key, String group, long timeout) throws IllegalStateException;
### 二.默认实现类
##### 1.dynamicConfiguration
```
    private DynamicConfiguration dynamicConfiguration = DynamicConfiguration.getDynamicConfiguration();
```
```
    /**
     * Find DynamicConfiguration instance
     *
     * @return DynamicConfiguration instance
     */
    static DynamicConfiguration getDynamicConfiguration() {
        Optional<DynamicConfiguration> optional = ApplicationModel.getEnvironment().getDynamicConfiguration();
        return optional.orElseGet(() -> getExtensionLoader(DynamicConfigurationFactory.class)
                .getDefaultExtension()
                .getDynamicConfiguration(null));
    }
```
##### 2.实现明细
    @Override
    public void addListener(String key, String group, ConfigurationListener listener) {
        dynamicConfiguration.addListener(key, group, listener);
    }

    @Override
    public void removeListener(String key, String group, ConfigurationListener listener) {
        dynamicConfiguration.removeListener(key, group, listener);
    }

    @Override
    public String getRule(String key, String group, long timeout) throws IllegalStateException {
        return dynamicConfiguration.getConfig(key, group, timeout);
    }
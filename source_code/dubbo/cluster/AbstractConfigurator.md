## AbstractConfigurator
### 一.实现Configurator接口
##### 1.获取配置url （待实现）
    /**
     * Get the configurator url.
     *
     * @return configurator url.
     */
    URL getUrl();
##### 2.处理配置url（待实现）
    /**
     * Configure the provider url.
     *
     * @param url - old provider url.
     * @return new provider url.
     */
    URL configure(URL url);
##### 3.compare接口，先比对IP（0.0.0.0优先级最低），再比对优先级
     /**
     * Sort by host, then by priority
     * 1. the url with a specific host ip should have higher priority than 0.0.0.0
     * 2. if two url has the same host, compare by priority value；
     */
    @Override
    default int compareTo(Configurator o) {
        if (o == null) {
            return -1;
        }

        int ipCompare = getUrl().getHost().compareTo(o.getUrl().getHost());
        // host is the same, sort by priority
        if (ipCompare == 0) {
            int i = getUrl().getParameter(PRIORITY_KEY, 0);
            int j = o.getUrl().getParameter(PRIORITY_KEY, 0);
            return Integer.compare(i, j);
        } else {
            return ipCompare;
        }
    }
##### 4.toConfigurators方法 将url转为configurators（由子类实现具体规则）
    /**
     * Convert override urls to map for use when re-refer. Send all rules every time, the urls will be reassembled and
     * calculated
     *
     * URL contract:
     * <ol>
     * <li>override://0.0.0.0/...( or override://ip:port...?anyhost=true)&para1=value1... means global rules
     * (all of the providers take effect)</li>
     * <li>override://ip:port...?anyhost=false Special rules (only for a certain provider)</li>
     * <li>override:// rule is not supported... ,needs to be calculated by registry itself</li>
     * <li>override://0.0.0.0/ without parameters means clearing the override</li>
     * </ol>
     *
     * @param urls URL list to convert
     * @return converted configurator list
     */
    static Optional<List<Configurator>> toConfigurators(List<URL> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            return Optional.empty();
        }

        ConfiguratorFactory configuratorFactory = ExtensionLoader.getExtensionLoader(ConfiguratorFactory.class)
                .getAdaptiveExtension();

        List<Configurator> configurators = new ArrayList<>(urls.size());
        for (URL url : urls) {
            if (EMPTY_PROTOCOL.equals(url.getProtocol())) {
                configurators.clear();
                break;
            }
            Map<String, String> override = new HashMap<>(url.getParameters());
            // 任意地址可能会变自动添加，他不能影响url变化的判断  此处存疑
            //The anyhost parameter of override may be added automatically, it can't change the judgement of changing url
            override.remove(ANYHOST_KEY);
            if (override.size() == 0) {
                configurators.clear();
                continue;
            }
            //子类构造方法实现
            configurators.add(configuratorFactory.getConfigurator(url));
        }
        Collections.sort(configurators);
        return Optional.of(configurators);
    }
### 二.内部方法
##### 1.构造器（默认子类下的实现）
    public AbstractConfigurator(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("configurator url == null");
        }
        this.configuratorUrl = url;
    }
##### 2.configure 实际处理url方法（存疑）
    @Override
    public URL configure(URL url) {
        // If override url is not enabled or is invalid, just return.
        if (!configuratorUrl.getParameter(ENABLED_KEY, true) || configuratorUrl.getHost() == null || url == null || url.getHost() == null) {
            return url;
        }
        /**
         * This if branch is created since 2.7.0.
         */
        String apiVersion = configuratorUrl.getParameter(CONFIG_VERSION_KEY);
        if (StringUtils.isNotEmpty(apiVersion)) {
            String currentSide = url.getParameter(SIDE_KEY);
            String configuratorSide = configuratorUrl.getParameter(SIDE_KEY);
            if (currentSide.equals(configuratorSide) && CONSUMER.equals(configuratorSide) && 0 == configuratorUrl.getPort()) {
                url = configureIfMatch(NetUtils.getLocalHost(), url);
            } else if (currentSide.equals(configuratorSide) && PROVIDER.equals(configuratorSide) && url.getPort() == configuratorUrl.getPort()) {
                url = configureIfMatch(url.getHost(), url);
            }
        }
        /**
         * This else branch is deprecated and is left only to keep compatibility with versions before 2.7.0
         */
        else {
            url = configureDeprecated(url);
        }
        return url;
    }
##### 3.doConfigure 两个子类的实现都是调用URL#addParametersIfAbsent
    public URL addParametersIfAbsent(Map<String, String> parameters) {
        if (CollectionUtils.isEmptyMap(parameters)) {
            return this;
        }
        Map<String, String> map = new HashMap<>(parameters);
        map.putAll(getParameters());
        return new URL(protocol, username, password, host, port, path, map);
    }
### 三.子类（两者大同小异）
##### 1.OverrideConfigurator
    public OverrideConfigurator(URL url) {
        super(url);
    }

    @Override
    public URL doConfigure(URL currentUrl, URL configUrl) {
        return currentUrl.addParameters(configUrl.getParameters());
    }
##### 2AbsentConfigurator

    public AbsentConfigurator(URL url) {
        super(url);
    }

    @Override
    public URL doConfigure(URL currentUrl, URL configUrl) {
        return currentUrl.addParametersIfAbsent(configUrl.getParameters());
    }
### 四.parser
##### 1.ConfiguratorConfig 依赖配置类
    public static final String SCOPE_SERVICE = "service";
    public static final String SCOPE_APPLICATION = "application";

    private String configVersion;
    private String scope;
    private String key;
    private Boolean enabled = true;
    private List<ConfigItem> configs;
##### 2.ConfigItem 配置类明细 overrider协议
    public static final String GENERAL_TYPE = "general";
    public static final String WEIGHT_TYPE = "weight";
    public static final String BALANCING_TYPE = "balancing";
    public static final String DISABLED_TYPE = "disabled";

    private String type;
    private Boolean enabled;
    private List<String> addresses;
    private List<String> providerAddresses;
    private List<String> services;
    private List<String> applications;
    private Map<String, String> parameters;
    private String side;
##### 3.ConfigParser 明细

`yaml转化`

    private static <T> T parseObject(String rawConfig) {
        Constructor constructor = new Constructor(ConfiguratorConfig.class);
        TypeDescription itemDescription = new TypeDescription(ConfiguratorConfig.class);
        itemDescription.addPropertyParameters("items", ConfigItem.class);
        constructor.addTypeDescription(itemDescription);

        Yaml yaml = new Yaml(constructor);
        return yaml.load(rawConfig);
    }

 `调整服务`
   ```
   private static List<URL> appItemToUrls(ConfigItem item, ConfiguratorConfig config) {
        List<URL> urls = new ArrayList<>();
        List<String> addresses = parseAddresses(item);
        for (String addr : addresses) {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append("override://").append(addr).append("/");
            List<String> services = item.getServices();
            if (services == null) {
                services = new ArrayList<>();
            }
            if (services.isEmpty()) {
                services.add("*");
            }
            for (String s : services) {
                urlBuilder.append(appendService(s));
                urlBuilder.append(toParameterString(item));
                
                urlBuilder.append("&application=").append(config.getKey());

                parseEnabled(item, config, urlBuilder);

                urlBuilder.append("&category=").append(APP_DYNAMIC_CONFIGURATORS_CATEGORY);
                urlBuilder.append("&configVersion=").append(config.getConfigVersion());

                urls.add(URL.valueOf(urlBuilder.toString()));
            }
        }
        return urls;
    }   
   ```



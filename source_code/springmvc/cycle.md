记录下我印象中mvc的流程

#### 一.基础类

##### 1.HttpServletBean

基础bean 基于debug结果并无处理具体请求，调用init()方法，具体实现类是 FrameworkServlet

##### 2.FrameworkServlet

initServletBean()方法

initWebApplicationContext  初始化上下文  onRefresh()  

processRequest()方法

统一请求处理类（get，post,delete等）

##### 3.DispatcherServlet

入口方法onRefresh()

其中initHandlerMappings()方法获取扫描的mappingHandle

doService()方法

是FrameworkServlet的延伸 ，设置必要的setAttribute属性  getHandler()方法中根据request获取对应的HandlerExecutionChain

#### 二.流程

1.初始化流程 HttpServerBean init()-->FrameworkServlet initServletBean()--> DispatcherServlet onRefresh()

2.请求流程  

FrameworkServlet  doGet()/doPost()/doPut等     http方法

 --> processRequest()   统一处理请求，设置requestAttributes等

 -->DispatcherServlet doService() 设置必要Attribute

-->doDispatch() 实际调用 根据结果和默认模版进行匹配页面（ModelAndView）

-->getHandler() 获取路径对应类方法（调用反射执行）


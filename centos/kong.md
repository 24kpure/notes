#### Kong安装建议使用docker方便点

##### 参考：https://docs.konghq.com/install/docker/?_ga=2.26065533.1714605421.1545788961-1376076142.1531136965

安装好postgre后把需要的目录挂载出来

docker run -d --name kong2 \
​    --network=kong-net \
​    -e "KONG_DATABASE=postgres" \
​    -e "KONG_PG_HOST=kong-database" \
​    -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
​    -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" \
​    -p 80:8000 \
​    -p 443:8443 \
​    -p 8001:8001 \
​    -p 8444:8444 \
​    kong:0.13



一般而言更改kong服务配置现在都是直接改数据库，不过用http访问会稳一点，另外那个web控制台在我这个时间点就是garage不好用  api都被取代了没有及时更新。

##### 一般服务配置流程 基于版本 0.13.0

1.添加service

路径 ip:8001/services

tip: 路径和端口注意即可 会反馈serviceId这个后面有用

```json
{
    "id": "0c61e164-6171-4837-8836-8f5298726d53",
    "created_at": 1422386534,
    "updated_at": 1422386534,
    "name": "my-service",
    "retries": 5,
    "protocol": "http",
    "host": "example.com",
    "port": 80,
    "path": "/some_api",
    "connect_timeout": 60000,
    "write_timeout": 60000,
    "read_timeout": 60000
}
```

### 

2.配置路由

路径 ip:8001/routes

tip: 一个服务可有多个路由，对应path都会匹配一个服务，此处可以配置域名或者路径作为访问识别

```
{
    "id": "173a6cee-90d1-40a7-89cf-0329eca780a6",
    "created_at": 1422386534,
    "updated_at": 1422386534,
    "name": "my-route",
    "protocols": ["http", "https"],
    "methods": ["GET", "POST"],
    "hosts": ["example.com", "foo.test"],
    "paths": ["/foo", "/bar"],
    "regex_priority": 0,
    "strip_path": true,
    "preserve_host": false,
    "service": {"id":"f5a9c0ca-bdbb-490f-8928-2ca95836239a"}
}
```

### 

3.配置流

路径 ip:8001/upstreams

tip: 路由对应一个流，可以理解服务提供来源。参数比较多，建议看贵方文档。提一些重要的，hash_on可以根据来源ip进行删了,健康检查还没有配到实际生产中。

```
{
    "id": "ce44eef5-41ed-47f6-baab-f725cecf98c7",
    "created_at": 1422386534,
    "name": "my-upstream",
    "hash_on": "none",
    "hash_fallback": "none",
    "hash_on_cookie_path": "/",
    "slots": 10000,
    "healthchecks": {
        "active": {
            "https_verify_certificate": true,
            "unhealthy": {
                "http_statuses": [429, 404, 500, 501, 502, 503, 504, 505],
                "tcp_failures": 0,
                "timeouts": 0,
                "http_failures": 0,
                "interval": 0
            },
            "http_path": "/",
            "timeout": 1,
            "healthy": {
                "http_statuses": [200, 302],
                "interval": 0,
                "successes": 0
            },
            "https_sni": "example.com",
            "concurrency": 10,
            "type": "http"
        },
        "passive": {
            "unhealthy": {
                "http_failures": 0,
                "http_statuses": [429, 500, 503],
                "tcp_failures": 0,
                "timeouts": 0
            },
            "type": "http",
            "healthy": {
                "successes": 0,
                "http_statuses": [200, 201, 202, 203, 204, 205, 206, 207, 208, 226, 300, 301, 302, 303, 304, 305, 306, 307, 308]
            }
        }
    }
}
```

### 

4.配置节点

路径 ip:8001/targets

tip: 一个流对应多个节点，可以动态修改权重

```
    "id": "ec1a1f6f-2aa4-4e58-93ff-b56368f19b27",
    "created_at": 1422386534,
    "upstream": {"id":"ba641b07-e74a-430a-ab46-94b61e5ea66b"},
    "target": "example.com:8000",
    "weight": 100
}
```

### 

5.https配置

路由先要配置好支持https

配置certificates把证书上传 

配置sni 域名与路由配置的域名一致即可










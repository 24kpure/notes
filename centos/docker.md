#### 安装docker

##### 参考：https://docs.docker.com/install/linux/docker-ce/centos/#upgrade-docker-after-using-the-convenience-script



#### 常用命令

##### 1.查看docker指定容器映射端口

docker port 2dda3f327330 5000

##### 2.端口映射

docker run -d  --name dashboard  -p8288:8288 dashboard dashboard:latest

3.拉取|删除|启动|停止

docker run|docker rm|docker start|docker stop

##### 4.查看日志

docker logs -f --tail=100 docker容器名

##### 5.查看docker版本 

docker -v

##### 6.查看docker镜像:

docker images

##### 7.删除镜像 

docker rmi -f 镜像id/或者镜像名字

##### 8.查看运行容器

docker ps

##### 9.查看运行全部容器

docker ps -a

##### 8.停止容器运行

docker stop 容器名字或者容器id

9.进入容器中

docker exec -ti 容器的名字或者容器的id bash

docker exec -it gps_node_born /bin/sh
说明:如果镜像是基于最小进项制作，将会不存在很多bash环境，即只能通过 docker exec -ti 容器的名字或者容器的id +linux命令查看容器中信息

##### 10.运行一个新的容器

docker run --name=容器的名字 -p 外部端口号:内部端口号 -v 外部路径:内部映射路径 -w 初始工作目录 -d 使用的镜像id或者名字 需要执行的指令
docker run --name=order-gps-v2 -p 6433:8080 -v /data/node/order-gps-v2/:/node -v /etc/localtime:/etc/localtime -w /node -d 872a24668c44 node server.js

##### 11.重命名容器名

docker rename old容器名  new容器名

##### 12.删除容器

docker rm  容器名或id

##### 13打包镜像，后面有点

docker build -t dashboard .

##### 14.docker查看容器日志

docker ps -a
然后 docker inspect 对应的容器id 
找到 LogPath

##### 15.更改docker目录

修改docker.service文件，使用-g参数指定存储位置

vi /usr/lib/systemd/system/docker.service  

ExecStart=/usr/bin/dockerd --graph /new-path/docker 

 // reload配置文件 

systemctl daemon-reload 

 // 重启docker 

systemctl restart docker.service

//查看 Docker Root Dir: /var/lib/docker是否改成设定的目录/new-path/docker 

docker info